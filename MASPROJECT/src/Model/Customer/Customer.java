package Model.Customer;

import Model.Employee.EmployeeType;
import Model.Order.Order;
import Utilities.ObjectPlus;

import java.io.Serializable;
import java.util.*;

public class Customer extends ObjectPlus implements Serializable {
    private String name;
    private EnumSet<CustomerType> customerTypes;

    //Delivery Customer
    private String phoneNumber, lastDeliveryAddress;

    private Set<Order> lastOrders = new HashSet<>();
    public Customer(String name, EnumSet<CustomerType> customerTypes, String phoneNumber, String lastDeliveryAddress, Set<Order> lastOrders) {
        super();
        setName(name);
        setCustomerTypes(customerTypes);

        if(customerTypes.contains(CustomerType.DELIVERY)){
            setPhoneNumber(phoneNumber);
            setLastDeliveryAddress(lastDeliveryAddress);
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

    public Set<CustomerType> getCustomerTypes(){
        return Collections.unmodifiableSet(customerTypes);
    }

    public void setCustomerTypes(EnumSet<CustomerType> customerTypes){
        if(customerTypes == null || customerTypes.isEmpty())
            throw new IllegalArgumentException("Null customer role is not allowed!");
        this.customerTypes = customerTypes;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Phone number must be provided!");
        if (!phoneNumber.matches("\\+?[0-9]+"))
            throw new IllegalArgumentException("Invalid phone number format!");
        this.phoneNumber = phoneNumber;
    }

    public String getLastDeliveryAddress(){
        return lastDeliveryAddress;
    }

    public void setLastDeliveryAddress(String lastDeliveryAddress){
        if(lastDeliveryAddress == null || lastDeliveryAddress.isBlank())
            throw new IllegalArgumentException("Delivery address is empty!");
        this.lastDeliveryAddress = lastDeliveryAddress;
    }

    public Set<Order> getLastOrders(){
        return Collections.unmodifiableSet(lastOrders);
    }

    public void setLastOrders(Set<Order> lastOrders){
        if(lastOrders.isEmpty())
            throw new IllegalArgumentException("Empty orders is not allowed!");
        this.lastOrders = lastOrders;
    }

    public void addCustomerOrder(Order order) throws IllegalAccessException{
        if(!customerTypes.contains(CustomerType.DELIVERY))
            throw new IllegalArgumentException("This is not a delivery customer!");
        if(order == null)
            throw new IllegalArgumentException("Empty relation!");
        if(order.getCustomer() != this)
            throw new IllegalArgumentException("Different customer!");
        if(lastOrders.contains(order))
            return;
        lastOrders.add(order);
        order.addCustomer(this);
    }

    public void removeCustomerOrder(Order order) throws IllegalAccessException{
        if(!customerTypes.contains(CustomerType.DELIVERY))
            throw new IllegalArgumentException("This person is not a delivery customer!");
        if(order == null)
            throw new IllegalArgumentException("Empty relation!");
        if(!lastOrders.contains(order))
            return;
        lastOrders.remove(order);
        order.removeCustomer(this);
    }
    @Override
    public String toString() {
        return "Model.Customer.Customer{id=" + id + ", name='" + name + "', email='" + email + ", phoneNumber='" + phoneNumber + "'}";
    }
}
