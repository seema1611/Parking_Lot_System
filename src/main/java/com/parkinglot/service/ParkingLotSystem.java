package com.parkinglot.service;

import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.VehicleSize;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.Vehicle;
import com.parkinglot.observer.InformObserver;
import com.parkinglot.observer.ParkingLotRegister;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    private List<ParkingLot> parkingLotList;
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
        if (this.parkingLotList.contains(parkingLot)) {
            return true;
        }
        return false;
    }

    public void parkVehicle(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        parkingLot = maxSpaceInWhichParkingLot();
        if (parkingLot.isParkingFull()) {
            throw new ParkingLotException("Parking Is Full", ParkingLotException.ExceptionType.PARKING_FULL);
        }
        parkingLot.parkVehicle(vehicle, driverType, vehicleSize);
        if (parkingLot.isParkingFull()) {
            informObserver.parkingFull();
        }
    }

    private ParkingLot maxSpaceInWhichParkingLot() {
        return parkingLotList.stream()
                .sorted(Comparator.comparing(list -> list.getListOfEmptyParkingSlots().size(), Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    public boolean isParkedVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isParkedVechicle(vehicle))
                return true;
        }
        throw new ParkingLotException("Vehicle Is Not Parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    public boolean unParkedVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isParkedVechicle(vehicle)) {
                parkingLot.unParkedVehicle(vehicle);
                informObserver.parkingAvailable();
                return true;
            }
        }
        throw new ParkingLotException("Vehicle Is Not Available", ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED);
    }


    public int findVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLotList)
            if (parkingLot.isParkedVechicle(vehicle))
                return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("Vehicle Is Not Available", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public List<List<Integer>> findVehicleByColor(String color) {
        List<List<Integer>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingSlot -> parkingLot.findByColor(color))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<List<String>> findByColorAndModel(String color, String model) {
        List<List<String>> vehicleListByColorAndModel = new ArrayList<>();
        for (ParkingLot lot : this.parkingLotList) {
            List<String> byColorAndModel = lot.findByColorAndModel(color, model);
            vehicleListByColorAndModel.add(byColorAndModel);
        }
        return vehicleListByColorAndModel;
    }

    public List<List<String>> findVehicleByModel(String model) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByModel(model))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<List<String>> findVehicleByTime(int parkedTime) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByTime(parkedTime))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public void registerOwner( ParkingLotRegister register) {
        informObserver.registerParkingLotObserver( register );
    }

    public void deRegisterOwner( ParkingLotRegister register ) {
        informObserver.deRegisterParkingLotObserver( register );
    }
}
