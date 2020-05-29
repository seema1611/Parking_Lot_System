package com.parkinglot.service;

import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.VehicleSize;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.observer.InformObserver;
import com.parkinglot.observer.ParkingLotRegister;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    private List< ParkingLot > parkingLotList;
    private InformObserver informObserver;
    private ParkingLot parkingLot;

    public ParkingLotSystem() {
        this.informObserver = new InformObserver();
        this.parkingLotList = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLot parkingLot) {
        if(this.parkingLotList.contains(parkingLot)){
            return true;
        }
        return false;
    }

    public void parkVehicle(Object vehicle, DriverType driverType, VehicleSize vehicleSize) {
        parkingLot = maxSpaceInWhichParkingLot();
        if ( parkingLot.isParkingFull() ) {
            throw new ParkingLotException("Parking Is Full", ParkingLotException.ExceptionType.PARKING_FULL);
        }
        parkingLot.parkVehicle(vehicle, driverType, vehicleSize);
        if ( parkingLot.isParkingFull() ) {
            informObserver.parkingFull();
        }
    }

    private ParkingLot maxSpaceInWhichParkingLot() {
        return parkingLotList.stream()
                .sorted(Comparator.comparing(list -> list.getListOfEmptyParkingSlots().size(), Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    public boolean isParkedVehicle(Object vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isParkedVechicle(vehicle))
                return true;
        }
        throw new ParkingLotException("Vehicle Is Not Parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    public boolean unParkedVehicle(Object vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isParkedVechicle(vehicle)) {
                parkingLot.unParkedVehicle(vehicle);
                informObserver.parkingAvailable();
                return true;
            }
        }
        throw new ParkingLotException("Vehicle Not UnParked", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED);
    }


    public int findVehicle(Object vehicle) {
        for (ParkingLot parkingLot : parkingLotList)
            if (parkingLot.isParkedVechicle(vehicle))
                return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("Vehicle is not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public void registerOwner( ParkingLotRegister register) {
        informObserver.registerParkingLotObserver( register );
    }

    public void deRegisterOwner( ParkingLotRegister register ) {
        informObserver.deRegisterParkingLotObserver( register );
    }
}
