package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class AdminMenu2 {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
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

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End


  public void adminWork() {
    admin : while(true) {
      try {
        dbConnect();
        System.out.println("\n\n관리자로 로그인 하셨습니다. \n수행하실 작업을 선택해 주십시오.");
        System.out.print("\n[1. 회원 관리]   [2. 코멘트 확인]   [3. 단어 관리]   "
            + "[9. 관리자 계정 로그아웃]\n >>> ");

        int command = Integer.parseInt(sc.nextLine());
        switch(command) {
          case 1:
            try {
              System.out.println("\n[회원 관리]");
              loop2 : while(true) {
                System.out.println("\n수행하실 작업을 선택해 주십시오.");
                System.out.print("[1. 회원 리스트]   [2. 회원 삭제]   [8. 뒤로가기]\n >>> ");
                int command2 = Integer.parseInt(sc.nextLine());
                switch (command2) {
                  case 1: listMember(); continue;
                  case 2: deleteMember(); continue;
                  case 8: break loop2;
                  default: System.out.println("번호를 잘못 입력하셨습니다."); continue;
                }
              }
            }catch (Exception e) {System.out.println("error1: "+e);}
            break;
          case 2:
            cmtMember();
            break;
          case 3:
            manageWord();
            break;
          case 9: System.out.println("\n로그아웃"); break admin;
          default:
            System.out.println("번호를 잘못 입력하셨습니다."); continue;
        }
      }catch (Exception e) {System.out.println("error2: "+e);}
    }

  }// adminWork() End

  public void listMember() throws Exception {
    System.out.println("\n[회원 리스트]");
    sql = "select MEMNO,NAME,ID,EMAIL,CDATE,SCORE from member";
    RS = ST.executeQuery(sql);

    System.out.println("[No.]\t[Name]\t[ID]\t[Email]\t\t\t[Created Date]\t[Score]");

    while(RS.next() == true) {
      uMemno = RS.getInt("MEMNO");
      uName = RS.getString("NAME");
      uID = RS.getString("ID");
      uEmail = RS.getString("EMAIL");
      uCdate = RS.getDate("CDATE");
      uScore = RS.getInt("SCORE");
      System.out.println(uMemno + "\t" + 
          uName  + "\t" + 
          uID    + "\t" + 
          uEmail + "\t" + 
          uCdate + "\t" + 
          uScore);
    }
  }//listMember end

  //1. 회원 삭제//////////////////////////////////////////////////
  public void deleteMember() throws Exception {
    System.out.println("\n[회원 삭제]");
    while(true) {
      System.out.print("\n삭제하실 아이디를 입력하여 주십시오.\n >>> ");
      String deleteID = sc.next();
      if (deleteID.equals("Admin")) {
        System.out.println("관리자는 삭제하실 수 없습니다.");
        return;
      } else if (delete(deleteID)) {
        System.out.println("\n회원 삭제가 완료되었습니다.");
      } else {
        System.out.println("\n아이디를 다시 확인하십시오.");
      }
      return;
    }
  }//deleteMember end


  //테스트용 임시삽입 delete(SqlDb)
  public boolean delete(String id) {
    try {
      sql = "select * from member where ID = '" + id + "'";
      RS = ST.executeQuery(sql);
      while(RS.next() == true) {
        delId = RS.getString("ID");
      }
      String p = sc.nextLine();
      if (id.equals(delId)) {
        sql = "delete from member where ID = '" + id + "'";
        System.out.print("\n삭제하려는 아이디가 '"+ id +"' 맞습니까? (y/N)>>> ");
        p = sc.nextLine();
        if (p.equals("y")) {
          ST.executeUpdate(sql);
          return true ;
        } else {
          return false;
        }
      } else { return false ; }
    } catch(Exception e) { System.out.println("error delete:" + e); return false; }
  }//Delete 


  //[2. 코멘트 확인]////////////////////////////////////////////////
  public void cmtMember() throws Exception {
    System.out.println("\n[코멘트 확인]");
    sql = "select comment from member";
    RS = ST.executeQuery(sql);
    System.out.println("\nComment");
    if(RS.next() == true) {
      ucmt = RS.getString("comment");
      System.out.println(ucmt);
    }
  }



  //[3. 단어 관리]//////////////////////////////////////////////////
  public void manageWord() {
    System.out.println("\n[단어 관리]");
    //단어관리 수정/추가 기능
    word: while(true) {
      System.out.println();
      System.out.print("[1. 단어 추가]   [2. 단어 삭제]   [3. 단어 목록]   [8. 뒤로가기]   \n >>> ");
      int menu = Integer.parseInt(sc.nextLine());
      switch(menu) {
        case 1: addWord(); break;
        case 2: delWord(); break;
        case 3: printWord(); break;
        case 8: break word;
        default: System.out.println("메뉴 번호 확인"); continue;
      }
    }
    System.out.println("\n단어관리 종료\n");
  }

  public void addWord() {
    try {
      System.out.println("\n[단어 추가]");
      System.out.print("\n  level>>> ");
      int wlevel = Integer.parseInt(sc.nextLine());
      System.out.print("  영단어>>> ");
      String weng = sc.nextLine();
      System.out.print("  단어 뜻>>> ");
      String wkor = sc.nextLine();

      sql = "insert into word(wordNum, wordLevel, eng, kor) values( word_seq.nextval, " + wlevel + ", '" + weng + "', '" + wkor + "')";
      int check = ST.executeUpdate(sql);
      if(check > 0) {
        System.out.println("단어 추가 완료");
        System.out.print("\n  단어 목록 출력? (y/N)>>> ");
        String print = sc.nextLine();
        if (print.equals("y")) {
          printWord();
        } else {
          return;
        }
      } 
    } catch(Exception e) {System.out.println("error addword:" + e);}
  }

  public void delWord() {
    try {
      System.out.println("\n[단어 삭제]");
      System.out.print("\n  삭제할 단어 입력>>> ");
      String weng = sc.nextLine();
      sql = "delete from word where eng = '" + weng + "'";
      ST.executeUpdate(sql);
      System.out.println("\n단어 삭제 완료");
      System.out.print("\n  단어 목록 출력? (y/N)>>> ");
      String print = sc.nextLine();
      if (print.equals("y")) {
        printWord();
      } else {
        return;
      }
    } catch(Exception e) {System.out.println("error delword:" + e);}
  }

  public void printWord() {
    try {
      System.out.println("\n[단어 출력]");
      sql = "select wordLevel, eng, kor from word order by wordLevel";
      RS = ST.executeQuery(sql);
      System.out.println("\n영단어\t단어뜻");
      while(RS.next() == true) {
        String peng = RS.getString("eng");
        String pkor = RS.getString("kor");
        System.out.println(peng + "\t" + pkor);
      }
    } catch(Exception e) {System.out.println("error printWord:" + e);}
  }


}
