package com.ratelimiter;

import com.ratelimiter.requests.Request;
import com.ratelimiter.user.User;
import com.ratelimiter.user.UserManager;
import com.ratelimiter.user.UserPlan;

public class Service {
    private final UserManager userManager = new UserManager();

    public static void main(String[] args) {
        Service service = new Service();

        User basic = service.userManager.registerUser(UserPlan.BASIC);
        User gold = service.userManager.registerUser(UserPlan.GOLD);
        User diamond = service.userManager.registerUser(UserPlan.DIAMOND);

        new Thread(() -> {
            for (int i = 0; i < 7; i++) {
                try {
                    service.userManager.makeRequest(new Request("Req-basic-" + i, basic.getUserId()));
                    service.userManager.makeRequest(new Request("Req-gold-" + i, gold.getUserId()));
                    service.userManager.makeRequest(new Request("Req-diamond-" + i, diamond.getUserId()));
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}
