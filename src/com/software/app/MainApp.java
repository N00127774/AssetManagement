package com.software.app;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Model model = Model.getInstance();
        int opt;

        do {
            // Different Options(Menu)Building up the Menu interface
            System.out.println("1. Create new Customer");
            System.out.println("2. Deleteikkk existing Customers");
            System.out.println("3. Edit existing Customers");
            System.out.println("4. View all Customers");
            System.out.println();
            System.out.println("5. Create new Branch");
            System.out.println("6. Delete existing Branch");
            System.out.println("7. Edit existing Branch");
            System.out.println("8. View all Branches");
            System.out.println();
            System.out.println("9. Exit");
            System.out.println();

            //User Input(Int) When you input a number it prints out the number you input.
            System.out.print("Enter option: ");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            System.out.println("You chose option " + opt);

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
                    viewCustomers(model);
                    break;

                }
                
                
                case 5: {
                    System.out.println("Creating branches");
                    createBranch(keyboard, model);
                    break;
                }

                case 6: {
                    System.out.println("Deleting branches");
                    deleteBranch(keyboard, model);
                    break;
                }

                case 7: {
                    System.out.println("Editing branches");
                    editBranch(keyboard, model);
                    break;

                }
                case 8: {
                    System.out.println("Viewing branches");
                    viewBranchs(model);
                    break;

                }
                
            }
        } //This is the while Loop, this loop keeps going unless the number 4 as been enterened,
        //When the digit number four as been entered, the exit option is selected.
        //This is the second step after I have the Menu part done printing out the messages.    
        while (opt != 9);
    }

    private static void createCustomer(Scanner keyb, Model mdll) {
        //keyboard created
        Customer c = readCustomer(keyb);

        // if statement for if the customr as been added or not
        if (mdll.addCustomer(c)) {
            System.out.println("Customer added to database.");
        } else {
            System.out.println("Customer not added to database.");
        }
        System.out.println();
    }

    private static void deleteCustomer(Scanner keyboard, Model model) {
        System.out.println("Enter the CustomerID of the customer to delete");
        int customerId = Integer.parseInt(keyboard.nextLine());
        Customer c;

        c = model.findCustomerByCustomerID(customerId);
        if (c != null) {
            if (model.removeCustomer(c)) {
                System.out.println("Customer deleted");
            } else {
                System.out.println("Customer not deleted");
            }
        } else {
            System.out.println("Customer not found");
        }
    }

    private static void editCustomer(Scanner kb, Model m) {
        System.out.print("Enter the customerID of the customer to edit:");
        int customerId = Integer.parseInt(kb.nextLine());
        Customer c;

        c = m.findCustomerByCustomerID(customerId);
        if (c != null) {
            editCustomerDetails(kb, c);
            if (m.updateCustomer(c)) {
                System.out.println("Customer updated");

            } else {
                System.out.println("Customer not updated");
            }
        } else {
            System.out.println("Customer not found");
        }
    }

    //different variable from the one in the CustomerTableGateway   

    private static void viewCustomers(Model model) {
        List<Customer> customers = model.getCustomers();
        System.out.println();
        if (customers.isEmpty()) {
            System.out.println("There are no customers in the database");
        } else {
            System.out.printf("%25s %20s %18s %50s %25s %15s %20s\n", "Name", "email", "MobileNumber", " Address ", " DateRegistered ", " CustomerID ", "BranchID:");
            for (Customer cr : customers) {
                System.out.printf("%25s %20s %18s %50s %25s %15s %20s\n",
                        cr.getName(),
                        cr.getEmail(),
                        cr.getMobileNumber(),
                        cr.getAddress(),
                        cr.getDateRegistered(),
                        cr.getCustomerID(),
                        cr.getBranchID());

            }
                           // The if statement prints out if there are no customer in database, 
            // The cr is a variable that stores the customers objects inside.

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
    
         
            line = getString(keyb, "Enter branch ID:");
            branchID= Integer.parseInt(line);
  

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
        line2 = getString(keyb, "Enter Branch ID [" + c.getBranchID() + "]: ");

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

        if (line2.length() != 0) {
          branchID = Integer.parseInt(line2);
            c.setBranchID(branchID );
        }
    }
    
        private static void createBranch(Scanner keyb, Model mdll) {
        //keyboard created
        Branch b = readBranch(keyb);

        // if statement for if the customr as been added or not
        if (mdll.addBranch(b)) {
            System.out.println("Branch added to database.");
        } else {
            System.out.println("Branch not added to database.");
        }
        System.out.println();
    }

    private static void deleteBranch(Scanner keyboard, Model model) {
        System.out.println("Enter the BranchID of the branch to delete");
        int branchID = Integer.parseInt(keyboard.nextLine());
        Branch b;

        b = model.findBranchByBranchID(branchID);
        if (b != null) {
            if (model.removeBranch(b)) {
                System.out.println("Branch deleted");
            } else {
                System.out.println("Branch not deleted");
            }
        } else {
            System.out.println("Branch not found");
        }
    }

    private static void editBranch(Scanner kb, Model m) {
        System.out.print("Enter the branchID of the branch to edit:");
        int branchID = Integer.parseInt(kb.nextLine());
        Branch b;

        b = m.findBranchByBranchID(branchID);
        if (b != null) {
            editBranchDetails(kb, b);
            if (m.updateBranch(b)) {
                System.out.println("Branch updated");

            } else {
                System.out.println("Branch not updated");
            }
        } else {
            System.out.println("Branch not found");
        }
    }

    //different variable from the one in the BranchTableGateway   

    private static void viewBranchs(Model model) {
        List<Branch> branchs = model.getBranches();
        System.out.println();
        if (branchs.isEmpty()) {
            System.out.println("There are no branchs in the database");
        } else {
            System.out.printf("%45s %25s %25s %30s\n", "address", "number", "openingHours", " managerName "); 
            for (Branch br : branchs) {
                System.out.printf("%45s %25s %25s %30s\n",
                        br.getAddress(),
                        br.getNumber(),
                        br.getOpeningHours(),
                        br.getManagerName());
                       
            }
                           // The if statement prints out if there are no branch in database, 
            // The cr is a variable that stores the branchs objects inside.

        }
        System.out.println();

    }

    private static Branch readBranch(Scanner keyb) {
        String address, number, openingHours, managerName;
   
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
