package baeldung.matchingNullWithMockito;

public class Main {
    Helper helper = new Helper();

    String methodUnderTest() {
        return helper.concat("Baeldung", null);
    }
}
