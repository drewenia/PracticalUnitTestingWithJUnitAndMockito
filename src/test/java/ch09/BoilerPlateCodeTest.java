package ch09;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoilerPlateCodeTest {
    private static Collaborator collaborator;
    private static SUT sut;

    @BeforeAll
    static void setUp(){
        sut = new SUT();
        collaborator = mock(Collaborator.class);
        //sut.setCollaborator(collaborator);
    }

    @Test
    void shouldReturnABC(){
       when(collaborator.doSth()).thenReturn("abc");
       assertEquals("abc",sut.useCollaborator());
    }
}
