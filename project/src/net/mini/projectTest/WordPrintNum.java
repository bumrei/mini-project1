package net.mini.projectTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class WordPrintNum {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  PreparedStatement pstmt = null;   

  String sql = null;
  Scanner sc = new Scanner(System.in);
  int uMemno;
  String uName = null;
  String uID = null;
  String uPsw = null;
  String uEmail = null;
  Date uCdate = null;
  int uScore;
  int uadmin;
  String userID = null;
  String userPsw = null;
  int userAdmin;
  String ucmt = null;
  String delId = null;
  int sLevel = 0;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public static void main(String[] args) {
    try {
      WordPrintNum wp = new WordPrintNum();

      wp.dbConnect();
      wp.selectLevel();

    } catch(Exception e) {}
  }

  public void selectLevel() throws Exception {
    Loop :while(true) {
      System.out.print("\nLevel 선택 [1~3] [8. 뒤로가기]\n >>> ");
      try {
        sLevel = Integer.parseInt(sc.nextLine());

        if (sLevel >= 1 && sLevel <=3) {
          System.out.println("\n  Level: " + sLevel);

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
}