package com.elevator.button;


import lombok.Getter;

public class DropButton implements RequestButton {

    @Getter  private int floor;

    public DropButton(int floor) {
        this.floor = floor;
    }

    @Override
    public void press() {
        System.out.println("Request to drop at floor " + floor);
    }
}
