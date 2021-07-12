package com.sql.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JoinRandom {
  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  String msg = "isud = crud쿼리문기술";
  int Gtotal = 0; // 조회갯수72/ 전체갯수329
  int question = 0;
  String uquestion = "문제 = 정답";
  int uscore = 0;//데이터베이스 점수담을 곳
  Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    JoinRandom t = new JoinRandom();
    t.dbconnect();
    //t.dbjoin();
    //t.memCheck();
    t.wordTest();
  }

  public void dbconnect() {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver"); //오라클드라이브로드
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
      CN = DriverManager.getConnection( url, "system", "1234");
      System.out.println("오라클 드라이브및 서버연결성공");

      ST = CN.createStatement();
    }catch(Exception ex) { System.out.println("에러이유 " + ex);}
  }//dbconnect end

  public void dbjoin() {
    try {
      String msg = "";
      String uname = "";
      String uid = "";
      String upsw = "";
      String uemail = "";
      System.out.println(CN.isClosed() ? "접속실패" : "접속완료");

      System.out.println("[신규등록]");
      //이름, 생년, 아이디, 암호, 이메일
      System.out.print("  이름>>> ");
      uname = sc.nextLine();
      System.out.print("  ID>>> ");
      uid = sc.nextLine();
      System.out.print("  암호>>> ");
      upsw = sc.nextLine();
      System.out.print("  Email>>> ");
      uemail = sc.nextLine();
      System.out.println("\n[입력 정보 확인]");
      System.out.println("  이름: " + uname + "\n  ID: " + uid + "\n  암호: " + upsw + "\n  Email: " + uemail);
      System.out.println();

      //    msg = "insert into member(name, id, psw, email) values('kim', 'khh', 1111, 'haha@gmail.com'";
      msg = "insert into member(name, id, psw, email, cdate) values('" + uname + "', '" + uid + "', " + upsw + ", '" + uemail + "', sysdate)";
      RS = ST.executeQuery(msg);
      if(RS.next() == true) {
        uname = RS.getString("name");
        uid = RS.getString("id");
        upsw = RS.getString("psw");
        uemail = RS.getString("email");
      }//if end
      System.out.println(msg);

    }catch(Exception ex) { System.out.println("에러이유 " + ex);}
  }//join end

  public void memCheck() {
    try {
      System.out.println("[회원 확인]");
      System.out.print("  ID>>> ");
      String cid = sc.nextLine();
      System.out.print("  암호>>> ");
      String cpsw = sc.nextLine();

      msg = "select code from member where id = '"+cid+"', psw = '"+cpsw+"'";

      //회원정보확인
      if(!cid.equals("아이디") | !cpsw.equals("암호")) {
        System.out.println("정보를 다시 확인해주세요");
      } 

      RS = ST.executeQuery(msg);
      if(RS.next() == true) {
        System.out.println("회원 확인 완료");
      }//if end
      System.out.println(msg);

    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
  }//memCheck end


  public void wordTest() { //중복제거필요
    try {
      //문제 총 개수반환
      msg = "select count(*) from word";
      RS = ST.executeQuery(msg);
      int count = 0;
      if( RS.next() == true) {
        count = RS.getInt("count(*)");
      }//if end

      //문제 5번 반복출제
      System.out.println("[랜덤출력]");
      this.output(this.q());
      System.out.println("\n*");

    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
  }//wordTest end

  public int[] q() {
    int[] eng = new int[3];
    for(int i = 0; i < eng.length; i++) {
      eng[i] = (int)(Math.random()*5) +1;
      for(int j = 0; j < i; j++) {
        if (eng[i] == eng[j]) {
          i--;
        }
      }
    }
    return eng;
  }

  public String output(int[] q) {
    try {
      //      select rownum, word.* from word;
      for(int i = 0; i < q.length; i++) {
        msg = "select eng from word where code = " + q[i];
        System.out.println(msg);
        System.out.print("문제>> ");
        RS = ST.executeQuery(msg);
        if( RS.next() == true) {
          uquestion = RS.getString("eng");
          System.out.println(uquestion);
        }//if end

        //문제풀기
        System.out.print("정답입력>> ");
        String uanswer = sc.nextLine();

        //시간제한구현

        msg = "select kor from word where code =" + q[i];
        RS = ST.executeQuery(msg);
        String answer = "정답담을 그릇";
        if( RS.next() == true) {
          answer = RS.getString("kor");
        }//if end

        //정답비교, 점수저장
        if(answer.equals(uanswer)) {

          //데이터베이스 점수 끌어오기
          msg = "select score from ranking where id = '1'";
          RS = ST.executeQuery(msg);
          if(RS.next() == true) {
            uscore = RS.getInt("score");
          }//if end

          uscore++;
          System.out.println("정답입니다");
          System.out.println("현재점수는 " + uscore + "입니다");

          //랭킹에 점수저장
          msg = "update ranking set score = " + uscore + " where id = '1'";
          int ok = ST.executeUpdate(msg);

        }else {
          uscore--;
          System.out.println("틀렸습니다. 정답은 " + answer + "입니다");
          System.out.println("현재점수는 " + uscore + "입니다");

          //랭킹에 점수저장
          msg = "update ranking set score =" + uscore + " where id = '1'";
          int ok = ST.executeUpdate(msg);
        }//if end

        System.out.println();
      }//for END
    }catch(Exception ex) {System.out.println("에러이유: "+ex);}
    return msg;
  }//output END



}//Test Class END
