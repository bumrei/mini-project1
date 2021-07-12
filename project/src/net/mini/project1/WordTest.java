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
  String userID;
  int level;
  int[] question = new int[10];
  Scanner sc = new Scanner(System.in);

  WordTest(String userID, int level) {
    this.userID = userID;
    this.level = level;
  }

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

    System.out.println("영어단어 테스트");

    try {

      //랜덤문제생성
      randomSetting();

      //테스트 5번 반복
      for(int i =0 ; i<question.length; i++) {
        //문제 랜덤출력
        System.out.printf("문제>> %s\n",getWord("eng", i));
        System.out.print("정답>> ");
        String uanswer = sc.nextLine();

        int currentScore = answerCheck(uanswer, i);
        setDBScore(currentScore);

        System.out.println();
      }//for end
    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
  }//wordTest end

  //문제 총 개수반환
  public int getTotalWordNum() {
    int count = 0;
    try {
      msg = "select count(*) from word where wordlevel = " + level;
      RS = ST.executeQuery(msg);
      if( RS.next() == true) {
        count = RS.getInt("count(*)");
      }//if end
    }catch(Exception ex) { }
    return count;
  }//getTotalWordNum end

  public void randomSetting() {
    for(int i = 0; i < question.length; i++) {
      question[i] = (int)(Math.random()*getTotalWordNum()) +1;
      for(int j = 0; j < i; j++) {
        if(question[i] == question[j]) {
          i--;
        }//if end
      }//for end
    }//for end
  }//print[] END

  //데이터베이스 단어 가져오기
  public String getWord(String type, int number) {
    String word = "단어";
    try {
      msg = "select " + type + " from "
          + "(select row_number() over(partition by wordlevel order by wordlevel) as row_num, b.* "
          + "from word b) a "
          + "where wordlevel = " + level + " and a.row_num = " + question[number];
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
      msg = "select score from member where id = '" + userID + "'";
      RS = ST.executeQuery(msg);
      if(RS.next() == true) {
        score = RS.getInt("score");
      }//if end
    }catch(Exception ex) { }
    return score;
  }//getDBScore end

  //정답채점, 점수매기기
  public int answerCheck(String userAnswer, int number) {
    String answer = getWord("kor",number);
    int score = getDBScore();

    if(answer.equals(userAnswer)) {
      System.out.println("정답입니다.");
      switch(level) {
        case 1: score++; break;
        case 2: score += 2; break;
        case 3: score += 3; break;
      }//switch end
    }else {
      System.out.println("틀렸습니다. 정답은 " + answer + "입니다.");
      switch(level) {
        case 1: score--; break;
        case 2: score -= 2; break;
        case 3: score -= 3; break;
      }//switch end
    }//if end
    System.out.println("현재점수는 " + score + "입니다.");
    return score;
  }//answerCheck end

  //데이터베이스에 점수저장
  public void setDBScore(int score) {
    try {
      msg = "update member set score =" + score + " where id = '" + userID + "'";
      ST.executeUpdate(msg);
    }catch(Exception ex) { }
  }//setDBScore end
}//Class END
