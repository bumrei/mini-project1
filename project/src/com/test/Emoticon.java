package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Emoticon {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = "";
  String userID = LogInMenu.userID;;
  int urownum;
  int urow;
  int ucol;
  Scanner sc = new Scanner(System.in);
  int[] price = new int[3];
  String[][] ars = new String[3][5];
  boolean[][] arb = new boolean[3][5];

  public void arrContents() {
    ars[0][0] = "   -_-^    "; ars[0][1] = "  ∙̑◡∙̑    "; ars[0][2] = "      ᵔ︡⌔ᵔ︠   ";
    ars[0][3] = "  •ܫ•      "; ars[0][4] = "   ◠‿◠     "; ars[1][0] = " (´•̥ω•̥`) ";
    ars[1][1] = " ( ˃̣̣̥᷄⌓˂̣̣̥᷅ )            "; ars[1][2] = " (๑•̀ω•́)۶ "; ars[1][3] = "♪(๑ᴖ◡ᴖ๑)♪  ";
    ars[1][4] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][0] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][1] = "( ⁎ ᵕᴗᵕ ⁎ )";
    ars[2][2] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][3] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][4] = "( ⁎ ᵕᴗᵕ ⁎ )";
    price[0] = 10; price[1] = 30; price[2] = 50;
  }


  public void emojiShop() throws Exception {
    arrContents();
    selectItem();
    System.out.println("단어상점에 오신것을 환영합니다!!\n");
    System.out.println("[진열장]");
    for (int i = 0; i < ars.length; i++) {
      if (i == 0) {
        System.out.println("┌───────────────┬───────────────┬───────────────┬───────────────┬───────────────┐");
      } else {
        System.out.println("├───────────────┼───────────────┼───────────────┼───────────────┼───────────────┤");
      }
      System.out.print("│");
      for (int a = 0; a < ars[i].length; a++) {
        if (arb[i][a] == true) {
          System.out.print("    [" + ((i*5)+(a+1)) + "] ■ \t│");
        } else {
          System.out.print("    [" + ((i*5)+(a+1)) + "] □ \t│");
        }
      }
      System.out.println("\n├───────────────┼───────────────┼───────────────┼───────────────┼───────────────┤");
      System.out.print("│");
      for (int j = 0; j < ars[i].length; j++) {
        System.out.print("  " +ars[i][j] + "\t│");
      } 
      System.out.println();
      System.out.print("│");
      for (int b = 0; b < ars[i].length; b++) {
        if (i == 0 && b == 0) {
          System.out.print("    [기본]\t│");
        } else {
          System.out.print("    [" + price[i] + "원]" + "\t│");
        }
      } 
      System.out.println();
    }
    System.out.println("└───────────────┴───────────────┴───────────────┴───────────────┴───────────────┘\n");
    purchase();
  }

  public void purchase() throws Exception {
    while(true) {
      try {
        int userrow = 0, usercol = 0;
        System.out.println("구매하실 물품을 입력해 주세요");
        int num = Integer.parseInt(sc.nextLine());

        userrow = (num-1) / 5;
        usercol = (num-1) % 5;

        if (num > 15 || num <= 0) {
          System.out.println("잘못 입력하셨습니다.");
          continue;
        } else if (arb[userrow][usercol] == true) {
          System.out.println("이미 구매하신 상품입니다.");
          continue;
        }

        msg = "insert into transaction values('" + userID + "'," + userrow + "," + usercol + ")";
        int a = ST.executeUpdate(msg);
        if (a == 1) {
          System.out.println("구매를 완료하셨습니다. 인벤토리에서 확인하세요.");
          break;
        } else {
          System.out.println("구매가 완료되지 않았습니다. 다시 시도해 주세요.");
          continue;
        }
      }catch (Exception e) {System.out.println("숫자만 입력 가능합니다.");}
    }
  }

  public void inventory() throws Exception {
    dbConnect();
    selectItem();
    arrContents();
    System.out.println("인벤토리입니다.");
    msg = "select ROWNUM,IROW,JCOLUMN from transaction where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      urownum = RS.getInt("ROWNUM");
      urow = RS.getInt("IROW");
      ucol = RS.getInt("JCOLUMN");
      System.out.println(urownum + ": \t[\t" + ars[urow][ucol] + "\t]");
    }

  }

  public void selectItem() throws Exception {
    dbConnect();
    msg = "select IROW,JCOLUMN from transaction where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      urow = RS.getInt("IROW");
      ucol = RS.getInt("JCOLUMN");
      arb[urow][ucol] = true;
    }
  }


  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement();
  }

}
