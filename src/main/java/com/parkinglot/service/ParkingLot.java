package com.parkinglot.service;

import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.VehicleSize;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.ParkingSlot;
import com.parkinglot.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    private int parkingCapacity;
    private List<ParkingSlot> vehiclesList;
    private ParkingSlot parkingSlot;
    private int emptyParkingSlot;

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

    public void parkVehicle(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        if (isParkedVechicle(vehicle)) {
            throw new ParkingLotException("Vehicle Already Parked", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        emptyParkingSlot = getEmptyParkingSlotListBasedOnDriverType(driverType);
        parkingSlot = new ParkingSlot(vehicle, emptyParkingSlot);
        this.vehiclesList.set(emptyParkingSlot, parkingSlot);
    }

    private Integer getEmptyParkingSlotListBasedOnDriverType(DriverType driverType) {
        List<Integer> emptySlots = getListOfEmptyParkingSlots().stream()
                .sorted(driverType.order)
                .collect(Collectors.toList());
        return emptySlots.get(0);
    }
    
    public boolean isParkedVechicle(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle, emptyParkingSlot);
        if (vehiclesList.contains(parkingSlot))
            return true;
        return false;
    }

    public boolean unParkedVehicle(Vehicle vehicle) {
        int slot = findVehicle(vehicle);
        vehiclesList.set(slot, null);
        return true;
    }

    public int findVehicle(Object vehicle) {
        return vehiclesList.indexOf(parkingSlot);
    }

    public List<Integer> findByColor(String color) {
        List<Integer> vehicleListByColor = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equals(color))
                .map(parkingSlot -> parkingSlot.getLocation())
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<String> findByColorAndModel(String color, String model) {
        List<String> vehicleListByColorAndModel = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equals(color))
                .filter(parkingSlot -> parkingSlot.getVehicle().getModel().equals(model))
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByColorAndModel;
    }

    public boolean isParkingFull() {
        if (this.parkingCapacity == this.vehiclesList.size() && !vehiclesList.contains(null)) {
            return true;
        }
        return false;
    }
}