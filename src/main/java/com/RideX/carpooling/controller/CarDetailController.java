package com.RideX.carpooling.controller;

import com.RideX.carpooling.forms.CarForm;
import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.model.CarDetails;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.CarDetailsRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import com.RideX.carpooling.services.ImageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/user/car")
public class CarDetailController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CustomUUIDService customUUIDService;

    @Autowired
    private CarDetailsRepository carRepository;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    private User getCurrentUser() {
        return getCurrentLoggedInUser.getCurrentUser();
    }

    @GetMapping("/register")
    public String register(Model model) {
        logger.info("CarDetailController :: register :: url [/user/car/register]");
        User user = getCurrentUser();
        if (!model.containsAttribute("car")){
            CarDetails existingCarDetails = user.getCarDetails();
            CarForm car = new CarForm();
            if (existingCarDetails != null){
                car.setCarMake(existingCarDetails.getCarMake());
                car.setCarModel(existingCarDetails.getCarModel());
                car.setYear(existingCarDetails.getYear());
                car.setCarType(existingCarDetails.getCarType());
                car.setColor(existingCarDetails.getColor());
                car.setFuelType(existingCarDetails.getFuelType());
                car.setSeat_available(existingCarDetails.getSeat_available());
                car.setAc_available(existingCarDetails.isAc_available());
            }
            model.addAttribute("car", car);
        }
        return "user/carRegistration";
    }
    @PostMapping("/register-process")
    public String processCarRegister(@Valid @ModelAttribute("car") CarForm carForm, BindingResult rBindingResult, HttpSession session){
        logger.info("CarDetailController :: processCarRegister :: url [/user/car/register-process]");
        logger.info("Car-register Form Processing");
        logger.info(carForm.toString());
        User user = getCurrentUser();
        if (rBindingResult.hasErrors()) {
            logger.info("Validation errors found, returning to form");
            session.setAttribute("message", new Message("Please correct the form errors", MessageType.red));
            return "user/carRegistration";
        }
        CarDetails carDetails = user.getCarDetails();
        boolean isNew = (carDetails == null);
        CarDetails details = isNew ? new CarDetails() : carDetails;
        if (isNew){
            details.setCar_id("CRD"+customUUIDService.getUUID());
            details.setUser(user);
            String carNumber = carForm.getCarNumber();
            Optional<CarDetails> isUniqueCarNumber = carRepository.findByCarNumber(carNumber);
            logger.info("car number is {}",carNumber);
            logger.info("isUniqueCarNumber is {}",isUniqueCarNumber);
            if (isUniqueCarNumber.isPresent()) {
                session.setAttribute("message", new Message("Car number already exists", MessageType.red));
                return "redirect:/user/car/register";
            }
            details.setCarNumber(carNumber);
        }
        details.setCarMake(carForm.getCarMake());
        details.setCarModel(carForm.getCarModel());
        details.setYear(carForm.getYear());
        details.setCarType(carForm.getCarType());
        details.setColor(carForm.getColor());
        details.setFuelType(carForm.getFuelType());
        details.setSeat_available(carForm.getSeat_available());
        details.setAc_available(carForm.isAc_available());
        if (carForm.getCarImages() != null && !carForm.getCarImages().isEmpty()) {
            List<String> car_image_urls = new ArrayList<>();
            List<String> cloudinaryPublicId = new ArrayList<>();
            for (MultipartFile file : carForm.getCarImages()) {
                String filename = "CDIURL"+customUUIDService.getUUID();
                String fileURL = imageService.uploadImage(file, filename);
                if (fileURL != null) {
                    car_image_urls.add(fileURL);
                    cloudinaryPublicId.add(filename);
                }
            }
            details.setCar_image_url(car_image_urls);
            details.setCloudinaryImagePublicId(cloudinaryPublicId);
            user.setDateUpdate(new Date());
        }
        carRepository.save(details);
        userRepository.save(user);
        session.setAttribute("message", new Message(isNew ? "Car Details Created!" : "Car Details Updated", MessageType.green));
        logger.info("Car - register-update-saved");
        return "redirect:/user/car/register";
    }

}
