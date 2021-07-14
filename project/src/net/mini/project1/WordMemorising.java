package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class WordMemorising {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = "";
  String ENG;
  String KOR;
  int engin;
  Scanner sc =  new Scanner(System.in);


  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement();
  }


  public int Gtotal() throws Exception {
    int cnt = 0;
    msg = "select count(*) ucnt from word";
    RS = ST.executeQuery(msg);
    if (RS.next() == true) {
      cnt = RS.getInt("ucnt");
    }
    return cnt;
  }

  public void printSeperate() {
    if (ENG.length() < 8) {
      System.out.println("\t\t\t\t" + ENG+ "\t\t\t" + KOR);
    } else {
      System.out.println("\t\t\t\t" + ENG+ "\t\t" + KOR);
    }
    System.out.println("\n\n\t\t\t\t[Enter. 다음]   [2. 이전]   [8. 뒤로가기]\n\n\n\n\n\n\n\n");
    String command = sc.nextLine();
    for (int i = 0; i <30; i++) {
      System.out.println("\n\n\n");
    }
    switch(command) {
      case "1":
        break;
      case "2":
        if (engin > 0) {
          engin -= 2;
        } else if (engin == 0) {
          engin -= 1;
        }
        break;
      case "8":
        System.out.println("종료합니다.");
        System.exit(0);
      default :
    }
  }

  public void printWords()  throws Exception {
    int[] num = randomNum();
    for (engin = 0; engin < Gtotal(); engin++) {
      enginStart();
      System.out.println("\t\t\t\t(" + (engin+1) + ")\n");
      System.out.println("\t\t\t\t[단 어]\t\t\t[ 뜻 ]\n\t\t\t\t______________________________\n");
      msg = "select ENG,KOR from word where WORDNUM =" + num[engin];
      RS = ST.executeQuery(msg);
      if (RS.next() == true) {
        ENG = RS.getString("ENG");
        KOR = RS.getString("KOR");
        printSeperate();
        enginStart();
      }
    }
    System.out.println("모든 단어를 정독하셨습니다. 게임에 도전하세요!");
    back();
  }

  public void enginStart() {
    if (engin == 0) {
      for (int i = 0; i < 7; i++) {
        System.out.println("\n");
      }
    }
  }

  public int[] randomNum() throws Exception {
    dbConnect();
    int[] num = new int[Gtotal()];
    for(int i = 0; i < num.length; i++) {
      num[i] = (int)(Math.random()*num.length) +1;
      for(int j = 0; j < i; j++) {
        if(num[i] == num[j]) {
          i--;
        }//if end
      }//for end
    }
    return num;
  }

  public void back() {
    while(true) {
      System.out.println("[8. 뒤로가기]\n>>> ");
      String command = sc.nextLine();
      if (command.equals("8")) {
        return;
      } else {
        System.out.println("잘못입력하셨습니다.");
        continue;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    WordMemorising wd = new WordMemorising();
    wd.printWords();
  }
}
