package baeldung.mockingPrivateFieldsWithMockito;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockServiceUnitTest {
    private final Person mockedPerson = mock(Person.class);

    @Test
    void givenNameChangedWithReflectionUtils_whenGetName_thenReturnName() throws Exception {
        MockService mockService = new MockService();
        Field field = ReflectionUtils.findFields(
                MockService.class,
                f -> f.getName().equals("person"),
                ReflectionUtils.HierarchyTraversalMode.TOP_DOWN).get(0);
        field.setAccessible(true);
        field.set(mockService, mockedPerson);

        when(mockedPerson.getName()).thenReturn("Jane Doe");
        assertEquals("Jane Doe", mockService.getName());
    }

    @Test
    void givenNameChangedWithReflectionTestUtils_whenGetName_thenReturnName() {
        MockService mockService = new MockService();
        ReflectionTestUtils.setField(mockService, "person", mockedPerson);

        when(mockedPerson.getName()).thenReturn("Jane Doe");
        assertEquals("Jane Doe", mockService.getName());
    }
}
