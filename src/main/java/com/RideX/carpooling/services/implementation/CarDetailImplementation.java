package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.dto.Car_DTO_Details;
import com.RideX.carpooling.model.CarDetails;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.CarDetailsRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CarDetailsServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarDetailImplementation implements CarDetailsServices {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarDetailsRepository carDetailsRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Car_DTO_Details findCarDetails(String userId) {
        Car_DTO_Details carDetailDTO = new Car_DTO_Details();
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if (userOpt.isEmpty()) {
            return new Car_DTO_Details();
        }
        User user = userOpt.get();
        Optional<CarDetails> carDetailsOpt = carDetailsRepository.findByUser(user);

        if (carDetailsOpt.isEmpty()){
            return new Car_DTO_Details();
        }
        CarDetails carDetail = carDetailsOpt.get();
        carDetailDTO.setCarMake(carDetail.getCarMake());
        carDetailDTO.setCarModel(carDetail.getCarModel());
        carDetailDTO.setYear(carDetail.getYear());
        carDetailDTO.setCarType(carDetail.getCarType());
        carDetailDTO.setColor(carDetail.getColor());
        carDetailDTO.setFuelType(carDetail.getFuelType());
        carDetailDTO.setSeat_available(carDetail.getSeat_available());
        carDetailDTO.setCarNumber(carDetail.getCarNumber());
        carDetailDTO.setAc_available(carDetail.isAc_available());
        carDetailDTO.setCar_Image_Url(carDetail.getCar_image_url().get(0));

        return carDetailDTO;
    }
}
