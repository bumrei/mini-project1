package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Member {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
  Scanner sc = new Scanner(System.in);
  String uID = null;
  String uPsw = null;
  String userID = null;
  String userPsw = null;
  JoinMember jm = new JoinMember();
  AdminMenu am = new AdminMenu();

  public static void main(String[] args) throws Exception {
    Member mb = new Member();
    mb.dbConnect();
    mb.executeMember();
  }

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void matchLoginFromDB() throws Exception {
    msg = "select * from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
    }
  }

  public void executeMember() throws Exception {
    while (true) {
      System.out.print("[1. 로그인]   [2. 회원가입]   [9. 종료] \n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1" : login(); break;
        case "2" : jm.join(); break;
        case "9" :
          System.out.println("게임을 종료합니다.");
          System.exit(0);
          break;
        default :
          System.out.println("옳바른 번호를 입력해 주십시오.");
      }
    }
  } // executeMember() End

  public void login() throws Exception {
    while (true) {
      System.out.print("ID>>");
      userID = sc.nextLine();
      if (userID.equals("Admin")) {
        adminLogin();
        return;
      }

      System.out.print("\nPassword>>");
      userPsw = sc.nextLine();

      matchLoginFromDB();

      if (userID.equals(uID) && userPsw.equals(uPsw)) {
        System.out.println("로그인 성공");
        goIntoTheGame();
        return;
      } else {
        System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");
        continue;
      }
    }
  }

  public void goIntoTheGame() throws Exception {
    System.out.println("게임 로딩중...");
    Thread.sleep(3000);
    System.out.println("준비중");
    // 게임 실행파일 들어가기!!!
  }

  public void adminLogin() {
    System.out.print("Passwords>> ");
    String adminPsw = sc.nextLine();
    System.out.print("\nPin number>> ");
    String adminPin = sc.nextLine();

    if (adminPsw.equals("12345") && adminPin.equals("1234")) {
      System.out.println("관리자로 로그인하셨습니다.");
      am.adminWork();
    } else {
      System.out.println("관리자 비밀번호 혹은 핀번호를 잘못 입력하셨습니다.");
      return;
    }
  }



}
