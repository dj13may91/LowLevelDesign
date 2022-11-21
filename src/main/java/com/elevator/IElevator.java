package com.elevator;


import com.elevator.button.DropButton;

public interface IElevator {
    void addAccess(DropButton accessibleFloor);

    void denyAccess(DropButton restrictedFloor);
}
