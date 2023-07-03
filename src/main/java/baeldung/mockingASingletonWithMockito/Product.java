package baeldung.mockingASingletonWithMockito;

public class Product {
    private final String name;
    private final String description;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
