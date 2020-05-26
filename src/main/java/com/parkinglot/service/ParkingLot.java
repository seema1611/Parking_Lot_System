package com.parkinglot.service;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.observer.InformObserver;
import com.parkinglot.observer.ParkingLotRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private int parkingCapacity;
    private List vehicleList;
    InformObserver informObserver;

    public ParkingLot(int parkingCapacity) {
        this.informObserver = new InformObserver();
        setCapacity(parkingCapacity);
    }

    public int setCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        initializeParkingLot();
        return vehicleList.size();
    }

    public void initializeParkingLot() {
        this.vehicleList = new ArrayList();
        IntStream.iterate( 0, i ->i).limit(parkingCapacity)
                .forEach(slots -> vehicleList.add(null));
    }

    public List<Integer> getListOfEmptyParkingSlots() {
        List<Integer> emptySlotList = new ArrayList<>();
        IntStream.range(0, this.parkingCapacity)
                .filter(slot -> vehicleList.get(slot) == null)
                .forEach(emptySlotList::add);
        //System.out.println(emptySlotList);
        return emptySlotList;
    }

    public void parkVehicle(Object vehicle) {
        if (this.parkingCapacity == this.vehicleList.size() && !vehicleList.contains(null)) {
            throw new ParkingLotException("Parking Is Full", ParkingLotException.ExceptionType.PARKING_FULL);
        }
        if (this.vehicleList.contains(vehicle)) {
            throw new ParkingLotException("Vehicle Already Parked", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        List slotNumber = getListOfEmptyParkingSlots();
        this.vehicleList.set((Integer) slotNumber.get(0), vehicle);
        //System.out.println("Slot: " +slotNumber+ " Name: " +vehicle);
        if ( !vehicleList.contains(null)) {
            informObserver.parkingFull();
        }
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

    public int findVehicle(Object vehicle) {
        if(vehicleList.contains(vehicle)) {
            System.out.println(vehicleList.indexOf(vehicle));
            return vehicleList.indexOf(vehicle);
        }
        throw new ParkingLotException("Vehicle Is Not Available", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public void registerOwner( ParkingLotRegister register) {
        informObserver.registerParkingLotObserver( register );
    }

    public void deRegisterOwner( ParkingLotRegister register ) {
        informObserver.deRegisterParkingLotObserver( register );
    }
}