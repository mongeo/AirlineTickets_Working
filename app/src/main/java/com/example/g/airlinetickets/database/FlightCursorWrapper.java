package com.example.g.airlinetickets.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.g.airlinetickets.Flight;

import java.util.UUID;

public class FlightCursorWrapper extends CursorWrapper {
    public FlightCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Flight getFlightItem(){
        String uuidString = getString(getColumnIndex(FlightSchema.FlightTable.Cols.UUID));
        String flightNum = getString(getColumnIndex(FlightSchema.FlightTable.Cols.FLIGHT));
        String departure = getString(getColumnIndex(FlightSchema.FlightTable.Cols.DEPARTURE));
        String arrival = getString(getColumnIndex(FlightSchema.FlightTable.Cols.ARRIVAL));
        String departureTime = getString(getColumnIndex(FlightSchema.FlightTable.Cols.DEPARTURE_TIME));
        int seats = getInt(getColumnIndex(FlightSchema.FlightTable.Cols.SEATS));
        double price = getDouble(getColumnIndex(FlightSchema.FlightTable.Cols.PRICE));
        Flight flight = new Flight(UUID.fromString(uuidString),flightNum,departure,arrival,departureTime,seats,price);
        return flight;
    }
}


/*
    private UUID flightID;
    private String flightNum;
    private String departure;
    private String arrival;
    private String departureTime;
    private int seats;
    private double price;
 */