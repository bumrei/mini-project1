package com.test3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import oracle.security.o5logon.d;

public class AccountInfo {

  Statement ST;
  ResultSet RS;
  Scanner sc = new Scanner(System.in);
  String msg , ans, cEmail , sPsw ,  fpsw;
  String userID;
  JoinMember jm = new JoinMember();
  Dao d = new Dao(userID);
  
  public AccountInfo() { }

  public AccountInfo(String userID) {
    this.userID = userID;
  }

  public void info() throws Exception {

    
    System.out.println("\n\n               내정보              ");
    System.out.println("───────────────────────────────────────────────── ");
    d.select(userID);
      System.out.println("\t"+ d.getuName() +"님과 함께한지 "+  datedif(d.getuDate()) + " 일째 입니다" );
      System.out.println("  Name   :\t"+ d.getuName() );
      System.out.println("  I  D   :\t"+ d.getuId() );
      System.out.println("  Email  :\t" + d.getuEmail());
      System.out.println("  SCORE  :\t" + d.getuScore());
      System.out.println("  POINT  :\t" + d.getPoint());
      System.out.println("  LEVEL  :\t" + d.getMemLevel ());
      System.out.println("  E X P  :\t" + d.getExp() );
     
      if( d.getUcomnt() != null ) {
        System.out.print(" Comment :\t" );
        printcomnt(d.getUcomnt());
          System.out.print(" 답  변  :\t");
          if( d.getCom() != null ) {
            printcomnt(d.getCom());
          } else { System.out.println("현재 등록된 답변이 없습니다.");}
      }
      System.out.println("\n\n               정답률              ");
      System.out.println("───────────────────────────────────────────────── ");
      System.out.printf("  TOTAL  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n", d.getQuestionTotalCnt(),d.getAnswerTotalCnt(),d.getAnswerTotalRate());
      System.out.printf("  Lv. 1  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n",d.getQuestionCnt1(),d.getAnswerCnt1(),d.getAnswerRate1());
      System.out.printf("  Lv. 2  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n", d.getQuestionCnt2(),d.getAnswerCnt2(),d.getAnswerRate2());
      System.out.printf("  Lv. 3  : 총 %s문제 중 %s개 정답, 정답률: %s%%\n\n",d.getQuestionCnt3(),d.getAnswerCnt3(),d.getAnswerRate3());
      

      Loop: while(true) {
        System.out.println("\n\n[1. 이메일 변경]   [2. 비밀번호 변경]   [3. 건의사항]"
            + "   [4. 회원탈퇴]   [8. 뒤로가기]");
        System.out.print(" >>> ");
        String command = sc.nextLine();
        switch (command) {
          case "1": chgEmail();  break;
          case "2": chgPasswd();  break;
          case "3": comnt();  break;
          case "4": delId(); break;
          case "8": System.out.println("\n뒤로가기\n");  break Loop;
          default : System.out.println("\n번호를 잘못입력하셨습니다 다시 입력해주세요. "); break;
        }  
      }
    }//if

// 현재 날짜 비교 함수
  public long datedif(Date date) throws ParseException {
    long calDateDays = 0;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 소문자mm으로 할 경우 분을 의미한다.
    String onlyDate = format.format(System.currentTimeMillis());  // 현재시간 
    Date currentDate = format.parse(onlyDate);
    // Date로 변환된 두 날짜를 계산한 후, 리턴값으로 long type 변수를 초기화
    long calDate = currentDate.getTime() - date.getTime(); 
    calDateDays = calDate / ( 24*60*60*1000); 
    return calDateDays+1;
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
      d.dbConnect();
      Loop: while(true) {
        System.out.println("\n  [이메일 변경]");
        System.out.print("\n변경할 이메일을 입력하세요.\n >>> ");
        cEmail = sc.nextLine();
        if(jm.stringCheck(cEmail)&&cEmail.length()<25) {continue;}
        if((!cEmail.contains("@"))) {
          System.out.println("양식이 잘못되었습니다. 다시 입력해주세요.");
          continue;
        }
        msg = "update member set email = '"+cEmail+"' where id = '"+userID+"'";
        d.ST.executeUpdate(msg);
        System.out.println("\n변경된 이메일: " + cEmail);
        System.out.println("\n변경이 완료되었습니다.");
        break Loop;
      }
      } catch(Exception e) {System.out.println("errordd: " + e);}

  }

  public void chgPasswd() {
    try {
      Loop: while(true) {
        System.out.println("\n  [비밀번호 변경]");
        System.out.print("\n현재 비밀번호를 입력해주세요\n >>> ");
        ans = sc.nextLine();
        d.select(userID);
        if (!ans.equals(d.getuPsw())) {
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
            d.dbConnect();
            msg = "update member set psw = '"+sPsw+"' where id = '"+userID+"'";
            d.ST.executeUpdate(msg);
            System.out.println("\n변경된 비밀번호: " + sPsw);
            System.out.println("\n변경이 완료되었습니다.");
            break Loop;
          }else {
            System.out.println("비밀번호 불일치. 다시 입력하세요.");
            continue;
          }//if end
        }
      }
      d.dbclose();
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
        comnt = sc.nextLine(); }
      msg = "update member SET comnt = '"+ comnt +"'  where id = '"+userID+"' ";
      d.dbConnect();
      d.ST.executeUpdate(msg);
      System.out.println("\n건의사항을 관리자에게 전송했습니다.");
    } catch(Exception e) {System.out.println("error: 건의사항 "+e);}
  }

  public void delId() throws Exception {
    System.out.println("\n[회원탈퇴]");
    System.out.println("회원정보를 입력하세요.");
    while(true) {
      System.out.print("패스워드  : " );
       fpsw = sc.nextLine();
       d.select(userID);
        if (fpsw.equals(d.getuPsw())) {
          msg = "delete from member where ID = '" + userID + "'";
          System.out.print("정말 탈퇴 하시겠습니까? (y/N)>>> " );
          String a = sc.nextLine();
                  if (a.equals("y")) {
                    d.dbConnect();
                    d.ST.executeUpdate(msg);
                    System.out.println("회원 탈퇴가 완료되었습니다.");
                     break;
                  } else {System.out.println("회원 탈퇴를 취소하셨습니다.");break;}
        }  System.out.println("비밀번호를를 다시 확인하십시오.");
          continue;
        }//else 

    MainMenu mm = new MainMenu();
    mm.mainmenu();
  }//delId

}// Class END
