package Model.Order;

import Utilities.ObjectPlus;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Topping extends ObjectPlus implements Serializable {
    private String name;
    private Double priceSmall, priceMedium, priceBig;
    private Pizza pizza;
    private Double price;

    private Set<Pizza> pizzas = new HashSet<>();

    public Topping(String name, Double priceSmall, Double priceMedium, Double priceBig) throws IllegalAccessException{
        super();
        setName(name);
        //Validation of sizes
        if(this.getPizza().getPizzaSize().contains(Size.SMALL)){
            setPriceSmall(priceSmall);
            setPrice(getPriceSmall());
        }

        if(this.getPizza().getPizzaSize().contains(Size.MEDIUM)){
            setPriceMedium(priceMedium);
            setPrice(getPriceMedium());
        }

        if(this.getPizza().getPizzaSize().contains(Size.BIG)){
            setPriceBig(priceBig);
            setPrice(getPriceBig());
        }
    }

    public Pizza getPizza(){
        return pizza;
    }

    public void setPizza(Pizza pizza){
        if(pizza == null)
            throw new IllegalArgumentException("We need pizza for toppings!");
        this.pizza = pizza;
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

    public Double getPriceSmall() {
        return priceSmall;
    }

    public void setPriceSmall(Double priceSmall){
        if(priceSmall == null)
            throw new IllegalArgumentException("Small pizza price is empty!");
        if(priceSmall < 0)
            throw new IllegalArgumentException("Small pizza price can not be smaller than 0!");
        this.priceSmall = priceSmall;
    }

    public Double getPriceMedium() {
        return priceMedium;
    }

    public void setPriceMedium(Double priceMedium){
        if(priceMedium == null)
            throw new IllegalArgumentException("Medium pizza price is empty!");
        if(priceMedium < 0)
            throw new IllegalArgumentException("Medium pizza price can not be smaller than 0!");
        this.priceMedium = priceMedium;
    }

    public Double getPriceBig() {
        return priceBig;
    }

    public void setPriceBig(Double priceBig){
        if(priceBig == null)
            throw new IllegalArgumentException("Big pizza price is empty!");
        if(priceBig < 0)
            throw new IllegalArgumentException("Big pizza price can not be smaller than 0!");
        this.priceBig = priceBig;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        if(price == null)
            throw new IllegalArgumentException("Price can not be null!");
        if(price < 0)
            throw new IllegalArgumentException("Price can not be negative!");
        this.price = price;
    }

    //Pizza methods
    public Set<Pizza> getPizzas(){
        return Collections.unmodifiableSet(pizzas);
    }

    public void addPizza(Pizza pizza){
        if(pizza == null)
            throw new IllegalArgumentException("Empty pizza!");
        if(pizzas.contains(pizza))
            return;
        pizza.addTopping(this);
    }

    public void removePizza(Pizza pizza){
        if(pizza == null)
            throw new IllegalArgumentException("Empty pizza!");
        if(!pizzas.contains(pizza))
            return;
        pizzas.remove(pizza);
        pizza.removeTopping(this);
    }
}
