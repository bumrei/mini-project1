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
  String userID = null;
  String userPsw = null;

  public static void main(String[] args) throws Exception {
    Login lg = new Login();
    lg.dbConnect();
    lg.loginFrame();
    lg.selectFromDB();
    lg.matchWithDB();
  }//main END

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    System.out.println("연결 성공");
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
}//Class End
