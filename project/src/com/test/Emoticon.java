package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Emoticon {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  PreparedStatement PST ;

  String msg = "";
  String userID = LogInMenu.userID;;
  int urownum;
  int urow;
  int ucol;
  int upoint;
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
    Loop: while(true) {
      System.out.println("[1. 이모티콘 구매]   [2. 인벤토리 확인]   [8. 뒤로가기]");
      String ans = sc.nextLine();
      if (ans.equals("2")) {
        inventory(); break;
      } else if (ans.equals("1")){
        showcase(); break;
      } else if (ans.equals("8")) {
        break Loop;
      } else {
        System.out.println("메뉴선택 다시");
        continue;
      }
    }
  }

  public void showcase() {
    try {
      System.out.println(userID+" 님의 보유 포인트: "+ upoint);
      System.out.println("\n[진열장]");
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
    }catch(Exception e) {}
  }

  public void purchase() throws Exception {
    while(true) {
      try {
        if (upoint <= 0) {
          System.out.println("구매할 포인트가 없습니다.");
          back();
        }

        int userrow = 0, usercol = 0;
        System.out.println("\n구매하실 물품을 입력해 주세요");
        int num = Integer.parseInt(sc.nextLine());

        userrow = (num-1) / 5;
        usercol = (num-1) % 5;
        if (num > 15 || num <= 0) {
          System.out.println("잘못 입력하셨습니다.");
          continue;
        } else if (num == 1) {
          System.out.println("기본 아이템입니다.");
          continue;
        } else if (arb[userrow][usercol] == true) {
          System.out.println("이미 구매하신 상품입니다.");
          continue;
        } 

        msg = "insert into transaction values('" + userID + "'," + userrow + "," + usercol + ")";
        int a = ST.executeUpdate(msg);

        if (a == 1) {
          if (num <6) {
            if (upoint >= 10) {
              upoint -= 10;              
            } else {
              System.out.println("포인트가 모자랍니다.");
            }
          } else if (num <11) {
            if (upoint >= 30) {
              upoint -= 30;              
            } else {
              System.out.println("포인트가 모자랍니다.");
            }
          } else {
            if (upoint >= 50) {
              upoint -= 50;              
            } else {
              System.out.println("포인트가 모자랍니다.");
            }
          }

          msg = "update member set point = ? where id = ?";
          PST = CN.prepareStatement(msg);
          PST.setInt(1, upoint);
          PST.setString(2, userID);
          PST.executeUpdate();

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

    msg = "select * from member where id = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while(RS.next() == true) {
      upoint = RS.getInt("point");
    }
  }


  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement();
  }

  public void back() {
    System.out.println("\n[8. 뒤로가기]");
    String command = sc.nextLine();
    if (command.equals("8")) {
      return;
    }
  }

}
