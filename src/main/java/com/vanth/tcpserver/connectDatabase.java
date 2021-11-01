package com.vanth.tcpserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDatabase {
	public static Connection getConnection()
    {
        Connection ketnoi = null;
        String uRL = "jdbc:sqlserver://localhost:1433;databaseName=VehicleTracking";
        String user = "api";
        String pass = "123";
        try 
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ketnoi = DriverManager.getConnection(uRL, user, pass);
//            System.out.println("Connection success");
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            System.err.println("Connection fail");
            e.printStackTrace();
        }
        return ketnoi;
    }
//    public static void main(String args[])
//    {
//        getConnection();
//    }
}
