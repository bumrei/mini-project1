package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

//Class Login-Find, Join, Admin

///////////LogIn/////////////
public class LogInMenu {
  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
  Scanner sc = new Scanner(System.in);

  String uID = null;
  String uPsw = null;
  String uName = null;
  String uEmail = null;
  String userID = null;
  String userPsw = null;

  String fname = "찾기용이름";
  String femail = "찾기용메일";
  String fID = "찾기용아이디";
  String fpsw = "찾기용비번";
  String rpsw = "바꾸기용비번";

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void login() throws Exception {
    AdminMenu am = new AdminMenu();
    GameMenu gm = new GameMenu();
    this.dbConnect();
    while (true) {
      System.out.print("\n  ID>>> ");
      userID = sc.nextLine();
      if (userID.equals("Admin")) {
        am.adminLogin();
        return;
      }
      System.out.print("  Password>>> ");
      userPsw = sc.nextLine();
      matchLoginFromDB();

      if (userID.equals(uID) && userPsw.equals(uPsw)) {
        System.out.println("\n로그인 성공");
        gm.goIntoTheGame();
        return;
      } else {
        System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");
        continue;
      }
    }
  }

  public void matchLoginFromDB() throws Exception {
    msg = "select * from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
    }
  }
}

///////////Join/////////////
class JoinMember {
  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  String msg = "isud = crud쿼리문기술";
  Scanner sc = new Scanner(System.in);
  String id;
  String psw;
  String name;
  String email;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void join() throws Exception {
    this.dbConnect();
    System.out.println("회원가입");

    setName();
    setID();
    setPSW();
    setEmail();

    insertMember();
    System.out.println("회원가입이 완료되었습니다.");
  }//join end

  public void setName() {
    while(true) {
      System.out.print("이름>> ");
      name = sc.nextLine();
      if(stringCheck(name)) {continue;}
      break;
    }//while end
  }//setName end

  //아이디 입력
  public void setID() {
    loop : while(true) {
      while(true) {
        System.out.print("아이디>> ");
        id = sc.nextLine();
        if(stringCheck(id)) {continue;}
        break;
      }//while end
      try {
        msg = "select ID from member";
        RS = ST.executeQuery(msg);
        while(RS.next()==true) {
          String tmpid = RS.getString("ID");
          if(id.equals(tmpid)) {
            System.out.println("이미 있는 ID입니다.");
            continue loop;
          }//if end
        }//while end
        System.out.println("아이디로 사용가능합니다.");
        break;
      }catch(Exception ex) {System.out.println("에러" + ex);}
    }//while end
  }//setID end

  //비밀번호입력
  public void setPSW() {
    while(true) {
      while(true) {
        System.out.print("비밀번호>> ");
        psw = sc.nextLine();
        if(stringCheck(psw)) {continue;}
        break;
      }//while end
      System.out.print("비밀번호 재확인>>");
      String tmp = sc.nextLine();
      if(psw.equals(tmp)) {
        System.out.println("비밀번호가 일치합니다.");
        break;
      }else {
        System.out.println("비밀번호가 일치하지 않습니다.");
        continue;
      }//if end
    }//while end
  }//setPSW end

  public void setEmail() {
    while(true) {
      while(true) {
        System.out.print("email>>  ");
        email = sc.nextLine();
        if(stringCheck(email)) {continue;}
        break;
      }//while end
      if(!email.contains("@")) {
        System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
        continue;
      }//if end
      break;
    }//while end
  }//setEmail end

  public void insertMember() {
    msg = "INSERT INTO member(memNo, name, ID, psw, email, cdate) "
        + "VALUES(member_seq.nextval,'"+ name +"','"+ id +"','"+ psw +"','"+ email +"',sysdate)";
    try {
      ST.executeUpdate(msg);
    }catch(Exception ex) { }
  }//insertMember end

  //문자체크 null,빈문자,공백
  public boolean stringCheck(String string) {
    boolean check = string == null || string.isEmpty() || string.indexOf(" ") != -1;
    if(check) {
      System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
    }
    return check;
  }//stringCheck end
}//Class END

///////////Admin/////////////
class AdminMenu {

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

  public void adminLogin() throws Exception {
    this.dbConnect();
    System.out.print("  Passwords>>> ");
    String adminPsw = sc.nextLine();
    System.out.print("\n  Pin number>>> ");
    String adminPin = sc.nextLine();

    if (adminPsw.equals("12345") && adminPin.equals("1234")) {
      System.out.println("관리자로 로그인하셨습니다.");
      adminWork();
    } else {
      System.out.println("관리자 비밀번호 혹은 핀번호를 잘못 입력하셨습니다.");
      return;
    }
  }

  public void adminWork() {
    admin : while(true) {
      try {
        //        dbConnect();
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
    sql = "select comnt from member";
    RS = ST.executeQuery(sql);
    System.out.println("\nComment");
    if(RS.next() == true) {
      ucmt = RS.getString("comnt");
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

///////////Find/////////////
class FindMember {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
  Scanner sc = new Scanner(System.in);

  String uID = null;
  String uPsw = null;
  String uName = null;
  String uEmail = null;
  String userID = null;
  String userPsw = null;
  String fname = "찾기용이름";
  String femail = "찾기용메일";
  String fID = "찾기용아이디";
  String fpsw = "찾기용비번";
  String rpsw = "바꾸기용비번";

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void matchFindFromDB() throws Exception {
    msg = "select * from member where NAME = '" + fname + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
      uName = RS.getString("NAME");
      uEmail = RS.getString("EMAIL");
    }
  }

  public void findDb() throws Exception {
    this.dbConnect();
    System.out.println("\n[아이디 / 비밀번호 찾기]");
    Loop: while(true) {
      System.out.print("\n[1. 아이디 찾기]   [2. 비밀번호 찾기]   [8. 뒤로가기]\n >>> ");

      int menu = Integer.parseInt(sc.nextLine());
      switch(menu) {
        case 1:
          findId();
          break;
        case 2:
          findPsw();
          break;
        case 8:
          System.out.println("\n뒤로가기");
          break Loop;
        default:
          System.out.println("메뉴 번호 확인");
          break;
      }
    }
  }

  public void findId() {
    try {
      System.out.println("\n[아이디 찾기]");
      System.out.println("\n회원정보를 입력하세요.");

      System.out.print("  이름 >>> ");
      fname = sc.nextLine();
      System.out.print("  Email >>> ");
      femail = sc.nextLine();

      matchFindFromDB();
      if (fname.equals(uName) && femail.equals(uEmail)) {
        msg = "select id from member where name = '"+fname+"' and email = '"+femail+"'";
        RS = ST.executeQuery(msg);
        if (RS.next()==true) {
          fID = RS.getString("ID");
          System.out.println("\n당신의 ID는 '" + fID + "' 입니다.");
        }
        return;
      } else {System.out.println("\n정보없음");}
      System.out.println();
    } catch(Exception e) {System.out.println("error: "+e);}
  }

  public void findPsw() {
    try {
      System.out.println("\n[비밀번호 찾기]");
      System.out.println("\n회원정보를 입력하세요.");

      System.out.print("  이름 >>> ");
      fname = sc.nextLine();
      System.out.print("  ID >>> ");
      fID = sc.nextLine();

      matchFindFromDB();
      if (fname.equals(uName) && fID.equals(uID)) {
        msg = "select psw from member where name = '"+fname+"' and id = '"+fID+"'";
        RS = ST.executeQuery(msg);
        if (RS.next()==true) {
          fpsw = RS.getString("psw");
          System.out.println("\n회원확인이 되었습니다.");
          resetPsw();
        }
        return;
      }  else {System.out.println("\n정보없음");}
      System.out.println();
    } catch(Exception e) {System.out.println("error: "+e);}
  }

  public void resetPsw() {
    try {
      System.out.println("\n비밀번호를 재설정합니다.");
      System.out.print(" 새로운 비밀번호를 입력해주세요\n >>> ");
      rpsw = sc.nextLine();

      msg = "update member set psw = '"+rpsw+"' where id = '"+fID+"'";
      ST.executeUpdate(msg);

      System.out.println("\n\n변경이 완료되었습니다.");
      System.out.println("\n변경된 비밀번호는 " +rpsw+ " 입니다.");
    } catch(Exception e) {System.out.println("error: "+e);}
  }
}