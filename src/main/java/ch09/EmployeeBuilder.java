package ch09;

public class EmployeeBuilder {
    private String firstname;
    private String lastname;
    private Address address;
    private Position position;
    private Phone mobilePhone;
    private Phone stationaryPhone;

    public EmployeeBuilder withFirstName(String firstname){
        this.firstname = firstname;
        return this;
    }

    public EmployeeBuilder withLastname(String lastname){
        this.lastname = lastname;
        return this;
    }

    public EmployeeBuilder withAddress(Address address){
        this.address = address;
        return this;
    }

    public EmployeeBuilder withPosition(Position position){
        this.position = position;
        return this;
    }

    public EmployeeBuilder withMobilePhone(Phone mobilePhone){
        this.mobilePhone = mobilePhone;
        return this;
    }

    public EmployeeBuilder withStationaryPhone(Phone stationaryPhone){
        this.stationaryPhone = stationaryPhone;
        return this;
    }

    public Employee build(){
        return new Employee(firstname,lastname,position,address,mobilePhone,stationaryPhone);
    }
}
