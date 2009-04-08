package org.springframework.context.annotation;

import org.springframework.util.Assert;

public class MockEventListener {
    boolean eventOneReceived = false;
    boolean eventTwoReceived = false;

    @EventHandler
    public void onAppEvent(EventOne event) {
        Assert.notNull(event);
        eventOneReceived = true;
    }

    @EventHandler
    public void onAppEvent(EventTwo event) {
        Assert.notNull(event);
        eventTwoReceived = true;
    }
}
