package com.garage.garage.controller;

import com.garage.garage.model.*;
import com.garage.garage.repository.UserRepository;
import com.garage.garage.service.*;
import com.garage.garage.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DayRosterService dayRosterService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private PartService partService;

    @GetMapping("")
    public String adminPage() {
        // Return the view name for the admin page
        return "admin-menu";
    }

    @GetMapping("/roster")
    public String rosterPage(Model model) {

        Map<Integer, String> staffMap = staffService.getStaffNamesMap();
        List<DayRoster> dayRosters = dayRosterService.getAllDayRosters();

        model.addAttribute("staffMap", staffMap);
        model.addAttribute("dayRosters", dayRosters);
        return "roster-read";
    }

    @GetMapping("/roster/update/{dayOfWeek}")
    public String showRosterUpdateForm(@PathVariable("dayOfWeek") DayOfWeek dayOfWeek, Model model) {

        Map<Integer, String> staffMap = staffService.getStaffNamesMap();
        DayRoster dayRoster = dayRosterService.getDayRosterByDayOfWeek(dayOfWeek);

        model.addAttribute("staffMap", staffMap);
        model.addAttribute("dayRoster", dayRoster);
        return "roster-update";
    }

    @PostMapping("/roster/update")
    public String updateRoster(@ModelAttribute DayRoster dayRoster) {

        dayRosterService.updateDayRoster(dayRoster);
        return "redirect:/admin/roster";
    }

    @GetMapping("/schedule")
    public String showScheduleSelect(Model model) {
        List<DaySchedule> days = bookingService.get30Days();
        model.addAttribute("daySchedules", days);
        return "schedule-select";
    }

    @GetMapping("/schedule/view/{date}")
    public String showScheduleView(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {

        DaySchedule daySchedule = bookingService.getDay(date);
        Map<Integer, String> staffMap = staffService.getStaffNamesMap();

        List<TimeSlot> timeSlots = daySchedule.getTimeSlots();
        List<Booking> dayBookings = new ArrayList<>();

        for (TimeSlot timeslot : timeSlots) {
            for (Booking booking : timeslot.getBookings()) {
                dayBookings.add(booking);
            }
        }

        if(dayBookings.isEmpty()) {
            return "schedule-empty";
        }

        Map<Integer, String> usersMap = userServiceImpl.getUserNamesMap();
        Map<Long, String> vehiclesMap = vehicleService.getVehicleLicensesMap();
        Map<Long, String> servicesMap = serviceService.getServiceNamesMap();

        model.addAttribute("usersMap", usersMap);
        model.addAttribute("vehiclesMap", vehiclesMap);
        model.addAttribute("servicesMap", servicesMap);
        model.addAttribute("staffMap", staffMap);
        model.addAttribute("dayBookings", dayBookings);
        return "schedule-view";
    }

    @GetMapping("/schedule/edit/{date}/{id}")
    public String showScheduleEdit(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable("id") Long bookingId, Model model) {

        Booking booking = bookingService.getBookingById(bookingId);
        Service service = serviceService.getServiceById(booking.getService());
        Map<Integer, StaffServiceCount> staffServiceCountMap = getStaffWithCountMap(booking.getDatein());

        model.addAttribute("serviceTime", service.getTimeSlots());
        model.addAttribute("booking", booking);
        model.addAttribute("staffMap", staffServiceCountMap);
        return "schedule-edit";
    }

    @PostMapping("/schedule/edit")
    public String updateSchedule(@ModelAttribute Booking booking) {

        bookingService.updateBooking(booking);
        return "redirect:/admin/schedule/view/" + booking.getDatein();
    }

    @GetMapping("/create-invoice")
    public String showCreateInvoice(Model model) {

        List<User> users = userRepository.findAll();
        List<Service> services = serviceService.getAllServices();
        List<Accessory> accessories = accessoryService.getAllAccessories();
        List<Part> parts = partService.getAllParts();
        model.addAttribute("users", users);
        model.addAttribute("services", services);
        model.addAttribute("accessories", accessories);
        model.addAttribute("parts", parts);
        model.addAttribute("invoice", new Invoice());
        return "invoice-form";
    }

    @PostMapping("/create-invoice")
    public String createInvoice(@ModelAttribute("invoice") Invoice invoice, RedirectAttributes redirectAttributes) {

        // Store the invoice object in the flash scope
        redirectAttributes.addFlashAttribute("invoice", invoice);

        return "redirect:/admin/print-invoice";
    }

    @GetMapping("/print-invoice")
    public String showPrintInvoice(Model model) {

        // Retrieve the invoice object from the flash scope
        Invoice invoice = (Invoice) model.getAttribute("invoice");

        String date = String.valueOf(LocalDate.now());
        String customer = invoice.getUser().getFirstName() + " " + invoice.getUser().getLastName();
        String mobile = invoice.getUser().getMobile();
        String license = invoice.getLicense();
        String make = invoice.getMake();

        List<String> services = new ArrayList<>();
        List<String> servicePrices = new ArrayList<>();
        for (Service service: invoice.getServices()) {
            services.add(service.getService() + ":   €" + String.valueOf(service.getPrice()));
            servicePrices.add(String.valueOf(service.getPrice()));
        }

        List<String> accessories = new ArrayList<>();
        List<String> accessoryPrices = new ArrayList<>();
        for (Accessory accessory: invoice.getAccessories()) {
            accessories.add(accessory.getItem() + ":   €" + String.valueOf(accessory.getPrice()));
            accessoryPrices.add(String.valueOf(accessory.getPrice()));
        }

        List<String> parts = new ArrayList<>();
        List<String> partPrices = new ArrayList<>();
        for (Part part: invoice.getParts()) {
            parts.add(part.getPart() + ":   €" + String.valueOf(part.getPrice()));
            partPrices.add(String.valueOf(part.getPrice()));
        }

        String total = String.valueOf(invoice.calcTotal());

        model.addAttribute("date", date);
        model.addAttribute("customer", customer);
        model.addAttribute("mobile", mobile);
        model.addAttribute("license", license);
        model.addAttribute("make", make);
        model.addAttribute("services", services);
        model.addAttribute("accessories", accessories);
        model.addAttribute("parts", parts);
        model.addAttribute("total", total);
        return "invoice-print";
    }

    // Get only staff of the date
    public Map<Integer, StaffServiceCount> getStaffWithCountMap(LocalDate date) {
        Map<Integer, StaffServiceCount> staffServiceCountMap = new HashMap<>();
        DaySchedule daySchedule = bookingService.getDay(date);
        DayRoster dayRoster = dayRosterService.getDayRosterByDayOfWeek(date.getDayOfWeek());

        Integer id1 = dayRoster.getStaffId1();
        Integer id2 = dayRoster.getStaffId2();
        Integer id3 = dayRoster.getStaffId3();
        Integer id4 = dayRoster.getStaffId4();
        staffServiceCountMap.put(id1, new StaffServiceCount(staffService.getStaffById(id1)));
        staffServiceCountMap.put(id2, new StaffServiceCount(staffService.getStaffById(id2)));
        staffServiceCountMap.put(id3, new StaffServiceCount(staffService.getStaffById(id3)));
        staffServiceCountMap.put(id4, new StaffServiceCount(staffService.getStaffById(id4)));

        List<TimeSlot> timeSlots = daySchedule.getTimeSlots();
        for (TimeSlot timeSlot : timeSlots) {
            List<Booking> bookings = timeSlot.getBookings();
            for (Booking booking : bookings) {
                Integer staffId = booking.getStaff();
                Service service = serviceService.getServiceById(booking.getService());
                Integer serviceCountToAdd = service.getTimeslots();
                if(staffServiceCountMap.containsKey(staffId)) {
                    staffServiceCountMap.get(staffId).addServiceCount(serviceCountToAdd);
                }
            }
        }

        return staffServiceCountMap;
    }


}
