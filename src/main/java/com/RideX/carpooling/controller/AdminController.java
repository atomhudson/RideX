package com.RideX.carpooling.controller;

import com.RideX.carpooling.dto.UserSearchForm;
import com.RideX.carpooling.dto.User_Details;
import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.model.GovtIDVerifyRequest;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RideRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.DetailServices;
import com.RideX.carpooling.services.GovtIdRequestService;
import com.RideX.carpooling.services.RidesServices;
import com.RideX.carpooling.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserServices userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RidesServices ridesServices;

    @Autowired
    private DetailServices detailServices;

    @Autowired
    private GovtIdRequestService govtIdRequestService;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    private User getCurrentUser() {
        return getCurrentLoggedInUser.getCurrentUser();
    }

    @RequestMapping(value = "/dashboard")
    public String adminDashboard(Model model) {
        User user = getCurrentUser();
        User_Details userDetails = detailServices.getUserDetails(user);

        userDetails.setName(user.getfName() + " " + user.getlName());
        userDetails.setProfileUrl(user.getProfilePic());
        userDetails.setGovtIdType(user.getGovtIdType());
        userDetails.setEmail(user.getEmail());
        userDetails.setPhone(user.getPhone());
        userDetails.setEmailVerified(user.isEmailVerified());
        userDetails.setPhoneVerified(user.isPhoneVerified());
        userDetails.setGovtIdVerified(user.isGovtIdVerified());
        userDetails.setVerify_Request(user.getGovtIdVerifyRequest());
        userDetails.setRole(user.getRoleList() != null && !user.getRoleList().isEmpty() ? user.getRoleList().get(0) : "ROLE_ADMIN");
        
        List<GovtIDVerifyRequest> govtIdRequests = govtIdRequestService.getAllPendingRequest(false, false);

        // Platform KPI metrics
        List<User> allUsers = userRepository.findAll();
        long usersCount = allUsers.size();
        long verifiedUser = allUsers.stream().filter(User::isGovtIdVerified).count();
        long nonVerifiedUser = usersCount - verifiedUser;
        long enabledUsers = allUsers.stream().filter(User::isEnabled).count();
        long nonEnabledUsers = usersCount - enabledUsers;
        long driversCount = allUsers.stream().filter(u -> u.getCarDetails() != null).count();

        List<Rides> allRides = rideRepository.findAll();
        long totalRides = allRides.size();
        long activeRides = allRides.stream()
                .filter(r -> "PLANNED".equalsIgnoreCase(r.getRideStatus()) || "ONGOING".equalsIgnoreCase(r.getRideStatus()))
                .count();
        long completedRides = allRides.stream()
                .filter(r -> "COMPLETED".equalsIgnoreCase(r.getRideStatus()))
                .count();

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("govtIdRequests", govtIdRequests);
        model.addAttribute("host", AppConstants.HOST);

        model.addAttribute("usersCount", usersCount);
        model.addAttribute("verifiedUser", verifiedUser);
        model.addAttribute("nonVerifiedUser", nonVerifiedUser);
        model.addAttribute("enabledUsers", enabledUsers);
        model.addAttribute("nonEnabledUsers", nonEnabledUsers);
        model.addAttribute("driversCount", driversCount);
        model.addAttribute("totalRides", totalRides);
        model.addAttribute("activeRides", activeRides);
        model.addAttribute("completedRides", completedRides);

        return "admin/adminDashboard";
    }

    @GetMapping("/getUsers")
    public String getAllUsers(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "dateCreate") String sortBy,
            @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        Page<User> userPage = userService.getAllUsers(page, size, sortBy, direction);

        populateUserDirectoryStats(model);

        model.addAttribute("isValueEmpty", false);
        model.addAttribute("isFieldEmpty", false);
        model.addAttribute("userPage", userPage);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("userSearchForm", new UserSearchForm());
        return "admin/users";
    }

    @RequestMapping("/getUsers/search")
    public String getUsersBySearch(@ModelAttribute UserSearchForm userSearchForm, Model model,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
                                   @RequestParam(value = "sortBy", defaultValue = "dateCreate") String sortBy,
                                   @RequestParam(value = "direction", defaultValue = "desc") String direction) {

        populateUserDirectoryStats(model);

        if (userSearchForm == null || userSearchForm.getValue() == null || userSearchForm.getValue().trim().isEmpty()) {
            return "redirect:/admin/getUsers";
        }

        String searchVal = userSearchForm.getValue().trim();
        String field = userSearchForm.getField() != null ? userSearchForm.getField().trim().toLowerCase() : "";

        if (size < 1) size = AppConstants.PAGE_SIZE;
        if (page < 0) page = 0;

        Page<User> pageContact;

        if (field.isEmpty() || "all".equals(field)) {
            // Universal Search across User ID, Full Name, Email, Phone
            pageContact = userService.searchUsersUniversal(searchVal, page, size, sortBy, direction);
        } else {
            switch (field) {
                case "userid" -> pageContact = userService.searchByUserId(searchVal, page, size, sortBy, direction);
                case "email" -> pageContact = userService.searchByEmail(searchVal, page, size, sortBy, direction);
                case "phone" -> pageContact = userService.searchByPhone(searchVal, page, size, sortBy, direction);
                default -> pageContact = userService.searchUsersUniversal(searchVal, page, size, sortBy, direction);
            }
        }

        if (pageContact.isEmpty()) {
            model.addAttribute("isFieldEmpty", true);
            model.addAttribute("message", "No user records found matching '" + searchVal + "'");
            model.addAttribute("userPage", userService.getAllUsers(page, size, sortBy, direction));
            model.addAttribute("userSearchForm", userSearchForm);
            model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
            return "admin/users";
        }

        model.addAttribute("isFieldEmpty", false);
        model.addAttribute("userSearchForm", userSearchForm);
        model.addAttribute("userPage", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "admin/users";
    }

    private void populateUserDirectoryStats(Model model) {
        List<User> all = userRepository.findAll();
        long total = all.size();
        long drivers = all.stream().filter(u -> u.getCarDetails() != null).count();
        long verified = all.stream().filter(User::isGovtIdVerified).count();
        long suspended = all.stream().filter(u -> !u.isEnabled()).count();

        model.addAttribute("statTotalUsers", total);
        model.addAttribute("statDrivers", drivers);
        model.addAttribute("statVerified", verified);
        model.addAttribute("statSuspended", suspended);
    }

    @GetMapping("/getRides")
    public String getRides(Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
                           @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                           @RequestParam(value = "direction", defaultValue = "desc") String direction,
                           @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        Page<Rides> ridesPage;
        if (date != null) {
            ridesPage = ridesServices.getRidesByDate(date, page, size, sortBy, direction);
        } else {
            ridesPage = ridesServices.getAllRides(page, size, sortBy, direction);
        }
        
        List<Rides> allRides = rideRepository.findAll();
        long plannedCount = allRides.stream().filter(r -> "PLANNED".equalsIgnoreCase(r.getRideStatus())).count();
        long ongoingCount = allRides.stream().filter(r -> "ONGOING".equalsIgnoreCase(r.getRideStatus())).count();
        long completedCount = allRides.stream().filter(r -> "COMPLETED".equalsIgnoreCase(r.getRideStatus())).count();
        long cancelledCount = allRides.stream().filter(r -> "CANCELLED".equalsIgnoreCase(r.getRideStatus())).count();

        model.addAttribute("statPlanned", plannedCount);
        model.addAttribute("statOngoing", ongoingCount);
        model.addAttribute("statCompleted", completedCount);
        model.addAttribute("statCancelled", cancelledCount);

        model.addAttribute("ridePages", ridesPage);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("selectedDate", date);
        return "admin/rides";
    }

    @GetMapping("/ride/{rideId}/passengers")
    @ResponseBody
    public List<Map<String, Object>> getRidePassengers(@PathVariable String rideId) {
        Rides ride = ridesServices.getRideById(rideId);
        if (ride == null || ride.getRideRequests() == null) {
            return new ArrayList<>();
        }

        return ride.getRideRequests().stream()
                .map(request -> {
                    Map<String, Object> passengerData = new HashMap<>();
                    User user = request.getUser();
                    if (user != null) {
                        passengerData.put("name", user.getfName() + " " + user.getlName());
                        passengerData.put("email", user.getEmail());
                        passengerData.put("phone", user.getPhone());
                        passengerData.put("userId", user.getUserId());
                    } else {
                        passengerData.put("name", "Unknown Passenger");
                        passengerData.put("email", "—");
                        passengerData.put("phone", "—");
                        passengerData.put("userId", "—");
                    }
                    passengerData.put("seats", request.getRequestedSeats());
                    passengerData.put("requestTime", request.getRequestedAt());
                    passengerData.put("coins", request.getUsedCoins());
                    passengerData.put("price", request.getFinalPrice());
                    passengerData.put("isCoDriver", request.isCoDriver());
                    passengerData.put("status", request.getRequestStatus());
                    return passengerData;
                })
                .collect(Collectors.toList());
    }
}
