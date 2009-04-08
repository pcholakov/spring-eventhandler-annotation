package org.springframework.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * An ApplicationListener implementation which dispatches ApplicationEvent's based on
 * method annotations. This approach provides type safety and readability over implementing
 * ApplicationListener directly and doing multiple <code>instanceof</code> checks.
 * <p/>
 * This class can either be extended and have event handler methods added to it, or it can
 * be instantiated as is and have its <code>target</code> set to dispatch to another object.
 * The latter approach is preferable in case you ever need to use inheritance to derive your
 * event handler from a different superclass.
 *
 * @author Pavel Tcholakov
 */
public class AnnotatedApplicationListener implements ApplicationListener {
    private Object target = this;
    private Log log = LogFactory.getLog(getClass());

    public final void onApplicationEvent(final ApplicationEvent event) {
        Assert.notNull(target, "target is null");

        ReflectionUtils.doWithMethods(target.getClass(),
                new ReflectionUtils.MethodCallback() {
                    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                        if (log.isTraceEnabled()) {
                            log.trace("Dispatching event to " + method);
                        }
                        ReflectionUtils.invokeMethod(method, target, new Object[]{event});
                    }
                },
                new ReflectionUtils.MethodFilter() {
                    public boolean matches(Method method) {
                        final EventHandler eh = AnnotationUtils.getAnnotation(method, EventHandler.class);
                        final Class<?>[] params = method.getParameterTypes();
                        return eh != null && params.length == 1 && params[0].isAssignableFrom(event.getClass());
                    }
                });
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
