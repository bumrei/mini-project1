package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class MSG {
  Connection CN ;
  Statement ST;
  ResultSet RS;
  String msg;
  Scanner sc = new Scanner(System.in);

  String userID = "ID1";

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public static void main(String[] args)throws Exception {
    MSG m = new MSG();
    m.message();
  }

  public void message()  throws Exception {
    System.out.println("\n  [쪽지함]");

    this.dbConnect();

    String toID, mess, sID;
    java.sql.Date mdate;
    System.out.println("─────────────────────────────────────────────────────");
    msg = "select * from message order by code asc";
    RS = ST.executeQuery(msg);
    while(RS.next() == true) {
      sID = RS.getString("ID");
      toID = RS.getString("ToID");
      mess = RS.getString("mess");
      mdate = RS.getDate("mdate");

      if (sID.equals(userID)) {
        System.out.println(mdate + " \t  " + "To. " + toID +"  \t " + mess);
      } else {
        System.out.println(mdate + " \t" + "From. " + sID +"  \t " + mess);
      }
    }
    System.out.println("\n\n쪽지를 보내시겠습니까? (Y/n)");
    String ans = sc.nextLine();
    if (ans.equals("n")) {
      System.out.println("\n쪽지를 보내지 않습니다.");
    } else {
      System.out.println();
      sendMess();
    }
  }

  public void sendMess() throws Exception{
    try {
      String  tid, mess ;
      System.out.println("  [쪽지보내기]");
      while(true) { 
        System.out.print("\n쪽지를 받을 ID를 입력해주세요 (8: 뒤로가기)\n >>> ");
        tid = sc.nextLine();

        if (tid.equals("8")) {
          break;
        }

        msg = "select * from member where id = '" + tid +"'";
        RS = ST.executeQuery(msg);
        if (RS.next() == true) {

          System.out.print("\n쪽지 내용을 입력해주세요\n >>> ");
          mess = sc.nextLine();

          if(mess == null) {
            System.out.print("\n내용을 입력해주세요.\n >>> ");
            return;
          }

          if (mess.length()>25) {
            System.out.println("\n25자이내로 작성해주세요");
            System.out.print("\n내용을 입력해주세요.\n >>> ");
            mess = sc.nextLine();
            continue; 
          } 
          msg = "insert into message values(message_seq.nextval, '"+userID+"', '"+mess+"', sysdate, '"+tid+"')";
          ST.executeUpdate(msg);
          System.out.println("\n쪽지를 " + tid +" 님에게 전송했습니다."); 
          return; 
        }else {
          System.out.println("\nID를 다시 확인해주세요");
        }
      }
    } catch(Exception e) {System.out.println("errorcom: "+e);}
  }

}
