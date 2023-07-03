package baeldung.matchingNullWithMockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;

public class MainUnitTest {
    /* @InjectMocks, Mockito'nun bir özelliğidir ve bir sınıfta, diğer sınıflardaki mock nesnelerini otomatik olarak
    enjekte etmek için kullanılır. Bu anotasyon, test edilecek sınıfın örneğini oluştururken diğer sınıflardaki mock
    nesnelerini otomatik olarak enjekte eder.*/
    @InjectMocks
    Main main;

    @Mock
    Helper helper;

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenMethodUnderTest_thenSecondParameterNull() {
        main.methodUnderTest();
        verify(helper).concat("Baeldung", null);
    }

    @Test
    void whenMethodUnderTest_thenSecondParameterNullWithMatchers() {
        main.methodUnderTest();
        verify(helper).concat(any(), isNull());
    }
}
