package com.parkinglot.test;

import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    private ParkingLot parkingLot;
    private Object vehicle;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot();
        vehicle = new Object();
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
}
