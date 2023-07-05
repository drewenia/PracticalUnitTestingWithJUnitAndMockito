package ch06;

import java.util.ArrayList;
import java.util.List;

public class CollectionTestUser {
    List<String> phones = new ArrayList<>();
    public void addPhone(String phone){
        phones.add(phone);
    }

    public List<String> getPhones(){
        return this.phones;
    }
}
