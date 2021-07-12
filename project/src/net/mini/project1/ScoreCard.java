package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ScoreCard {

  public static void main(String[] args) {
    Connection CN = null;  // DB 서버연결정보 - 서버 ip 주소 계정id, password
    Statement ST = null;  // ST = CN.createStatement() 명령어 생성 - 신규등록해라, 조회하라, 삭제하라
    ResultSet RS = null;  //select 조회결과값을 기억하는 덩어리 장치.
    String msg = "isud=crud쿼리문 기술";
    Scanner sc = new Scanner(System.in);

    try {
      Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클드라이브로드
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
      CN = DriverManager.getConnection(url , "system", "1234"); //오라클 서버, 계정, 비번, 연결하기. // XE는 우리가 설치한 DB서버의 이름. //CN은 getConnection의 리턴값이 Connection이니까 해주는것이다.
      //첫번째명령어 생성
      ST = CN.createStatement();
      msg = "select ID, score from member order by score";
      RS = ST.executeQuery(msg);
      System.out.println("아이디\t점 수");
      while (RS.next() == true) {
        String uid = RS.getString("ID");
        int uscore = RS.getInt("score");
        System.out.println(uid+"\t" + uscore);
      }

    } catch (Exception ex) { System.out.println("에러 =" + ex);}

    sc.close();
  }// main END
}

