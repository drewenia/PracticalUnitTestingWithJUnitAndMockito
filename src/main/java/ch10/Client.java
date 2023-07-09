package ch10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private List<Fund> funds = new ArrayList<>();

    public void addFund(Fund fund){
        funds.add(fund);
    }
    public BigDecimal getValueOfAllFunds(){
        BigDecimal value = BigDecimal.ZERO;
        for (Fund f : funds){
            value = value.add(f.getValue());
        }
        return value;
    }
}
