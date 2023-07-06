package ch07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrivateMethodAccessModifierTest {
    @Test
    void testingPrivateMethodWithReflection(){
        SomeClass sut = new SomeClass();
        assertTrue(sut.privateMethod(55567L));
    }
}
