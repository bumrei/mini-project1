package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Lanking {
  Connection CN = null;  
  Statement ST = null; 
  ResultSet RS = null;  
  String msg = "isud=crud쿼리문 기술";
  Scanner sc = new Scanner(System.in);

  public void lanking() throws Exception {
    dbConnect();
    msg = "select ID, score from member order by score desc";
    RS = ST.executeQuery(msg);
    System.out.println("아이디\t점 수");
    while (RS.next() == true) {
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.println(uid+"\t" + uscore);
    }
  }

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver"); 
    String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
    CN = DriverManager.getConnection(url , "system", "1234"); 
    ST = CN.createStatement();
  }



}

