package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.services.CustomUUIDService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUUIDImplementation implements CustomUUIDService {
    @Override
    public String getUUID() {
        String uuidPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
        return uuidPart;
    }
}
