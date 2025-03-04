package Model.Order;

import Utilities.ObjectPlus;

import java.io.Serializable;

public class Beverage extends ObjectPlus implements Serializable {
    private String name;
    private Boolean cold;
    private Double price;

    public Beverage(String name, Boolean cold, Double price){
        setName(name);
        setCold(cold);
        setPrice(price);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name is empty!");
        if(!name.matches("[A-Za-z]+"))
            throw new IllegalArgumentException("Name can not contain any special characters or numbers!");
        this.name = name;
    }

    public Boolean getCold(){
        return cold;
    }

    public void setCold(Boolean cold){
        if(cold == null)
            throw new IllegalArgumentException("Null is not an option!");
        this.cold = cold;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price){
        if(price == null)
            throw new IllegalArgumentException("Price is empty!");
        if(price < 0)
            throw new IllegalArgumentException("Price can not be smaller than 0!");
        this.price = price;
    }
}
