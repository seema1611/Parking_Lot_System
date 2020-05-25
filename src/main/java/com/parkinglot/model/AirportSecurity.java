package com.parkinglot.model;

import com.parkinglot.observer.ParkingLotRegister;

public class AirportSecurity implements ParkingLotRegister {
    private boolean parkingCapacity;

    @Override
    public void parkingFull( boolean parkingCapacity ) {
        this.parkingCapacity = parkingCapacity;
    }

    public boolean isParkingFull() {
        return this.parkingCapacity;
    }
}