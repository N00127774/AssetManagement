package com.software.app;

import java.util.Comparator;

public class CustomerIDComparator  implements Comparator<Customer>{

    @Override
    public int compare(Customer c1, Customer c2) {
        return (int)(c1.getCustomerID() - c2.getCustomerID());
       //if c1 greater than c2 result= negative.
       // if c2 greater than c1 result = positive.
       // if c1=c2 result= 0.        
    }
    
}
