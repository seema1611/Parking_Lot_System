package com.parkinglot.test;

import com.parkinglot.enums.VehicleSize;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.register.AirportSecurity;
import com.parkinglot.register.ParkingOwner;
import com.parkinglot.enums.DriverType;
import com.parkinglot.service.ParkingLot;
import com.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ParkingLotTest {
    private Object vehicle;
    private ParkingLotSystem parkingLotSystem;
    private ParkingLot parkingLot;
    public ParkingOwner parkingOwner;
    public AirportSecurity airportSecurity;
    private List<Integer> listOfEmptySlots;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        vehicle = new Object();
        parkingLot = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot);
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
    }

    //UC1
    //TC-1.1
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean isParked = parkingLotSystem.isParkedVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    //TC-1.2
    @Test
    public void givenVehicleToPark_WhenNotParked_ShouldReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.isParkedVehicle(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    //TC-1.3
    @Test
    public void givenVehicleToPark_WhenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        Object vehicle1 = new Object();
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean isParked1 = parkingLotSystem.isParkedVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        boolean isParked2 = parkingLotSystem.isParkedVehicle(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }

    //TC-1.4
    @Test
    public void givenVehicleToPark_WhenSameVehicleAlreadyParked_ShouldThrowException() {
        parkingLot.setCapacity(1);
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);
        }
    }

    //UC2
    //TC-1.2
    @Test
    public void givenVehicleToUnPark_WhenUnParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL,  VehicleSize.SMALL);
        boolean unParked = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertTrue(unParked);
    }

    //TC-2.2
    @Test
    public void givenVehicleToUnpark_WhenNotParked_ShouldReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.unParkedVehicle(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, e.type);
        }
    }

    //TC-2.3
    @Test
    public void givenVehicleToUnPark_WhenCapacityIs2_ShouldBeAbleToUnPark2Vehicle() {
        parkingLot.setCapacity(2);
        Object vehicle1 = new Object();
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean unParked1 = parkingLotSystem.unParkedVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL,  VehicleSize.SMALL);
        boolean unParked2 = parkingLotSystem.unParkedVehicle(vehicle1);
        Assert.assertTrue(unParked1 && unParked2);
    }

    //UC-3
    //TC-3.1
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(parkingOwner.isParkingFull());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
    }

    //TC-3.2
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.deRegisterOwner(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
            Assert.assertFalse(parkingOwner.isParkingFull());
        }
    }

    //UC-4
    //TC-4.1
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        try {
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertTrue(airportSecurity.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
    }

    //TC-4.2
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsNotObserver_ShouldInformAirportSecurity() {
        try {
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.deRegisterOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
            Assert.assertFalse(airportSecurity.isParkingFull());
        }
    }

    //TC-4.3
    @Test
    public void givenVehicle_WhenParkingFullAndAirportAndOwnerAreObserver_ShouldInformBoth() {
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
            Assert.assertTrue(parkingOwner.isParkingFull() && airportSecurity.isParkingFull());
        }
    }

    //UC-5
    //TC-5.1
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndOwnerIsObserver_ShouldInformOwner() {
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
        parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull());
    }

    //TC-5.2
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndAirportIsObserver_ShouldInformAirport() {
        try {
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertTrue(airportSecurity.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
        parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertFalse(airportSecurity.isParkingFull());
    }

    //TC-5.3
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndAirportAndOwnerAreObserver_ShouldInformBoth() {
        Object vehicle1 = new Object();
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertTrue(parkingOwner.isParkingFull() && airportSecurity.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
        parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull() && airportSecurity.isParkingFull());
    }

    //UC-6
    //TC-6.1
    @Test
    public void givenParkingLotSystem_WhenParkingCapacityIsSet_ShouldReturnParkingCapacity() {
        int parkingLotCapacity = parkingLot.setCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }

    //TC-6.2
    @Test
    public void givenParkingLotSystem_WhenListOfEmptySlotsCalled_ShouldReturnAvailableSlots() {
        List<Integer> expectedList = new ArrayList();
        expectedList.add(0);
        parkingLot.setCapacity(2);
        listOfEmptySlots = parkingLot.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        listOfEmptySlots = parkingLot.getListOfEmptyParkingSlots();
        Assert.assertEquals(expectedList, listOfEmptySlots);
    }

    //UC-7
    //TC-7.1
    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        int slotNumber = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(4, slotNumber);
    }

    //TC-7.2
    @Test
    public void givenParkingLotSystem_WhenVehicleNotFound_ShouldThrowException() {
        try {
            parkingLot.setCapacity(5);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.findVehicle(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

     /*//UC-8
    //TC-8.1
    @Test
    public void givenParkingLot_WhenVehicleParkedRecodTime_ShouldInformChargesToCustomer() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkVehicle( vehicle );
        parkingLotSystem.isParkedVehicle( vehicle );
        parkingLotSystem.unParkedVehicle( vehicle );
        int chargesPerHour = parkingLotSystem.getTime();
        Assert.assertEquals(20,chargesPerHour);
    }*/

    //UC9
    //TC-9.1
    @Test
    public void givenParkingLotSystem_WhenAddedLots_ShouldReturnTrue() {
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot);
        Assert.assertTrue(isLotAdded);
    }

    //9.2
    @Test
    public void givenParkingLotSystem_WhenNotAddedLots_ShouldReturnFalse() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertFalse(isLotAdded);
    }

    //TC-9.3
    @Test
    public void givenMultipleVehiclesLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.unParkedVehicle(vehicle);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        Object lastEmptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(0, lastEmptySlot);
    }

    //TC-9.4
    @Test
    public void givenMultipleVehiclesAtMultipleParkingLotsLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.unParkedVehicle(vehicle);             //Remove Lot1Spot1
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        Object lastEmptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Object lastEmptySlot2 = parkingLot2.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(0, lastEmptySlot);
        Assert.assertEquals(0, lastEmptySlot2);
    }

    //UC10
    //TC-10.1
    @Test
    public void givenVehicleToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLot.setCapacity(5);
        Object vehicle2 = new Object();
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.unParkedVehicle(vehicle2);
        parkingLotSystem.unParkedVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        int vehicleParkedLocation = parkingLot.findVehicle(vehicle2);
        Assert.assertEquals(1, vehicleParkedLocation);
    }

    //TC-10.2
    @Test
    public void givenMultipleVehiclesAtMultipleParkingLots_WhenParkEvenly_shouldParkVehicleBasedOnDriverType() {
        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot2);
        parkingLot.setCapacity(3);
        Object vehicle2 = new Object();
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(new Object(), DriverType.NORMAL, VehicleSize.SMALL);
        int vehicleParkedLocation = parkingLotSystem.findVehicle(vehicle);
        int vehicleParkedLocation1 = parkingLotSystem.findVehicle(vehicle2);
        Assert.assertEquals(0, vehicleParkedLocation);
        Assert.assertEquals(0, vehicleParkedLocation1);
    }

    //UC11
    //TC-11.1
    @Test
    public void givenLargeVehicleToPark_WhenInParkingLotSpace_ShouldParkVehicle() {
        parkingLot.setCapacity(2);
        Object vehicle2 = new Object();
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.LARGE);
        int spot = parkingLotSystem.findVehicle(vehicle);
        int spot1 = parkingLotSystem.findVehicle(vehicle2);
        Assert.assertEquals(0, spot);
        Assert.assertEquals(1, spot1);
    }

    //TC-11.2
    @Test
    public void givenLargeVehicleToPark_WhenParkingLotHasSpaceAndDriverIsHandicap_ShouldParkVehicle() {
        parkingLot.setCapacity(5);
        Object vehicle2 = new Object();
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.LARGE);
        int spot = parkingLotSystem.findVehicle(vehicle);
        int spot1 = parkingLotSystem.findVehicle(vehicle2);
        Assert.assertEquals(0, spot);
        Assert.assertEquals(4, spot1);
    }
}

