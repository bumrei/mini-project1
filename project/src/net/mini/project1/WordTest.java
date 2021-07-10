package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class WordTest {

  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  String msg = "isud = crud쿼리문기술";
  int wordNum;
  String question = "문제";
  String answer = "정답";
  int score;//데이터베이스 점수담을 곳
  Scanner sc = new Scanner(System.in);

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

      //테스트 5번 반복
      for(int i =0 ; i<5; i++) {

        //문제 랜덤출력
        System.out.print("문제>> ");
        wordNum = (int)(Math.random()*count)+1;

        //데이터베이스 문제 가져오기
        msg = "select eng from word where code =" + wordNum;
        RS = ST.executeQuery(msg);
        if( RS.next() == true) {
          question = RS.getString("eng");
        }//if end
        System.out.println(question);

        //문제풀기
        System.out.print("정답입력>> ");
        String uanswer = sc.nextLine();

        //데이터베이스 정답 가져오기
        msg = "select kor from word where code =" + wordNum;
        RS = ST.executeQuery(msg);
        if( RS.next() == true) {
          answer = RS.getString("kor");
        }//if end

        //데이터베이스 점수 가져오기
        msg = "select score from member where id = 'ID1'";
        RS = ST.executeQuery(msg);
        if(RS.next() == true) {
          score = RS.getInt("score");
        }//if end

        //정답채점, 점수매기기
        if(answer.equals(uanswer)) {
          score++;
          System.out.println("정답입니다");
        }else {
          score--;
          System.out.println("틀렸습니다. 정답은 " + question + "입니다");
        }//if end
        System.out.println("현재점수는 " + score + "입니다");

        //데이터베이스에 점수저장
        msg = "update member set score =" + score + " where id = 'ID1'";
        ST.executeUpdate(msg);

        System.out.println();
      }//for end
    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
  }//wordTest end
}//Class END
