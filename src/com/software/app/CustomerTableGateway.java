package com.software.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerTableGateway {

    private Connection mConnection;
    private static final String TABLE_NAME = "customers";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_MOBILE = "mobileNumber";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_DATEREGISTERED = "dateRegistered";
    private static final String COLUMN_CUSTOMERID = "customerId";
    private static final String COLUMN_BRANCHID =  "branchID";

    public CustomerTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertCustomer(String n, String e, String m, String a, String d, int bId) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        int id = -1;
        Date date;

        query = "INSERT INTO " + TABLE_NAME + "("
                + COLUMN_NAME + ", "
                + COLUMN_EMAIL + ", "
                + COLUMN_MOBILE + ", "
                + COLUMN_ADDRESS + ", "
                + COLUMN_DATEREGISTERED + ", " 
                + COLUMN_BRANCHID +
                 ")VALUES (?, ?, ?, ?, ?, ?)";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = new Date(format.parse(d).getTime());
        } catch (ParseException ex) {
            date = null;
        }

        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, n);
        stmt.setString(2, e);
        stmt.setString(3, m);
        stmt.setString(4, a);
        stmt.setDate(5, date);
        //gettin an int
        stmt.setInt (6, bId);
        
        if (bId == -1) {
            stmt.setNull(6, java.sql.Types.INTEGER);
        }
        
        else {
            stmt.setInt(6, bId);
        }
        
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            //if one row was inserted,retrieve the id assigned to that row

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);

        }
        //return the id assigned to the row in the database
        return id;
    }

    public boolean deleteCustomer(int id) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        //the required SQL DELETE statement with place holders for the id of the row to be remove from the database
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_CUSTOMERID + " = ?";

        //create a PreparedStatement object to execute the query and insert the customer id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        //excute query
        numRowsAffected = stmt.executeUpdate();
        //return true if only one row was deleted from the the database
        return (numRowsAffected == 1);

    }

    public List<Customer> getCustomers() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of // SQL query 
        List<Customer> customers;   // the java.util.List containing the Customer  
        // created for each row in the result of the query
        // the id of a customer

        String name, email, mobileNumber, address, dateRegistered;
        int customerId, branchID;
        Customer c;       // a Customer object created from a row in the result of
        // the query

                    // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

                    // Iterate through the result set, extraction the data from each row
        //and storing it in a Customer object, which is inserted  into an intially 
        // empty ArrayList
        customers = new ArrayList<Customer>();
        while (rs.next()) {

            name = rs.getString(COLUMN_NAME);
            email = rs.getString(COLUMN_EMAIL);
            mobileNumber = rs.getString(COLUMN_MOBILE);
            address = rs.getString(COLUMN_ADDRESS);
            dateRegistered = rs.getString(COLUMN_DATEREGISTERED);
            customerId = rs.getInt(COLUMN_CUSTOMERID);
            branchID= rs.getInt(COLUMN_BRANCHID);

            if (rs.wasNull()) {
                branchID = -1;
            }
            
            c = new Customer(name, email, mobileNumber, address, dateRegistered, customerId, branchID);
            customers.add(c);
        }

        return customers;
    }

    boolean updateCustomer(Customer c) throws SQLException {
        String query;  //the SQL query excute
        PreparedStatement stmt;
        int numRowsAffected;
        int bId;

        //the required SQL INSERT statement place holders for the values to be inserted into the database
        query = " UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + "=?, "
                + COLUMN_EMAIL + "=?, "
                + COLUMN_MOBILE + "=?, "
                + COLUMN_ADDRESS + "=?, "
                + COLUMN_DATEREGISTERED + "=?, "
                + COLUMN_BRANCHID + "=? "
                + " WHERE " + COLUMN_CUSTOMERID + " = ?";

        //create a PreparedStatment object to excute the query and insert the new valuies into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, c.getName());
        stmt.setString(2, c.getEmail());
        stmt.setString(3, c.getMobileNumber());
        stmt.setString(4, c.getAddress());
        stmt.setString(5, c.getDateRegistered());
        stmt.setInt(7, c.getCustomerID());
        stmt.setInt(6, c.getBranchID());
        bId = c.getBranchID();
        
        if (bId == -1) {
            stmt.setNull(6,  java.sql.Types.INTEGER);
        }
        
        else {
            stmt.setInt(6, bId);
        }
        stmt.setInt(7, c.getCustomerID());
        // excute the query
        numRowsAffected = stmt.executeUpdate();

        //return the true if one and only one row was updated in the database
        return (numRowsAffected == 1);

    }
}
