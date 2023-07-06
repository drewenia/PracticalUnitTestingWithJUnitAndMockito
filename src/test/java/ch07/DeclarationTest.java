package ch07;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class DeclarationTest {
    Collaborator collaborator = mock(Collaborator.class);
    OtherCollaborator otherCollaborator = mock(OtherCollaborator.class);
    SUT sut = new SUT(collaborator,otherCollaborator);

    @Test
    void testA(){
        sut.someMethod();

        //assertions
    }
}
