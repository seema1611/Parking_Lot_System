package com.parkinglot.model;

public class ParkingOwner {
    private boolean parkingCapacity;

    public void parkingFull() {
        this.parkingCapacity = true;
        //System.out.println( "Parking is full" );
    }

    public boolean isParkingFull() {
        return this.parkingCapacity;
    }
}

