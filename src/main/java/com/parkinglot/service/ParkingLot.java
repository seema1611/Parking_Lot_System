package com.parkinglot.service;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.ParkingOwner;
import com.parkinglot.observer.InformObserver;
import com.parkinglot.observer.ParkingLotRegister;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int parkingCapacity;
    private List vehicleList;
    private ParkingOwner parkingOwner;
    private int currentCapacity = 0;
    InformObserver informObserver;

    public ParkingLot( int parkingCapacity ) {
        this.parkingCapacity = parkingCapacity;
        this.vehicleList = new ArrayList();
        this.informObserver = new InformObserver();
    }

    public void parkVehicle( Object vehicle ) {
        if( this.parkingCapacity == currentCapacity ) {
            informObserver.parkingFull();
            throw new ParkingLotException( "Parking Is Full", ParkingLotException.ExceptionType.PARKING_FULL );
        }
        this.vehicleList.add( vehicle );
        currentCapacity++;
    }

    public boolean isParkedVehicle( Object vehicle ) {
        if( this.vehicleList.contains(vehicle) )
            return true;
        throw new ParkingLotException( "Vehicle Not Parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED );
    }

    public boolean unParkedVehicle( Object vehicle ) {
        if (this.vehicleList.contains(vehicle)) {
            this.vehicleList.remove(vehicle);
            informObserver.parkingAvailable();
            return true;
        }
        throw new ParkingLotException( "Vehicle Not UnParked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED );
    }

    public void registerOwner( ParkingLotRegister register) {
        informObserver.registerParkingLotObserver( register );
    }

    public void deRegisterOwner( ParkingLotRegister register ) {
        informObserver.deRegisterParkingLotObserver( register );
    }
}