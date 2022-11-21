package com.elevator;

import lombok.SneakyThrows;

public class Application {
    private ElevatorManager handler = new ElevatorManager(10, 1);

    @SneakyThrows
    public static void main(String[] args) {
        Application app = new Application();
        app.reqDrop(1);
        app.createPickup(3);
        app.reqDrop(5);
        Thread.sleep(5500);
        app.reqDrop(2);
    }

    public void createPickup(int floor) {
        handler.addPickupRequest(floor);
    }

    public void reqDrop(int floor) {
        handler.addDropRequest(floor);
    }
}


// https://www.hackerrank.com/codepair/pjttsvsawjmfstbabbsxaobcjnqkcree/questions/1?b=eyJpbnRlcnZpZXdfaWQiOjM1OTU0OTYsInJvbGUiOiJpbnRlcnZpZXdlciIsInNob3J0X3VybCI6Imh0dHBzOi8vaHIuZ3MvYmUwYzczOCIsImNhbmRpZGF0ZV91cmwiOiJodHRwczovL2hyLmdzL2VjZjJjNzAifQ