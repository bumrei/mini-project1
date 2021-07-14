package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class dao {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String sql = null;
  Scanner sc = new Scanner(System.in);
  int uMemno;
  String uName = null;
  String uID = null;
  String uPsw = null;
  String uEmail = null;
  Date uCdate = null;
  int uScore;
  int uadmin;
  String userID = LogInMenu.userID;
  String userPsw = null;
  int userAdmin;
  String ucmt = null;
  String delId = null;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  
  
 
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
