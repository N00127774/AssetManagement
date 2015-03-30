package com.software.app;

public class Branch {
 private int branchID;
 private String address;
 private String number;
 private String openingHours;
 private String managerName;
 
 
 public Branch(int bId, String a, String n, String o, String mn){
 
  this.branchID=bId;
  this.address=a;
  this.number=n;
  this.openingHours=o;
  this.managerName=mn;

}
 
 public Branch(String a,String n, String o, String mn){
     this(-1,a,n,o,mn);
 }
 
 /* get methods*/
 public int getBranchID (){
     return branchID;
 }
 
 public String getAddress(){
     return address;
 }
 
 public String getNumber(){
    return number; 
 }
 
 public String getOpeningHours(){
     return openingHours;
 }
 
 public String getManagerName(){
     return managerName;
 }
 
 
 /* set Methods*/
 public void setBranchID(int branchID){
     this.branchID= branchID;
 }
 
 public void setAddress(String address){
     this.address=address;
 }        
 
 public void setNumber(String number){
     this.number=number;
 }
 
 public void setOpeningHours(String openingHours){
     this.openingHours=openingHours;
 }
 
 public void setManagerName(String managerName){
     this.managerName=managerName;
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
}
