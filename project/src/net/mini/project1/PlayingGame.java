package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class PlayingGame {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  PreparedStatement pstmt = null;
  Scanner sc = new Scanner(System.in);
  int sLevel = 0;
  String sql = null;
  WordTest wt = new WordTest("ID2");
  Lanking lk = new Lanking();

  public void game() throws Exception {


    System.out.println("҉ ٩(๑>ω<๑)۶҉     단어 맞추기 게임 월드에 오신것을 환영합니다.  ꉂ (๑¯ਊ¯)σ \n");

    while(true) {

      System.out.print("[1. 단어 암기] \n\n[2. 단어 테스트]\n\n[3. 랭킹표]\n\n[4. 내 정보]\n\n"
          + "[5. comment to 관리자]\n\n[8. 로그 아웃]\n\n[9. 게임 종료]");

      System.out.print("\n\n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1": this.selectLevel(); break;
        case "2": // 단어 테스트 게임
          wt.dbConnect();
          wt.wordTest();
          break;
        case "3": lk.lanking(); back();
        System.out.println("준비중");
        break;
        case "4": // 내 정보
          System.out.println("준비중");
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

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void selectLevel() throws Exception {
    Loop :while(true) {
      dbConnect();
      System.out.print("\nLevel 선택 [1~3] [8. 뒤로가기]\n >>> ");
      try {
        sLevel = Integer.parseInt(sc.nextLine());

        if (sLevel >= 1 && sLevel <=3) {
          System.out.println("\n  Level: " + sLevel);

          System.out.println("단어 범위 [1~100]");
          System.out.print("\n출력할 단어 시작번호>>> ");
          int snum = Integer.parseInt(sc.nextLine());
          System.out.print("출력할 단어 마지막번호>>> ");
          int fnum = Integer.parseInt(sc.nextLine());

          sql = "select * from (select row_number() "
              + "over(partition by wordlevel order by wordNum) as row_num, b.* from word b) "
              + "a where wordlevel = " + sLevel + " and a.row_num between ? and ? ";
          pstmt = CN.prepareStatement(sql);
          pstmt.setInt(1, snum);
          pstmt.setInt(2, fnum);
          RS = pstmt.executeQuery();

          System.out.println("\n  영단어 \t 단어뜻");
          System.out.println("---------------------------");
          while(RS.next() == true) {
            String peng = RS.getString("eng");
            String pkor = RS.getString("kor");
            System.out.println("  " + peng + " \t " + pkor);
          }
        } else if (sLevel == 8) {
          System.out.println("뒤로가기");
          break Loop;
        } else {
          System.out.println("\n번호 다시 확인해주세요.");
        }
      } catch(Exception e) {System.out.println("번호 다시 확인해주세요");}
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
