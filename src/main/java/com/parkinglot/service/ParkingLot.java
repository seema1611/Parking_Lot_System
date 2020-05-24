package com.parkinglot.service;

import com.parkinglot.exception.ParkingLotException;

public class ParkingLot {

    private Object vehicle;

    public void parkVehicle( Object vehicle ) {
        this.vehicle = vehicle;
    }

    public boolean isParkedVehicle( Object vehicle ) {
        if(this.vehicle == vehicle)
            return true;
        throw new ParkingLotException( "Vehicle Not Parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED );
    }

    public boolean unParkedVehicle(Object vehicle) {
        if (this.vehicle == vehicle)
            return true;
        return false;
    }
}