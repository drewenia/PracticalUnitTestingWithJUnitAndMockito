package ch07;

public record Person(String name,String surname) {
    public String getNick() {
        return name + " " + surname;
    }
}
