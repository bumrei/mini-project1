package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

//Class Login, Join, Find

///////////LogIn/////////////
public class LogInMenu {
  Connection CN ;
  Statement ST ;
  ResultSet RS;
  String msg;
  Scanner sc = new Scanner(System.in);
  String uID , uPsw ,uName , uEmail, userPsw ;
  String userID ;
  String fname , femail ,fID , fpsw , rpsw, ucom;
  Date udate, ldate, ndate;
  int uscord;

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void login() throws Exception {
    this.dbConnect();
    while (true) {
      System.out.println("\n  [로그인]");   
      System.out.print("\n  ID >>> ");
      userID = sc.nextLine();

      AdminMenu am = new AdminMenu(userID);
      GameMenu gm = new GameMenu(userID);

      if (userID.equals("Admin")) {
        am.adminLogin();
        return;
      }
      System.out.print("  Password >>> ");
      userPsw = sc.nextLine();
      matchLoginFromDB();

      if (userID.equals(uID) && userPsw.equals(uPsw)) {
        System.out.println("\n" +uName+"님 환영합니다.");
        if (ucom != null) {
          System.out.println("\n건의사항 답변이 도착했습니다.");
        }
        if (ldate()>0) {
          System.out.println("\n새로운 공지사항이 있습니다.");
        } 
        gm.goIntoTheGame();
        return;
      } else {
        System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.\n");
        return;
      }
    }
  }

  public long ldate() throws Exception {
    dbConnect();

    // 날짜 차이를 담을 변수 생성
    long calDateDays = 0;
    msg = "select cdate from notice order by cdate desc";
    RS = ST.executeQuery(msg);
    if (RS.next() == true) {
      ndate = RS.getDate("cdate");}  // 가입일자
    long calDate = ndate.getTime() - ldate.getTime(); 
    //udate 공지 날짜에 넣어줘야함
    calDateDays = calDate / ( 24*60*60*1000);
    return calDateDays;
  }

  public void matchLoginFromDB() throws Exception {
    msg = "select * from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      uName = RS.getString("name");
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
      uscord = RS.getInt("score");
      ucom = RS.getString("com");
      udate = RS.getDate("cdate");
      ldate = RS.getDate("ldate");
    }
  }
}

///////////Join/////////////
class JoinMember {
  Connection CN; 
  Statement ST ; 
  PreparedStatement PST ;
  ResultSet RS ; 
  String msg ;
  Scanner sc = new Scanner(System.in);
  String id, psw, name, email;

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
      System.out.print("\n  닉네임 (2-10자,공백X) \n >>>");
      name = sc.nextLine();
      if(stringCheck(name, 2, 10)) {
        System.out.println("\n양식이 잘못되었습니다. 다시 입력해주세요.");
        continue;
      }
      break;
    }//while end
  }//setName end

  //아이디 입력
  public void setID() {
    loop : while(true) {
      while(true) {
        System.out.print("\n  아이디 (2-10자,공백X,한글X) \n >>>");
        id = sc.nextLine();
        if(stringCheck(id, 2, 10) || id.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
          System.out.println("\n양식이 잘못되었습니다. 다시 입력해주세요.");
          continue;
        }
        break;
      }//while end
      try {
        msg = "select * from member";
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
      }catch(Exception ex) { }
    }//while end
  }//setID end

  //비밀번호입력
  public void setPSW() {
    while(true) {
      while(true) {
        System.out.print("\n  비밀번호 (8-15자,공백X,한글X) \n >>>");
        psw = sc.nextLine();
        if(stringCheck(psw, 8, 15) || psw.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
          System.out.println("\n양식이 잘못되었습니다. 다시 입력해주세요.");
          continue;
        }
        break;
      }//while end
      System.out.print("\n  비밀번호 재확인 \n>>");
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
      System.out.print("\n  email (12-25자,공백X,한글X) \n >>>");
      email = sc.nextLine();
      if(stringCheck(email, 12, 25) || emailCheck() || email.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
        System.out.println("\n정확히 입력하셨는지 다시 확인해주세요.");
        continue;
      }
      break;
    }//while end
  }//setEmail end

  //문자체크 null,빈문자,공백
  public boolean stringCheck(String string) {
    boolean check = string == null || string.isEmpty() || string.indexOf(" ") != -1;
    if(check) {
      System.out.println("\n양식이 잘못되었습니다. 다시 입력해주세요.");
    }
    return check;
  }//stringCheck end

  //문자체크 null,빈문자,공백,최대길이초과,최소길이미만
  public boolean stringCheck(String string, int minLength, int maxLength) {
    boolean check = string == null || string.isEmpty()
        || string.indexOf(" ") != -1 || string.length()<minLength
        || string.length()>maxLength;
        return check;
  }//stringCheck end

  //이메일에크 @있는지,@뒤에 '.'이 있는지 @앞에 1글자이상 있는지, @뒤에 5글자이상 있는지
  public boolean emailCheck() {
    boolean check = true;
    if(email.contains("@")) {
      int index = email.indexOf("@");
      String front = email.substring(0, index);
      int frontLen = front.length();
      String last = email.substring(index+1);
      int lastLen = last.length();

      check = !last.contains(".") || frontLen < 1 || lastLen<5;
    }
    return check;
  }//emailCheck end

  public void insertMember() throws SQLException {
    msg = "INSERT INTO member(memNo, name, ID, psw, email, cdate) "
        + "VALUES(member_seq.nextval, ?, ?, ?, ?,sysdate)";
    PST = CN.prepareStatement(msg);
    PST.setString(1, name);
    PST.setString(2, id);
    PST.setString(3, psw);
    PST.setString(4, email);
    PST.executeUpdate();

    msg = "INSERT INTO answerRate(ID) VALUES(?)";
    PST = CN.prepareStatement(msg);
    PST.setString(1, id);
    PST.executeUpdate();

  }//insertMember end
}//JoinMember Class END

///////////Find/////////////
class FindMember {

  Connection CN;
  Statement ST;
  ResultSet RS;
  String msg;
  Scanner sc = new Scanner(System.in);
  String uID, uPsw, uName, uEmail, userID, userPsw; 
  String fname, femail, fID, fpsw, rpsw;

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
      System.out.print("\n[1. 아이디 찾기]   [2. 비밀번호 찾기]   [9. 뒤로가기]\n >>> ");

      String menu = sc.nextLine();
      switch(menu) {
        case "1": findId(); break;
        case "2": findPsw(); break;
        case "9": System.out.println("\n뒤로가기"); break Loop;
        default: System.out.println("메뉴 번호 확인"); break;
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
    } catch(Exception e) { }
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
    } catch(Exception e) { }
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
    } catch(Exception e) { }
  }
}