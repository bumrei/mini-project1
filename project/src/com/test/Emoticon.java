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
    ars[0][0] = "   ᵔεᵔ     "; ars[0][1] = "  ∙̑◡∙̑    "; ars[0][2] = "      ᵔ︡⌔ᵔ︠   ";
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
    System.out.println("-----------------------------------------------------------------------------------------");
    System.out.println("\t    1행\t\t    2행\t\t    3행\t\t    4행\t\t    5행\t\t|");
    System.out.println("-----------------------------------------------------------------------------------------");
    System.out.println("     |\t\t\t\t\t\t\t\t\t\t\t|");
    for (int i = 0; i < ars.length; i++) {
      System.out.print((i+1)+ "열  |\t");
      for (int j = 0; j < ars[i].length; j++) {
        if (arb[i][j] == true) {
          System.out.print("■ " + ars[i][j] + "\t");
        } else {
          System.out.print("□ " + ars[i][j] + "\t");
        }
      } 
      System.out.print("|");
      System.out.println();
      System.out.print("     |\t");
      for (int b = 0; b < ars[i].length; b++) {
        System.out.print("    [" + price[i] + "원]" + "\t");
      } 
      System.out.print("|");
      System.out.println("\n     |\t\t\t\t\t\t\t\t\t\t\t|");
    }
    System.out.println("-----------------------------------------------------------------------------------------\n");
    purchase();
  }

  public void purchase() throws Exception {
    while(true) {
      try {
        int userrow = 0, usercol = 0;
        System.out.println("구매하실 물품을 입력해 주세요  (행열)");
        String str = sc.nextLine();
        String[] rowcol = str.split("");
        userrow = Integer.parseInt(rowcol[0]);
        usercol = Integer.parseInt(rowcol[1]);
        if (str.length() > 2) {
          System.out.println("잘못 입력하셨습니다.");
          continue;
        } else if (userrow > 3 || userrow < 1|| usercol > 5 || usercol < 1) {
          System.out.println("잘못 입력하셨습니다.");
          continue;
        } else if (arb[userrow-1][usercol-1] == true) {
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
      System.out.println(urownum + ": \t[\t" + ars[urow-1][ucol-1] + "\t]");
    }

  }

  public void selectItem() throws Exception {
    dbConnect();
    msg = "select IROW,JCOLUMN from transaction where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      urow = RS.getInt("IROW");
      ucol = RS.getInt("JCOLUMN");
      arb[urow-1][ucol-1] = true;
    }
  }


  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement();
  }

}
