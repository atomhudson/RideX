package com.RideX.carpooling.services;


import java.util.*;

public interface RouteService {

    public List<String> getDistanceAndDuration(String fromLatStr, String fromLonStr, String toLatStr, String toLonStr);
}
