package com.parkinglot.test;

import com.parkinglot.enums.VehicleSize;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.Vehicle;
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
    private Vehicle vehicle;
    private ParkingLotSystem parkingLotSystem;
    private ParkingLot parkingLot;
    public ParkingOwner parkingOwner;
    public AirportSecurity airportSecurity;
    private List<Integer> listOfEmptySlots;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        parkingLot = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot);
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
        vehicle = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
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
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.isParkedVehicle(vehicle1);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    //TC-1.3
    @Test
    public void givenVehicleToPark_WhenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
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
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean unParked = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertTrue(unParked);
    }

    //TC-2.2
    @Test
    public void givenVehicleToUnpark_WhenNotParked_ShouldReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.unParkedVehicle( new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, e.type);
        }
    }

    //TC-2.3
    @Test
    public void givenVehicleToUnPark_WhenCapacityIs2_ShouldBeAbleToUnPark2Vehicle() {
        parkingLot.setCapacity(2);
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean unParked1 = parkingLotSystem.unParkedVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        boolean unParked2 = parkingLotSystem.unParkedVehicle(vehicle1);
        Assert.assertTrue(unParked1 && unParked2);
    }

    //UC-3
    //TC-3.1
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(parkingOwner.isParkingFull());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
    }

    //TC-3.2
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.deRegisterOwner(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
            Assert.assertFalse(parkingOwner.isParkingFull());
        }
    }

    //UC-4
    //TC-4.1
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertTrue(airportSecurity.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
    }

    //TC-4.2
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsNotObserver_ShouldInformAirportSecurity() {
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.deRegisterOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
            Assert.assertFalse(airportSecurity.isParkingFull());
        }
    }

    //TC-4.3
    @Test
    public void givenVehicle_WhenParkingFullAndAirportAndOwnerAreObserver_ShouldInformBoth() {
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
            Assert.assertTrue(parkingOwner.isParkingFull() && airportSecurity.isParkingFull());
        }
    }

    //UC-5
    //TC-5.1
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndOwnerIsObserver_ShouldInformOwner() {
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
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
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
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
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLotSystem.registerOwner(parkingOwner);
            parkingLotSystem.registerOwner(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
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
        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        try {
            parkingLot.setCapacity(5);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.findVehicle(vehicle1);
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

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle2 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle3 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);

        parkingLotSystem.unParkedVehicle(vehicle);

        Vehicle vehicle4 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, VehicleSize.SMALL);

        Object emptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(0, emptySlot);
    }

    //TC-9.4
    @Test
    public void givenMultipleVehiclesAtMultipleParkingLotsLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        parkingLot.setCapacity(3);

        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLotSystem.addLot(parkingLot1);

        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle2 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);

        parkingLotSystem.unParkedVehicle(vehicle);

        Vehicle vehicle3 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);

        Object emptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Object emptySlot1 = parkingLot1.getListOfEmptyParkingSlots().get(0);

        Assert.assertEquals(0, emptySlot);
        Assert.assertEquals(0, emptySlot1);
    }

    //UC10
    //TC-10.1
    @Test
    public void givenVehicleToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLot.setCapacity(5);

        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle2 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle3 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle3, DriverType.HANDICAP, VehicleSize.SMALL);

        parkingLotSystem.unParkedVehicle(vehicle2);
        parkingLotSystem.unParkedVehicle(vehicle);

        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);

        int locationOfParkedVehicle = parkingLot.findVehicle(vehicle2);
        Assert.assertEquals(1, locationOfParkedVehicle);
    }

    //TC-10.2
    @Test
    public void givenMultipleVehiclesAtMultipleParkingLots_WhenParkEvenly_shouldParkVehicleBasedOnDriverType() {
        parkingLot.setCapacity(3);

        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot1);

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.SMALL);

        Vehicle vehicle2 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);

        Vehicle vehicle3 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);

        int locationOfParkedVehicle = parkingLotSystem.findVehicle(vehicle);
        int locationOfParkedVehicle1 = parkingLotSystem.findVehicle(vehicle2);

        Assert.assertEquals(0,  locationOfParkedVehicle);
        Assert.assertEquals(0,  locationOfParkedVehicle1);
    }

    //UC11
    //TC-11.1
    @Test
    public void givenLargeVehicleToPark_WhenParkingLotHasSpace_ShouldParkVehicle() {
        parkingLot.setCapacity(2);

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.LARGE);

        int vehicleSpot = parkingLotSystem.findVehicle(vehicle);
        int vehicleSpot1 = parkingLotSystem.findVehicle(vehicle1);

        Assert.assertEquals(0, vehicleSpot);
        Assert.assertEquals(1, vehicleSpot1);
    }

    //TC-11.2
    @Test
    public void givenLargeVehicleToPark_WhenParkingLotHasSpaceAndDriverIsHandicap_ShouldParkVehicle() {
        parkingLot.setCapacity(10);

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.LARGE);

        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.LARGE);

        int vehicleSpot = parkingLotSystem.findVehicle(vehicle);
        int vehicleSpot1 = parkingLotSystem.findVehicle(vehicle1);

        Assert.assertEquals(0, vehicleSpot);
        Assert.assertEquals(9, vehicleSpot1);
    }

    //TC-11.3
    @Test
    public void givenMultipleVehiclesAtMultipleParkingLots_WhenParkEvenly_shouldParkVehicle() {
        parkingLot.setCapacity(3);

        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot1);

        Vehicle vehicle1 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.LARGE);

        Vehicle vehicle2 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);

        Vehicle vehicle3 =  new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.LARGE);

        int locationOfParkedVehicle = parkingLotSystem.findVehicle(vehicle);
        int locationOfParkedVehicle1 = parkingLotSystem.findVehicle(vehicle3);

        Assert.assertEquals(0,  locationOfParkedVehicle);
        Assert.assertEquals(2,  locationOfParkedVehicle1);
    }

    //UC-12
    //TC-12.1
    @Test
    public void givenWhiteCarToPark_whenParkingLotToParkVehicle_shouldReturnLocationOfAllWhiteCars() {
        parkingLot.setCapacity(2);
        List<List<Integer>> expectedWhiteCarList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        lot1.add(0);
        lot1.add(1);
        expectedWhiteCarList.add(lot1);

        Vehicle vehicle = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        List<List<Integer>> whiteVehicleColorList = parkingLotSystem.findVehicleByColor("WHITE");
        Assert.assertEquals(expectedWhiteCarList, whiteVehicleColorList);
    }

    //TC-12.2
    @Test
    public void givenWhiteCarToPark_whenMultipleParkingLotToParkVehicle_shouldReturnLocationOfAllWhiteCars() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot1);

        List<List<Integer>> expectedWhiteCarList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        List<Integer> lot2 = new ArrayList<>();
        lot1.add(0);
        lot2.add(0);
        expectedWhiteCarList.add(lot1);
        expectedWhiteCarList.add(lot2);

        Vehicle vehicle = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        List<List<Integer>> whiteVehicleColorList = parkingLotSystem.findVehicleByColor("WHITE");
        Assert.assertEquals(expectedWhiteCarList, whiteVehicleColorList);
    }

    //UC-13
    //TC-13.1
    @Test
    public void givenParkingLotSystem_WhenNotParkedBlueToyotaCar_ShouldReturnEmptyList() {
        parkingLot.setCapacity(2);
        List<List<String>> expectedBlueCarList = new ArrayList<>();
        List<String> blueCarList = new ArrayList();
        expectedBlueCarList.add(blueCarList);

        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle2 = new Vehicle("BLUE", "BMW", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);

        List<List<String>> blueVehicleColorList = parkingLotSystem.findByColorAndModel("BLUE", "TOYOTA");

        Assert.assertEquals(expectedBlueCarList, blueVehicleColorList);
    }

    //TC-13.2
    @Test
    public void givenBlueToyotaCar_WhenMultipleParkingLotToParkVehicle_ShouldReturnLocationOfAllBlueToyotaCar() {
        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot2);

        ParkingLot parkingLot3 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot3);

        List<List<String>> expectedBlueCarList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("0 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH11-9398', attender='Seema'}");
        expectedBlueCarList.add(lot1);

        List<String> lot2 = new ArrayList<>();
        lot2.add("0 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH11-9398', attender='Seema'}");
        expectedBlueCarList.add(lot2);

        List<String> lot3 = new ArrayList<>();
        lot3.add("0 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH11-9398', attender='Seema'}");
        expectedBlueCarList.add(lot3);

        Vehicle vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle2 = new Vehicle("BLUE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle3 = new Vehicle("BLUE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);

        List<List<String>> blueVehicleColorList = parkingLotSystem.findByColorAndModel("BLUE", "TOYOTA");
        Assert.assertEquals(expectedBlueCarList, blueVehicleColorList);
    }

    //UC-14
    //TC-14.1
    @Test
    public void givenBMWCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllBMWCars() {
        parkingLot.setCapacity(2);
        List<List<String>> expectedBMWCarList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("1 Vehicle{color='WHITE', model='BMW', numberPlate='MH11-9398', attender='Seema'}");
        expectedBMWCarList.add(lot1);

        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);

        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH11-9398", "Seema");
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);

        List<List<String>> vehicleByModel = parkingLotSystem.findVehicleByModel("BMW");
        Assert.assertEquals(expectedBMWCarList, vehicleByModel);
    }
}