package com.test3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AccountInfo {

  Connection CN ;
  Statement ST ;
  ResultSet RS ;
  PreparedStatement PST ;
  Scanner sc = new Scanner(System.in);
  String msg , ans, cEmail , sPsw , upsw, fpsw;
  String uID , uPsw ,uName , uEmail, userPsw, ucom ;
  Date udate, ldate, ndate;
  String userID;
  JoinMember jm = new JoinMember();

  public AccountInfo() { }

  public AccountInfo(String userID) {
    this.userID = userID;
  }

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void info() throws Exception {
    dbConnect();
    long calDateDays = 0;
    System.out.println("\n\n               내정보              ");
    System.out.println("─────────────────────────────────────── ");
    msg = "select * from member, answerRate where member.ID = answerRate.ID and member.ID = '" + userID + "'";

    RS = ST.executeQuery(msg);
    if (RS.next() == true) {
      String userName = RS.getString("NAME");
      userID = RS.getString("ID");
      String userEmail = RS.getString("EMAIL");
      int userScore = RS.getInt("SCORE");
      int userPoint = RS.getInt("point");
      ucom = RS.getString("com");
      udate = RS.getDate("cdate");
      ldate = RS.getDate("ldate");
      int questionTotalCnt = RS.getInt("questionTotalCnt");
      int answerTotalCnt = RS.getInt("answerTotalCnt");
      int questionCnt1 = RS.getInt("questionCnt1");
      int questionCnt2 = RS.getInt("questionCnt2");
      int questionCnt3 = RS.getInt("questionCnt3");
      int answerCnt1 = RS.getInt("answerCnt1");
      int answerCnt2 = RS.getInt("answerCnt2");
      int answerCnt3 = RS.getInt("answerCnt3");
      int answerTotalRate = RS.getInt("answerTotalRate");
      int answerRate1 = RS.getInt("answerRate1");
      int answerRate2 = RS.getInt("answerRate2");
      int answerRate3 = RS.getInt("answerRate3");
      int exp = RS.getInt("exp");
      String memLevel = RS.getString("memLevel");

      System.out.println("  Name   :\t"+ userName );
      System.out.println("  I  D   :\t"+ userID );
      System.out.println("  Email  :\t"+ userEmail );
      System.out.println("  SCORE  :\t"+ userScore );
      System.out.println("  POINT  :\t"+ userPoint );
      System.out.println("  LEVEL  :\t"+ memLevel );
      System.out.println("  E X P  :\t"+ exp );
      System.out.println("가입일자 :\t"+ udate );
      System.out.println("마지막로그인날짜 :\t"+ ldate );
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 소문자mm으로 할 경우 분을 의미한다.
      String onlyDate = format.format(System.currentTimeMillis());  // 현재시간 
      Date currentDate = format.parse(onlyDate);
      // Date로 변환된 두 날짜를 계산한 후, 리턴값으로 long type 변수를 초기화
      long calDate = currentDate.getTime() - udate.getTime(); 
      calDateDays = calDate / ( 24*60*60*1000); 
      System.out.println("\t\t저희와 함깨한지 "+ calDateDays + " 일째 입니다" );

      System.out.println("\n\n               정답률              ");
      System.out.println("─────────────────────────────────────── ");
      System.out.printf("  TOTAL  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n",questionTotalCnt,answerTotalCnt,answerTotalRate);
      System.out.printf("  Lv. 1  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n",questionCnt1,answerCnt1,answerRate1);
      System.out.printf("  Lv. 2  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n",questionCnt2,answerCnt2,answerRate2);
      System.out.printf("  Lv. 3  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n\n",questionCnt3,answerCnt3,answerRate3);

      if(RS.getString("comnt") != null ) {
        String usercomnt = RS.getString("comnt");
        System.out.print(" Comment :\t\n" );
        printcomnt(usercomnt);
        System.out.print(" 답  변  :\t");
        if(RS.getString("com") != null ) {
          String com = RS.getString("com");
          printcomnt(com);
        } else { System.out.println("\t현재 등록된 답변이 없습니다.");}
      }

      Loop: while(true) {
        System.out.println("\n\n[1. 이메일 변경]   [2. 비밀번호 변경]   [3. 건의사항]"
            + "   [4. 회원탈퇴]   [8. 뒤로가기]");
        System.out.print(" >>> ");
        String command = sc.nextLine();
        switch (command) {
          case "1": chgEmail();  break;
          case "2": chgPasswd();  break;
          case "3": comnt();  break;
          case "4": delId();  System.out.println("\n\n안녕히가세요!"); System.exit(0);
          case "8": System.out.println("\n뒤로가기\n");  break Loop;
          default : System.out.println("\n번호를 잘못입력하셨습니다 다시 입력해주세요. "); break;
        }  
      }
    }//if
  }

  public void printcomnt(String usercomnt) {
    if (usercomnt.length()>25) {
      System.out.println(usercomnt.substring(0,25));
      if(usercomnt.length()>50) {
        System.out.println("\t\t"+usercomnt.substring(25,50));
        if(usercomnt.length()>75) {
          System.out.println("\t\t"+usercomnt.substring(50,75));
          System.out.println("\t\t"+usercomnt.substring(75,usercomnt.length()));
        } else {System.out.println("\t\t"+ usercomnt.substring(50,usercomnt.length()));}
      } else {System.out.println("\t\t"+ usercomnt.substring(25,usercomnt.length()));}

    } else {System.out.println(usercomnt.substring(0,usercomnt.length())); 
    } //25이하      
  }

  public void chgEmail() {
    try {
      Loop: while(true) {
        System.out.println("\n  [이메일 변경]");
        System.out.print("\n변경할 이메일을 입력하세요.\n >>> ");
        cEmail = sc.nextLine();
        if(jm.stringCheck(cEmail)) {continue;}
        if(!cEmail.contains("@")) {
          System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
          continue;
        }
        msg = "update member set email = '"+cEmail+"' where id = '"+userID+"'";
        ST.executeUpdate(msg);
        System.out.println("\n변경된 이메일: " + cEmail);
        System.out.println("\n변경이 완료되었습니다.");
        break Loop;
      }
    } catch(Exception e) {System.out.println("error: " + e);}

  }

  public void chgPasswd() {
    try {
      Loop: while(true) {
        System.out.println("\n  [비밀번호 변경]");
        System.out.print("\n현재 비밀번호를 입력해주세요\n >>> ");
        ans = sc.nextLine();
        msg = "select * from member where psw = '"+ans+"' and id = '"+userID+"'";
        RS = ST.executeQuery(msg);
        if (!RS.next() == true) {
          System.out.println("입력한 비밀번호를 다시 확인하세요.");
          continue;
        }
        System.out.print("\n변경할 비밀번호를 입력하세요.\n >>> ");
        String sPsw = sc.nextLine();

        if(jm.stringCheck(sPsw)) {continue;}
        while(true) {
          System.out.print("\n비밀번호 재확인\n >>> ");
          String tmp = sc.nextLine();
          if(sPsw.equals(tmp)) {
            System.out.println("\n비밀번호가 일치합니다.");
            msg = "update member set psw = '"+sPsw+"' where id = '"+userID+"'";
            ST.executeUpdate(msg);
            System.out.println("\n변경된 비밀번호: " + sPsw);
            System.out.println("\n변경이 완료되었습니다.");
            break Loop;
          }else {
            System.out.println("비밀번호 불일치. 다시 입력하세요.");
            continue;
          }//if end
        }
      }
    } catch(Exception e) {System.out.println("error: " + e);}
  }

  public void comnt() {
    try {
      String  comnt = "";
      System.out.println("\n  [건의사항]");
      System.out.print("\n건의사항을 입력해주세요.\n >>> ");
      comnt = sc.nextLine();
      while(comnt.length()>100) {
        System.out.println("\n100자이내로 작성해주세요");
        System.out.print("\n건의사항을 입력해주세요.\n >>> ");
        comnt = sc.nextLine();
        continue; } 
      msg = "update member SET comnt = ? where id = ? ";
      PST = CN.prepareStatement(msg);
      PST.setString(1,  comnt );
      PST.setString(2, userID);
      PST.executeUpdate();
      System.out.println("\n건의사항을 관리자에게 전송했습니다.");
    } catch(Exception e) {System.out.println("error: "+e);}
  }

  public void delId() throws Exception {
    System.out.println("\n[회원탈퇴]");
    System.out.println("회원정보를 입력하세요.");
    while(true) {
      System.out.print("패스워드  : ");
      fpsw = sc.nextLine();
      msg = "select * from member where ID = '" + userID + "'";

      RS = ST.executeQuery(msg);
      if (RS.next() == true) {
        upsw = RS.getString("psw");      
        if (fpsw.equals(upsw)) {
          msg = "delete from member where ID = '" + userID + "'";
          System.out.print("정말 탈퇴 하시겠습니까? (y/N)>>> ");
          String a = sc.nextLine();
          if (a.equals("y")) {
            ST.executeUpdate(msg);
            System.out.println("회원 탈퇴가 완료되었습니다.");
            System.out.println();
            break;
          } else {System.out.println("회원 탈퇴를 취소하셨습니다.");}
        }  else {
          System.out.println("비밀번호를를 다시 확인하십시오.");
          continue;
        }//else 
      } 
    }  
    MainMenu mm = new MainMenu();
    mm.mainmenu();
  }//delId

}// Class END
