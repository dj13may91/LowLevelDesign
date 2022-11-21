package com.elevator;

import com.elevator.button.DropButton;
import com.elevator.button.FloorManager;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {
    private FloorManager floorManager = FloorManager.getInstance();

    private final List<Elevator> elevatorList = new ArrayList<>();
    private final int totalFloors;
    private final int totalElevators;

    public ElevatorManager(int totalFloors, int totalElevators) {
        this.totalFloors = totalFloors;
        this.totalElevators = totalElevators;
        installElevators();
    }

    public void addPickupRequest(int from){
        floorManager.pickup(from);
    }

    public void addDropRequest(int to){
        elevatorList.get(0).dropRequest(to);
    }

    public void cancelDrop(int to){
        elevatorList.get(0).cancelDrop(to);
    }

    public void cancelPickup(int from){
        floorManager.cancelPickup(from);
    }

    private void installElevators() {
        for (int i = 0; i < totalElevators; i++) {
            Elevator ele = new Elevator("E-" + (i + 1));
            for (int j = 0; j < totalFloors; j++)
                ele.addAccess(new DropButton(j + 1));
            elevatorList.add(ele);
        }
    }
}
