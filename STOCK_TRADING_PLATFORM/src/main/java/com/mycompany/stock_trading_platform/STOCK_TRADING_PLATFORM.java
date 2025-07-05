/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.stock_trading_platform;


/**
 *
 * @author Hp
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
public class STOCK_TRADING_PLATFORM {

    public static void main(String[] args) {
        DBConnection.connect();
        System.out.println("********Welcome to Stock Trading Platform********");
       Scanner sc = new Scanner(System.in);
             while (true) {
            
             System.out.println("1. View Market");
         System.out.println("2. Buy Stock");
          System.out.println("3. Sell Stock");
           System.out.println("4. View Portfolio");
            System.out.println("5. View Market");
                 System.out.println("6. Exit");
            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            
            switch(choice){
                case 1: 
                    System.out.println("********View Market********");
                    
    ArrayList<Stock> market = stock_market.getAllStocks();

    
    for (Stock s : market) {
        s.display(); 
    }
                    break;
                case 2:
                    System.out.println("********Buy Stock********");
                   

   

    System.out.print("Enter your User ID: ");
    int userId = sc.nextInt();

    System.out.print("Enter Stock ID to buy: ");
    int stockId = sc.nextInt();

    System.out.print("Enter quantity to buy: ");
    int quantity = sc.nextInt();

    try {
        Connection conn = DBConnection.connect();

        PreparedStatement ps1 = conn.prepareStatement("SELECT price, quantity FROM stock WHERE id = ?");
        ps1.setInt(1, stockId);
        ResultSet rs1 = ps1.executeQuery();

        if (rs1.next()) {
            double price = rs1.getDouble("price");
            int availableQty = rs1.getInt("quantity");

            // 🔍 Step 2: Get user's current balance
            PreparedStatement ps2 = conn.prepareStatement("SELECT balance FROM users WHERE id = ?");
            ps2.setInt(1, userId);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                double balance = rs2.getDouble("balance");

                double totalCost = price * quantity;

                if (quantity <= availableQty && totalCost <= balance) {
                   
                    PreparedStatement insert = conn.prepareStatement("INSERT INTO portfolio (user_id, stock_id, quantity, buy_price) VALUES (?, ?, ?, ?)");
                    insert.setInt(1, userId);
                    insert.setInt(2, stockId);
                    insert.setInt(3, quantity);
                    insert.setDouble(4, price);
                    insert.executeUpdate();

                    
                    double newBalance = balance - totalCost;
                    PreparedStatement updateBalance = conn.prepareStatement("UPDATE users SET balance = ? WHERE id = ?");
                    updateBalance.setDouble(1, newBalance);
                    updateBalance.setInt(2, userId);
                    updateBalance.executeUpdate();

                 
                    int newQty = availableQty - quantity;
                    PreparedStatement updateStock = conn.prepareStatement("UPDATE stock SET quantity = ? WHERE id = ?");
                    updateStock.setInt(1, newQty);
                    updateStock.setInt(2, stockId);
                    updateStock.executeUpdate();

                    System.out.println("Purchase successful! You bought " + quantity + " shares.");
                } else {
                    System.out.println("Not enough balance or stock quantity.");
                }

            } else {
                System.out.println("User not found.");
            }
        } else {
            System.out.println("Stock not found.");
        }

        conn.close();

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
                    break;
                case 3:
                    System.out.println("********Sell Stock********");
                     

    System.out.print("Enter your User ID: ");
    int userId3 = sc.nextInt();

    System.out.print("Enter Stock ID to sell: ");
    int stockId3 = sc.nextInt();

    System.out.print("Enter quantity to sell: ");
    int sellQty = sc.nextInt();

    try {
        Connection conn = DBConnection.connect();

        
        PreparedStatement ps1 = conn.prepareStatement("SELECT quantity, buy_price FROM portfolio WHERE user_id = ? AND stock_id = ?");
        ps1.setInt(1, userId3);
        ps1.setInt(2, stockId3);
        ResultSet rs1 = ps1.executeQuery();

        if (rs1.next()) {
            int ownedQty = rs1.getInt("quantity");
            double buyPrice = rs1.getDouble("buy_price");

            if (sellQty <= ownedQty) {
                
                PreparedStatement ps2 = conn.prepareStatement("SELECT price, quantity FROM stock WHERE id = ?");
                ps2.setInt(1, stockId3);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    double currentPrice = rs2.getDouble("price");
                    int availableQty = rs2.getInt("quantity");

                    double totalSellAmount = currentPrice * sellQty;

                    
                    PreparedStatement updateBalance = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE id = ?");
                    updateBalance.setDouble(1, totalSellAmount);
                    updateBalance.setInt(2, userId3);
                    updateBalance.executeUpdate();

                   
                    int newStockQty = availableQty + sellQty;
                    PreparedStatement updateStock = conn.prepareStatement("UPDATE stock SET quantity = ? WHERE id = ?");
                    updateStock.setInt(1, newStockQty);
                    updateStock.setInt(2, stockId3);
                    updateStock.executeUpdate();

                   
                    if (sellQty == ownedQty) {
                        PreparedStatement delete = conn.prepareStatement("DELETE FROM portfolio WHERE user_id = ? AND stock_id = ?");
                        delete.setInt(1, userId3);
                        delete.setInt(2, stockId3);
                        delete.executeUpdate();
                    } else {
                        int remainingQty = ownedQty - sellQty;
                        PreparedStatement updatePort = conn.prepareStatement("UPDATE portfolio SET quantity = ? WHERE user_id = ? AND stock_id = ?");
                        updatePort.setInt(1, remainingQty);
                        updatePort.setInt(2, userId3);
                        updatePort.setInt(3, stockId3);
                        updatePort.executeUpdate();
                    }

                    System.out.println(" Sold " + sellQty + " shares successfully!");
                } else {
                    System.out.println(" Stock not found.");
                }
            } else {
                System.out.println(" You don’t own enough shares.");
            }
        } else {
            System.out.println(" You don’t own this stock.");
        }

        conn.close();

    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
    }
                    break;
                case 4: 
                    System.out.println("********View Portfolio********");
                    
  
    System.out.print("Enter your User ID: ");
    int userId4 = sc.nextInt();

    try {
        Connection conn = DBConnection.connect();

        
        PreparedStatement ps = conn.prepareStatement("SELECT stock_id, quantity, buy_price FROM portfolio WHERE user_id = ?");
        ps.setInt(1, userId4);
        ResultSet rs = ps.executeQuery();

        boolean hasPortfolio = false;

        while (rs.next()) {
            hasPortfolio = true;
            int stock_Id = rs.getInt("stock_id");
            int qty = rs.getInt("quantity");
            double buyPrice = rs.getDouble("buy_price");

            PreparedStatement ps2 = conn.prepareStatement("SELECT name, price FROM stock WHERE id = ?");
            ps2.setInt(1, stock_Id);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                String name = rs2.getString("name");
                double currentPrice = rs2.getDouble("price");

                double totalBuy = buyPrice * qty;
                double totalCurrent = currentPrice * qty;
                double profitLoss = totalCurrent - totalBuy;

                System.out.println("-----------------------------------------");
                System.out.println("Stock: " + name);
                System.out.println("Quantity: " + qty);
                System.out.println("Buy Price: Rs. " + buyPrice);
                System.out.println("Current Price: Rs. " + currentPrice);
                System.out.println("Total Investment: Rs. " + totalBuy);
                System.out.println("Current Value: Rs. " + totalCurrent);
                System.out.println((profitLoss >= 0 ? "Profit: " : "Loss: ") + profitLoss);
                System.out.println("-----------------------------------------");
            }
        }

        if (!hasPortfolio) {
            System.out.println(" You don't have any stocks yet.");
        }

        conn.close();

    } catch (Exception e) {
        System.out.println(" Error: " + e.getMessage());
    }
                    break;
                case 5:
                    System.out.println("********View Balance********");
                      
    System.out.print("Enter your User ID: ");
    int userId5 = sc.nextInt();

    try {
        Connection conn = DBConnection.connect();
        PreparedStatement ps = conn.prepareStatement("SELECT balance FROM users WHERE id = ?");
        ps.setInt(1, userId5);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            double balance = rs.getDouble("balance");
            System.out.println(" Your current balance is: Rs. " + balance);
        } else {
            System.out.println(" User not found.");
        }

        conn.close();

    } catch (Exception e) {
        System.out.println(" Error: " + e.getMessage());
    }

        break;
        case 6: 
             System.out.println("Thank you for using Stock Trading Platform!");
    System.exit(0);
    
    
         default:
                    System.out.println("********Invalid choice********");
                    break;}
            
            
             }          
    }
}
