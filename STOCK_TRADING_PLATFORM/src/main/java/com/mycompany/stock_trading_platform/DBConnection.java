/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.stock_trading_platform;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Hp
 */
public class DBConnection {
     public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=stock_trading_market;integratedSecurity=true;encrypt=false";
            conn = DriverManager.getConnection(url);
           // System.out.println(" Connected to SQL Server!");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}
