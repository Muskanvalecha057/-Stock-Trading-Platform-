/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.stock_trading_platform;

/**
 *
 * @author Hp
 */
public class Stock {
     private int id;
    private String name;
    private double price;
    private int quantity;

    public Stock(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Price: Rs. " + price + " | Quantity: " + quantity);
    }
}
