package com.software.app;

//Customer Class
public class Customer {

    //declaring the attributes

    private String name;
    private String email;
    private String mobileNumber;
    private String address;
    private String dateRegistered;
    private int customerID;
    private int branchID;

    //Constructor
    public Customer(String nm, String e, String m, String a, String d, int cId,int bId) {
        this.name = nm;
        this.email = e;
        this.mobileNumber = m;
        this.address = a;
        this.dateRegistered = d;
        this.customerID = cId;
        this.branchID=bId;

    }

    //Second Constructor with default ID

    public Customer(String nm, String e, String m, String a, String d, int bId) {
        this(nm, e, m, a, d, -1,bId);
    }

    //Get and Set mehods.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getBranchID(){
        return branchID;
    }
    
    public void setBranchID(){
        this.branchID = branchID;
    }
    
    
}
