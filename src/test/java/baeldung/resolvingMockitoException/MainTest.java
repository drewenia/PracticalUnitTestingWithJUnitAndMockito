package baeldung.resolvingMockitoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class MainTest {
    @Mock
    Helper helper;
    @InjectMocks
    Main main;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void givenValueUpperThan5_WhenMethodUnderTest_ThenDelegatesToHelperClass() {
        main.methodUnderTest(7);
        verify(helper).getBaueldungString();
    }

    @Test
    void givenValueLowerThan5_WhenMethodUnderTest_ThenDelegatesToGetBaeldungString() {
        main.methodUnderTest(3);
        verify(helper).getBaueldungString();
    }
}
