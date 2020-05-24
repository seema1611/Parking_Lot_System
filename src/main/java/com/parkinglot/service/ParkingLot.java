package com.parkinglot.service;

public class ParkingLot {

    private Object vehicle;

    public void parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isParkedVehicle(Object vehicle) {
        if(this.vehicle == vehicle)
            return true;
        return false;
    }
}