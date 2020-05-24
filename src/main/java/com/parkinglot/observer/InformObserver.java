package com.parkinglot.observer;

import java.util.ArrayList;
import java.util.List;

public class InformObserver {
    public List< ParkingLotRegister > list;
    private boolean parkingCapacity;

    public InformObserver() {
        this.list = new ArrayList<>();
    }

    public void registerParkingLotObserver( ParkingLotRegister parkingLotRegister ) {
        this.list.add( parkingLotRegister );
    }

    public void deRegisterParkingLotObserver(ParkingLotRegister parkingLotRegister) {
        this.list.remove( parkingLotRegister );
    }

    public void parkingFull() {
        this.parkingCapacity = true;
        isParkingFull();
    }

    public void isParkingFull() {
        for( ParkingLotRegister parkingLotRegister : list)
            parkingLotRegister.parkingFull(this.parkingCapacity);
    }
}
