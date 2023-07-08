package ch09;

public class PhoneBuilder {
    private String phoneNumber;

    public PhoneBuilder withPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Phone build(){
        return new Phone(phoneNumber);
    }
}
