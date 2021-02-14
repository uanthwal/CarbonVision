package com.dal.carbonfootprint.dashboard;

import java.util.Date;

/**
 * Model to store user travel details
 */
public class Travel {

    Date travelDate;
    int distance;
    String destination;
    String source;
    String userid;

    /**
     * Default constructor
     */
    public Travel() {
    }

    /**
     * Parameterized constructor
     * @param travelDate
     * @param distance
     * @param destination
     * @param source
     * @param userid
     */
    public Travel(Date travelDate, int distance, String destination, String source, String userid) {
        this.travelDate = travelDate;
        this.distance = distance;
        this.destination = destination;
        this.source = source;
        this.userid = userid;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
