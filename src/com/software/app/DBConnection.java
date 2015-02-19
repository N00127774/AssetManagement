package com.software.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection sConnection;

    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        String host, db, user, password;

        host = "daneel";
        db = "N00127774";
        user = "N00127774";
        password = "N00127774";

        //the if statement  just basically checks if connection is null or closed 
        //if closed the jbc.driver is loaded up, and if its not found the url string 
        //is created and connects to the mysql database.
        //the connection is made by giving my details to log in
        if (sConnection == null || sConnection.isClosed()) {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver");
            // giving the sConnection object refrences by passing in the url user and password
            sConnection = DriverManager.getConnection(url, user, password);
        }

        return sConnection;
    }

}
