package com.elevator.button;


import com.elevator.constant.Direction;

public class PickupButton implements RequestButton {
    private int floor;
    private Direction destination;

    public PickupButton(int floor, Direction destination) {
        this.floor = floor;
        this.destination = destination;
        this.press();
    }

    @Override
    public void press() {

    }
}
