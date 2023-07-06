package ch07;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MyPartialSutTest {
    @Test
    void testMyMethod() {
        MyPartialSut sut = spy(new MyPartialSut());
        MyCollaborator myCollaborator = mock(MyCollaborator.class);

        doReturn(myCollaborator).when(sut).createCollaborator();
    }
}