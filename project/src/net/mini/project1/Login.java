package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Login {


  public static void main(String[] args) {
    Connection CN = null;
    Statement ST = null;
    ResultSet RS = null;
    String msg = null;
    Scanner sc = new Scanner(System.in);
    String uID = null;
    String uPsw = null;

    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@localhost:1521:XE";
      CN = DriverManager.getConnection(url, "system", "1234");
      System.out.println("연결 성공");

      ST = CN.createStatement(); // CN에 접속해 명령어를 생성하고 실행 대기하라.

      // 로그인 창
      System.out.print("ID >> ");
      String userID = sc.nextLine();

      System.out.println("Password >> ");
      String userPsw = sc.nextLine();

      // 사용자 정보 DB에서 select 하기

      msg = "select ID, PSW from member where ID = '" + userID + "'";
      RS = ST.executeQuery(msg); // CN에 접속해 RS에 select 명령어를 생성하고 실행해라.
      if (RS.next()==true) {
        uID = RS.getString("ID");
        uPsw = RS.getString("PSW");
      }

      // DB 사용자 정보와 입력정보 메치 확인하기.
      if (userID.equals(uID) && userPsw.equals(uPsw)) {
        System.out.println("로그인에 성공하였습니다.");
        // 여기에 게임실행 메서드가 들어갈 것이다.

      } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}








    }catch (Exception ex) {System.out.println("에러 이유 =" + ex); }

  }

}
