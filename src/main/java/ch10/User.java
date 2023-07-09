package ch10;

public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.name = name;
    }
}
