package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class GameMenu {
  Connection CN ;
  Statement ST ;
  ResultSet RS ;
  PreparedStatement pstmt ;
  Scanner sc = new Scanner(System.in);
  String msg ;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void goIntoTheGame() throws Exception {
    //    System.out.print("\n\n게임 로딩중  "); Thread.sleep(500);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰▰"); Thread.sleep(100);
    //    System.out.print("▰▰▰▰▰▰▰"); Thread.sleep(1000);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(500);
    //    System.out.print("▰"); Thread.sleep(500);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(1000);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(100);
    //    System.out.print("▰"); Thread.sleep(500);
    //    System.out.print("▰▰▰▰▰▰"); Thread.sleep(500);
    //    System.out.println("꧁ଘ(੭ˊ꒳ˋ)੭✧\n");
    Thread.sleep(700);
    game();
  }

  public void game() throws Exception {
    Game game = new Game();
    WordList wl = new WordList();
    AccountInfo ai = new AccountInfo();
    this.dbConnect();
    System.out.println("\n=================================================================================\n");
    System.out.println("҉ ٩(๑>ω<๑)۶҉     단어 맞추기 게임 월드에 오신것을 환영합니다.  ꉂ (๑¯ਊ¯)σ \n");

    while(true) {
      System.out.println("\n-------------------------\n");
      System.out.print("[1. 단어 암기] \n\n[2. 단어 게임 시작]\n\n[3. 랭킹]\n\n[4. 내 정보]"
          + "\n\n[5. 공지사항]\n\n[8. 로그아웃]\n\n[9. 게임 종료]");

      System.out.print("\n\n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1": wl.printWords();  break;
        case "2": game.wordTest();  break;
        case "3": ranking(); back();  break;
        case "4": ai.info();  break;
        case "5": notification();  break;
        case "8": System.out.println("로그아웃 합니다.");  return;
        case "9": System.out.println("게임을 종료합니다.");  System.exit(0);
        default : System.out.println("올바른 번호를 입력해 주세요.  !!!( •̀ ᴗ •́ )و!!!");
      }
    }
  }

  public void ranking() throws Exception {
    //msg = "select ID, score from member order by score desc";
    msg = "select rownum, m.* from (select id, score, rank() over (order by score desc) rank from member) m where rownum <=10";
    RS = ST.executeQuery(msg);
    System.out.println("랭킹\t아이디\t점 수");
    while (RS.next() == true) {
      int uranking = RS.getInt("rank");
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.println(uranking + "\t"+uid+"\t" + uscore);
    }

    msg = "select rownum, m.* from (select id, score, rank() over (order by score desc) rank from member) m where id = '"+ LogInMenu.userID+"'";
    RS = ST.executeQuery(msg);
    System.out.println("내랭킹\t아이디\t점수");
    while (RS.next() == true) {
      int uranking = RS.getInt("rank");
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.printf("%d\t%s\t%d\n",uranking,uid,uscore);
    }
  }

  public void notification() {
    try{
      msg = "select code, title, content from notice order by code";
      RS = ST.executeQuery(msg);
      System.out.println("\nNo. \t Title \t\t\t\t\t Content");
      while(RS.next() == true) {
        int pcode = RS.getInt("code");
        String ptitle = RS.getString("title");
        String pcontent = RS.getString("content");
        System.out.println(pcode + "\t" + ptitle + " \t\t\t\t\t " + pcontent);
      }
    } catch(Exception e) {}
  }

  public void back() {
    System.out.println("[8. 뒤로가기]");
    String command = sc.nextLine();
    if (command.equals("8")) {
      return;
    }
  }



}
