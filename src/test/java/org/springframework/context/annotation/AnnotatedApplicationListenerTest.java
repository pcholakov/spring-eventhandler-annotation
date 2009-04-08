package org.springframework.context.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AnnotatedApplicationListenerTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MockEventListener eventListener;

    @Test
    public void testEventDispatch() {
        assertFalse(eventListener.eventOneReceived);
        assertFalse(eventListener.eventTwoReceived);

        applicationContext.publishEvent(new EventOne(this));
        assertTrue(eventListener.eventOneReceived);
        assertFalse(eventListener.eventTwoReceived);

        applicationContext.publishEvent(new EventTwo(this));
        assertTrue(eventListener.eventOneReceived);
        assertTrue(eventListener.eventTwoReceived);
    }

}

class EventOne extends ApplicationEvent {
    public EventOne(Object source) {
        super(source);
    }
}

class EventTwo extends ApplicationEvent {
    public EventTwo(Object source) {
        super(source);
    }
}
