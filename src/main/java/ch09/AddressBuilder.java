package ch09;

public class AddressBuilder {
    private String address;

    public AddressBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public Address build(){
        return new Address(address);
    }
}
