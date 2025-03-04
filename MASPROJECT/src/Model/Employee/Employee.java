package Model.Employee;

import Model.Order.Order;
import Model.Order.Pizza;
import Utilities.ObjectPlus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Employee extends ObjectPlus implements Serializable {

    private String name, surname, phoneNumber;
    private LocalDate dateOfBirth;

    private EnumSet<EmployeeType> employeeTypes;
    private static final int minimalRequiredAge = 18;
    //driver
    private String licensePlate;
    private Set<Order> deliveredOrders = new HashSet<>();

    //waiter
    private Double tips;
    private Set<Order> servedOrders = new HashSet<>();

    //cook
    private Set<Pizza> cookedPizzas = new HashSet<>();



    public Employee(String name, String surname, LocalDate dateOfBirth, String phoneNumber, EnumSet<EmployeeType> employeeTypes, String licensePlate, Double tips) throws IllegalAccessException {
        super();
        validateMinimalAge();
        setName(name);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        setPhoneNumber(phoneNumber);
        setEmployeeTypes(employeeTypes);

        //Validation of roles
        if(employeeTypes.contains(EmployeeType.DRIVER)) {
            setLicensePlate(licensePlate);
        }

        if(employeeTypes.contains(EmployeeType.WAITER)) {
            setTips(tips);
        }
    }

    public Set<EmployeeType> getEmployeeTypes(){
        return Collections.unmodifiableSet(employeeTypes);
    }

    public void setEmployeeTypes(EnumSet<EmployeeType> employeeTypes) {
        if(employeeTypes == null || employeeTypes.isEmpty())
            throw new IllegalArgumentException("Null employee role is not allowed!");
        this.employeeTypes = employeeTypes;
    }

    //region Employee getters and setters
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

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        if(surname == null || surname.isBlank())
            throw new IllegalArgumentException("Surname is empty!");
        if(!surname.matches("[A-Za-z]+"))
            throw new IllegalArgumentException("Surname can not contain any special characters or numbers!");
        this.surname = surname;
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


    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth){
        if(dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth can not be empty!");
        if(dateOfBirth.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date can not be from the future!");
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() throws IllegalAccessException { //derived attribute
        return Period.between(this.getDateOfBirth(), LocalDate.now()).getYears();
    }

    public void validateMinimalAge() throws IllegalAccessException {
        if(this.getAge() < Employee.minimalRequiredAge)
            throw new IllegalArgumentException("We don't take minors as employees!");
    }
    //end region

    //region Driver
    public String getLicensePlate() throws IllegalAccessException {
        if(!employeeTypes.contains(EmployeeType.DRIVER))
            throw new IllegalArgumentException("This person is not a Driver!");
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate){
        if(!employeeTypes.contains(EmployeeType.DRIVER))
            throw new IllegalArgumentException("This person is not a Driver!");
        if(licensePlate == null || licensePlate.isBlank())
            throw new IllegalArgumentException("License plate needs to be recorded for all drivers!");
        this.licensePlate = licensePlate;
    }

    public Set<Order> getDeliveredOrders() throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.DRIVER))
            throw new IllegalArgumentException("This employee is not a driver!");
        return Collections.unmodifiableSet(this.deliveredOrders);
    }

    public void addDeliveryOrder(Order deliveryOrder) throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.DRIVER))
            throw new IllegalArgumentException("This employee is not a driver!");
        if(deliveryOrder == null)
            throw new IllegalArgumentException("Order is empty");
        if(deliveryOrder.getDriver() != this)
            throw new IllegalArgumentException("Different Driver!");
        if(deliveredOrders.contains(deliveryOrder))
            return;
        deliveredOrders.add(deliveryOrder);
        deliveryOrder.addDriver(this);
    }

    public void removeDeliveryOrder(Order deliveryOrder) throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.DRIVER))
            throw new IllegalArgumentException("This employee is not a driver!");
        if(deliveryOrder == null)
            throw new IllegalArgumentException("Order is empty!");
        if(!deliveredOrders.contains(deliveryOrder))
            return;
        deliveredOrders.remove(deliveryOrder);
        deliveryOrder.removeDriver(this);
    }
    //end region

    //region Waiter
    private Double getTips() throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.WAITER))
            throw new IllegalArgumentException("This person is not a Waiter!");
        return tips;
    }

    private void setTips(Double tips){
        if(!employeeTypes.contains(EmployeeType.WAITER))
            throw new IllegalArgumentException("This person is not a Waiter!");
        if(tips == null)
            throw new IllegalArgumentException("Tips can not be left empty!");
        if(tips < 0)
            throw new IllegalArgumentException("Tips can not be smaller than 0!");
        this.tips = tips;
    }

    public Set<Order> getServedOrders() {
        if(!employeeTypes.contains(EmployeeType.WAITER))
            throw new IllegalArgumentException("This employee is not a Waiter!");
        return Collections.unmodifiableSet(servedOrders);
    }

    public void addServedOrder(Order servedOrder) throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.WAITER))
            throw new IllegalArgumentException("This person is not a Waiter!");
        if(servedOrder == null)
            throw new IllegalArgumentException("Empty relation!");
        if(servedOrder.getWaiter() != this)
            throw new IllegalArgumentException("Different Waiter!");
        if(servedOrders.contains(servedOrder))
            return;
        servedOrders.add(servedOrder);
        servedOrder.addWaiter(this);
    }

    public void removeServedOrder(Order servedOrder) throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.WAITER))
            throw new IllegalArgumentException("This person is not a Waiter!");
        if(servedOrder == null)
            throw new IllegalArgumentException("Empty relation!");
        if(!servedOrders.contains(servedOrder))
            return;
        servedOrder.removeWaiter(this);
    }
    //end region

    //region Cook
    public void addCookedPizza(Pizza cookedPizza) throws IllegalAccessException{
        if(!employeeTypes.contains(EmployeeType.COOK))
            throw new IllegalArgumentException("This person is not a Cook!");
        if(cookedPizza == null)
            throw new IllegalArgumentException("Empty relation!");
        if(cookedPizza.getCook() != this)
            throw new IllegalArgumentException("Different Cook!");
        if(cookedPizzas.contains(cookedPizza))
            return;
        cookedPizza.addCook(this);
    }
    //end region

    @Override
    public String toString(){
        String toString = "Model.Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", employeeTypes=" + employeeTypes +
                ", licensePlate=" + licensePlate +
                ", tips=" + tips +
                ", deliveredOrders=";
        for(Order order : deliveredOrders)
            toString += " " + order.getId() + ", ";
        toString += "servedOrders=";
        for(Order order : servedOrders)
            toString += " " + order.getId() + ", ";
        toString += "cookedPizzas=";
        for(Pizza pizza : cookedPizzas)
            toString += " " + pizza.getName() + ", ";
        toString += '}';
        return toString;
    }

//    public void removeCookedPizza(Pizza cookedPizza) throws IllegalAccessException{
//        if(!employeeTypes.contains(EmployeeType.COOK))
//            throw new IllegalArgumentException("This person is not a Cook!");
//        if(cookedPizza == null)
//            throw new IllegalArgumentException("Empty relation!");
//        if(!cookedPizzas.contains(cookedPizza))
//            return;
//        cookedPizza.removeCook(this);
//    }
}
