package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class Member {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
  int uMemno;
  String uName = null;
  String uID = null;
  String uPsw = null;
  String uEmail = null;
  Date uCdate = null;
  int uScore;
  int adminPin = 1234;
  int useradmin;
  String adminPwd = "12345";
  String userID = null;
  String userPsw = null;
  Scanner sc = new Scanner(System.in);
  JoinMember jm = new JoinMember();
  AdminMenu ad = new AdminMenu();

  public void executeLogin() {

    while (true) {
      try {
        dbConnect();
        int controlThread = 700;
        System.out.println("                    ◤----------------◥ ");
        System.out.println("================= * | 단어 암기 게임 | * ===================");
        System.out.print("                    ◣----------------◢ ");
        Thread.sleep(controlThread);
        System.out.println("\n       *            *              *         *         *");
        Thread.sleep(controlThread);
        System.out.println("\n*         apple           *              *          Haha");
        Thread.sleep(controlThread);
        System.out.println("\n      Sweet           *        자동차             *");
        Thread.sleep(controlThread);
        System.out.println("\n    *        양초               *         Fly          *");
        Thread.sleep(controlThread);
        System.out.println("\n*           *         Amazing        *                장난감 ");
        Thread.sleep(controlThread);
        System.out.println("\n        Car             *                 Happy      *");
        Thread.sleep(controlThread);
        System.out.print("\n[1. 로그인]   [2. 회원가입]   [9. 종료]\n >>> ");
        int command = Integer.parseInt(sc.nextLine());

        switch(command) {
          case 1: login(); break;
          case 2: jm.join(); break;
          case 9: System.out.println("게임을 종료합니다."); System.exit(0);
          default: System.out.println("번호를 잘못 입력하셨습니다."); }
      }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
    }
  }


  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void login() throws Exception {
    boolean a = loginFrame();
    selectFromDB();
    if (a) {
      goIntoTheGame();
    } else return;
  }

  public boolean loginFrame() throws Exception {
    System.out.print("ID >> ");
    userID = sc.nextLine();
    if(userID.equals("Admin")) {
      adminLogin(); 
      return false;
    }
    System.out.print("Password >> ");
    userPsw = sc.next();
    return true;
  }// loginFrame End

  public void selectFromDB() throws Exception {
    msg = "select ID, PSW from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    if (RS.next()==true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
    }
    return;
  }// selectFromDB End


  public void goIntoTheGame() throws Exception {
    if (userID.equals(uID) && userPsw.equals(uPsw)) {
      System.out.println("로그인에 성공하였습니다.");
      System.out.println("게임 로딩중...");
      Thread.sleep(2000);
      // WordTest.java 삽입.
      System.out.println("준비중입니다.");
    } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}
  }

  public void adminLogin() throws Exception {
    System.out.println("관리자로 로그인하시겠습니까?  (y/N)");
    String command = sc.nextLine();
    if (command.equals("y")) {
      System.out.print("A Password >> ");
      userPsw = sc.nextLine();
      System.out.print("\nA pin number>> ");
      useradmin = Integer.parseInt(sc.nextLine());
      if (userPsw.equals(adminPwd) || useradmin == adminPin) {
        ad.adminWork();
      }
    } else return;
  }


}
