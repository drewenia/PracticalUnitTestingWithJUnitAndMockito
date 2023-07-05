package ch06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class SystemIdGeneratorTest {
    AtomicIdGenerator atomicIdGenerator = new AtomicIdGenerator();
    @Test
    void shouldIdIsUnique() {
        Long idA = atomicIdGenerator.nextId();
        Long idB = atomicIdGenerator.nextId();

        assertNotEquals(idA,idB);
    }
}
