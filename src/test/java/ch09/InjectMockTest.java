package ch09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class InjectMockTest {
    @Mock
    private Collaborator collaborator;
    @InjectMocks
    private SUT sut;

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnABC() {
        when(collaborator.doSth()).thenReturn("abc");
        assertEquals("abc", sut.useCollaborator());
    }
}
