package ch07;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class EachMethodTest {
    @Test
    void testA() {
        Collaborator collaborator = mock(Collaborator.class);
        OtherCollaborator otherCollaborator = mock(OtherCollaborator.class);
        SUT sut = new SUT(collaborator,otherCollaborator);

        sut.someMethod();

        //assertions
    }
}
