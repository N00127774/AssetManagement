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
            System.out.println("2. Delete existing Customers");
            System.out.println("3. Edit existing Customers");
            System.out.println("4. View all Customers");
            System.out.println("5. Exit");
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
            }
        } //This is the while Loop, this loop keeps going unless the number 4 as been enterened,
        //When the digit number four as been entered, the exit option is selected.
        //This is the second step after I have the Menu part done printing out the messages.    
        while (opt != 5);
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
            System.out.printf("%-25s %-20s %18s %50s %25s %15s\n", "Name", "email", "MobileNumber", " Address ", " DateRegistered ", "CustomerID");
            for (Customer cr : customers) {
                System.out.printf("%-25s %-20s %18s %50s %25s  %15d\n",
                        cr.getName(),
                        cr.getEmail(),
                        cr.getMobileNumber(),
                        cr.getAddress(),
                        cr.getDateRegistered(),
                        cr.getCustomerID());
            }
                           // The if statement prints out if there are no customer in database, 
            // The cr is a variable that stores the customers objects inside.

        }
        System.out.println();

    }

    private static Customer readCustomer(Scanner keyb) {
        String name, email, mobileNumber, address, dateRegistered;
        int customerId;
        String line;
            // the do while loop prints out the statement repeatedly if there are no input in the frequired fields.
            //if the length of input =0 it qill print out the message again till there's something inserted.
        do {
            name = getString(keyb, "Enter name:");
        } while (name.length() == 0);

        do {
            email = getString(keyb, "Enter email:");
        } while (email.length() == 0);

        do {
            mobileNumber = getString(keyb, "Enter MobileNumber:");
        } while (mobileNumber.length() == 0);

        do {
            address = getString(keyb, "Enter Address:");
        } while (address.length() == 0);

        do {
            dateRegistered = getString(keyb, "Enter Date Registered:");
        } while (dateRegistered.length() == 0);

            //this do while loop will continue to ask user to input something
        //becuase the  fileds cannot be empty due to the settings on mySQL database.
    //reading in the input for customer ID
    //customerId STORES THE INPUT FROM KEYBOARD, INT, into the customerID.
        Customer c
                = new Customer(name, email, mobileNumber,
                        address, dateRegistered);

        return c;
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.println(prompt);
        return keyboard.nextLine();
    }

    private static void editCustomerDetails(Scanner keyb, Customer c) {
        String name, email, mobileNumber, address, dateRegistered;
        int customerId;
        String line1;

        name = getString(keyb, "Enter name[ " + c.getName() + "]:");
        email = getString(keyb, "Enter email[" + c.getEmail() + "]:");
        mobileNumber = getString(keyb, "Enter Mobile Number" + c.getMobileNumber() + "]:");
        address = getString(keyb, "Enter Address" + c.getAddress() + "]:");
        dateRegistered = getString(keyb, "Enter Date Registered" + c.getDateRegistered() + "]:");
        line1 = getString(keyb, "Enter Customer ID [" + c.getCustomerID() + "]: ");

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

        if (line1.length() != 0) {
            customerId = Integer.parseInt(line1);
            c.setCustomerID(customerId);
        }
    }
}
