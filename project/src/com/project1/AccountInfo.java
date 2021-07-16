package com.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AccountInfo {

  Connection CN ;
  Statement ST ;
  ResultSet RS ;
  PreparedStatement PST ;
  Scanner sc = new Scanner(System.in);
  String msg , ans, cEmail , sPsw , upsw, fpsw;
  String userID = LogInMenu.userID;
  JoinMember jm = new JoinMember();

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void info() throws Exception {
    dbConnect();
    System.out.println("\n\n               내정보              ");
    System.out.println("─────────────────────────────────────── ");
    msg = "select * from member where ID = '" + userID + "'";

    RS = ST.executeQuery(msg);
    if (RS.next() == true) {
      String userName = RS.getString("NAME");
      userID = RS.getString("ID");
      String userEmail = RS.getString("EMAIL");
      int userScore = RS.getInt("SCORE");

      System.out.println("  Name   :\t"+ userName );
      System.out.println("  I  D   :\t"+ userID );
      System.out.println("  Email  :\t"+ userEmail );
      System.out.println("  SCORE  :\t"+ userScore );

      if(RS.getString("comnt") != null ) {
        String usercomnt = RS.getString("comnt");
        System.out.println(" Comment :\t"+ usercomnt );
      }
      Loop: while(true) {
        System.out.println("\n\n[1. 이메일 변경]   [2. 비밀번호 변경]   [3. 점수 초기화]   [4. 건의사항]"
            + "   [5. 회원탈퇴]   [8. 뒤로가기]");
        System.out.print(" >>> ");
        String command = sc.nextLine();
        switch (command) {
          case "1": chgEmail();  break;
          case "2": chgPasswd();  break;
          case "3": resetScore();  break;
          case "4": comnt();  break;
          case "5": delId();  System.out.println("\n\n안녕히가세요!"); System.exit(0);
          case "8": System.out.println("\n뒤로가기\n");  break Loop;
          default : System.out.println("\n번호를 잘못입력하셨습니다 다시 입력해주세요. "); break;
        }  
      }
    }//if
  }


  public void chgEmail() {
    try {
      Loop: while(true) {
        System.out.println("\n  [이메일 변경]");
        System.out.print("\n변경할 이메일을 입력하세요.\n >>> ");
        cEmail = sc.nextLine();
        if(jm.stringCheck(cEmail)) {continue;}
        if(!cEmail.contains("@")) {
          System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
          continue;
        }
        msg = "update member set email = '"+cEmail+"' where id = '"+userID+"'";
        ST.executeUpdate(msg);
        System.out.println("\n변경된 이메일: " + cEmail);
        System.out.println("\n변경이 완료되었습니다.");
        break Loop;
      }
    } catch(Exception e) {System.out.println("error: " + e);}

  }

  public void chgPasswd() {
    try {
      Loop: while(true) {
        System.out.println("\n  [비밀번호 변경]");
        System.out.print("\n현재 비밀번호를 입력해주세요\n >>> ");
        ans = sc.nextLine();
        msg = "select * from member where psw = '"+ans+"' and id = '"+userID+"'";
        RS = ST.executeQuery(msg);
        if (!RS.next() == true) {
          System.out.println("입력한 비밀번호를 다시 확인하세요.");
          continue;
        }
        System.out.print("\n변경할 비밀번호를 입력하세요.\n >>> ");
        String sPsw = sc.nextLine();

        if(jm.stringCheck(sPsw)) {continue;}
        while(true) {
          System.out.print("\n비밀번호 재확인\n >>> ");
          String tmp = sc.nextLine();
          if(sPsw.equals(tmp)) {
            System.out.println("\n비밀번호가 일치합니다.");
            msg = "update member set psw = '"+sPsw+"' where id = '"+userID+"'";
            ST.executeUpdate(msg);
            System.out.println("\n변경된 비밀번호: " + sPsw);
            System.out.println("\n변경이 완료되었습니다.");
            break Loop;
          }else {
            System.out.println("비밀번호 불일치. 다시 입력하세요.");
            continue;
          }//if end
        }
      }
    } catch(Exception e) {System.out.println("error: " + e);}
  }

  public void resetScore() {
    try {
      System.out.println("\n  [점수 초기화]");
      System.out.print("\n점수를 초기화 하시겠습니까? (y/N)\n >>> ");
      ans = sc.nextLine();
      if (ans.equals("y")) {
        msg = "update member set score = 0 where id = '" + userID + "'";
        ST.executeUpdate(msg);
        System.out.println("\n점수가 초기화되었습니다.");
      } else {
        System.out.println("\n초기화 취소");
      }
    } catch(Exception e) {System.out.println("error: " + e);}
  }

  public void comnt() {
    try {
      String  comnt = "";
      System.out.println("\n  [건의사항]");
      System.out.print("\n건의사항을 입력해주세요.\n >>> ");
      comnt = sc.nextLine();
      msg = "update member SET comnt = ? where id = ? ";
      PST = CN.prepareStatement(msg);
      PST.setString(1,  comnt );
      PST.setString(2, userID);
      PST.executeUpdate();
      System.out.println("\n건의사항을 관리자에게 전송했습니다.");
    } catch(Exception e) {System.out.println("error: "+e);}
  }

  public void delId() throws Exception {
    System.out.println("\n[회원탈퇴]");
    System.out.println("회원정보를 입력하세요.");
    while(true) {
      System.out.print("패스워드  : ");
      fpsw = sc.nextLine();
      msg = "select * from member where ID = '" + userID + "'";

      RS = ST.executeQuery(msg);
      if (RS.next() == true) {
        upsw = RS.getString("psw");      
        if (fpsw.equals(upsw)) {
          msg = "delete from member where ID = '" + userID + "'";
          System.out.print("정말 탈퇴 하시겠습니까? (y/N)>>> ");
          String a = sc.nextLine();
          if (a.equals("y")) {
            ST.executeUpdate(msg);
            System.out.println("회원 탈퇴가 완료되었습니다.");
            System.out.println();
            break;
          } else {System.out.println("회원 탈퇴를 취소하셨습니다.");}
        }  else {
          System.out.println("비밀번호를를 다시 확인하십시오.");
          continue;
        }//else 
      } 
    }  
    MainMenu mm = new MainMenu();
    mm.mainmenu();
  }//delId

}// Class END
