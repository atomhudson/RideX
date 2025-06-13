package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.dto.User_Details;
import com.RideX.carpooling.model.Details;
import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.DetailsRepository;
import com.RideX.carpooling.repositories.RatingRepository;
import com.RideX.carpooling.services.DetailServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailServicesImplementation implements DetailServices {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public User_Details getUserDetails(User user) {
        logger.info("DetailServicesImplementation :: getUserDetails(String userId: ({})",user.toString());

        Optional<Details> detailsOpt = detailsRepository.findByUser(user);

        if (detailsOpt.isEmpty()) {
            return new User_Details();
        }
        Details details = detailsOpt.get();
        User_Details userDetails = new User_Details();

        userDetails.setBio(details.getBio());
        userDetails.setAge(details.getAge());
        userDetails.setJoined_Date(user.getDateCreate());
        userDetails.setGender(details.getGender());
        userDetails.setOccupation(details.getOccupation());
        userDetails.setSpokenLanguages(details.getSpokenLanguages());
        userDetails.setCity(details.getCity());
        userDetails.setMusicPreference(details.isMusicPreference());
        userDetails.setPetsAllowed(details.isPetsAllowed());
        userDetails.setSmokingAllowed(details.isSmokingAllowed());
        userDetails.setTalkative(details.isTalkative());

        return userDetails;
    }

}
