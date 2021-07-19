package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class GameMenu {
  Connection CN ;
  Statement ST ;
  ResultSet RS ;
  PreparedStatement pstmt ;
  Scanner sc = new Scanner(System.in);
  String msg ;
  String userID;
  Dao d = new Dao(userID);
  Emoticon em = new Emoticon(userID);
  public GameMenu() { }
  public GameMenu(String userID) {
    this.userID = userID;
  }

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void game() throws Exception {
    WordList wl = new WordList(userID);
    Game game = new Game(userID);
    AccountInfo ai = new AccountInfo(userID);
    Emoticon em = new Emoticon(userID);

    while(true) {
      d.select(userID);
      System.out.println(" \t    ┌────────────────────────────────────────────┐");
      System.out.print(em.printChar(d.getMychar()));
      System.out.println("  < " + em.saying());
      System.out.println(" \t    └────────────────────────────────────────────┘");
      System.out.print("\n\t\t\t\t\t[ 보유 골드 : " + d.getPoint() + " G ]\n");
      Thread.sleep(1500);
      System.out.print("\n\n  [1. 단어 암기] \n\n  [2. 단어 게임 시작]\n\n  [3. 랭킹]\n\n  [4. 내 정보]"
          + "\n\n  [5. 인벤토리]\n\n  [6. 상점]\n\n  [7. 공지사항]\n\n  [9. 로그아웃]\n\n  [0. 게임 종료]");
      System.out.print("\n\n >>> ");
      String menu = sc.nextLine();
      this.dbConnect();
      switch (menu) {
        case "1": wl.printWords();  break;
        case "2": game.wordTest();  break;
        case "3": ranking(); back();  break;
        case "4": ai.info();  break;
        case "5": em.inventory();  break;
        case "6": em.purchase(); break;
        case "7": notification();  break;
        case "9": System.out.println("\n로그아웃 합니다.");  return;
        case "0": System.out.println("\n\n게임을 종료합니다.\n안녕히가세요!!");  System.exit(0);
        default : System.out.println("번호를 다시 확인해주세요!!!( •̀ ᴗ •́ )و!!!");
      }
    }
  }

  public void ranking() throws Exception {
    System.out.println(" \t    ┌────────────────────────────────────────────┐");
    System.out.print(em.printChar(d.getMychar()));
    System.out.println("  < " + em.saying());
    System.out.println(" \t    └────────────────────────────────────────────┘\n");
    msg = "select ID, score from member order by score desc";
    int rank10 = 0 ;
    System.out.println("\n\t      [ Top 10 ]");
    msg = "select rownum, m.* from (select id, score, rank() over (order by score desc) rank from member) m where rownum <=10";
    RS = ST.executeQuery(msg);
    System.out.println("\n\t 순위\t아이디\t\t점 수");
    while (RS.next() == true) {
      int uranking = RS.getInt("rank");
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.printf("\t  %d\t%-10s\t%d\n", uranking, uid, uscore);
      //      System.out.println("\t  "+uranking + "\t "+uid+"\t  " + uscore);
      if(uranking  == 10 ) {
        rank10 = uscore ;
      }
    }
    System.out.println("\t-----------------------------");
    msg = "select rownum, m.* from (select id, score, rank() over (order by score desc) rank from member) m where id = '"+ userID+"'";
    RS = ST.executeQuery(msg);
    System.out.println("\t내 순위\t아이디\t\t점 수");
    if (RS.next() == true) {
      int uranking = RS.getInt("rank");
      String uid = RS.getString("ID");
      int uscore = RS.getInt("score");
      System.out.printf("\t→ %d\t%-10s\t%d\n",uranking,uid,uscore);
      if (uranking == 1) {System.out.println("\n\t현재 1등입니다!!");}
      if (uranking > 10) {
        int n = uranking -10;
        int x = rank10 - uscore ; 
        System.out.println("\n Top10 에 올라가기까지 " + n + "등 / "+ x + "점 남았습니다");
      }
    }
  }

  public void notification() {
    AdminMenu am = new AdminMenu();
    notice: while(true) {
      try{
        update("ldate",userID);
        msg = "select * from notice order by code asc";
        RS = ST.executeQuery(msg);
        System.out.println("\nNo.\t  Date  \t Title");
        System.out.println("------------------------------------------------------------");
        while(RS.next() == true) {
          int pcode = RS.getInt("code");
          Date pdate = RS.getDate("cdate");
          String ptitle = RS.getString("title");
          System.out.println(pcode + "\t  " + pdate + "  \t " + ptitle);
        }
      } catch(Exception e) {}
      while(true) {
        System.out.println("\n\n[1. 공지 상세조회]   [2. 공지 검색]   [9. 뒤로가기]");
        System.out.print(" >>> ");
        String menu = sc.nextLine();
        switch (menu) {
          case "1": am.detailsNotice(); break;
          case "2": searchNotice(); break;
          case "9": break notice;
          default : System.out.println("\n번호를 다시 확인해주세요."); continue;
        }//switch end
      }//while end
    }//while end
  }//notification end

  public void searchNotice() {
    int pcode = 0;
    String input = null, ptitle = "제목";
    Date pdate;
    try {
      System.out.println("\n검색할 단어를 입력해주세요.");
      input = sc.nextLine();
      msg = "select * from notice where title like '%" + input+ "%'"
          + "or content like '%" + input +"%'";
      RS = ST.executeQuery(msg);
      System.out.println("\nNo.\t  Date  \t Title");
      System.out.println("------------------------------------------------------------");
      while (RS.next() == true) {
        pcode = RS.getInt("code");
        ptitle = RS.getString("title");
        pdate = RS.getDate("cdate");
        System.out.println(pcode + "\t  " + pdate + "  \t " + ptitle);
      }//while end
      if(pcode == 0) {
        System.out.println("\n조회결과가 없습니다.");
      }//if end
    }catch(Exception ex) { }
  }//searchNotice end

  public void update(String date, String userID ) throws Exception{
    dbConnect();
    msg = "update member set " +date+ " = sysdate where id = '"+userID+"'";
    ST.executeUpdate(msg);
  }

  public void back() {
    System.out.println("\n[9. 뒤로가기]");
    String command = sc.nextLine();
    if (command.equals("9")) {
      return;
    }
  }
}
