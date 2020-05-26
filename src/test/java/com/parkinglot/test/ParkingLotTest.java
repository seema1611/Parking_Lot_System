package com.parkinglot.test;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.register.AirportSecurity;
import com.parkinglot.register.ParkingOwner;
import com.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {
    private Object vehicle;
    private ParkingLot parkingLot;
    private List<Integer> listOfEmptySlots;
    public ParkingOwner parkingOwner;
    public AirportSecurity airportSecurity;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot( 2 );
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
        vehicle = new Object();;
        listOfEmptySlots = parkingLot.getListOfEmptyParkingSlots();
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
            parkingLot.parkVehicle( vehicle );
            parkingLot.isParkedVehicle( new Object() );
        } catch(ParkingLotException e) {
            Assert.assertEquals( ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type );
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
        } catch( ParkingLotException e ) {
            Assert.assertEquals( ParkingLotException.ExceptionType.VEHICLE_NOT_UNPARKED, e.type );
        }
    }

    //UC-3
    //TC-3.1
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch (ParkingLotException e) {
            Assert.assertTrue( parkingOwner.isParkingFull() );
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
    }

    //TC-3.2
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.deRegisterOwner( parkingOwner );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
    }

    //UC-4
    //TC-4.1
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        try {
            parkingLot.registerOwner( airportSecurity );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            Assert.assertTrue( airportSecurity.isParkingFull() );
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
    }

    //TC-4.2
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsNotObserver_ShouldInformAirportSecurity() {
        try {
            parkingLot.registerOwner( airportSecurity );
            parkingLot.deRegisterOwner( airportSecurity );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
    }

    //TC-4.3
    @Test
    public void givenVehicle_WhenParkingFullAndAirportAndOwnerAreObserver_ShouldInformBoth() {
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.registerOwner( airportSecurity );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
            Assert.assertTrue( parkingOwner.isParkingFull() && airportSecurity.isParkingFull());
        }
    }

    //UC-5
    //TC-5.1
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndOwnerIsObserver_ShouldInformOwner() {
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            Assert.assertTrue( parkingOwner.isParkingFull() );
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
        parkingLot.unParkedVehicle( vehicle );
        Assert.assertFalse( parkingOwner.isParkingFull() );
    }

    //TC-5.2
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndAirportIsObserver_ShouldInformAirport() {
        try {
            parkingLot.registerOwner( airportSecurity );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            System.out.println(e);
            Assert.assertTrue( airportSecurity.isParkingFull() );
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
        parkingLot.unParkedVehicle( vehicle );
        Assert.assertFalse( airportSecurity.isParkingFull() );
    }

    //TC-5.3
    @Test
    public void givenVehicle_WhenParkingIsAvailableAndAirportAndOwnerAreObserver_ShouldInformBoth() {
        Object vehicle1 = new Object();
        try {
            parkingLot.registerOwner( parkingOwner );
            parkingLot.registerOwner( airportSecurity );
            parkingLot.parkVehicle( vehicle );
            parkingLot.parkVehicle( vehicle1 );
            parkingLot.parkVehicle( new Object() );
        } catch ( ParkingLotException e ) {
            Assert.assertTrue( parkingOwner.isParkingFull() && airportSecurity.isParkingFull() );
            Assert.assertEquals( ParkingLotException.ExceptionType.PARKING_FULL, e.type );
        }
        parkingLot.unParkedVehicle( vehicle );
        Assert.assertFalse( parkingOwner.isParkingFull() && airportSecurity.isParkingFull() );
    }

    //UC-6
    //TC-6.1
    @Test
    public void givenParkingLotSystem_WhenParkingCapacityIsSet_ShouldReturnParkingCapacity() {
        int parkingLotCapacity = parkingLot.setCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }
}
