package com.parkinglot.service;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.ParkingOwner;

public class ParkingLot {
    private final int parkingCapacity;
    private Object vehicle;
    private ParkingOwner parkingOwner;
    private int currentCapacity = 0;

    public ParkingLot(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    public void parkVehicle( Object vehicle ) {
        if( this.parkingCapacity == currentCapacity ) {
            parkingOwner.parkingFull();
            throw new ParkingLotException( "Parking Is Full", ParkingLotException.ExceptionType.PARKING_FULL );
        }
        this.vehicle = vehicle;
        currentCapacity++;
    }

    public boolean isParkedVehicle( Object vehicle ) {
        if(this.vehicle == vehicle)
            return true;
        throw new ParkingLotException( "Vehicle Not Parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED );
    }

    public boolean unParkedVehicle(Object vehicle) {
        if (this.vehicle == vehicle)
            return true;
        throw new ParkingLotException( "Vehicle Not UnParked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED );
    }

    public void registerOwner(ParkingOwner parkingOwner) {
        this.parkingOwner = parkingOwner;
    }
}