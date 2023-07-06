package ch07;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class MySutTest {
    @Test
    void testMyMethod(){
        MyCollaborator myCollaborator = mock(MyCollaborator.class);
        MySut mySut = new MySut(myCollaborator);

        //normal mockito stubbing/test spying test
    }
}
