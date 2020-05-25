package com.parkinglot.observer;

import java.util.ArrayList;
import java.util.List;

public class InformObserver {
    public List< ParkingLotRegister > observerList;
    private boolean parkingCapacity;

    public InformObserver() {
        this.observerList = new ArrayList<>();
    }

    public void registerParkingLotObserver( ParkingLotRegister parkingLotRegister ) {
        this.observerList.add( parkingLotRegister );
    }

    public void deRegisterParkingLotObserver( ParkingLotRegister parkingLotRegister ) {
        this.observerList.remove( parkingLotRegister );
    }

    public void parkingFull() {
        this.parkingCapacity = true;
        for( ParkingLotRegister parkingLotRegister : observerList )
            parkingLotRegister.parkingFull( this.parkingCapacity );
    }

    public void parkingAvailable() {
        this.parkingCapacity = false;
        for( ParkingLotRegister parkingLotRegister : observerList )
            parkingLotRegister.parkingFull( this.parkingCapacity );
    }
}
