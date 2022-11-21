package com.elevator.button;

import java.util.HashSet;
import java.util.Set;

public class FloorManager implements PickupHandler {

    private final Set<Integer> pickUpReq = new HashSet<>();

    private static FloorManager floorManager = new FloorManager();

    private FloorManager() {
    }

    public static FloorManager getInstance() {
        return floorManager;
    }

    @Override
    public void pickup(int to) {
        pickUpReq.add(to);
    }

    @Override
    public void cancelPickup(int to) {
        if (pickUpReq.contains(to)) {
            pickUpReq.remove(to);
            System.out.println("Cancelled pickup from " + to);
        }
    }

    public boolean doPickup(int from) {
        return pickUpReq.remove(from);
    }
}
