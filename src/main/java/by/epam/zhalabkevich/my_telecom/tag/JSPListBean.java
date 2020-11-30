package by.epam.zhalabkevich.my_telecom.tag;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class JSPListBean {
    private Iterator<Tariff> iterator;
    private List<Tariff> list;

    public JSPListBean(){}

    public JSPListBean(List list) {
        this.list = list;
    }

    public String getSize() {
        iterator = list.iterator();
        return Integer.toString(list.size());
    }

    public Tariff getElement() {
        return iterator.next();
    }

    public String getSpeed(){
        return Integer.toString(getElement().getSpeed());
    }

    public String getPrice(){
        return getElement().getPrice().toString();
    }

    public String getName(){
        return getElement().getName();
    }

    public String getDescription(){
        return getElement().getDescription();
    }



}
