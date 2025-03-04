package Model;

import java.io.Serializable;

public class MenuItem implements Serializable {
    public enum Category {
        PIZZA, PASTA, SIDE, DRINK
    }

    private String name;
    private double price;
    private double discount;
    private Category category;

    public MenuItem(String name, double price, double discount, Category category) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getFinalPrice() {
        return price - (price * discount / 100);
    }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + String.format("%.2f", getFinalPrice());
    }
}
