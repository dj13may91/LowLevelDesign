package com.elevator.button;

public class EmergencyButton implements RequestButton{

    @Override
    public void press() {
        System.out.println("Emer alert");
    }
}
