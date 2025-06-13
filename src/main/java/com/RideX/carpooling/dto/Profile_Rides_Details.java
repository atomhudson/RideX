package com.RideX.carpooling.dto;

import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.model.Rides;

import java.util.List;

public class Profile_Rides_Details {

    private List<Rides> ridesCreated;
    private List<Rides> ridesJoined;
    private List<RideRequest> requestMade;

    public List<Rides> getRidesCreated() {
        return ridesCreated;
    }

    public void setRidesCreated(List<Rides> ridesCreated) {
        this.ridesCreated = ridesCreated;
    }

    public List<Rides> getRidesJoined() {
        return ridesJoined;
    }

    public void setRidesJoined(List<Rides> ridesJoined) {
        this.ridesJoined = ridesJoined;
    }

    public List<RideRequest> getRequestMade() {
        return requestMade;
    }

    public void setRequestMade(List<RideRequest> requestMade) {
        this.requestMade = requestMade;
    }
}
