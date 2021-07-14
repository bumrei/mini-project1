package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ResetScore {
  //내정보에 넣을 점수 초기화 기능
  Scanner sc = new Scanner(System.in);
  String msg = null;
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;

  String ans = null;
  String cmd = null;

  String uID = null;
  String cEmail = null;
  String cPsw = null;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public static void main(String[] args) throws Exception {
    ResetScore reset = new ResetScore();
    reset.dbConnect();
    reset.editInfo();
  }


  //회원정보 수정
  public void editInfo() {
    try {
      Loop: while(true) {
        System.out.println("\n변경할 정보를 선택하세요.");
        System.out.println("[1. 이메일 변경]   [2. 비밀번호 변경]   [3. 점수 초기화]   [8. 뒤로가기]");
        System.out.print(" >>> ");
        cmd = sc.nextLine();
        switch(cmd) {
          case "1":
            chgEmail();
            break;
          case "2":
            chgPasswd();
            break;
          case "3":
            resetScore();
            break;
          case "8":
            System.out.println("뒤로가기");
            break Loop;
          default:
            System.out.println("올바른 번호를 입력하세요.");
            break;
        }
      }
    } catch(Exception e) {System.out.println("error: " + e);}  
  }



  public void chgEmail() {
    try {
      Loop: while(true) {
        System.out.println("\n  [이메일 변경]");
        System.out.print("\n현재 이메일 입력해주세요.\n >>> ");
        ans = sc.nextLine();
        msg = "select * from member where EMAIL = '" + ans + "'";
        RS = ST.executeQuery(msg);
        if (RS.next() == true) {
          uID = RS.getString("ID");
        } else {
          System.out.println("입력한 이메일을 다시 확인하세요.");
          continue;
        }
        System.out.print("\n변경할 이메일을 입력하세요.\n >>> ");
        cEmail = sc.nextLine();
        if(stringCheck(cEmail)) {continue;}
        if(!cEmail.contains("@")) {
          System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
          continue;
        }
        msg = "update member set email = '"+cEmail+"' where id = '"+uID+"'";
        ST.executeUpdate(msg);
        System.out.println("변경된 이메일: " + cEmail);
        System.out.println("변경이 완료되었습니다.");
        break Loop;
      }
    } catch(Exception e) {System.out.println("error: " + e);}

  }


  public void chgPasswd() {
    try {
      System.out.println("\n  [비밀번호 변경]");
      System.out.print("\n현재 비밀번호를 입력해주세요\n >>> ");
      ans = sc.nextLine();
      msg = "select * from member where psw = '" + ans + "'";//현재로그인아이디추가
      RS = ST.executeQuery(msg);
      Loop: while(true) {
        if (RS.next() == true) {
          String uID = RS.getString("ID");
          System.out.print("\n변경할 비밀번호를 입력하세요.\n >>> ");
          String sPsw = sc.nextLine();

          if(stringCheck(sPsw)) {continue;}
          while(true) {
            System.out.print("\n비밀번호 재확인>>");
            String tmp = sc.nextLine();
            if(sPsw.equals(tmp)) {
              System.out.println("\n비밀번호가 일치합니다.");
              msg = "update member set psw = '"+sPsw+"' where id = '"+uID+"'";
              ST.executeUpdate(msg);
              System.out.println("\n변경된 비밀번호: " + sPsw);
              System.out.println("변경이 완료되었습니다.");
              break Loop;
            }else {
              System.out.println("비밀번호가 일치하지 않습니다.");
              continue;
            }//if end
          }
        } else {
          System.out.println("입력한 비밀번호를 다시 확인하세요.");
          continue;
        }
      }
    } catch(Exception e) {System.out.println("error: " + e);}
  }

  public void resetScore() {
    try {
      System.out.println("\n  [점수 초기화]");
      System.out.print("점수를 초기화 하시겠습니까? (y/N)\n >>> ");
      ans = sc.nextLine();
      if (ans.equals("y")) {
        msg = "update member set score = 0 where id = '" + uID + "'";
        ST.executeUpdate(msg);
        System.out.println("점수 초기화가 완료되었습니다.");
      } else {
        System.out.println("\n초기화 취소");
      }
    } catch(Exception e) {System.out.println("error: " + e);}
  }


  public boolean stringCheck(String string) {
    boolean check = string == null || string.isEmpty() || string.indexOf(" ") != -1;
    if(check) {
      System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
    }
    return check;
  }
}
