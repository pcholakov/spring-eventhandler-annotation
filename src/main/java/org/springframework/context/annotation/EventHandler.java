package org.springframework.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method annotation which indicates that it is capable of handling Spring ApplicationEvents.
 * The method signature should have a single parameter whose type is inherited from ApplicationEvent.
 *
 * @author Pavel Tcholakov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
}
