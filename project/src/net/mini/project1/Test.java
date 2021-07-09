package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Test {

  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  String msg = "isud = crud쿼리문기술";
  int Gtotal = 0; // 조회갯수72/ 전체갯수329
  int question = 0;
  String uquestion = "문제 = 정답";
  int uscore = 0;//데이터베이스 점수담을 곳
  Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    Test t = new Test();
    t.dbconnect();
    t.wordTest();
  }

  public void dbconnect() {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver"); //오라클드라이브로드
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
      CN = DriverManager.getConnection( url, "system", "1234");
      System.out.println("오라클 드라이브및 서버연결성공");

      ST = CN.createStatement();
    }catch(Exception ex) { System.out.println("에러이유 " + ex);}
  }//dbconnect end

  public void wordTest() { //중복제거필요
    try {
      //문제 총 개수반환
      msg = "select count(*) from word";
      RS = ST.executeQuery(msg);
      int count = 0;
      if( RS.next() == true) {
        count = RS.getInt("count(*)");
      }//if end

      //문제 5번 반복출제
      for(int i =0 ; i<5; i++) {

        //문제 랜덤출력
        System.out.print("문제>> ");
        int question = (int)(Math.random()*count)+1;

        msg = "select eng from word where code =" + question;
        RS = ST.executeQuery(msg);
        if( RS.next() == true) {
          uquestion = RS.getString("eng");
          System.out.println(uquestion);
        }//if end

        //문제풀기
        System.out.print("정답입력>> ");
        String uanswer = sc.nextLine();

        msg = "select kor from word where code =" + question;
        RS = ST.executeQuery(msg);
        String answer = "정답담을 그릇";
        if( RS.next() == true) {
          answer = RS.getString("kor");
        }//if end

        //정답비교, 점수저장
        if(answer.equals(uanswer)) {

          //데이터베이스 점수 끌어오기
          msg = "select score from ranking where id = 1";
          RS = ST.executeQuery(msg);
          if(RS.next() == true) {
            uscore = RS.getInt("score");
          }//if end

          uscore++;
          System.out.println("정답입니다");
          System.out.println("현재점수는 " + uscore + "입니다");

          //랭킹에 점수저장
          msg = "update ranking set score =" + uscore + " where id = '1'";
          int ok = ST.executeUpdate(msg);

        }else {
          uscore--;
          System.out.println("틀렸습니다. 정답은 " + uquestion + "입니다");
          System.out.println("현재점수는 " + uscore + "입니다");

          //랭킹에 점수저장
          msg = "update ranking set score =" + uscore + " where id = '1'";
          int ok = ST.executeUpdate(msg);
        }//if end

        System.out.println();
      }//for end
    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
  }//wordTest end
}//Test Class END
