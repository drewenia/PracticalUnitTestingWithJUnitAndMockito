package baeldung.resolvingMockitoException;

public class Main {
    Helper helper = new Helper();

    String methodUnderTest(int i) {
        if (i > 5)
            return helper.getBaueldungString();
        return "Hello";
    }
}
