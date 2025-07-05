/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.stock_trading_platform;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Hp
 */
public class stock_market {
      public static ArrayList<Stock> getAllStocks() {
        ArrayList<Stock> stockList = new ArrayList<>();

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM stock");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                Stock s = new Stock(id, name, price, quantity);
                stockList.add(s);
            }

            conn.close();
        } catch (Exception e) {
            System.out.println(" Error fetching stocks: " + e.getMessage());
        }

        return stockList;
    }
}
