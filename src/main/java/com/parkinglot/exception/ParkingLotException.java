package com.parkinglot.exception;

public class ParkingLotException extends RuntimeException {

    public enum ExceptionType {
        VEHICLE_NOT_PARKED, VEHICLE_NOT_UNPARKED, PARKING_FULL, VEHICLE_ALREADY_PARKED, VEHICLE_NOT_FOUND
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super( message );
        this.type = type;
    }
}