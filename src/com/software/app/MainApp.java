package com.software.app;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    
    private static final int NAME_ORDER = 1;
    private static final int CUSTOMERID_ORDER = 2;
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Model model;
        String option = null;
        int opt = 12;
     System.out.println(" Welcome Choose one of the following options:");
        do {
            try{
             model = Model.getInstance();
            // Different Options(Menu)Building up the Menu interface
            
                System.out.println();
                System.out.println();
                System.out.println("Customer Table");
                System.out.println();
                System.out.println(" 1. Create new Customer");
                System.out.println(" 2. Delete existing Customers");
                System.out.println(" 3. Edit existing Customers");
                System.out.println(" 4. View all Customers");
                System.out.println(" 5. View all Customers by Customer Id");
                System.out.println(" 6. View single Customer");
                System.out.println();
                System.out.println();
         
                System.out.println("Branch Table");
                System.out.println();
                System.out.println(" 7. Create new Branch");
                System.out.println(" 8. Delete existing Branch");
                System.out.println(" 9. Edit existing Branch");
                System.out.println(" 10. View all Branches");
                System.out.println(" 11. View single Branch");
                System.out.println();
                System.out.println();
                System.out.println("___________");
                System.out.println(" 12. Exit");
                System.out.println();

                //User Input(Int) When you input a number it prints out the number you input.

                opt = getInt(keyboard, "Enter option: " , 12);
                System.out.println();
                System.out.println("You chose option " + opt);
                System.out.println();
                System.out.println();

            // The swtich statement= If statement.the follwing cases, print out different
                //statements, depending on the number you input in.
                switch (opt) {
                    case 1: {
                        System.out.println("Creating Customer");
                        createCustomer(keyboard, model);
                        break;
                    }

                    case 2: {
                        System.out.println("Deleting Customer");
                        deleteCustomer(keyboard, model);
                        break;
                    }

                    case 3: {
                        System.out.println("Editing Customers");
                        editCustomer(keyboard, model);
                        break;

                    }
                    case 4: {
                        System.out.println("Viewing Customers");
                        viewCustomers(model, NAME_ORDER);
                        break;

                    }
                    
                    case 5: {
                        System.out.println("Viewing Customers");
                        viewCustomers(model, CUSTOMERID_ORDER);
                        break;

                    }
                    case 6: {
                        System.out.println("Viewing single Customer");
                        viewCustomer(keyboard, model);
                        break;

                    }


                    case 7: {
                        System.out.println("Creating branch");
                        createBranch(keyboard, model);
                        break;
                    }

                    case 8: {
                        System.out.println("Deleting branches");
                        deleteBranch(keyboard, model);
                        break;
                    }

                    case 9: {
                        System.out.println("Editing branches");
                        editBranch(keyboard, model);
                        break;

                    }
                    case 10: {
                        System.out.println("Viewing branches");
                        viewBranchs(model);
                        break;

                    }
                    
                    case 11: {
                        System.out.println("Viewing branch");
                        viewBranch(keyboard,model);
                        break;

                    }

                }
            }
            catch (DataAccessException e){
              System.out.println();
              System.out.println(e.getMessage());
              System.out.println();
            }
                    
        } //This is the while Loop, this loop keeps going unless the number 4 as been enterened,
        //When the digit number four as been entered, the exit option is selected.
        //This is the second step after I have the Menu part done printing out the messages.    
        while (opt != 12);
    }

    private static void createCustomer(Scanner keyb, Model mdll) throws DataAccessException {
        //keyboard created
        Customer c = readCustomer(keyb);

        // if statement for if the customr as been added or not
        if (mdll.addCustomer(c)) {
            System.out.println();
            System.out.println("Customer added to database.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Customer not added to database.");
            System.out.println();
        }
        System.out.println();
    }

    private static void deleteCustomer(Scanner keyboard, Model model) throws DataAccessException {
        int customerId = getInt(keyboard,"Enter the CustomerID of the customer to delete", -1);// open  making sure Number is input inside the field instead of words.
        Customer c;

        c = model.findCustomerByCustomerID(customerId);
        if (c != null) {
            if (model.removeCustomer(c)) {
                System.out.println();
                System.out.println("Customer deleted");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Customer not deleted");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Customer not found");
            System.out.println();
        }
    
     
    }

    private static void editCustomer(Scanner kb, Model m) throws DataAccessException {
        int customerId = getInt(kb,"Enter the customerID of the customer to edit:", -1);
        Customer c;

        c = m.findCustomerByCustomerID(customerId);
        if (c != null) {
            editCustomerDetails(kb, c);
            if (m.updateCustomer(c)) {
                System.out.println();
                System.out.println("Customer updated");
                System.out.println();

            } else {
                System.out.println();
                System.out.println("Customer not updated");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Customer not found");
            System.out.println();
        }
        
        
    }

    //different variable from the one in the CustomerTableGateway   

    private static void viewCustomers(Model mdl, int order) {
        List<Customer> customers = mdl.getCustomers();
        System.out.println();
        if (customers.isEmpty()) {
            System.out.println();
            System.out.println("There are no customers in the database");
        } 
        else {
            if (order == NAME_ORDER) {
             Collections.sort(customers);
            }
            else if (order == CUSTOMERID_ORDER){
              Comparator<Customer> cmptr = new CustomerIDComparator();
              Collections.sort(customers, cmptr);
                
            }
            displayCustomers(customers, mdl);
            
                           // The if statement prints out if there are no customer in database, 
            // The cr is a variable that stores the customers objects inside
        }
        System.out.println();

    }
    
    private static void displayCustomers(List<Customer> customers, Model mdl){
        System.out.printf("%25s %35s %25s %50s %25s %18s %20s\n", 
                "Name", "email", "Mobile Number", " Address ", " Date Registered ", " CustomerID ", "Manager Name:");
       for (Customer cr : customers){
           
        Branch b = mdl.findBranchByBranchID(cr.getBranchID());
         System.out.printf("%25s %35s %25s %50s %25s %18s %20s\n",
                        cr.getName(),
                        cr.getEmail(),
                        cr.getMobileNumber(),
                        cr.getAddress(),
                        cr.getDateRegistered(),
                        cr.getCustomerID(),
                        (b != null) ? b.getManagerName() : "");
                  
                  
          
       } 
        
    }
    
        /* VIEWING A SINGLE CUSTOMER FROM TABLE*/
        private static void viewCustomer(Scanner keyboard, Model model) throws DataAccessException {
        System.out.println();
        int customerId = getInt(keyboard,"Enter the CustomerID of the customer you want to view", -1);// open  making sure Number is input inside the field instead of words.
        System.out.println();
        Customer c;

        c = model.findCustomerByCustomerID(customerId);
        System.out.println();
        if (c != null) {
             System.out.println();
             Branch b = model.findBranchByBranchID(c.getBranchID());
            System.out.println("Name              :" + c.getName());
            System.out.println("Email             :" + c.getEmail());
            System.out.println("Mobile Number     :" + c.getMobileNumber());
            System.out.println("Address           :" + c.getAddress());
            System.out.println("Date Registered   :" + c.getDateRegistered());
            System.out.println("Manager Name       :" +((b != null) ? b.getManagerName() : ""));
        } else {
            System.out.println("Customer not found");
        }
         System.out.println();
     
    }
    
    
    
    
    
    
    
    
    
    
    private static Customer readCustomer(Scanner keyb) {
        String name, email, mobileNumber, address, dateRegistered;
        int customerId;
        int branchID;
        String line;
            // the do while loop prints out the statement repeatedly if there are no input in the frequired fields.
            //if the length of input =0 it qill print out the message again till there's something inserted.
     
            name = getString(keyb, "Enter name:");
 
  
            email = getString(keyb, "Enter email:");
  

  
            mobileNumber = getString(keyb, "Enter MobileNumber:");
     


            address = getString(keyb, "Enter Address:");
   

     
            dateRegistered = getString(keyb, "Enter Date Registered:");
    
         
            branchID = getInt(keyb, "Enter branch ID:", -1);
         
  

            //this do while loop will continue to ask user to input something
        //becuase the  fileds cannot be empty due to the settings on mySQL database.
    //reading in the input for customer ID
    //customerId STORES THE INPUT FROM KEYBOARD, INT, into the customerID.
        Customer c
                = new Customer(name, email, mobileNumber,
                        address, dateRegistered,branchID);

        return c;
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.println(prompt);
        return keyboard.nextLine();
    }

    private static void editCustomerDetails(Scanner keyb, Customer c) {
        String name, email, mobileNumber, address, dateRegistered;
        int customerId;
        int branchID;
        String line2;

        name = getString(keyb, "Enter name[ " + c.getName() + "]:");
        email = getString(keyb, "Enter email[" + c.getEmail() + "]:");
        mobileNumber = getString(keyb, "Enter Mobile Number" + c.getMobileNumber() + "]:");
        address = getString(keyb, "Enter Address" + c.getAddress() + "]:");
        dateRegistered = getString(keyb, "Enter Date Registered" + c.getDateRegistered() + "]:");
        branchID= getInt(keyb, "Enter Branch ID [" + c.getBranchID() + "]: ", 0);

        if (name.length() != 0) {
            c.setName(name);
        }
        if (email.length() != 0) {
            c.setEmail(email);
        }

        if (mobileNumber.length() != 0) {
            c.setMobileNumber(mobileNumber);
        }

        if (address.length() != 0) {
            c.setAddress(address);
        }

        if (dateRegistered.length() != 0) {
            c.setDateRegistered(dateRegistered);
        }

        if (branchID != c.getBranchID()) {
            c.setBranchID(branchID );
        }
    }
    
        private static void createBranch(Scanner keyb, Model mdll) throws DataAccessException {
        //keyboard created
        Branch b = readBranch(keyb);

        // if statement for if the customr as been added or not
        if (mdll.addBranch(b)) {
            System.out.println();
            System.out.println("Branch added to database.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Branch not added to database.");
            System.out.println();
        }
        System.out.println();
    }

    private static void deleteBranch(Scanner keyboard, Model model) throws DataAccessException {
        int branchID = getInt(keyboard,"Enter the BranchID of the branch to delete", -1);
        Branch b;

        b = model.findBranchByBranchID(branchID);
        if (b != null) {
            if (model.removeBranch(b)) {
                System.out.println();
                System.out.println("Branch deleted");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Branch not deleted");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Branch not found");
            System.out.println();
        }
        
     
        
    }

    private static void editBranch(Scanner kb, Model m) throws DataAccessException {
        int branchID = getInt(kb,"Enter the branchID of the branch to edit:", -1);
        System.out.println();  
        Branch b;

        b = m.findBranchByBranchID(branchID);
        if (b != null) {
            editBranchDetails(kb, b);
            if (m.updateBranch(b)) {
                System.out.println();
                System.out.println("Branch updated");
                System.out.println();

            } else {
                System.out.println();
                System.out.println("Branch not updated");
            }
        } else {
            System.out.println("Branch not found");
            System.out.println();
        }
        
        
      
        
        
    }

    //different variable from the one in the BranchTableGateway   

    private static void viewBranchs(Model model) {
        List<Branch> branches = model.getBranches();
        System.out.println();
    
            System.out.printf("%15s %45s %25s %25s %30s\n", " branchID ", " address", " number ", " openingHours ", " managerName "); 
            for (Branch br : branches) {
                System.out.println();
                System.out.printf("%15d %45s %25s %25s %30s\n",
                        br.getBranchID(),
                        br.getAddress(),
                        br.getNumber(),
                        br.getOpeningHours(),
                        br.getManagerName()
                );
                       
            }
                           // The if statement prints out if there are no branch in database, 
            // The cr is a variable that stores the branchs objects inside.

        
        System.out.println();

    }
    
    
    
    
       private static void viewBranch(Scanner keyboard, Model model) {
       int branchID = getInt(keyboard,"Enter the BranchID of the branch to view", -1);
        Branch b;

        b = model.findBranchByBranchID(branchID); //Search Manager with the specific ID
        if (b != null) {
          System.out.println();
          System.out.println("Address             :" + b.getAddress());
          System.out.println("Mobile  Number      :" + b.getNumber());
          System.out.println("Opening Hours       :" + b.getOpeningHours());
          System.out.println("Manager Name        :" + b.getManagerName());
          System.out.println();
        
          List<Customer> customerList = model.getCustomerByBranchID(b.getBranchID());
          System.out.println();
          if (customerList. isEmpty()) {
              System.out.println();
              System.out.println("This manager manages no customers");
          }
          else {
              System.out.println();
              System.out.println("This manager manages the following customers: ");
              System.out.println();
              displayCustomers(customerList, model);
          }
          System.out.println();
        } 
        else {
            System.out.println("Branch not found");
        }
        System.out.println();
    }
    
    
    

    private static Branch readBranch(Scanner keyb) {
        String address, number, openingHours, managerName;
        int branchID;
   
            // the do while loop prints out the statement repeatedly if there are no input in the frequired fields.
            //if the length of input =0 it qill print out the message again till there's something inserted.
        do {
            address = getString(keyb, "Enter Address");
        } while (address.length() == 0);

        do {
            number= getString(keyb, "Enter email:");
        } while (number.length() == 0);

        do {
            openingHours = getString(keyb, "Enter OpeningHours:");
        } while (openingHours .length() == 0);

        do {
           managerName = getString(keyb, "Enter Manager Name:");
        } while ( managerName.length() == 0);

       
            //this do while loop will continue to ask user to input something
        //becuase the  fileds cannot be empty due to the settings on mySQL database.
    //reading in the input for branch ID
    //branchId STORES THE INPUT FROM KEYBOARD, INT, into the branchID.
        Branch b
                = new Branch(address, number, openingHours,
                       managerName);

        return b;
    }



    private static void editBranchDetails(Scanner keyb, Branch b) {
        String address, number, openingHours, managerName;
        
    

       address = getString(keyb, "Enter Address[ " + b.getAddress() + "]:");
       number = getString(keyb, "Enter Number[" + b.getNumber() + "]:");
       openingHours= getString(keyb, "Enter Opening Hours[" + b.getOpeningHours() + "]:");
       managerName = getString(keyb, "Enter ManagerName" + b.getManagerName() + "]:");
      

        if (address.length() != 0) {
            b.setAddress(address);
        }
        if (number.length() != 0) {
            b.setNumber(number);
        }

        if (openingHours.length() != 0) {
            b.setOpeningHours(openingHours);
        }

        if (managerName.length() != 0) {
            b.setManagerName(managerName);
        }

      

       
    }
    
    
    
    private static int getInt(Scanner keyb, String prompt, int defaultValue){
       int opt = defaultValue;
       boolean finished = false;
       do {
           
           try {
            System.out.print(prompt); /* user puttin in a letter instead of number*/
            String line = keyb.nextLine();
            if(line.length()> 0) {
            opt = Integer.parseInt(line);
            }
              finished = true;/* if int passed in finisehd will be true, and it loops again, but if letter jobs out of loop and prints out the message.*/
           
           } 
           catch(NumberFormatException e){
               System.out.println("Exception: " + e.getMessage());
           }
           
           
         }
         while(!finished);
        return opt;   /*returning value*/
    }

 
    
}
   
    
    
    

