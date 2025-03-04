package Model.Order;

import Model.Employee.EmployeeType;
import Utilities.ObjectPlus;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Pizza extends ObjectPlus implements Serializable {
    private String name, sauceType;
    private Double price;
    private EnumSet<Size> pizzaSizes;
    private Set<Topping> toppings = new HashSet<>();

    public Pizza(String name, String sauceType, Double price, EnumSet<Size> pizzaSizes, Set<Topping> toppings) throws IllegalAccessException{
        super();
        setName(name);
        setSauceType(sauceType);
        setPizzaSize(pizzaSizes);
        setToppings(toppings);
        //Validation of size
        if(pizzaSizes.contains(Size.SMALL)){
            setPrice(price*0.5);
        }

        if(pizzaSizes.contains(Size.MEDIUM)){
            setPrice(price);
        }

        if(pizzaSizes.contains(Size.BIG)){
            setPrice(price*1.5);
        }

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

    public String getSauceType(){
        return sauceType;
    }

    public void setSauceType(String sauceType){
        if(sauceType == null || sauceType.isBlank())
            throw new IllegalArgumentException("SauceType is empty!");
        if(!sauceType.matches("[A-Za-z]+"))
            throw new IllegalArgumentException("SauceType can not contain any special characters or numbers!");
        this.sauceType = sauceType;
    }

    public Set<Size> getPizzaSize(){
        return Collections.unmodifiableSet(pizzaSizes);
    }

    public void setPizzaSize(EnumSet<Size> pizzaSizes) {
        if(pizzaSizes == null || pizzaSizes.isEmpty())
            throw new IllegalArgumentException("Null pizza size is not allowed!");
        this.pizzaSizes = pizzaSizes;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        if(price == null )
            throw new IllegalArgumentException("Price can not be null!");
        if(price <0)
            throw new IllegalArgumentException("Price can not be smaller than 0!");
        this.price = price;
    }

    //Toppings methods
    public Set<Topping> getToppings() {
        return Collections.unmodifiableSet(this.toppings);
    }

    public void setToppings(Set<Topping> toppings){
        if(toppings == null || toppings.isEmpty())
            throw new IllegalArgumentException("Toppings can not be empty!");
        this.toppings = toppings;
    }

    public void addTopping(Topping topping){
        if(topping == null)
            throw new IllegalArgumentException("Empty topping!");
        if(toppings.contains(topping))
            return;
        toppings.add(topping);
        topping.addPizza(this);
    }

    public void removeTopping(Topping topping){
        if(topping == null)
            throw new IllegalArgumentException("Empty topping!");
        if(!toppings.contains(topping))
            return;
        toppings.remove(topping);
        topping.removePizza(this);
    }
}
