package com.software.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    //loads up the list class
    List<Customer> customers;
    CustomerTableGateway gateway;
    
    List<Branch> branches;
    BranchTableGateway branchGateway;

    // the model class is private so it wont be accessed anywhere else.
    private Model() {
        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new CustomerTableGateway(conn);
            this. branchGateway = new BranchTableGateway(conn);

         this.customers = this.gateway.getCustomers();
         this.branches = this.branchGateway.getBranches();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addCustomer(Customer c) {
        boolean result = false;
        try {
            // The gateway returns an id and then the  get method is called to insert value in the parameter.
            int id = this.gateway.insertCustomer(c.getName(), c.getEmail(), c.getMobileNumber(), c.getAddress(), c.getDateRegistered(), c.getBranchID());
            //if statement works if the id is not equal to -1 and then  the boolean wil be true.
            if (id != -1) {
                c.setCustomerID(id);
                this.customers.add(c);
                result = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean removeCustomer(Customer c) {
        boolean removed = false;

        try {
            // removes the customer in the database with this customerid stated
            removed = this.gateway.deleteCustomer(c.getCustomerID());
            if (removed) {
                removed = this.customers.remove(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    Customer findCustomerByCustomerID(int customerId) {
        Customer c = null;
        int i = 0;
        boolean found = false;
        while (i < this.customers.size() && !found) {
            c = this.customers.get(i);
            if (c.getCustomerID() == customerId) {
                found = true;
            } else {
                i++;

            }
        }
        if (!found) {
            c = null;
        }
        return c;
    }

    boolean updateCustomer(Customer c) {
        boolean updated = false;

        try {
            updated = this.gateway.updateCustomer(c);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }



   public boolean addBranch(Branch b) {
        boolean result = false;
        try {                                   
            // The gateway returns an id and then the  get method is called to insert value in the parameter.
            int id = this.branchGateway.insertBranch(b.getAddress(), b.getNumber(), b.getOpeningHours(),  b.getManagerName());
            //if statement works if the id is not equal to -1 and then  the boolean wil be true.
            if (id != -1) {
                b.setBranchID(id);
                this.branches.add(b);
                result = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

   
      public boolean removeBranch(Branch b) {
        boolean removed = false;

        try {
            // removes the customer in the database with this customerid stated
            removed = this.branchGateway.deleteBranch(b.getBranchID());
            if (removed) {
                removed = this.branches.remove(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }


      public List<Branch>getBranches() {
        return this.branches;
    }
      
      
       Branch findBranchByBranchID (int branchID) {
        Branch b = null;
        int i = 0;
        boolean found = false;
        while (i < this.branches.size() && !found) {
            b = this.branches.get(i);
            if (b.getBranchID() == branchID) {
                found = true;
            } else {
                i++;

            }
        }
        if (!found) {
            b = null;
        }
        return b;
    }
       
       
        boolean updateBranch(Branch b) {
        boolean updated = false;

        try {
            updated = this.branchGateway.updateBranch(b);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }
       
       









}

    