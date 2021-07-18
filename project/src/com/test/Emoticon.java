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
  String userID;
  int urownum;
  int urow;
  int ucol;
  int upoint;
  Scanner sc = new Scanner(System.in);
  int[] price = new int[3];
  String[][] ars = new String[3][5];
  boolean[][] arb = new boolean[3][5];
  Dao d = new Dao(userID);

  public Emoticon(String userID) {
    this.userID = userID;
    // TODO Auto-generated constructor stub
  }



  public void arrContents() {
    ars[0][0] = "   -_-^    "; ars[0][1] = "  ∙̑◡∙̑    "; ars[0][2] = "      ᵔ︡⌔ᵔ︠   ";
    ars[0][3] = "  •ܫ•      "; ars[0][4] = "   ◠‿◠     "; ars[1][0] = " (´•̥ω•̥`) ";
    ars[1][1] = " ( ˃̣̣̥᷄⌓˂̣̣̥᷅ )            "; ars[1][2] = " (๑•̀ω•́)۶ "; ars[1][3] = "♪(๑ᴖ◡ᴖ๑)♪  ";
    ars[1][4] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][0] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][1] = "( ⁎ ᵕᴗᵕ ⁎ )";
    ars[2][2] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][3] = "( ⁎ ᵕᴗᵕ ⁎ )"; ars[2][4] = "  준비중  ";
    price[0] = 10; price[1] = 30; price[2] = 50;
  }


  public String printChar(int num) {
    arrContents();
    int userrow = (num-1) / 5;
    int usercol = (num-1) % 5;
    return ars[userrow][usercol];
  }

  public String printChar2(int num) {

    arrContents();
    int userrow = (num-1) / 5;
    int usercol = (num-1) % 5;
    String th = ars[userrow][usercol] + saying();
    return th;
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
        d.select(userID);
        upoint = d.getPoint();
        if (upoint <= 0) {
          System.out.println("구매할 포인트가 없습니다. [9. 뒤로가기]");
          String command = sc.nextLine();
          if (command.equals("9")) {
            break;
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
              continue;
            }
          } else if (num <11) {
            if (upoint >= 30) {
              upoint -= 30;              
            } else {
              System.out.println("포인트가 모자랍니다.");
              continue;
            }
          } else {
            if (upoint >= 50) {
              upoint -= 50;              
            } else {
              System.out.println("포인트가 모자랍니다.");
              continue;
            }
          }

          msg = "update member set point = ? where id = ?";
          PST = CN.prepareStatement(msg);
          PST.setInt(1, upoint);
          PST.setString(2, userID);
          PST.executeUpdate();

          System.out.println("구매를 완료하셨습니다. 인벤토리에서 확인하세요.\n\n");
          break;
        } else {
          System.out.println("구매가 완료되지 않았습니다. 다시 시도해 주세요.\n");
          continue;
        }
      }catch (Exception e) {System.out.println("숫자만 입력 가능합니다.\n");}
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
    while (true) {
      System.out.println("[1. 케릭터 선택]   [9. 뒤로가기]");
      String command = sc.nextLine();

      switch (command) {
        case "1":
          while (true) {
            //            try {
            System.out.print("캐릭터를 선택해 주세요.  [0. 뒤로가기]\n >> ");
            command = sc.nextLine();
            if (command.equals("0")) {
              break;
            }

            msg = "select a.* from (select rownum as rnum, IROW, JCOLUMN from transaction where id = '" + userID + "') a where a.rnum =" + command;
            RS = ST.executeQuery(msg);
            if (RS.next() == true) {
              int usrow = RS.getInt("IROW");
              int uscol = RS.getInt("JCOLUMN");
              System.out.println("캐릭터가 변경되었습니다.\n\n");
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
        case "9":
          return;
        default :
          System.out.println("옳바른 번호를 입력해 주세요.");
          continue;
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

    String[] ab = new String[15];
    ab[0] = "화이팅!! 할 수 있습니다!!"; ab[1] = "이모티콘 한번 구매해 보세요"; 
    ab[2] = "게임 실행은 2번입니다!ㅎㅎ"; ab[3] = "충분히 단어를 외우고 게임에 참여해보세요!"; 
    ab[4] = "빨리 레벨업 하시죠!"; ab[5] = "가즈아!! 레벨업 가즈아!!"; 
    ab[6] = "아버지가 말씀하셨죠.. English is future!!"; ab[7] = "랭킹 1위 한번 찍어 봅시다!!";
    ab[8] = "흥얼 흥얼~~~ 이노래 아시나요?ㅎㅎㅎ"; 
    ab[9] = "충분한 휴식을 취해주시고 게임해주세요ㅎㅎ";
    ab[10] = "";
    ab[11] = "";
    ab[12] = "";
    ab[13] = "";
    ab[14] = "";
    int a = (int)(Math.random()*15);

    return ab[a];
  }

}
