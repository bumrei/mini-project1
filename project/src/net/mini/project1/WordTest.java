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
  Scanner sc = new Scanner(System.in);

  public void dbConnect() {
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
      //테스트 5번 반복
      for(int i =0 ; i<5; i++) {
        //문제 랜덤출력
        int wordNum = (int)(Math.random()*getTotalWordNum())+1;
        System.out.printf("문제>> %s\n", getWord("eng", wordNum));
        System.out.print("정답>> ");
        String uanswer = sc.nextLine();

        int currentScore = answerCheck(uanswer, wordNum);
        setDBScore(currentScore);

        System.out.println();
      }//for end
    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
  }//wordTest end

  //문제 총 개수반환
  public int getTotalWordNum() {
    int count = 0;
    try {
      msg = "select count(*) from word";
      RS = ST.executeQuery(msg);
      if( RS.next() == true) {
        count = RS.getInt("count(*)");
      }//if end
    }catch(Exception ex) { }
    return count;
  }//getTotalWordNum end

  //데이터베이스 단어 가져오기
  public String getWord(String type, int wordNumber) {
    String word = "단어";
    try {
      msg = "select " + type + " from word where code =" + wordNumber;
      RS = ST.executeQuery(msg);
      if( RS.next() == true) {
        word = RS.getString(type);
      }//if end
    }catch(Exception ex) { }
    return word; 
  }//getWord end

  //데이터베이스 점수 가져오기
  public int getDBScore() {
    int score = 0;
    try {
      msg = "select score from member where id = 'ID1'";
      RS = ST.executeQuery(msg);
      if(RS.next() == true) {
        score = RS.getInt("score");
      }//if end
    }catch(Exception ex) { }
    return score;
  }//getDBScore end

  //정답채점, 점수매기기
  public int answerCheck(String userAnswer, int wordNumber) {
    String answer = getWord("kor",wordNumber);
    int score = getDBScore();

    if(answer.equals(userAnswer)) {
      score++;
      System.out.println("정답입니다");
    }else {
      score--;
      System.out.println("틀렸습니다. 정답은 " + answer + "입니다");
    }//if end
    System.out.println("현재점수는 " + score + "입니다");
    return score;
  }//answerCheck end

  //데이터베이스에 점수저장
  public void setDBScore(int score) {
    try {
      msg = "update member set score =" + score + " where id = 'ID1'";
      ST.executeUpdate(msg);
    }catch(Exception ex) { }
  }//updateDBScore end
}//Class END
