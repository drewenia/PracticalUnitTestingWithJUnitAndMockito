package ch09;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneLinerStubbingTest {
    private static final Collaborator collaborator =
            when(mock(Collaborator.class).doSth()).thenReturn("abc").getMock();
    private static SUT sut;

    @BeforeAll
    static void setUp(){
        sut = new SUT();
        //sut.setCollaborator(collaborator);
    }

    @Test
    void shouldReturnABC(){
        assertEquals("abc",sut.useCollaborator());
    }
}
