package ch07;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrivateMethodReflectionTest {
    @Test
    void testingPrivateMethodWithReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SomeClass sut = new SomeClass();
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = Long.class;
        Method method = sut.getClass().getDeclaredMethod("privateMethod", parameterTypes);
        method.setAccessible(true);

        Object[] parameters = new Object[1];
        parameters[0] = 5569L;

        Boolean result = (Boolean) method.invoke(sut,parameters);

        assertTrue(result);
    }
}
