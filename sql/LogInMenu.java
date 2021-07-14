package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

//Class Login, Join, Find

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
  static String userID = null;
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
      System.out.println("\n  [로그인]");
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
    System.out.println("\n  [회원가입]");

    setName();
    setID();
    setPSW();
    setEmail();

    insertMember();
    System.out.println("\n회원가입이 완료되었습니다.");
  }//join end

  public void setName() {
    while(true) {
      System.out.print("\n  이름 >>> ");
      name = sc.nextLine();
      if(stringCheck(name)) {continue;}
      break;
    }//while end
  }//setName end

  //아이디 입력
  public void setID() {
    loop : while(true) {
      while(true) {
        System.out.print("\n  아이디 >>> ");
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
            System.out.println("\n이미 있는 ID입니다.");
            continue loop;
          }//if end
        }//while end
        System.out.println("\n아이디로 사용가능합니다.");
        break;
      }catch(Exception ex) {System.out.println("에러" + ex);}
    }//while end
  }//setID end

  //비밀번호입력
  public void setPSW() {
    while(true) {
      while(true) {
        System.out.print("\n  비밀번호 >>> ");
        psw = sc.nextLine();
        if(stringCheck(psw)) {continue;}
        break;
      }//while end
      System.out.print("\n  비밀번호 재확인 >>>");
      String tmp = sc.nextLine();
      if(psw.equals(tmp)) {
        System.out.println("\n비밀번호가 일치합니다.");
        break;
      }else {
        System.out.println("\n비밀번호가 일치하지 않습니다.");
        continue;
      }//if end
    }//while end
  }//setPSW end

  public void setEmail() {
    while(true) {
      while(true) {
        System.out.print("\n  email >>>  ");
        email = sc.nextLine();
        if(stringCheck(email)) {continue;}
        break;
      }//while end
      if(!email.contains("@")) {
        System.out.println("\n양식이 잘못되었습니다. 다시 입력해주세요.");
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
      System.out.println("\n양식이 잘못되었습니다. 다시 입력해주세요.");
    }
    return check;
  }//stringCheck end
}//Class END



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