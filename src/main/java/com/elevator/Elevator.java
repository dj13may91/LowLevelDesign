package com.elevator;

import com.elevator.button.DropButton;
import com.elevator.button.DropHandler;
import com.elevator.button.FloorManager;
import com.elevator.constant.Direction;
import com.elevator.constant.ElevatorState;
import lombok.SneakyThrows;

import java.util.*;

public class Elevator implements IElevator, DropHandler {
    private final Set<DropButton> floorButtons = new HashSet<>();
    private final FloorManager floorManager = FloorManager.getInstance();
    private final String name;
    private int currFloor;
    private Direction currDir = Direction.UP;
    private ElevatorState state = ElevatorState.Idle;
    private final List<Integer> dropRequests = new ArrayList<>();

    int reqCount = 0;

    public Elevator(String name) {
        this.name = name;
        Collections.fill(dropRequests, 0);
        new Thread(this::completeDrop).start();
    }


    @Override
    public void addAccess(DropButton accessibleFloor) {
        floorButtons.add(accessibleFloor);
        dropRequests.add(accessibleFloor.getFloor() - 1, 0);
    }

    @Override
    public void denyAccess(DropButton restrictedFloor) {
    }

    @SneakyThrows
    public void completeDrop() {
        while (true) {
            boolean isIdle = false;
            while (reqCount == 0) {
                state = ElevatorState.Idle;
                if (!isIdle)
                    System.out.println("Elevator is waiting at floor " + currFloor);
                isIdle = true;
                Thread.sleep(1000);
            }
            int up = getNextFloor(Direction.UP);
            int down = getNextFloor(Direction.DOWN);
            if (Direction.UP.equals(currDir)) {
                if (up > 0) {
                    // c= 3, up =5
                    for (int i = currFloor; i <= up; i++) {
                        System.out.println("Elevator currently at " + i);
                        Thread.sleep(1000);
                    }
                    currFloor = up;
                    System.out.println("Elevator waiting(drop) at " + currFloor + " <D> " + currDir);
                    Thread.sleep(5000);
                    updateElevatorState(up);
                } else if (down > 0) {
                    currDir = Direction.DOWN;
                }
            }
            if (Direction.DOWN.equals(currDir)) {
                if (down > 0) {
                    for (int i = currFloor; i >= down; i--) {
                        System.out.println("Elevator currently at " + i);
                        checkPickUp();
                        Thread.sleep(1000);
                    }
                    currFloor = down;
                    // print at this floor

                    System.out.println("Dropping at " + currFloor + " <D> " + currDir);
                    Thread.sleep(5000);
                    reqCount = reqCount - dropRequests.get(down);
                    state = ElevatorState.MovingDown;
                    dropRequests.set(down, 0);
                } else if (up > 0) {
                    currDir = Direction.UP;
//                    currFloor = up;
                    state = ElevatorState.MovingUp;
                }
            }
        }
    }

    private void updateElevatorState(int up) {
        state = ElevatorState.MovingUp;
        reqCount = reqCount - dropRequests.get(up);
        dropRequests.set(up, 0);
    }

    private void checkPickUp() {
        if (floorManager.doPickup(currFloor)) {
            dropRequest(currFloor);
            System.out.println("Picked up in <E> " + name + " for floor " + currFloor);
        }
    }

    private int getNextFloor(Direction dir) {
        int res = currFloor; // next = index + 1
        while (res < dropRequests.size() && res >= 0) {
            if (dropRequests.get(res) > 0) {
                return res;
            }
            res = res + dir.move;
        }
        return -1;
    }

    @Override
    public void dropRequest(int to) {
        if (reqCount == 0) {
            if (currFloor < to) {
                currDir = Direction.UP;
            } else {
                currDir = Direction.DOWN;
            }
            System.out.println("Elevator started moving " + currDir);
        }
        reqCount++;
        dropRequests.set(to, dropRequests.get(to) + 1);
        System.out.println("Drop req ADDED for  <E> " + name + " to " + to);
    }

    @Override
    public void cancelDrop(int to) {
        if (dropRequests.get(to) > 0) {
            reqCount--;
            dropRequests.set(to, dropRequests.get(to) - 1);
            System.out.println("Drop req cancelled for <E> " + name + " to " + to);
        }
    }
}
