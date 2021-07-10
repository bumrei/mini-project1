package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Login {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
  Scanner sc = new Scanner(System.in);
  String uID = null;
  String uPsw = null;
  int uadmin;
  String userID = null;
  String userPsw = null;
  int userAdmin;

  public static void main(String[] args) {
    Login lg = new Login();
    while (true) {
      try {
        lg.dbConnect();
        System.out.println("================== * 단어 암기 게임 * ===================");
        Thread.sleep(1000);
        System.out.println("*           *             *              *        *");
        Thread.sleep(1000);
        System.out.println("     *           *                *                 *");
        Thread.sleep(1000);
        System.out.println("           *              *             *");
        Thread.sleep(1000);
        System.out.println("*           *        *            *               *");
        System.out.println("     *           *                           *         *");
        Thread.sleep(1000);
        System.out.print("[1. 일반회원 로그인]   [2. 관리자 로그인]   [3. 종료]\n >>> ");
        int command = Integer.parseInt(lg.sc.nextLine());

        switch(command) {
          case 1:
            lg.loginFrame();
            lg.selectFromDB();
            lg.matchWithDB();
            break;

          case 2:
            lg.adminLogin();
            lg.adminInfoFromDB();
            lg.matchAdminInfoWithDB();
            break;

          case 3:
            System.out.println("게임을 종료합니다.");
            System.exit(0);
          default:
            System.out.println("번호를 잘못 입력하셨습니다.");

        }
      }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
    }
  }//main END

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void loginFrame() {
    System.out.print("ID >> ");
    userID = sc.nextLine();

    System.out.print("Password >> ");
    userPsw = sc.nextLine();
  }// loginFrame End

  public void selectFromDB() throws Exception {
    msg = "select ID, PSW from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    if (RS.next()==true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
    }
  }// selectFromDB End

  public void matchWithDB() throws Exception {
    if (userID.equals(uID) && userPsw.equals(uPsw)) {
      System.out.println("로그인에 성공하였습니다.");
      System.out.println("게임 로딩중...");
      Thread.sleep(2000);
      System.out.println("범래: 그다음 게임이 시작됩니다. 여기에 게임 실행 메서드가 들어갈겁니다.");
      System.out.println("모두 화이팅");
      // 여기에 게임실행 메서드가 들어갈 것이다.
    } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}
  }// matchWithDB End


  public void adminLogin() {
    while(true) {
      try {
        System.out.print("ID >> ");
        userID = sc.nextLine();

        System.out.print("\nPassword >> ");
        userPsw = sc.nextLine();

        System.out.print("\nAdmin Pin >> ");
        userAdmin = Integer.parseInt(sc.nextLine());

        break;
      }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
    }
  }// adminLogin End

  public void adminInfoFromDB() throws Exception {
    msg = "select ID, PSW, ADMIN from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    if (RS.next()==true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
      uadmin = RS.getInt("ADMIN");
    }
  }// adminInfoFromDB End

  public void matchAdminInfoWithDB() throws Exception {
    if (userID.equals(uID) && userPsw.equals(uPsw) && userAdmin == uadmin) {
      System.out.println("관리자로 로그인 하셨습니다.");
      loop : while(true) {
        try {
          System.out.println("수행하실 작업을 선택해 주십시오.");
          System.out.print("[1. 회원 관리]   [2. 게임 설정 관리]"
              + "   [3. 단어장 관리]   [4. 관리자 계정 로그아웃]\n >>> ");

          int command = Integer.parseInt(sc.nextLine());

          switch(command) {
            case 1:
              System.out.println("회원 관리입니다.\n");
              System.out.println("Apologise, system is not ready.\n");
              break;
            case 2:
              System.out.println("게임 설정 관리 입니다.\n");
              System.out.println("Apologise, system is not ready.\n");
              break;
            case 3:
              System.out.println("단어장 관리 입니다.\n");
              System.out.println("Apologise, system is not ready.\n");
              break;
            case 4:
              break loop;
            default:
              System.out.println("잘못된 번호를 입력하셨습니다.");
          }
        }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
      }
    } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}
  }

}//Class End
