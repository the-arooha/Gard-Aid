package com.example.loginactivity.Model;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public float delivery_charge;
    public boolean isDeliveryOn;
    public String deliveryChargeAmount;
    public String subtotalAmount;
    public String orderId;
    public String customerId;
    public String name;
    public String address;
    public String city;
    public String state;
    public String zip;
    public List<Menu> menuList;
    public float totalAmount;
    public String restaurantName;
    public String deliveryAddress;
    public List<MenuItem> orderedItems;


    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void addMenuItem(MenuItem menuItem) {
        if (orderedItems == null) {
            orderedItems = new ArrayList<>();
        }
        orderedItems.add(menuItem);
    }

    public Order(String orderId, String name, String address, String city, String state, String zip, String subtotalAmount, String deliveryChargeAmount, String totalAmount, List<Menu> menuList, boolean isDeliveryOn, float delivery_charge) {
        this.orderId = orderId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.subtotalAmount = subtotalAmount;
        this.deliveryChargeAmount = deliveryChargeAmount;
        try {
            this.totalAmount = Float.parseFloat(totalAmount);
        } catch (NumberFormatException e) {
            // Handle the error appropriately
        }
        this.menuList = menuList;
        this.isDeliveryOn = isDeliveryOn;
        this.delivery_charge = delivery_charge;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
