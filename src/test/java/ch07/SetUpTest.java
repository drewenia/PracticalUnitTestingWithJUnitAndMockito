package ch07;

import org.junit.jupiter.api.BeforeAll;

import static org.mockito.Mockito.mock;

public class SetUpTest {
    private static Collaborator collaborator;
    private static OtherCollaborator otherCollaborator;
    private static SUT sut;

    @BeforeAll
    static void setUp(){
        collaborator = mock(Collaborator.class);
        otherCollaborator = mock(OtherCollaborator.class);
        sut = new SUT(collaborator,otherCollaborator);
    }
}
