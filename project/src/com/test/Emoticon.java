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

  String msg;
  String userID;
  int urownum, urow, ucol, upoint;
  Scanner sc = new Scanner(System.in);
  int[] price = new int[3];
  String[][] ars = new String[3][5];
  boolean[][] arb = new boolean[3][5];
  Dao d = new Dao(userID);

  public Emoticon(String userID) {
    this.userID = userID;
  }

  public void arrContents() {
    ars[0][0] = "  ^_^+   "; ars[0][1] = "  ∙̑◡∙̑  "; ars[0][2] = "  ᵔ︡⌔ᵔ︠     ";
    ars[0][3] = "  •ܫ•    "; ars[0][4] = "  ◠‿◠    "; ars[1][0] = "(´•̥ω•̥`)";
    ars[1][1] = "( ˃̣̣̥᷄⌓˂̣̣̥᷅ )         "; ars[1][2] = "(๑•̀ω•́)۶ "; ars[1][3] = "♪(๑ᴖ◡ᴖ๑)♪";
    ars[1][4] = "(๑˘ꇴ˘๑)  "; ars[2][0] = "Çっ•ﻌ•ʔっ "; ars[2][1] = "Ç •ɷ•ʔฅ  ";
    ars[2][2] = "♪(*´θ｀) "; ars[2][3] = "ε=(ง ˃̶͈̀ᗨ˂̶͈́)"; ars[2][4] = "(ꐦ ◣‸◢) ";
    price[0] = 500; price[1] = 3000; price[2] = 7000;
  }

  public String printChar(int num) {
    arrContents();
    int userrow = (num-1) / 5;
    int usercol = (num-1) % 5;
    return ars[userrow][usercol];
  }

  public void emojiShop() throws Exception {
    arrContents();
    selectItem();
    d.select(userID);
    System.out.println("\n  [진열장]\n");
    System.out.print("\t\t\t\t\t\t\t\t[보유 골드 : " + d.getPoint() + "]\n");
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
  }

  public void purchase() throws Exception {
    System.out.println("\n단어상점에 오신것을 환영합니다!!\n");
    while(true) {
      try {
        emojiShop();
        d.select(userID);
        upoint = d.getPoint();
        if (upoint <= 0) {
          System.out.println("구매할 포인트가 없습니다. [9. 뒤로가기]");
          String menu = sc.nextLine();
          if (menu.equals("9")) {
            break;
          } else {
            continue;
          }
        }

        int userrow = 0, usercol = 0;
        System.out.println("\n구매하실 물품을 입력해 주세요. [0. 뒤로가기]");
        int num = Integer.parseInt(sc.nextLine());
        if (num == 0) {
          System.out.println("\n\n");
          break;
        }
        userrow = (num-1) / 5;
        usercol = (num-1) % 5;
        if (num > 15 || num <= 0) {
          System.out.println("\n잘못 입력하셨습니다.");
          continue;
        } else if (num == 1) {
          System.out.println("\n기본 아이템입니다.");
          continue;
        } else if (arb[userrow][usercol] == true) {
          System.out.println("\n이미 구매하신 상품입니다.");
          continue;
        } 

        if (num <6) {
          if (upoint >= 500) {
            upoint -= 500;              
          } else {
            System.out.println("포인트가 모자랍니다.\n");
            Thread.sleep(1000);
            continue;
          }
        } else if (num <11) {
          if (upoint >= 3000) {
            upoint -= 3000;              
          } else {
            System.out.println("포인트가 모자랍니다.\n");
            Thread.sleep(1000);
            continue;
          }
        } else {
          if (upoint >= 7000) {
            upoint -= 7000;              
          } else {
            System.out.println("포인트가 모자랍니다.\n");
            Thread.sleep(1000);
            continue;
          }
        }

        msg = "insert into transaction values('" + userID + "'," + userrow + "," + usercol + ")";
        ST.executeUpdate(msg);
        msg = "update member set point = ? where id = ?";
        PST = CN.prepareStatement(msg);
        PST.setInt(1, upoint);
        PST.setString(2, userID);
        PST.executeUpdate();

        System.out.println("구매를 완료하셨습니다. 인벤토리에서 확인하세요.\n\n");
        Thread.sleep(1000);
        continue;
      }catch (Exception e) {System.out.println("숫자만 입력해주세요.\n");}
    }
  }

  public void inventory() throws Exception {
    dbConnect();
    selectItem();
    arrContents();
    System.out.println("\n인벤토리입니다.");
    msg = "select ROWNUM,IROW,JCOLUMN from transaction where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      urownum = RS.getInt("ROWNUM");
      urow = RS.getInt("IROW");
      ucol = RS.getInt("JCOLUMN");
      System.out.println(urownum + ": \t" + ars[urow][ucol]);
    }
    while (true) {
      System.out.println("[1. 캐릭터 선택]   [9. 뒤로가기]");
      String menu = sc.nextLine();
      switch (menu) {
        case "1":
          while (true) {
            System.out.print("\n캐릭터를 선택해 주세요.  [0. 뒤로가기]\n >>> ");
            menu = sc.nextLine();
            if (menu.equals("0")) {
              break;
            }
            msg = "select a.* from (select rownum as rnum, IROW, JCOLUMN from transaction where id = '" + userID + "') a where a.rnum =" + menu;
            RS = ST.executeQuery(msg);
            if (RS.next() == true) {
              int usrow = RS.getInt("IROW");
              int uscol = RS.getInt("JCOLUMN");
              System.out.println("\n캐릭터가 변경되었습니다.\n\n");
              int a = ((usrow*5)+(uscol+1));
              msg = "update member set MYCHAR =" + a + "where ID = '"+ userID + "'";
              ST.executeUpdate(msg);
            } else {
              System.out.println("잘못 입력하셨습니다.\n");
              continue;
            }
            break;
          }
          break;
        case "9": return;
        default : System.out.println("\n번호를 다시 확인해주세요."); continue;
      }
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

  public String saying() {

    String[] ab = new String[26];
    ab[0] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[1] = " 이모티콘 한번 구매해 보세요                │";
    ab[2] = " 게임 실행은 2번입니다!ㅎㅎ                 │";
    ab[3] = " 자 빨리 빨리 레벨업 하시죠! 달려달려!      │";
    ab[4] = " 자 빨리 빨리 레벨업 하시죠! 달려달려!      │";
    ab[5] = " 가즈아!! 레벨업 가즈아!!                   │";
    ab[6] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[7] = " 랭킹 1위 한번 찍어 봅시다!!                │";
    ab[8] = " 흥얼 흥얼~~~ 이노래 아시나요?ㅎㅎㅎ        │";
    ab[9] = " 충분한 휴식을 취해주시고 게임해주세요ㅎㅎ  │";
    ab[10] = " 어때요 재미있나요? 할만 한가요?            │";
    ab[11] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[12] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[13] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[14] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[15] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[16] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[17] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[18] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[19] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[20] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[21] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[22] = " 화이팅!! 할 수 있습니다!!                  │";
    ab[23] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[24] = " 아버지는 말씀하셨죠.. English is future!!  │";
    ab[25] = " 아버지는 말씀하셨죠.. English is future!!  │";
    int a = (int)(Math.random()*26);
    if (ab[a] == null) {
      return " 단어 쫙 외우고 시험 한번 가시죠!!          │";
    }
    return ab[a];
  }

}
