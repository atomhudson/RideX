package com.RideX.carpooling.controller;

import com.RideX.carpooling.dto.UserSearchForm;
import com.RideX.carpooling.dto.User_Details;
import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.model.GovtIDVerifyRequest;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.DetailServices;
import com.RideX.carpooling.services.GovtIdRequestService;
import com.RideX.carpooling.services.RidesServices;
import com.RideX.carpooling.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        System.out.println("Admin Dashboard");
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
        userDetails.setRole(user.getRoleList().get(0));
        List<GovtIDVerifyRequest> govtIdRequests = govtIdRequestService.getAllPendingRequest(false,false);
        logger.info("{}", govtIdRequests);
        logger.info("{}", govtIdRequests.size());

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("govtIdRequests", govtIdRequests);
        model.addAttribute("host",AppConstants.HOST);
        return "admin/adminDashboard";
    }

    @GetMapping("/getUsers")
    public String getAllUsers(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
            @RequestParam(value = "sortBy", defaultValue = "dateCreate") String sortBy,
            @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        Page<User> userPage = userService.getAllUsers(page, size, sortBy, direction);

        model.addAttribute("isValueEmpty",false);
        model.addAttribute("isFieldEmpty",false);
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

        // If no search is being performed (initial page load or form reset), redirect to default view
        if (userSearchForm == null || (userSearchForm.getField() == null && userSearchForm.getValue() == null)) {
            return "redirect:/admin/getUsers";
        }

        if (userSearchForm.getField() == null || userSearchForm.getField().isEmpty()) {
            model.addAttribute("isFieldEmpty", true);
            model.addAttribute("message", "Please select a field name");
            model.addAttribute("userPage", userService.getAllUsers(page, size, sortBy, direction));
            model.addAttribute("userSearchForm", new UserSearchForm());
            model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
            return "admin/users";
        }

        if (userSearchForm.getValue() == null || userSearchForm.getValue().isEmpty()) {
            model.addAttribute("isFieldEmpty", true);
            model.addAttribute("message", "Enter a value in search bar");
            model.addAttribute("userPage", userService.getAllUsers(page, size, sortBy, direction));
            model.addAttribute("userSearchForm", new UserSearchForm());
            model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
            return "admin/users";
        }

        if (size < 1) size = AppConstants.PAGE_SIZE;
        if (page < 0) page = 0;

        Page<User> pageContact;

        switch (userSearchForm.getField().toLowerCase()) {
            case "userid" -> pageContact = userService.searchByUserId(userSearchForm.getValue(), size, page, sortBy, direction);
            case "email" -> pageContact = userService.searchByEmail(userSearchForm.getValue(), size, page, sortBy, direction);
            case "phone" -> pageContact = userService.searchByPhone(userSearchForm.getValue(), size, page, sortBy, direction);
            default -> {
                model.addAttribute("isFieldEmpty", true);
                model.addAttribute("message", "Invalid search field selected");
                model.addAttribute("userPage", userService.getAllUsers(page, size, sortBy, direction));
                model.addAttribute("userSearchForm", new UserSearchForm());
                model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
                return "admin/users";
            }
        }

        if (pageContact.isEmpty()) {
            model.addAttribute("isFieldEmpty", true);
            model.addAttribute("message", "No records found for the given input");
            model.addAttribute("userPage", userService.getAllUsers(page, size, sortBy, direction));
            model.addAttribute("userSearchForm", new UserSearchForm());
            model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
            return "admin/users";
        }

        model.addAttribute("isFieldEmpty", false);
        model.addAttribute("userSearchForm", userSearchForm);
        model.addAttribute("userPage", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "admin/users";
    }

    @GetMapping("/getRides")
    public String getRides(Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                           @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                           @RequestParam(value = "direction", defaultValue = "desc") String direction,
                           @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ){
        Page<Rides> ridesPage;
        if (date != null) {
            ridesPage = ridesServices.getRidesByDate(date, page, size, sortBy, direction);
        } else {
            ridesPage = ridesServices.getAllRides(page, size, sortBy, direction);
        }
        model.addAttribute("ridePages", ridesPage);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("selectedDate", date);
        return "admin/rides";
    }

    @GetMapping("/ride/{rideId}/passengers")
    @ResponseBody
    public List<Map<String, Object>> getRidePassengers(@PathVariable String rideId) {
        Rides ride = ridesServices.getRideById(rideId);
        if (ride == null) {
            return new ArrayList<>();
        }

        return ride.getRideRequests().stream()
                .map(request -> {
                    Map<String, Object> passengerData = new HashMap<>();
                    User user = request.getUser();
                    passengerData.put("name", user.getfName() + " " + user.getlName());
                    passengerData.put("email", user.getEmail());
                    passengerData.put("phone", user.getPhone());
                    passengerData.put("userId", user.getUserId());
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
