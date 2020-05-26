package com.parkinglot.register;

import com.parkinglot.observer.ParkingLotRegister;

public class ParkingOwner implements ParkingLotRegister {
    private boolean parkingCapacity;

    @Override
    public void parkingFull( boolean parkingCapacity ) {
        this.parkingCapacity = parkingCapacity;
        isParkingFull();
    }

    public boolean isParkingFull() {
        return this.parkingCapacity;
    }
}

