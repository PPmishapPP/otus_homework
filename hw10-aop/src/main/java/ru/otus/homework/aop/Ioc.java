package ru.otus.homework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new LoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                handler
        );
    }

    static class LoggingInvocationHandler implements InvocationHandler {

        private final TestLogging original;

        private final Set<String> methods;

        public LoggingInvocationHandler(TestLogging original) {
            this.original = original;

            methods = Arrays.stream(original.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(Method::getName)
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methods.contains(method.getName())) {
                Method declaredMethod = original.getClass().getDeclaredMethod(
                        method.getName(),
                        method.getParameterTypes()
                );
                if (declaredMethod.isAnnotationPresent(Log.class)) {
                    System.out.println("executed method: calculation, param: " + Arrays.toString(args));
                }
            }

            return method.invoke(original, args);
        }
    }

}
