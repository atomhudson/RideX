package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.RatingService;
import com.RideX.carpooling.services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    private User getCurrentUser() {
        return getCurrentLoggedInUser.getCurrentUser();
    }

    @GetMapping("/ratingForm/{ratedId}")
    public String ratingForm(@PathVariable("ratedId") String id, Model model) {
        model.addAttribute("ratedId", id);
        return "user/ratingForm";
    }

    @PostMapping("/ratings/submit")
    public String submitRating(@RequestParam("ratedId") String ratedId,
                               @RequestParam("hs-ratings-readonly") int stars,
                               @RequestParam("review") String message,
                               HttpSession session) {

        System.out.println("Rating");
        System.out.println("ratedId: "+ratedId);
        System.out.println("Stars: " + stars);
        System.out.println("Message: " + message);
        User rater = getCurrentUser();
        User rated = userServices.getUserById(ratedId);

        Rating rating = new Rating();
        rating.setRater(rater);
        rating.setRated(rated);
        rating.setStars(stars);
        rating.setMessage(message);
        ratingService.saveRating(rating);

        System.out.println("rating submitted successfully");

        return "redirect:/user/profile";
    }
    @GetMapping("/profile/ratings/{id}")
    public String showProfile(@PathVariable("id") String id, Model model) {
        User user = userServices.getUserById(id);
        List<Rating> ratings = ratingService.getRatingsForUser(user);
        Map<Integer, Long> starCounts = ratingService.getStarCounts(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        List<String> formattedDates = ratings.stream()
                .map(rating -> rating.getTimestamp().format(formatter))
                .toList();
        long totalRatings = ratings.size();

        System.out.println(starCounts);
        System.out.println(ratings);
        System.out.println(formattedDates);
        System.out.println(totalRatings);
        
        model.addAttribute("user", user);
        model.addAttribute("ratings", ratings);
        model.addAttribute("starCounts", starCounts);
        model.addAttribute("formattedDates", formattedDates);
        model.addAttribute("totalRatings", totalRatings);
        return "user/rated";
    }
}
