package ru.otus.homework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLogging(TestLoggingInterface original) {
        InvocationHandler handler = new LoggingInvocationHandler(original);
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                handler
        );
    }

    static class LoggingInvocationHandler implements InvocationHandler {

        private final TestLoggingInterface original;
        private final Set<MethodKey> annotationMethods;

        public LoggingInvocationHandler(TestLoggingInterface original) {
            this.original = original;
            annotationMethods = Arrays.stream(original.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(method -> new MethodKey(method.getName(), method.getParameterTypes()))
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MethodKey methodKey = new MethodKey(method.getName(), method.getParameterTypes());
            if (annotationMethods.contains(methodKey)) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }
            return method.invoke(original, args);
        }
        record MethodKey(String name, Class<?>[] parameterTypes) {
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof MethodKey methodKey)) return false;
                return Objects.equals(name, methodKey.name) && Arrays.equals(parameterTypes, methodKey.parameterTypes);
            }

            @Override
            public int hashCode() {
                int result = Objects.hash(name);
                result = 31 * result + Arrays.hashCode(parameterTypes);
                return result;
            }

            @Override
            public String toString() {
                return "MethodKey{" +
                        "name='" + name + '\'' +
                        ", parameterTypes=" + Arrays.toString(parameterTypes) +
                        '}';
            }
        }
    }

}
