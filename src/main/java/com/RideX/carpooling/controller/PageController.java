package com.RideX.carpooling.controller;

import com.RideX.carpooling.forms.UserForm;
import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.services.UserServices;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class PageController {

    private Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("isLogInPage",true);
        return "fragments/login";
    }

    @RequestMapping("/team")
    public String team() {
        return "team";
    }

    @RequestMapping("features")
    public String features() {
        return "features";
    }

    @RequestMapping("/company")
    public String roles() {
        return "company";
    }

    @RequestMapping("/privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }

    @RequestMapping("/terms-of-service")
    public String termsOfService() {
        return "terms-of-service";
    }

    @RequestMapping("/cookie-policy")
    public String cookiePolicy() {
        return "cookie-policy";
    }

    @RequestMapping("/become-driver")
    public String becomeDriver() {
        return "become-driver";
    }

    @RequestMapping("/driver-requirements")
    public String driverRequirements() {
        return "driver-requirements";
    } 

    @RequestMapping("/driver-safety")
    public String driverSafety() {
        return "driver-safety";
    }

    @RequestMapping("/driver-resources")
    public String driverResources() {
        return "driver-resources";
    }


    @RequestMapping("/signup")
    public String doRegister(Model model){
        logger.info("PageController :: signup :: url[/signup]");
        model.addAttribute("isSignUpPage",true);
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserForm());
        }
        return "fragments/signup";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
        if (rBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userForm", userForm);
            return "redirect:/signup";
        }
        User user = new User();
        user.setfName(userForm.getFirstName());
        user.setlName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setPhone(userForm.getPhone());
        user.setPassword(userForm.getPassword());
        user.setEnabled(false);
        user.setProfilePic("https://res.cloudinary.com/dgv8awzpn/image/upload/v1732823751/samples/man-portrait.jpg");
        user.setDateCreate(new Date());
        user.setDateUpdate(new Date());
        User savedUser = userServices.saveUser(user);
        System.out.println("user saved :"+savedUser);
        Message message = new Message();
        message.setContent("Registration Successful");
        message.setType(MessageType.green);
        session.setAttribute("message", message);
        return "redirect:/signup";
    }
}
