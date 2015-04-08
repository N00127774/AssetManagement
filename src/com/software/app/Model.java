package com.software.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() throws DataAccessException {
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
    private Model() throws DataAccessException {
        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new CustomerTableGateway(conn);
            this.branchGateway = new BranchTableGateway(conn);

         this.customers = this.gateway.getCustomers();
         this.branches = this.branchGateway.getBranches();
        } catch (ClassNotFoundException ex) {
          throw new DataAccessException("Exception initialisingf Model Object: " + ex.getMessage());
        } catch (SQLException ex) {
          throw new DataAccessException("Exception initialisingf Model Object: " + ex.getMessage());
        }
    }

    public boolean addCustomer(Customer c) throws DataAccessException {
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
                   throw new DataAccessException("Exception adding customer: " + ex.getMessage());
        }
        return result;
    }

    public boolean removeCustomer(Customer c) throws DataAccessException {
        boolean removed = false;

        try {
            // removes the customer in the database with this customerid stated
            removed = this.gateway.deleteCustomer(c.getCustomerID());
            if (removed) {
                removed = this.customers.remove(c);
            }
        } catch (SQLException ex) {
                     throw new DataAccessException("Exception removing customer: " + ex.getMessage());
        }
        return removed;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }
    
    public List<Customer> getCustomerByBranchID(int branchID){
     List<Customer> list = new ArrayList<Customer>();
     for (Customer c : this.customers) {
         if (c.getBranchID() == branchID){
             list.add(c);
         } 
      }
        return list;
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

    boolean updateCustomer(Customer c) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.gateway.updateCustomer(c);
        } catch (SQLException ex) {
                    throw new DataAccessException("Exception updating customer: " + ex.getMessage());
        }

        return updated;
    }



   public boolean addBranch(Branch b) throws DataAccessException {
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
                     throw new DataAccessException("Exception adding Branch: " + ex.getMessage());
        }
        return result;
    }

   
      public boolean removeBranch(Branch b) throws DataAccessException {
        boolean removed = false;

        try {
            // removes the customer in the database with this customerid stated
            removed = this.branchGateway.deleteBranch(b.getBranchID());
            if (removed) {
                removed = this.branches.remove(b);
            }
        } catch (SQLException ex) {
          throw new DataAccessException("Exception removing branch: " + ex.getMessage());
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
       
       
        boolean updateBranch(Branch b) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.branchGateway.updateBranch(b);
        } catch (SQLException ex) {
                     throw new DataAccessException("Exception updating branch: " + ex.getMessage());
        }

        return updated;
    }
       
       









}

    