package com.parkinglot.service;

import com.parkinglot.exception.ParkingLotException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private int parkingCapacity;
    private List< ParkingSlot > vehiclesList;
    private ParkingSlot parkingSlot;

    public ParkingLot(int parkingCapacity) {
        setCapacity(parkingCapacity);
    }

    public int setCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        initializeParkingLot();
        return vehiclesList.size();
    }

    public void initializeParkingLot() {
        this.vehiclesList = new ArrayList();
        IntStream.range(0, this.parkingCapacity)
                .forEach(slots -> vehiclesList.add(null));
    }

    public List<Integer> getListOfEmptyParkingSlots() {
        List<Integer> emptyParkingSlotList = new ArrayList<>();
        IntStream.range(0, this.parkingCapacity)
                .filter(slot -> vehiclesList.get(slot) == null)
                .forEach(emptyParkingSlotList::add);
        return emptyParkingSlotList;
    }

    public void parkVehicle(Object vehicle) {
        if ( isParkedVechicle(vehicle)) {
            throw new ParkingLotException("Vehicle Already Parked", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        parkingSlot = new ParkingSlot(vehicle);
        int emptyParkingSlot = getListOfEmptyParkingSlots().get(0);
        this.vehiclesList.set(emptyParkingSlot, parkingSlot);
    }

    public boolean isParkedVechicle(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if (vehiclesList.contains(parkingSlot))
            return true;
        return false;
    }

    public boolean unParkedVehicle(Object vehicle) {
        int slot = findVehicle(vehicle);
        vehiclesList.set(slot, null);
        return true;
    }

    public int findVehicle(Object vehicle) {
        return vehiclesList.indexOf(parkingSlot);
    }

    public boolean isParkingFull() {
        if (this.parkingCapacity == this.vehiclesList.size() && !vehiclesList.contains(null)) {
            return true;
        }
        return false;
    }
}