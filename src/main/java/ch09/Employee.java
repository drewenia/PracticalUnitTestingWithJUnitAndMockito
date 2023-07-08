package ch09;

public record Employee(String name, String surname, Position position, Address address, Phone mobilePhone,
                       Phone stationaryPhone) {
}
