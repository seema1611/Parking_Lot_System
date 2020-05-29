package com.parkinglot.service;

import java.util.Comparator;

public enum DriverType {
    NORMAL(Comparator.reverseOrder()), HANDICAP(Comparator.naturalOrder());

    public Comparator<Integer> order;

    DriverType(Comparator<Integer> order) {
        this.order = order;
    }
}
