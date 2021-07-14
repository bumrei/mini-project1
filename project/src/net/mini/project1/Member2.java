package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Member2 {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
  String uID = null;
  String uPsw = null;
  String uName = null;
  String uEmail = null;
  String userID = null;
  String userPsw = null;

  String fname = "찾기용이름";
  String femail = "찾기용메일";
  String fID = "찾기용아이디";
  String fpsw = "찾기용비번";
  String rpsw = "바꾸기용비번";

  Scanner sc = new Scanner(System.in);
  JoinMember jm = new JoinMember();
  AdminMenu2 am = new AdminMenu2();
  PlayingGame2 pg = new PlayingGame2();

  public static void main(String[] args) throws Exception {
    Member2 mb = new Member2();
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

  public void matchFindFromDB() throws Exception {
    msg = "select * from member where NAME = '" + fname + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
      uName = RS.getString("NAME");
      uEmail = RS.getString("EMAIL");
    }
  }

  public void executeMember() throws Exception {
    while (true) {
      System.out.print("[1. 로그인]   [2. 회원가입]   [3. 아이디 / 비밀번호 찾기]   [9. 종료] \n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1" : login(); break;
        case "2" : jm.join(); break;
        case "3": findDb(); break;
        case "9" :
          System.out.println("게임을 종료합니다.");
          System.exit(0);
          break;
        default :
          System.out.println("올바른 번호를 입력해 주십시오.");
      }
    }
  } // executeMember() End

  public void login() throws Exception {
    while (true) {
      System.out.print("\n  ID>>> ");
      userID = sc.nextLine();
      if (userID.equals("Admin")) {
        adminLogin();
        return;
      }

      System.out.print("  Password>>> ");
      userPsw = sc.nextLine();

      matchLoginFromDB();

      if (userID.equals(uID) && userPsw.equals(uPsw)) {
        System.out.println("\n로그인 성공");
        goIntoTheGame();
        return;
      } else {
        System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");
        continue;
      }
    }
  }

  public void goIntoTheGame() throws Exception {
    System.out.print("\n\n게임 로딩중"); Thread.sleep(500);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰▰"); Thread.sleep(100);
    System.out.print("▰▰▰▰▰▰▰"); Thread.sleep(1000);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(500);
    System.out.print("▰"); Thread.sleep(500);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(1000);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(500);
    System.out.print("▰▰▰▰▰▰"); Thread.sleep(500);
    System.out.println("꧁ଘ(੭ˊ꒳ˋ)੭✧\n");
    Thread.sleep(700);
    pg.game();
  }

  public void adminLogin() {
    System.out.print("  Passwords>>> ");
    String adminPsw = sc.nextLine();
    System.out.print("\n  Pin number>>> ");
    String adminPin = sc.nextLine();

    if (adminPsw.equals("12345") && adminPin.equals("1234")) {
      System.out.println("관리자로 로그인하셨습니다.");
      am.adminWork();
    } else {
      System.out.println("관리자 비밀번호 혹은 핀번호를 잘못 입력하셨습니다.");
      return;
    }
  }


  public void findDb() {
    System.out.println("\n[아이디 / 비밀번호 찾기]");
    Loop: while(true) {
      System.out.print("\n[1. 아이디 찾기]   [2. 비밀번호 찾기]   [8. 뒤로가기]\n >>> ");

      int menu = Integer.parseInt(sc.nextLine());
      switch(menu) {
        case 1:
          findId();
          break;
        case 2:
          findPsw();
          break;
        case 8:
          System.out.println("\n뒤로가기");
          break Loop;
        default:
          System.out.println("메뉴 번호 확인");
          break;
      }
    }
  }

  public void findId() {
    try {
      System.out.println("\n[아이디 찾기]");
      System.out.println("\n회원정보를 입력하세요.");

      System.out.print("  이름 >>> ");
      fname = sc.nextLine();
      System.out.print("  Email >>> ");
      femail = sc.nextLine();

      matchFindFromDB();
      if (fname.equals(uName) && femail.equals(uEmail)) {
        msg = "select id from member where name = '"+fname+"' and email = '"+femail+"'";
        RS = ST.executeQuery(msg);
        if (RS.next()==true) {
          fID = RS.getString("ID");
          System.out.println("\n당신의 ID는 '" + fID + "' 입니다.");
        }
        return;
      } else {System.out.println("\n정보없음");}
      System.out.println();
    } catch(Exception e) {System.out.println("error: "+e);}
  }

  public void findPsw() {
    try {
      System.out.println("\n[비밀번호 찾기]");
      System.out.println("\n회원정보를 입력하세요.");

      System.out.print("  이름 >>> ");
      fname = sc.nextLine();
      System.out.print("  ID >>> ");
      fID = sc.nextLine();

      matchFindFromDB();
      if (fname.equals(uName) && fID.equals(uID)) {
        msg = "select psw from member where name = '"+fname+"' and id = '"+fID+"'";
        RS = ST.executeQuery(msg);
        if (RS.next()==true) {
          fpsw = RS.getString("psw");
          System.out.println("\n회원확인이 되었습니다.");
          resetPsw();
        }
        return;
      }  else {System.out.println("\n정보없음");}
      System.out.println();
    } catch(Exception e) {System.out.println("error: "+e);}
  }

  public void resetPsw() {
    try {
      System.out.println("\n비밀번호를 재설정합니다.");
      System.out.print(" 새로운 비밀번호를 입력해주세요\n >>> ");
      rpsw = sc.nextLine();

      msg = "update member set psw = '"+rpsw+"' where id = '"+fID+"'";
      ST.executeUpdate(msg);

      System.out.println("\n\n변경이 완료되었습니다.");
      System.out.println("\n변경된 비밀번호는 " +rpsw+ " 입니다.");
    } catch(Exception e) {System.out.println("error: "+e);}
  }


}
