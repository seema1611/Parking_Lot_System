package com.parkinglot.model;

import com.parkinglot.observer.ParkingLotRegister;

public class ParkingOwner implements ParkingLotRegister {
    private boolean parkingCapacity;

    public boolean isParkingFull() {
        return this.parkingCapacity;
    }

    @Override
    public void parkingFull( boolean parkingCapacity ) {
        this.parkingCapacity = parkingCapacity;
    }
}
