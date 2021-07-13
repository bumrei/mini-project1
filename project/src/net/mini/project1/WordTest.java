package net.mini.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WordTest {

  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  PreparedStatement PST=null ;
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  String msg = "isud = crud쿼리문기술";
  Scanner sc = new Scanner(System.in);
  String userID = "로그인 ID";
  String uanswer = "사용자입력값";
  int level;
  int[] questionNum = new int[6];

  WordTest(String userID) {
    this.userID = userID;
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

  public void wordTest() { //게임종료 후 리플레이와 뒤로가기 구현필요
    test: while(true) {
      System.out.println("난이도를 선택해주세요.(1~3)");
      System.out.print("난이도>> ");
      level = Integer.parseInt(sc.nextLine());

      System.out.println("영어단어테스트를 시작합니다.");
      System.out.println("문제당 제한시간은 10초입니다.");
      System.out.println("테스트중간에 그만두고 싶으시다면 'end'를 입력해주세요.\n");
      try {
        //안내사항 확인위한 시간텀
        Thread.sleep(2000);

        //랜덤문제생성
        randomSetting();

        //테스트 5번 반복
        for(int i =0 ; i<questionNum.length; i++) {
          //문제랜덤출력
          System.out.printf("문제>> %s\n",getWord("eng", i));
          System.out.print("정답>> ");

          //제한시간 10초설정
          ExecutorService ex = Executors.newSingleThreadExecutor();
          //쓰레드 1개인 ExecutorService를 리턴합니다. 싱글 쓰레드에서 동작해야하는 작업을 처리할 때 사용합니다.

          try {
            Future<String> result = ex.submit(new InputAnswer()); // 정답함수 받아서 쓰레드에 전달
            //submit은 Runnable 또는 Callable을 받는다. Runnable은 리턴값이 없고 Callable은 리턴값이 있다.
            try {
              uanswer = result.get(10, TimeUnit.SECONDS);
              //필요에 따라서 최대지정된 시간, 계산이 완료할 때까지 대기해, 그 후, 계산 결과가 이용 가능한 경우는 결과를 가져옵니다.
              //게임도중 종료
              if(uanswer.equals("end")) {
                break;
              }//if end
            } catch (ExecutionException e) { // 작업도중에 에러가 발생한 경우에 발생
              e.getCause().printStackTrace(); // 오류출력
            } catch (TimeoutException e){ //대기시간이 초과된 경우에 발생
              System.out.println("\n입력시간이 초과됐습니다."); // 시간초과
              answerCheck(uanswer, i);
              System.out.println();
              continue;
            } catch (InterruptedException e){ //현재 스레드가 인터럽트된 경우에 발생
              System.out.println("interrupted?");
              e.getCause().printStackTrace(); // 오류출력
            }//try end
          } finally {
            ex.shutdownNow(); //진행중인거 강제 종료
            //스레드 풀의 스레드는 기본적으로 데몬 스레드가 아니기 때문에 main 스레드가 종료되더라도 작업을 처리하기 위해 계속 실행 상태로 남아있습니다.
            //프로세스를 종료시키려면 스레드 풀을 종료시켜 스레드들이 종료 상태가 되도록 처리필요
          }//try end

          answerCheck(uanswer, i);
          System.out.println();
        }//for end
      }catch(Exception ex) {System.out.println("에러이유: "+ex);}

      System.out.println("테스트가 끝났습니다.\n");

      //게임종료 후 메뉴선택
      while(true) {
        System.out.println("1.리플레이 2.이전메뉴");
        System.out.println("메뉴>> ");
        String menu = sc.nextLine();

        switch(menu) {
          case "1":
            continue test;
            //break;
          case "2":
            return;
            //break;
          default:
            System.out.println("잘못입력하셨습니다.\n");
            continue;
        }//switch end
      }//while end
    }//while end
  }//wordTest end

  //문제 총 개수반환
  public int getTotalWordNum() {
    int count = 0;
    try {
      msg = "select count(*) from word where wordlevel = ?";
      PST = CN.prepareStatement(msg);
      PST.setInt(1, level);
      RS = PST.executeQuery();
      if( RS.next() == true) {
        count = RS.getInt("count(*)");
      }//if end
    }catch(Exception ex) { }
    return count;
  }//getTotalNumber end

  public void randomSetting() {
    for(int i = 0; i < questionNum.length; i++) {
      questionNum[i] = (int)(Math.random()*getTotalWordNum()) +1;
      for(int j = 0; j < i; j++) {
        if(questionNum[i] == questionNum[j]) {
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
          + "(select row_number() over(partition by wordlevel order by wordNum) as row_num, b.* "
          + "from word b) a "
          + "where wordlevel = " + level + " and a.row_num = " + questionNum[number];
      RS = ST.executeQuery(msg);

      if( RS.next() == true) {
        word = RS.getString(type);
      }//if end
    }catch(Exception ex) { }
    return word;
  }//getQuestion end

  //데이터베이스 점수 가져오기
  public int getDBScore() {
    int score = 0;
    try {
      msg = "select score from member where id = ?";
      PST = CN.prepareStatement(msg);
      PST.setString(1, userID);
      RS = PST.executeQuery();

      if(RS.next() == true) {
        score = RS.getInt("score");
      }//if end
    }catch(Exception ex) { }
    return score;
  }//getDBScore end

  //정답채점해서 DB에 점수저장
  public void answerCheck(String userAnswer, int number) {
    int score = getDBScore();
    String answer = getWord("kor", number);

    if(answer.equals(userAnswer)) {
      System.out.println("정답입니다.");
      switch(level) {
        case 1: score++; break;
        case 2: score += 2; break;
        case 3: score += 3; break;
      }//switch end
    }else {
      System.out.println("틀렸습니다. 정답은 '" + answer + "'입니다.");
      switch(level) {
        case 1: score--; break;
        case 2: score -= 2; break;
        case 3: score -= 3; break;
      }//switch end
    }//if end
    System.out.println("현재점수는 " + score + "점입니다.");

    try {
      msg = "update member set score = ? where id = ?";
      PST = CN.prepareStatement(msg);
      PST.setInt(1, score);
      PST.setString(2, userID);
      PST.executeUpdate();
    }catch(Exception ex) { }
  }//answerCheck end
}//WordTest Class END

class InputAnswer implements Callable<String> { // 값 입력받기
  @Override
  public String call() throws IOException {

    //    Scanner sc = new Scanner(System.in);
    //    String input = "사용자입력값";
    //    input = sc.nextLine();

    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String input = "";
    while ("".equals(input)) {
      try {
        while (!inp.ready()) {
          Thread.sleep(100);
        }//while end
        input = inp.readLine();
      } catch (InterruptedException e) {
        return null;
      }//try end
    } //while end

    return input;
  }//call end
}//InputAnswer Class END
