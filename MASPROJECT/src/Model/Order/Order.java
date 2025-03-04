package Model.Order;

import Model.Customer.Customer;
import Model.Employee.Employee;
import Model.MenuItem;
import Utilities.ObjectPlus;

import java.io.Serializable;
import java.sql.Driver;
import java.util.*;

public class Order extends ObjectPlus implements Serializable {
    private Boolean delivery;
    private String deliveryAddress, comment;
    private Integer  tableNumber;
    private Double totalPrice;

    //Pizzas
    private Set<Pizza> pizzas = new HashSet<>();

    //Beverages
    private Set<Beverage> beverages = new HashSet<>();

    //Employee
    private Employee driver, waiter;

    //Customer
    private Customer customer;

    public Order(Boolean delivery, String deliveryAddress, Integer tableNumber, Set<Pizza> pizzas, Set<Beverage> beverages,Double totalPrice, String comment, Employee driver, Employee waiter) {
        super();
        setDelivery(delivery);

        if(delivery){
            setDeliveryAddress(deliveryAddress);
            setDriver(driver);
        }
        if(!delivery) {
            setTableNumber(tableNumber);
            setWaiter(waiter);
        }
        setPizzas(pizzas);
        setBeverages(beverages);
        setComment(comment);
        setTotalPrice(totalPrice);
    }

    public Boolean getDelivery(){
        return delivery;
    }

    public void setDelivery(Boolean delivery){
        if(delivery == null)
            throw new IllegalArgumentException("Delivery can not be null!");
        this.delivery = delivery;
    }

    public String getDeliveryAddress(){
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress){
        if(deliveryAddress == null || deliveryAddress.isBlank())
            throw new IllegalArgumentException("Delivery address is empty!");
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getTableNumber(){
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber){
        if(tableNumber == null)
            throw new IllegalArgumentException("Table number can not be null!");
        if(tableNumber < 0)
            throw new IllegalArgumentException("Table number can not be smaller than 0!");
        if(tableNumber > 10)
            throw new IllegalArgumentException("There are only 10 tables in the restaurant!");
        this.tableNumber = tableNumber;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        if(comment == null)
            throw new IllegalArgumentException("Comment can not be null!");
        this.comment = comment;
    }

    public Double getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice){
        if(totalPrice == null)
            throw new IllegalArgumentException("Total price can not be null!");
        if(totalPrice < 0)
            throw new IllegalArgumentException("Total price can not be smaller than 0");
        this.totalPrice = totalPrice;
    }

    //Pizza methods
    public Set<Pizza> getPizzas(){
        return Collections.unmodifiableSet(this.pizzas);
    }

    public void setPizzas(Set<Pizza> pizzas){
        if(pizzas == null || pizzas.isEmpty())
            throw new IllegalArgumentException("Pizzas can not be empty!");
        this.pizzas = pizzas;
    }

    public void addPizzaToOrder(Pizza pizza){
        if(pizza == null)
            throw new IllegalArgumentException("Empty pizza!");
        pizzas.add(pizza);
        pizza.addOrder(this);
    }

    public void removePizzaFromOrder(Pizza pizza){
        if(pizza == null)
            throw new IllegalArgumentException("Empty pizza!");
        if(!pizzas.contains(pizza))
            return;
        pizzas.remove(pizza);
        pizza.removeOrder(this);
    }


    //Beverage methods
    public Set<Beverage> getBeverages(){
        return beverages;
    }

    public void setBeverages(Set<Beverage> beverages){
        if(beverages == null)
            throw new IllegalArgumentException("Beverages can not be null!");
        this.beverages = beverages;
    }

    public void addBeverageToOrder(Beverage beverage){
        if(beverage == null)
            throw new IllegalArgumentException("Empty beverage!");
        beverages.add(beverage);
        beverage.addOrder(this);
    }

    public void removeBeverageFromOrder(Beverage beverage){
        if(beverage == null)
            throw new IllegalArgumentException("Empty beverage!");
        if(!beverages.contains(beverage))
            return;
        beverages.remove(beverage);
        beverage.removeOrder(this);
    }

    //Employee methods
    public Employee getDriver(){
        return driver;
    }

    public void setDriver(Employee driver){
        if(driver == null)
            throw new IllegalArgumentException("The driver can not be empty!");
        this.driver = driver;
    }

    public void addDriver(Employee driver) throws IllegalAccessException {
        if(driver == null)
            throw new IllegalArgumentException("Driver can not be empty!");
        if(this.getDriver() == driver)
            throw new IllegalArgumentException("This driver is already assigned to this order!");
        if(this.getDriver() != null)
            throw new IllegalArgumentException("This order already has a driver!");
        this.setDriver(driver);
        driver.addDeliveryOrder(this);

    }

    public void removeDriver(Employee driver) throws IllegalAccessException{
        if(driver == null)
            throw new IllegalAccessException("Driver can not be empty!");
        if(this.getDriver() != driver)
            throw new IllegalAccessException("This driver is not assigned to the order!");
        this.setDriver(null);
        driver.removeDeliveryOrder(this);
    }

    public Employee getWaiter(){
        return waiter;
    }

    public void setWaiter(Employee waiter){
        if(waiter == null)
            throw new IllegalArgumentException("The waiter can not be empty!");
        this.waiter = waiter;
    }

    public void addWaiter(Employee waiter) throws IllegalAccessException {
        if(waiter == null)
            throw new IllegalArgumentException("Waiter can not be empty!");
        if(this.getWaiter() == waiter)
            throw new IllegalArgumentException("This waiter is already assigned to this order!");
        if(this.getWaiter() != null)
            throw new IllegalArgumentException("This order already has a waiter!");
        this.setWaiter(waiter);
        waiter.addServedOrder(this);

    }

    public void removeWaiter(Employee waiter) throws IllegalAccessException{
        if(waiter == null)
            throw new IllegalAccessException("Waiter can not be empty!");
        if(this.getWaiter() != waiter)
            throw new IllegalAccessException("This waiter is not assigned to the order!");
        this.setWaiter(null);
        waiter.removeServedOrder(this);
    }

    //Customer methods
    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        if(customer == null)
            throw new IllegalArgumentException("Customer can not be empty!");
        this.customer = customer;
    }

    public void addCustomer(Customer customer) throws IllegalAccessException {
        if(customer == null)
            throw new IllegalArgumentException("Customer can not be empty!");
        if(this.getCustomer() == customer)
            throw new IllegalArgumentException("This customer is already assigned to this order!");
        if(this.getCustomer() != null)
            throw new IllegalArgumentException("This order already has a customer!");
        this.setCustomer(customer);
        customer.addCustomerOrder(this);
    }

    public void removeCustomer(Customer customer) throws IllegalAccessException{
        if(customer == null)
            throw new IllegalArgumentException("Customer can not be empty!");
        if(this.getCustomer() != customer)
            throw new IllegalArgumentException("This customer is not assigned to the order!");
        this.setCustomer(null);
        customer.removeCustomerOrder(this);
    }


    @Override
    public String toString() {
        return "Model.Order.Order ID: " + id + ", Total Cost: $" + String.format("%.2f", getTotalCost()) + ", Status: " + status;
    }
}
