package com.sql.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class AdminMenu {

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

  SqlDb sdb = new SqlDb();

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public static void main(String[] args) {
    try {
      AdminMenu ad = new AdminMenu();

      ad.dbConnect();
      ad.adminWork();

    }catch (Exception e) {System.out.println("Error:" + e);}
  }


  public void adminWork() {
    admin : while(true) {
      try {
        System.out.println("관리자로 로그인 하셨습니다. 수행하실 작업을 선택해 주십시오.");
        System.out.print("[1. 회원 관리]   [2. 코멘트 확인]   [3. 단어 관리]   "
            + "[9. 관리자 계정 로그아웃]\n >>> ");

        int command = Integer.parseInt(sc.nextLine());
        switch(command) {
          case 1:
            System.out.println("[회원 관리]\n");
            System.out.println("수행하실 작업을 선택해 주십시오.");

            try {
              loop2 : while(true) {
                System.out.println("[1. 회원 리스트]   [2. 회원 삭제]   [8. 뒤로가기]");
                int command2 = Integer.parseInt(sc.nextLine());
                switch (command2) {
                  case 1: listMember(); break;
                  case 2: deleteMember(); break;
                  case 8: break loop2;
                  default: System.out.println("번호를 잘못 입력하셨습니다."); continue;
                }
                break;
              }
            }catch (Exception e) {System.out.println("error1: "+e);}
            break;
          case 2:
            System.out.println("[코멘트 확인]\n");
            cmtMember();
            break;
          case 3:
            System.out.println("[단어 관리]\n");
            manageWord();
            break;
          case 9: System.out.println("로그아웃"); break admin;
          default:
            System.out.println("잘못된 번호를 입력하셨습니다.");
        }
      }catch (Exception e) {System.out.println("error2: "+e);}
    }

  }// adminWork() End

  //////////////////////////////////////////////////////////////////////////////////////////////////

  //[1. 회원관리] in adminWork()////////////////////////////////////
  //1. 회원 리스트//////////////////////////////////////////////////
  public void listMember() throws Exception {
    System.out.println("[회원 리스트]\n");
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
    System.out.print("[8. 뒤로가기]\n >>> ");
    int command = Integer.parseInt(sc.nextLine());
    if(command == 8) return;
  }//listMember end

  //1. 회원 삭제//////////////////////////////////////////////////
  public void deleteMember() throws Exception {
    System.out.println("[회원 삭제]\n");
    while(true) {
      System.out.print("삭제하실 아이디를 입력하여 주십시오.\n >>> ");
      String deleteID = sc.next();
      if (deleteID.equals("Admin")) {
        System.out.println("관리자는 삭제하실 수 없습니다.");
        return;
      } else if (sdb.delete(deleteID)) {
        System.out.println("회원 삭제가 완료되었습니다.");
      }
      return;
    }
  }//deleteMember end

  //[2. 코멘트 확인]////////////////////////////////////////////////
  public void cmtMember() throws Exception {
    System.out.println("[코멘트 확인]");
    sql = "select comment from member";
    RS = ST.executeQuery(sql);
    System.out.println("");
    while(RS.next() == true) {
      ucmt = RS.getString("comment");
      System.out.println(ucmt);
    }
  }

  //[3. 단어 관리]//////////////////////////////////////////////////
  public void manageWord() {
    System.out.println("[단어 관리]");
    //단어관리 수정/추가 기능
  }

}
