package com.test3;

import java.io.IOException;
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


public class Game {

  Connection CN ; 
  Statement ST ; 
  PreparedStatement PST ;
  ResultSet RS ; 
  String sql = "isud = crud쿼리문기술";
  Scanner sc = new Scanner(System.in);

  String userID;
  int score, level ,point;

  double answerRate1, answerRate2, answerRate3, answerTotalRate;
  int exp;//유저경험치
  String memLevel;//유저레벨
  int quCount;//현재 플레이동안 푼 문제개수
  int anCount;//현재 플레이동안 맞춘 문제개수
  int[] questionNum = new int[6];
  Dao d = new Dao(userID);
  Emoticon em = new Emoticon(userID);

  public Game() { }

  public Game(String userID) {
    this.userID = userID;
  }

  public void dbclose() throws Exception {
    RS.close();
    ST.close();
    CN.close();
  }



  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void wordTest() throws Exception { //게임종료 후 리플레이와 뒤로가기 구현필요

    test: while(true) {
      d.select(userID);
      System.out.println("\t    ┌───────────────────────────────────────────┐");
      System.out.print(em.printChar(d.getMychar()));
      System.out.println("<  영어단어 암기게임에 오신 것을 환영합니다! │");
      System.out.println("\t    │ 플레이하실 난이도를 선택해주세요.(1~3)\t│");
      System.out.println("\t    └───────────────────────────────────────────┘");
      System.out.print("\t    난이도 >>> ");
      try{level = Integer.parseInt(sc.nextLine());
      System.out.println("\n\n");
      if(level<1 || level>3) {
        System.out.println("1~3 사이의 숫자를 입력해주세요.\n");
        continue;
      }
      }catch(Exception ex) { System.out.println("숫자를 입력해주세요.\n"); continue;}


      System.out.println("영어단어테스트를 시작합니다.");
      System.out.println("문제당 제한시간은 10초입니다.");
      System.out.println("테스트중간에 그만두고 싶으시다면 'end'를 입력해주세요.\n");
      try {
        dbConnect();
        //랜덤문제생성
        randomSetting();

        //테스트 반복
        for(int i =0 ; i<questionNum.length; i++) {
          //문제랜덤출력
          System.out.printf("문제>> %s\n",getWord("eng", i));
          System.out.print("정답>> ");

          //제한시간 10초설정
          ExecutorService ex = Executors.newSingleThreadExecutor();
          //쓰레드 1개인 ExecutorService를 리턴합니다. 싱글 쓰레드에서 동작해야하는 작업을 처리할 때 사용합니다.
          String uanswer = "사용자 답변";
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
      Result();      
      setEXP();
      //현재 개수 초기화(리플레이시 반영위해)
      quCount = 0;
      anCount = 0;

      //게임종료 후 메뉴선택
      while(true) {
        System.out.println("[1.리플레이]   [2.이전메뉴]");
        System.out.print("메뉴>> ");
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
      dbConnect();
      sql = "select count(*) from word where wordlevel = ?";
      PST = CN.prepareStatement(sql);
      PST.setInt(1, level);
      RS = PST.executeQuery();
      if( RS.next() == true) {
        count = RS.getInt("count(*)");
      }//if end
      dbclose();
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
      dbConnect();
      sql = "select * from "
          + "(select row_number() over(partition by wordlevel order by wordNum) as row_num, b.* "
          + "from word b) a "
          + "where wordlevel = " + level + " and a.row_num = " + questionNum[number];
      RS = ST.executeQuery(sql);
      if( RS.next() == true) {
        word = RS.getString(type);
      }//if end
    }catch(Exception ex) { }
    return word;
  }//getQuestion end


  //정답채점해서 DB에 점수저장
  public void answerCheck(String userAnswer, int number) throws Exception {
    dbConnect();
    d.select(userID);
    //데이터 저장을 따로 빼서 전역변수로 선언함
    d.questionTotalCnt++;
    quCount++;
    String answer = getWord("kor", number);

    if(answer.equals(userAnswer)) {
      System.out.println("정답입니다.");
      d.answerTotalCnt++;
      anCount++;

      switch(level) {
        case 1:
          d.uScore++; d.point += (int)(Math.random()*10) + 1;
          d.questionCnt1++; d.answerCnt1++;
          break;
        case 2:
          d.uScore += 2; d.point += (int)(Math.random()*10) + 11;
          d.questionCnt2++; d.answerCnt2++;
          break;
        case 3:
          d.uScore += 3; d.point += (int)(Math.random()*10) + 21;
          d.questionCnt3++; d.answerCnt3++;
          break;
      }//switch end
    }else {
      System.out.println("틀렸습니다. 정답은 '" + answer + "'입니다.");
      switch(level) {
        case 1: d.uScore --; d.questionCnt1++; break;
        case 2: d.uScore -= 2; d.questionCnt2++; break;
        case 3: d.uScore -= 3; d.questionCnt3++; break;
      }//switch end
    }//if end
    System.out.println("현재점수는 " + d.uScore + "점입니다.");

    setDBData(userID);

  }//answerCheck end

  public void Result() throws Exception {
    d.select(userID);
    System.out.printf("-----------lv.%d 테스트 결과-----------\n", level);
    System.out.printf("문제 %s개 중 정답 %s개, 정답률: %s%%\n\n", quCount, anCount,
        ((double)Math.round((double)anCount/quCount*10000)/100));
    int questionCount = 0; int answerCount = 0; double rate = 0;
    d.answerTotalRate = ((double)Math.round((double)d.answerTotalCnt/d.questionTotalCnt*10000)/100);
    switch(level) {
      case 1:
        questionCount = d.questionCnt1;
        answerCount = d.answerCnt1;
        d.answerRate1 = ((double)Math.round((double)d.answerCnt1/d.questionCnt1*10000)/100);
        rate = d.answerRate1;
        break;
      case 2:
        questionCount = d.questionCnt2;
        answerCount =d.answerCnt2;
        d.answerRate2 = ((double)Math.round((double)d.answerCnt2/d.questionCnt2*10000)/100);
        rate = d.answerRate2;
        break;
      case 3:
        questionCount =d.questionCnt3;
        answerCount = d.answerCnt3;
        d.answerRate3 = ((double)Math.round((double)d.answerCnt3/d.questionCnt3*10000)/100);
        rate = d.answerRate3;
        break;
    }

    System.out.printf("-----------lv.%d 누적  결과-----------\n", level);
    System.out.printf("총문제 %s개 중 정답 %s개, 정답률: %s%%\n\n", questionCount, answerCount, rate);

    setDBData(userID);
  }//Result end

  public void setEXP() throws Exception {
    String tmp =d.memLevel ;
    d.select(userID);
    d.exp = d.exp + (anCount * level);
    if(d.exp<10) {d.memLevel = "Bronze";//40
    }else if(d.exp<100) {d.memLevel = "Silver";
    }else if(d.exp<200) {d.memLevel = "Gold";
    }else if(d.exp<400) {d.memLevel = "Platinum";
    }else if(d.exp<800) {d.memLevel = "Diamond";
    }else {d.memLevel = "Mster";}//if end

    if(!d.memLevel.equals(tmp)) {
      System.out.printf("\nLevel Up!!   %s ==> %s\n\n\n", tmp, d.memLevel);
    }//if end

    setDBData(userID);
  }//setEXP end

  public void setDBData(String userID) {
    try {
      sql = "update member set score = ?, memLevel = ?, exp = ?, point = ? where ID = ?";
      PST = CN.prepareStatement(sql);
      PST.setInt(1, d.uScore); PST.setString(2, d.memLevel); PST.setInt(3, d.exp);
      PST.setInt(4, d.point); PST.setString(5, userID);
      PST.executeUpdate();

      sql = "update answerRate set "
          + "questionTotalCnt = ?, questionCnt1 = ?, questionCnt2 = ?, questionCnt3 = ?,"
          + "answerTotalCnt = ?,answerCnt1 = ?, answerCnt2 = ?, answerCnt3 = ?,"
          + "answerTotalRate = ?, answerRate1 = ?, answerRate2 = ?, answerRate3 = ? "
          + "where ID = ?";
      PST = CN.prepareStatement(sql);
      PST.setInt(1, d.questionTotalCnt); PST.setInt(2, d.questionCnt1);
      PST.setInt(3, d.questionCnt2); PST.setInt(4, d.questionCnt3);
      PST.setInt(5, d.answerTotalCnt); PST.setInt(6, d.answerCnt1);
      PST.setInt(7, d.answerCnt2); PST.setInt(8, d.answerCnt3);
      PST.setDouble(9, d.answerTotalRate); PST.setDouble(10, d.answerRate1);
      PST.setDouble(11, d.answerRate2); PST.setDouble(12, d.answerRate3);
      PST.setString(13, userID);
      PST.executeUpdate();

    }catch(Exception ex) {}
  }//setDBData end
}//Game class end



class WordList {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  PreparedStatement pstmt = null;
  Scanner sc = new Scanner(System.in);
  int sLevel = 0;
  String msg = null;
  int engin;
  String ENG;
  String userID;
  String KOR;
  Dao d = new Dao(userID);
  Emoticon em = new Emoticon(userID);

  public WordList(String userID) {
    this.userID = userID;
  }

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void printWords()  throws Exception {
    int[] num = randomNum();
    Loop: for (engin = 0; engin < Gtotal(); engin++) {
      enginStart();

      d.select(userID);
      System.out.println("\t\t\t    " + em.printChar2(d.getMychar()) + "\n");

      System.out.println("\t\t\t\t(" + (engin+1) + ")\n");
      System.out.println("\t\t\t\t[단 어]\t\t\t[ 뜻 ]\n\t\t\t\t______________________________\n");
      msg = "select ENG,KOR from word where WORDNUM =" + num[engin];
      RS = ST.executeQuery(msg);
      if (RS.next() == true) {
        ENG = RS.getString("ENG");
        KOR = RS.getString("KOR");
        if (ENG.length() < 8) {
          System.out.println("\t\t\t\t" + ENG+ "\t\t\t" + KOR);
        } else {
          System.out.println("\t\t\t\t" + ENG+ "\t\t" + KOR);
        }
        System.out.println("\n\n\t\t\t\t[Enter. 다음]   [2. 이전]   [8. 뒤로가기]\n\n\n\n\n\n\n\n");
        String command = sc.nextLine();
        for (int i = 0; i <30; i++) {
          System.out.println("\n\n\n");
        }
        switch(command) {
          case "2":
            if (engin > 0) {
              engin -= 2;
            } else if (engin == 0) {
              engin -= 1;
            }
            break;
          case "8":
            System.out.println("종료합니다.\n\n\n");
            break Loop;
          default :

        }
        enginStart();
      }
    }
    if (engin == Gtotal()) {
      System.out.println("모든 단어를 정독하셨습니다. 게임에 도전하세요!");
    }
  }

  public void enginStart() {
    if (engin == 0) {
      for (int i = 0; i < 7; i++) {
        System.out.println("\n");
      }
    }
  }

  public void printSeperate() {

  }

  public int Gtotal() throws Exception {
    int cnt = 0;
    msg = "select count(*) ucnt from word";
    RS = ST.executeQuery(msg);
    if (RS.next() == true) {
      cnt = RS.getInt("ucnt");
    }
    return cnt;
  }

  public int[] randomNum() throws Exception {
    dbConnect();
    int[] num = new int[Gtotal()];
    for(int i = 0; i < num.length; i++) {
      num[i] = (int)(Math.random()*num.length) +1;
      for(int j = 0; j < i; j++) {
        if(num[i] == num[j]) {
          i--;
        }//if end
      }//for end
    }
    return num;
  }
}//WordTest Class END



class InputAnswer implements Callable<String> { // 값 입력받기
  @Override
  public String call() throws IOException {

    Scanner sc = new Scanner(System.in);
    String input = "사용자입력값";
    input = sc.nextLine();

    //    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    //    String input = "";
    //    while ("".equals(input)) {
    //      try {
    //        while (!inp.ready()) {
    //          Thread.sleep(100);
    //        }//while end
    //        input = inp.readLine();
    //      } catch (InterruptedException e) {
    //        return null;
    //      }//try end
    //    } //while end

    return input;
  }//call end
}//InputAnswer Class END


