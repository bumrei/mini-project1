package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class GameMenu {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  PreparedStatement pstmt = null;
  Scanner sc = new Scanner(System.in);
  String msg = null;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void goIntoTheGame() throws Exception {
    System.out.print("\n\n게임 로딩중"); Thread.sleep(500);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰▰"); Thread.sleep(100);
    System.out.print("▰▰▰▰▰▰▰"); Thread.sleep(1000);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(500);
    System.out.print("▰"); Thread.sleep(500);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(1000);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(100);
    System.out.print("▰"); Thread.sleep(500);
    System.out.print("▰▰▰▰▰▰"); Thread.sleep(500);
    System.out.println("꧁ଘ(੭ˊ꒳ˋ)੭✧\n");
    Thread.sleep(700);
    game();
  }

  public void game() throws Exception {
    Game game = new Game();
    WordTest wt = new WordTest();
    this.dbConnect();
    System.out.println("҉ ٩(๑>ω<๑)۶҉     단어 맞추기 게임 월드에 오신것을 환영합니다.  ꉂ (๑¯ਊ¯)σ \n");

    while(true) {

      System.out.print("[1. 단어 암기] \n\n[2. 단어 테스트]\n\n[3. 랭킹표]\n\n[4. 내 정보]\n\n"
          + "[5. comment to 관리자]\n\n[8. 로그 아웃]\n\n[9. 게임 종료]");

      System.out.print("\n\n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1": 
          game.wordList(); 
          break;
        case "2": // 단어 테스트 게임
          wt.wordTest();
          break;
        case "3": 
          ranking(); 
          back();
          System.out.println("준비중");
          break;
        case "4": // 내 정보
          System.out.println("준비중");
          //내정보출력, 탈퇴, 비밀번호 변경(점수리셋?)
          break;
        case "5": // comment to 관리자
          System.out.println("준비중");
          break;
        case "8": // 로그아웃
          System.out.println("로그아웃 합니다.");
          return;
        case "9": // 종료
          System.out.println("게임을 종료합니다.");
          System.exit(0);
        default :
          System.out.println("올바른 번호를 입력해 주세요.  !!!( •̀ ᴗ •́ )و!!!");
      }
    }
  }

  public void ranking() throws Exception {
    msg = "select ID, score from member order by score desc";
    RS = ST.executeQuery(msg);
    System.out.println("아이디\t점 수");
    while (RS.next() == true) {
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.println(uid+"\t" + uscore);
    }
  }

  public void back() {
    System.out.println("[8. 뒤로가기]");
    String command = sc.nextLine();
    if (command.equals("8")) {
      return;
    }
  }



}
