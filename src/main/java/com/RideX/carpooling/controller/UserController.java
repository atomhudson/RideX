package com.RideX.carpooling.controller;

import com.RideX.carpooling.dto.Car_DTO_Details;
import com.RideX.carpooling.dto.Profile_Rides_Details;
import com.RideX.carpooling.dto.User_Details;
import com.RideX.carpooling.forms.DetailForm;
import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.model.Details;
import com.RideX.carpooling.model.GovtIDVerifyRequest;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.DetailsRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private DetailServices detailServices;

    @Autowired
    private RidesServices ridesServices;

    @Autowired
    private CarDetailsServices carDetailsServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GovtIdRequestService govtIdRequestService;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CustomUUIDService customUUIDService;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    private User getCurrentUser() {
        return getCurrentLoggedInUser.getCurrentUser();
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = getCurrentUser();
        User_Details userDetails = detailServices.getUserDetails(user);

        userDetails.setUserId(user.getUserId());
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
        userDetails.setCoins(user.getCoins());
        userDetails.setJoined_Date(user.getDateCreate());

        Profile_Rides_Details profileRidesDetails = ridesServices.ridesDetails(user.getUserId());
        Car_DTO_Details carDtoDetails = carDetailsServices.findCarDetails(user.getUserId());

        double averageRating = ratingService.getAverageRating(user);
        long totalRatings = ratingService.getTotalRatings(user);

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("profileRidesDetails", profileRidesDetails);
        model.addAttribute("carDtoDetails", carDtoDetails);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("totalRatings", totalRatings);

        return "user/profile";
    }
    @GetMapping("/details")
    public String detailsUpdate(Model model) {
        if (!model.containsAttribute("userDetails")) {
            User user = getCurrentUser();
            Details existingDetails = user.getUser_details();
            DetailForm userDetails = new DetailForm();

            if (existingDetails != null) {
                userDetails.setFname(user.getfName());
                userDetails.setLname(user.getlName());
                userDetails.setBio(existingDetails.getBio());
                userDetails.setDateOfBirth(existingDetails.getDateOfBirth());
                userDetails.setAge(existingDetails.getAge());
                userDetails.setGender(existingDetails.getGender());
                userDetails.setOccupation(existingDetails.getOccupation());
                userDetails.setSpokenLanguages(existingDetails.getSpokenLanguages());
                userDetails.setCity(existingDetails.getCity());
                userDetails.setMusicPreference(existingDetails.isMusicPreference());
                userDetails.setPetsAllowed(existingDetails.isPetsAllowed());
                userDetails.setSmokingAllowed(existingDetails.isSmokingAllowed());
                userDetails.setTalkative(existingDetails.isTalkative());
            }
            model.addAttribute("userDetails", userDetails);
        }
        return "user/detailsForm";
    }
    @PostMapping("/process-details")
    public String processDetailsUpdate(@Valid @ModelAttribute("userDetails") DetailForm detailForm, BindingResult rBindingResult, HttpSession session) {
        logger.info(detailForm.toString());
        User user = getCurrentUser();
        if (rBindingResult.hasErrors()) {
            logger.info("Validation errors found, returning to form");
            session.setAttribute("message", new Message("Please correct the form errors", MessageType.red));
            return "redirect:/user/details";
        }
        Details existingDetails = user.getUser_details();
        boolean isNew = (existingDetails == null);
        Details details = isNew ? new Details() : existingDetails;
        if (isNew) {
            details.setDetails_id("UDI"+customUUIDService.getUUID());
            details.setUser(user);
        }
        details.setBio(detailForm.getBio());
        details.setDateOfBirth(detailForm.getDateOfBirth());
        details.setAge(detailForm.getAge());
        details.setGender(detailForm.getGender());
        details.setOccupation(detailForm.getOccupation());
        details.setSpokenLanguages(detailForm.getSpokenLanguages());
        details.setCity(detailForm.getCity());
        details.setMusicPreference(detailForm.isMusicPreference());
        details.setPetsAllowed(detailForm.isPetsAllowed());
        details.setSmokingAllowed(detailForm.isSmokingAllowed());
        details.setTalkative(detailForm.isTalkative());
        detailsRepository.save(details);
        if (detailForm.getUserImage() != null && !detailForm.getUserImage().isEmpty()) {
            String filename = "UDIURL"+customUUIDService.getUUID();
            String fileURL = imageService.uploadImage(detailForm.getUserImage(), filename);
            user.setProfilePic(fileURL);
            user.setCloudinaryImagePublicId(filename);
            user.setDateUpdate(new Date());
        }
        if (!Objects.equals(user.getfName(), detailForm.getFname()) || !Objects.equals(user.getlName(), detailForm.getLname())) {
            user.setfName(detailForm.getFname());
            user.setlName(detailForm.getLname());
            user.setDateUpdate(new Date());
        }
        userRepository.save(user);
        session.setAttribute("message", new Message(isNew ? "User Details Created!" : "User Details Updated!", MessageType.green));
        logger.info("User - process-details-saved");
        return "redirect:/user/details";
    }
    @RequestMapping("/verifyGovtId")
    public String verifyGovtId(Model model) {
        User user = getCurrentUser();
        if (user.getGovtIdVerifyRequest() == null){
            return "user/govtIdVerify";
        }
        model.addAttribute("userEmail", user.getEmail());
        return "user/request_submitted";
    }
    @PostMapping("/govtId/submit")
    public String handleGovtIdForm(@RequestParam("govtIdType") String idType, @RequestParam("govtIdNumber") String idNumber, @RequestParam("imageData") String imageBase64, @RequestPart("govtIdPhotoFile") MultipartFile govtIdPhotoFile)
    {
        System.out.println("ID Type: " + idType);
        System.out.println("ID Number: " + idNumber);
        System.out.println("Image Base64 Size: " + imageBase64.length());
        System.out.println("File Name: " + govtIdPhotoFile.getOriginalFilename());

        GovtIDVerifyRequest govtIDVerifyRequest = new GovtIDVerifyRequest();
        User user = getCurrentUser();

        if (!govtIdPhotoFile.isEmpty()) {
            String filename = "GIRIURL"+customUUIDService.getUUID();
            String fileURL = imageService.uploadImage(govtIdPhotoFile, filename);
            govtIDVerifyRequest.setGovtIdImagePublicId(filename);
            govtIDVerifyRequest.setGovtIdImageUrl(fileURL);
        }
        govtIDVerifyRequest.setRequestId("GIVR"+customUUIDService.getUUID());
        govtIDVerifyRequest.setUserId(user.getUserId());
        govtIDVerifyRequest.setGovtIdUniqueNumber(idNumber);
        govtIDVerifyRequest.setVerified(false);
        govtIDVerifyRequest.setGovtIdType(idType);
        logger.info("Request: {}", govtIDVerifyRequest);
        user.setGovtIdVerifyRequest(govtIDVerifyRequest.getRequestId());
        govtIdRequestService.save(govtIDVerifyRequest);
        user.setDateUpdate(new Date());
        userRepository.save(user);
        return "redirect:/user/profile";
    }

    @GetMapping("/phoneVerify")
    public String showPhoneVerifyForm() {
        return "user/phoneVerify";
    }
    @PostMapping("/phoneVerify")
    public String verifyOtp(@RequestParam("phone") String phone,
                            @RequestParam("otp") String otp,
                            HttpSession session,
                            Model model) {
        String sessionOtp = (String) session.getAttribute("otp:" + phone);
        if (sessionOtp != null && sessionOtp.equals(otp)) {
            System.out.println("OTP verified successfully for " + phone);
            session.removeAttribute("otp:" + phone);

            User user = getCurrentUser();
            if (user != null){
                user.setPhoneVerified(true);
                user.setPhone(phone);
                user.setDateUpdate(new Date());
                userRepository.save(user);

                String successMessage = """
                    Your phone number <strong>%s</strong> has been successfully verified.<br><br>
                    Thank you for verifying your mobile number with RideX. You can now enjoy a more secure and personalized experience.
                """.formatted(user.getPhone());
                emailServices.sendEmail(
                        user.getEmail(),
                        "Phone Verification Successful - RideX",
                        successMessage
                );
            }
            return "redirect:/user/profile";
        } else {
            System.out.println("OTP mismatch for " + phone);
            model.addAttribute("phone", phone);
            model.addAttribute("step", 2);
            model.addAttribute("errorMessage", "Invalid OTP. Please try again.");

            return "user/phoneVerify";
        }
    }
    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam("phone") String phone, HttpSession session, Model model) {
        String otp = generateOtp();
        session.setAttribute("otp:" + phone, otp);
        System.out.println("Sending OTP to " + phone + ": " + otp);
        User user = getCurrentUser();
        if (user != null){
            String otpMessage = """
                We received a request to verify your mobile number: <strong>%s</strong>.<br><br>
                Your One-Time Password (OTP) is: <strong style="font-size: 18px; color: #007BFF;">%s</strong><br><br>
                Please enter this OTP on the verification page to confirm your number.<br>
                <em>If you did not request this, you can safely ignore this message.</em>
            """.formatted(phone, otp);

            emailServices.sendEmail(
                    user.getEmail(),
                    "Verify Your Mobile Number",
                    otpMessage
            );
        }
        model.addAttribute("phone", phone);
        model.addAttribute("step", 2);
        return "user/phoneVerify";
    }
    private String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }
}
