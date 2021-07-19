package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class MainMenu {

  Connection CN ;
  Statement ST ;
  ResultSet RS ;
  String msg ;
  String userID ;
  String uID , uPsw ,uName , uEmail, userPsw ;
  String fname , femail ,fID , fpsw , rpsw;
  Scanner sc = new Scanner(System.in);

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public static void main(String[] args) throws Exception {
    MainMenu mm = new MainMenu();
    mm.dbConnect();
    mm.mainmenu();
  }

  public void mainmenu() throws Exception {
    LogInMenu lm = new LogInMenu();
    JoinMember jm = new JoinMember();
    FindMember fm = new FindMember();
    System.out.println("──────────────────────── * [ 단어 암기 게임 ] * ─────────────────────────\n");
    Thread.sleep(800);
    System.out.println("*        [Java]                *              [전등]        *            *\n");
    Thread.sleep(600);
    System.out.println("   *                  *                           *    [telephone]   *\n");
    Thread.sleep(600);
    System.out.println("       *    [전화기]              *          *             *            *\n");
    Thread.sleep(600);
    System.out.println("*                   *     [apple]                 *             *\n");
    Thread.sleep(600);
    System.out.println("          *                *             *             [이클립스]    *\n");
    Thread.sleep(600);
    System.out.println("    *                      [car]             *                         *\n");
    Thread.sleep(600);
    System.out.println("*              [자동차]                *         [책상]      *           *\n\n");
    Thread.sleep(600);
    while (true) {
      System.out.print("\n\n[1. 로그인]   [2. 회원가입]   [3. 아이디 / 비밀번호 찾기]   [0. 종료] \n  >>> ");
      String menu = sc.nextLine();

      switch (menu) {
        case "1" : lm.login(); break;
        case "2" : jm.join(); break;
        case "3": fm.findDb(); break;
        case "0" :
          System.out.println("\n\n게임을 종료합니다.");
          System.exit(0);
          break;
        default :
          System.out.println("\n번호를 다시 확인해주세요.");
      }
    }
  } // executeMember() End



}
