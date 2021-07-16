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
    //    Thread.sleep(700);
    game();
  }

  public void game() throws Exception {
    Game game = new Game();
    WordList wl = new WordList();
    AccountInfo ai = new AccountInfo();
    Emoticon em = new Emoticon();
    this.dbConnect();
    System.out.println("\n=================================================================================\n");
    System.out.println("҉ ٩(๑>ω<๑)۶҉     단어 맞추기 게임 월드에 오신것을 환영합니다.  ꉂ (๑¯ਊ¯)σ \n");
    System.out.println("\n=================================================================================\n");
    while(true) {
      System.out.println("\n-------------------------\n");
      System.out.print("[1. 단어 암기] \n\n[2. 단어 게임 시작]\n\n[3. 랭킹]\n\n[4. 내 정보]"
          + "\n\n[5. 상점]\n\n[7. 공지사항]\n\n[9. 로그아웃]\n\n[0. 게임 종료]");

      System.out.print("\n\n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1": wl.printWords();  break;
        case "2": game.wordTest();  break;
        case "3": ranking(); back();  break;
        case "4": ai.info();  break;
        case "5": em.emojiShop(); break;
        case "6": System.out.println("쪽지함?");/*message();*/ break;
        case "7": notification();  break;
        case "9": System.out.println("로그아웃 합니다.");  return;
        case "0": System.out.println("게임을 종료합니다.");  System.exit(0);
        default : System.out.println("올바른 번호를 입력해 주세요.  !!!( •̀ ᴗ •́ )و!!!");
      }
    }
  }

  public void ranking() throws Exception {
    //msg = "select ID, score from member order by score desc";
    System.out.println("\n\t      [ Top 10 ]");
    msg = "select rownum, m.* from (select id, score, rank() over (order by score desc) rank from member) m where rownum <=10";
    RS = ST.executeQuery(msg);
    System.out.println("\n\t 순위\t아이디\t 점 수");
    while (RS.next() == true) {
      int uranking = RS.getInt("rank");
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.println("\t  "+uranking + "\t "+uid+"\t  " + uscore);
    }
    System.out.println("\t------------------------");
    msg = "select rownum, m.* from (select id, score, rank() over (order by score desc) rank from member) m where id = '"+ LogInMenu.userID+"'";
    RS = ST.executeQuery(msg);
    //    System.out.println("내 위치 →");
    System.out.println("\t내 순위\t아이디\t 점 수");
    if (RS.next() == true) {
      int uranking = RS.getInt("rank");
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.printf("\t→ %d\t %s\t  %d\n",uranking,uid,uscore);
      if (uranking > 10) {
        int n = uranking -10;
        System.out.println("\n top10 에 올라가기까지 " + n + "등 남았습니다");
      }
    }
  }

  public void notification() {
    try{
      msg = "select code, title, content from notice order by code";
      RS = ST.executeQuery(msg);
      System.out.println("\nNo. \t Title \t\t\t\t\t Content");
      System.out.println("-------------------------------------------------------------------------------");
      while(RS.next() == true) {
        int pcode = RS.getInt("code");
        String ptitle = RS.getString("title");
        String pcontent = RS.getString("content");
        System.out.println(pcode + "\t" + ptitle + " \t\t\t\t\t " + pcontent);
      }
    } catch(Exception e) {}
  }

  public void back() {
    System.out.println("\n[8. 뒤로가기]");
    String command = sc.nextLine();
    if (command.equals("8")) {
      return;
    }
  }



}
