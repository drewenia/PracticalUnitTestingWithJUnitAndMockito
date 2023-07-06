package ch07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MySutRefactoredTest {
    private MyCollaborator myCollaborator;

    class MySutRefactoredSubClassed extends MySutRefactored {
        @Override
        protected MyCollaborator createCollaborator() {
            return myCollaborator;
        }
    }

    @Test
    void myMethod(){
        MySutRefactored sut = new MySutRefactoredSubClassed();
        myCollaborator = mock(MyCollaborator.class);

        when(myCollaborator.someMethod()).thenReturn(true);
        assertTrue(sut.myMethod());
    }
}
