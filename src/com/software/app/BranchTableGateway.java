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

public class BranchTableGateway {

    private Connection mConnection;
    
    private static final String TABLE_NAME = "branches";
    private static final String COLUMN_BRANCHID = "branchID";
     private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_OPENINGHOURS = "openingHours";
    private static final String COLUMN_MANAGERNAME = "managerName";



    public BranchTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertBranch(String a, String n, String o, String mn) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        int id = -1;
        /*Date date;*/

        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_ADDRESS + ", "
                + COLUMN_NUMBER + ", "
                + COLUMN_OPENINGHOURS  + ", "
                + COLUMN_MANAGERNAME
               
                + ") VALUES (?, ?, ?, ?)";

      /*  DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = new Date(format.parse(d).getTime());
        } catch (ParseException ex) {
            date = null;
        }*/

        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, a);
        stmt.setString(2, n);
        stmt.setString(3, o);
        stmt.setString(4, mn);
       

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

    public boolean deleteBranch(int id) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        //the required SQL DELETE statement with place holders for the id of the row to be remove from the database
        query =" DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BRANCHID + " = ? ";

        //create a PreparedStatement object to execute the query and insert the customer id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        //excute query
        numRowsAffected = stmt.executeUpdate();
        //return true if only one row was deleted from the the database
        return (numRowsAffected == 1);

    }

    public List<Branch> getBranches() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of // SQL query 
        List<Branch> branches;   // the java.util.List containing the Customer  
        // created for each row in the result of the query
        // the id of a branch
        int branchID;
        String address , number, openingHours, managerName;
      
        Branch b;       // a Branch object created from a row in the result of
        // the query

                    // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

                    // Iterate through the result set, extraction the data from each row
        //and storing it in a Branch object, which is inserted  into an intially 
        // empty ArrayList
        branches = new ArrayList<Branch>();
        while (rs.next()) {
            
            branchID = rs.getInt(COLUMN_BRANCHID);
            address = rs.getString(COLUMN_ADDRESS);
            number= rs.getString(COLUMN_NUMBER);
            openingHours = rs.getString(COLUMN_OPENINGHOURS);
            managerName =  rs.getString(COLUMN_MANAGERNAME);
          

            b = new Branch(branchID, address, number, openingHours, managerName);
            branches.add(b);
        }

        return branches;
    }

    boolean updateBranch(Branch b) throws SQLException {
        String query;  //the SQL query excute
        PreparedStatement stmt;
        int numRowsAffected;

        //the required SQL INSERT statement place holders for the values to be inserted into the database
        query = " UPDATE " + TABLE_NAME + " SET "
                + COLUMN_ADDRESS    + " = ?, "
                + COLUMN_NUMBER     + " = ?, "
                + COLUMN_OPENINGHOURS + " = ?, "
                + COLUMN_MANAGERNAME   + " = ? "
             
                + " WHERE " + COLUMN_BRANCHID + " = ?";

        //create a PreparedStatment object to excute the query and insert the new valuies into the query
        stmt = mConnection.prepareStatement(query);
       
        stmt.setString(1, b.getAddress());
        stmt.setString(2, b.getNumber());
        stmt.setString(3, b.getOpeningHours());
        stmt.setString(4, b.getManagerName());
        stmt.setInt(5, b.getBranchID());
        

        // excute the query
        numRowsAffected = stmt.executeUpdate();

        //return the true if one and only one row was updated in the database
        return (numRowsAffected == 1);

    }
}

