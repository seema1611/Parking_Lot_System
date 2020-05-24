package com.parkinglot.test;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.AirportSecurity;
import com.parkinglot.model.ParkingOwner;
import com.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    private ParkingLot parkingLot;
    private Object vehicle;
    private ParkingOwner parkingOwner;
    AirportSecurity airportSecurity;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2 );
        vehicle = new Object();
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
    }

    //UC-1
    //TC-1.1
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLot.parkVehicle( vehicle );
        boolean isParked = parkingLot.isParkedVehicle( vehicle );
        Assert.assertTrue( isParked );
    }

    //TC-1.2
    @Test
    public void givenVehicle_WhenNotParked_ShouldReturnException() {
        try {
            parkingLot.isParkedVehicle( vehicle );
        } catch(ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    //UC-2
    //TC-2.1
    @Test
    public void givenVehicleToUnPark_WhenUnParked_ShouldReturnTrue() {
        parkingLot.parkVehicle( vehicle );
        boolean unParked = parkingLot.unParkedVehicle( vehicle );
        Assert.assertTrue( unParked );
    }

    //TC-2.2
    @Test
    public void givenVehicle_WhenNotUnParked_ShouldReturnException() {
        try {
            parkingLot.parkVehicle( vehicle );
            parkingLot.unParkedVehicle( new Object() );
        } catch(ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, e.type);
        }
    }

    //UC-3
    //TC-3.1
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch (ParkingLotException e) {
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
    }

    //TC-3.2
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.deRegisterOwner( parkingOwner );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
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
            parkingLot.registerOwner( airportSecurity );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch (ParkingLotException e) {
            Assert.assertTrue(airportSecurity.isParkingFull());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_FULL, e.type);
        }
    }
}
