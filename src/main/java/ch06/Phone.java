package ch06;

public class Phone {
    private String number;
    public void setNumber(String number){
        if (null == number || number.isEmpty()){
            throw new IllegalArgumentException("number can not be null or empty");
        }
        if (number.startsWith("+")){
            throw new IllegalArgumentException("number can not be start + sign");
        }
        this.number = number;
    }
}
