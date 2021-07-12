package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JoinMember {
  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  String msg = "isud = crud쿼리문기술";
  Scanner sc = new Scanner(System.in);
  String id;
  String psw;
  String name;
  String email;

  public void dbConnect() {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver"); //오라클드라이브로드
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
      CN = DriverManager.getConnection( url, "system", "1234");
      System.out.println("오라클 드라이브및 서버연결성공");

      ST = CN.createStatement();
    }catch(Exception ex) { System.out.println("에러이유 " + ex);}
  }//dbconnect end

  public void join() {
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
      System.out.println("아이디 중복확인 중 입니다.");
      try {
        Thread.sleep(1000);
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
        System.out.print("email>> (ex:abc@abc.com)");
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
