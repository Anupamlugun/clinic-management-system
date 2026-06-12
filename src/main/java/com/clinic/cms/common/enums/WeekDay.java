package com.clinic.cms.common.enums;

import lombok.Getter;

@Getter
public enum WeekDay {

    MONDAY("Monday", 1),
    TUESDAY("Tuesday", 2),
    WEDNESDAY("Wednesday", 3),
    THURSDAY("Thursday", 4),
    FRIDAY("Friday", 5),
    SATURDAY("Saturday", 6),
    SUNDAY("Sunday", 7);

    private final String name;
    private final int value;

    WeekDay(String name, int value) {
        this.name = name;
        this.value = value;
    }
}