package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

///////////Admin/////////////
public class AdminMenu {

  Connection CN ;
  Statement ST;
  ResultSet RS;
  String msg , uName , uID,  uPsw , uEmail , userID , ucmt , delId , userPsw ;
  Scanner sc = new Scanner(System.in);
  int uScore, uadmin, userAdmin, uMemno ;
  Date uCdate;
  Dao d = new Dao(userID);

  public AdminMenu() { }

  public AdminMenu(String userID) {
    this.userID = userID;
  }

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  public void adminLogin() throws Exception {
    this.dbConnect();
    System.out.print("  Passwords >>> ");
    String adminPsw = sc.nextLine();
    System.out.print("\n  Pin number >>> ");
    String adminPin = sc.nextLine();

    if (adminPsw.equals("12345") && adminPin.equals("1234")) {
      System.out.println("\n\n관리자로 로그인하셨습니다.");
      adminWork();
    } else {
      System.out.println("\n관리자 비밀번호 혹은 핀번호를 잘못 입력하셨습니다.");
      return;
    }
  }

  public void adminWork() {
    admin : while(true) {
      try {
        System.out.println("\n수행하실 작업을 선택해 주십시오.");
        System.out.print("\n[1. 회원 관리]   [2. 건의사항 확인]   [3. 단어 관리]   [4. 공지 관리]   "
            + "[9. 관리자 계정 로그아웃]\n >>> ");
        String menu = sc.nextLine();
        switch(menu) {
          case "1":
            try {
              System.out.println("\n  [회원 관리]");
              loop2 : while(true) {
                System.out.println("\n수행하실 작업을 선택해 주십시오.");
                System.out.print("[1. 회원 리스트]   [2. 회원 삭제]   [9. 뒤로가기]\n >>> ");
                String menu2 = sc.nextLine();
                switch (menu2) {
                  case "1": listMember(); continue;
                  case "2": deleteMember(); continue;
                  case "9": break loop2;
                  default: System.out.println("\n번호를 다시 확인해주세요."); continue;
                }
              }
            }catch (Exception e) {System.out.println("error: "+e);}
            break;
          case "2": cmtMember(); break;
          case "3": manageWord(); break;
          case "4": notice(); break;
          case "9": System.out.println("\n로그아웃합니다.\n안녕히가세요!"); break admin;
          default: System.out.println("\n번호를 다시 확인해주세요."); continue;
        }
      }catch (Exception e) {System.out.println("error: "+e);}
    }

  }// adminWork() End

  public void listMember() throws Exception {
    System.out.println("\n  [회원 리스트]");
    System.out.println("[No.]\t[Name]\t\t[ID]\t\t[Email]\t\t\t[Created Date]\t[Score]");
    d.select(userID);
  }//listMember end

  //1. 회원 삭제//////////////////////////////////////////////////
  public void deleteMember() throws Exception {

    System.out.println("\n  [회원 삭제]");
    while(true) {
      System.out.print("\n삭제하실 아이디를 입력하여 주십시오.\n >>> ");

      String  deleteID = sc.nextLine();
      Dao d = new Dao(deleteID);
      d.select(deleteID);
      if (deleteID.equals("Admin")) {
        System.out.println("관리자는 삭제하실 수 없습니다.");
        continue;
      } else if (d.getuId() == null) {
        System.out.println("\n아이디를 다시 확인하십시오.") ;
        continue;
      }    else {
        d.dbConnect();
        msg = "delete from member where ID = '" + deleteID + "'";
        System.out.print("\n삭제하려는 아이디가 '"+ deleteID +"' 맞습니까? (y/N) >>> ");
        String a = sc.nextLine();
        if (a.equals("y")) {
          d.ST.executeUpdate(msg);
          System.out.println("\n회원 삭제가 완료되었습니다.");
          return;
        }  else {System.out.println("\n회원 삭제를 취소했습니다.");break;
        } 
      }//else
    } 
  }//deleteMember end



  //[2. 코멘트 확인]////////////////////////////////////////////////
  public void cmtMember() throws Exception {
    System.out.println("\n  [건의사항 확인]");
    msg = " select id, comnt, com from member where comnt is not null";
    RS = ST.executeQuery(msg);
    System.out.println("\n  ID    \t\t \t Comment");
    while (RS.next()==true) {
      ucmt = RS.getString("comnt");
      uID = RS.getString("ID");
      String acom = RS.getString("com");
      System.out.println("──────────────────────────────────────────────────────────────────────");
      System.out.printf("  %-5s \t",uID);
      printcomnt(ucmt);
      if (acom != null) { System.out.println("──────────────────────────────────────────────────────────────────────");
      System.out.print(" >>> Admin답    ");  printcomnt(acom);
      }
    } 

    loop : while(true) {
      System.out.println("\n수행하실 작업을 선택해 주십시오.");
      System.out.print("[1. 건의사항 답변 ]   [2. 건의사항 삭제]   [9. 뒤로가기]\n >>> ");
      String menu = sc.nextLine();
      switch (menu) {
        case "1": com(); continue;
        case "2": delcomnt(); continue;
        case "9": break loop;
        default: System.out.println("\n번호를 다시 확인해주세요."); continue;
      }
    }
  }

  public void delcomnt() throws Exception {
    System.out.println("\n  [건의사항 삭제]");
    while(true) {
      System.out.print("\n삭제하실 건의사항 ID를 입력하여 주십시오.\n >>> ");
      String comntID  = sc.nextLine();
      msg = "select comnt from member where ID = '"+comntID+"'";
      RS = ST.executeQuery(msg);
      if (RS.next() == true) {
        String dcomnt = RS.getString("comnt");      
        if (dcomnt != null) {
          dbConnect();   
          msg = "update member set comnt = null where id = '" + comntID + "'";
          ST.executeUpdate(msg);
          System.out.println("건의사항삭제가 완료되었습니다.");
          System.out.println(); return;
        }  else {
          System.out.println("ID를 다시 확인해주세요");
        }//else 
      } 
    }      
  }//del end

  public void com() throws Exception{
    try {
      String  com , tid ;
      while(true) { 
        System.out.println("\n  [건의사항 답변]");
        System.out.println("\n답변할 ID를 입력해주세요");
        System.out.print(" >>> ");
        tid = sc.nextLine();
        d.select(tid);
        if(d.getUcomnt()!=null && d.getuId().equals(tid)) {
          System.out.print("\n답변을 입력해주세요.\n >>> ");
          com = sc.nextLine();
          while(com.length()>100) {
            System.out.println("\n100자이내로 작성해주세요");
            System.out.print("\n답변을 입력해주세요.\n >>> ");
            com = sc.nextLine();
            continue; } 
          msg = "update member set com = '"+ com +"' where id = '"+tid+"'";
          ST.executeUpdate(msg);
          System.out.println("\n답변을 " + tid +" 에게 전송했습니다."); return; }
        else {
          System.out.println("\nID를 다시 확인해주세요.");
        }
      } 
    } catch(Exception e) {System.out.println("error: "+e);}
  }



  public void printcomnt(String ucmt) {
    int len = ucmt.length();

    if (len>50) {
      System.out.printf(" %-50s \t\n",ucmt.substring(0,50));
      System.out.printf("\t\t %-5s \t\n",ucmt.substring(50,len)); }
    else {
      System.out.printf(" %-50s \t\n",ucmt.substring(0,len));
    }
  }

  //[3. 단어 관리]//////////////////////////////////////////////////
  public void manageWord() {
    System.out.println("\n  [단어 관리]");
    //단어관리 수정/추가 기능
    word: while(true) {
      System.out.println();
      System.out.print("[1. 단어 추가]   [2. 단어 삭제]   [3. 단어 목록]   [4. 단어 검색]   [9. 뒤로가기]\n >>> ");
      String menu = sc.nextLine();
      switch(menu) {
        case "1": addWord(); break;
        case "2": delWord(); break;
        case "3": printWord(); break;
        case "4": searchWord(); break;
        case "9": break word;
        default: System.out.println("\n번호를 다시 확인해주세요."); continue;
      }
    }
    System.out.println("\n단어관리 종료\n");
  }

  public void addWord() {
    JoinMember jm = new JoinMember();
    try {
      System.out.println("\n  [단어 추가]");
      int wlevel = 0;
      String weng = "추가할 영단어";
      String wkor = "추가할 단어뜻";
      while(true) {
        System.out.print("\n  level >>> ");
        try{wlevel = Integer.parseInt(sc.nextLine());
        if(wlevel<1 || wlevel>3) {
          System.out.println("1~3 사이의 숫자를 입력해주세요.\n");
          continue;
        }
        }catch(Exception ex) { System.out.println("\n숫자를 입력해주세요."); continue;}
        break;
      }//while end

      eng:
        while(true) {
          while(true) {
            System.out.print("  영단어 >>> ");
            weng = sc.nextLine();
            if(jm.stringCheck(weng) || weng.matches(".*[0-9ㄱ-ㅎㅏ-ㅣ가-힣]+.*"  )) {
              System.out.println("\n영어로 입력해주세요.");
              continue;
            }
            break;
          }
          msg = "select * from word where wordLevel = " + wlevel;
          RS = ST.executeQuery(msg);
          while(RS.next() == true) {
            String eng = RS.getString("eng");
            if(weng.equals(eng)) {
              System.out.println("\n이미 등록된 단어입니다.");
              continue eng;
            }//if end
          }//while end
          break;
        }//while end

      while(true) {
        System.out.print("  단어 뜻 >>> ");
        wkor = sc.nextLine();
        if(jm.stringCheck(wkor) || wkor.matches("^[a-zA-Z0-9]*$")) {
          System.out.println("\n한글로 입력해주세요.");
          continue;
        }
        break;
      }

      msg = "insert into word(wordNum, wordLevel, eng, kor) values( word_seq.nextval, " + wlevel + ", '" + weng + "', '" + wkor + "')";
      int check = ST.executeUpdate(msg);
      if(check > 0) {
        System.out.println("\n단어 추가가 완료되었습니다.");
        System.out.print("\n  단어 목록 출력? (y/N) >>> ");
        String print = sc.nextLine();
        if (print.equals("y")) {
          printWord();
        } else {
          return;
        }
      } 
    } catch(Exception e) {System.out.println("error:" + e);}
  }

  public void delWord() {
    String eng = "삭제할 단어";
    try {
      System.out.println("\n  [단어 삭제]");
      System.out.print("\n  삭제할 단어 입력 (영어) >>> ");
      String weng = sc.nextLine();

      msg = "select * from word";
      RS = ST.executeQuery(msg);
      while(RS.next() == true) {
        eng = RS.getString("eng");
        if(weng.equals(eng)) {
          msg = "delete from word where eng = '" + weng + "'";
          ST.executeUpdate(msg);
          System.out.println("\n단어 삭제 완료");
          System.out.print("\n  단어 목록 출력? (y/N) >>> ");
          String print = sc.nextLine();
          if (print.equals("y")) {
            printWord();
          } else {
            return;
          }//if end
        }//if end
      }//while end
      if(!weng.equals(eng)) {
        System.out.println("\n삭제할 수 없는 단어입니다.");
      }//if end
    } catch(Exception e) {System.out.println("error:" + e);}
  }

  public void printWord() {
    try {
      System.out.println("\n  [단어 출력]");
      msg = "select wordLevel, eng, kor from word order by wordLevel";
      RS = ST.executeQuery(msg);
      System.out.println("\n영단어\t\t\t단어뜻");
      while(RS.next() == true) {
        String peng = RS.getString("eng");
        String pkor = RS.getString("kor");
        System.out.printf("  %-15s \tㅣ   %-15s \t ",peng, pkor);
        System.out.println("\n──────────────────────────────────────────────────");
      }
    } catch(Exception e) {System.out.println("error:" + e);}
  }

  public void searchWord() {
    String type = "영어,한글";
    int level = 0;
    String eng ="영단어", kor = "뜻", sword = "검색단어";
    try {
      while(true) {
        System.out.println("\n검색할 단어를 입력해주세요.");
        System.out.print(" >>> ");
        sword = sc.nextLine();
        if(sword.matches("^[a-zA-Z]*$")) {type = "eng";
        }else if(sword.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {type = "kor";
        }else {System.out.println("\n영어나 한글로 입력해주세요.");continue;}
        break;
      }
      msg = "select * from word where " + type + " like '%" + sword + "%'";
      RS = ST.executeQuery(msg);

      while(RS.next() == true) {
        level = RS.getInt("wordLevel");
        eng = RS.getString("eng");
        kor = RS.getString("kor");
        if (!eng.equals("영단어")) {
          System.out.println("\n레벨\t영어\t\t뜻");
          System.out.println("---------------------------------");
        }
        System.out.printf("%d\t%s\t\t%s\n\n", level, eng, kor);
      }//while end
      if(eng.equals("영단어")) {
        System.out.println("\n목록에 없는 단어입니다.");
      }
    }catch(Exception ex) { }
  }//searchWord end

  public void notice() {
    try {
      Loop: while(true) {
        System.out.println("\n  [공지 관리]");
        System.out.print("[1. 공지 등록]   [2. 공지 목록]   [3. 공지 삭제]   [4. 공지 상세조회]   [9. 뒤로가기]\n >>> ");
        String menu = sc.nextLine();
        switch(menu) {
          case "1":
            System.out.println("\n  [공지 등록]");      
            System.out.print("  공지 제목 >>> ");
            String nTitle = sc.nextLine();
            System.out.print("  공지 내용 >>> ");
            String nContent = sc.nextLine();
            while(nContent.length()>100) {
              System.out.println("\n100자이내로 작성해주세요");
              System.out.print("\n 공지 내용 >>> ");
              nContent = sc.nextLine();
              continue; } 
            String msg = "insert into notice(code, title, content) values(notice_seq.nextval, '"+nTitle+"', '"+nContent+"')";
            ST.executeUpdate(msg);
            System.out.println("\n\n제목: " + nTitle);
            System.out.println("내용: " + nContent);
            System.out.println("\n공지 등록이 완료되었습니다.");
            break;
          case "2":
            msg = "select * from notice order by code asc";
            RS = ST.executeQuery(msg);
            System.out.println("\nNo.\t  Date  \t Title");
            System.out.println("------------------------------------------------------------");
            while(RS.next() == true) {
              int pcode = RS.getInt("code");
              Date pdate = RS.getDate("cdate");
              String ptitle = RS.getString("title");
              System.out.println(pcode + "\t  " + pdate + "  \t " + ptitle);
            }
            break;
          case "3": delNotice(); break;
          case "4": detailsNotice(); break;
          case "9":  break Loop;
          default: System.out.println("\n번호를 다시 확인해주세요."); break;
        }
      }
    } catch(Exception e) {System.out.println("error:" + e);}
  }

  public void delNotice() {
    String msg;
    int delNum = 0;
    while(true) {
      System.out.println("\n삭제할 게시물 번호를 입력해주세요.");
      try {delNum = Integer.parseInt(sc.nextLine()); 
      msg = "select * from notice where code = " + delNum;
      RS = ST.executeQuery(msg);
      if (RS.next() == true) {
        int pcode = RS.getInt("code");
        String ptitle = RS.getString("title");
        String pcontent = RS.getString("content");
        Date pdate = RS.getDate("cdate");
        System.out.println("\nNo.      :\t" + pcode);
        System.out.println("Date     :\t" + pdate);
        System.out.println("Title    :\t " + ptitle);
        System.out.print("Content  :\t" );
        AccountInfo ai = new AccountInfo();
        ai.printcomnt(pcontent);
      }else {
        System.out.println("\n공지번호를 다시 확인하십시오.");
        break;
      }//if end

      System.out.print("\n정말 삭제하시겠습니까? (y/N) >>> ");
      String a = sc.nextLine();
      if (a.equals("y")) {
        msg = "delete from Notice where code = " + delNum;
        ST.executeUpdate(msg);
        System.out.println("\n공지 삭제가 완료되었습니다.");
        System.out.println();
        break;
      } else {System.out.println("\n공지 삭제를 취소하셨습니다.");}
      }catch(Exception ex) {System.out.println("\n숫자를 입력해주세요.");continue;}
      break;
    }//while end
  }

  public void detailsNotice(){
    int pcode = 0;
    int num = 0;//사용자입력값
    String ptitle = "제목", pcontent = "내용";
    Date pdate;
    while(true) {
      try {
        dbConnect();
        System.out.println("\n상세조회할 공지 번호를 입력해주세요.");
        System.out.print(" >>> ");
        num = Integer.parseInt(sc.nextLine());
        msg = "select * from notice where code = " + num;
        RS = ST.executeQuery(msg);
        if (RS.next() == true) {
          pcode = RS.getInt("code");
          ptitle = RS.getString("title");
          pcontent = RS.getString("content");
          pdate = RS.getDate("cdate");
          System.out.println("\nNo.      :\t" + pcode);
          System.out.println("Date     :\t" + pdate);
          System.out.println("Title    :\t " + ptitle);
          System.out.print("Content  :\t" );
          AccountInfo ai = new AccountInfo();
          ai.printcomnt(pcontent);
        }else {
          System.out.println("\n공지번호를 다시 확인해주세요.");
          break;
        }//if end
      }catch(Exception ex) {System.out.println("\n번호로 입력하셔야합니다."); }
      break;
    }//while end
  }//noticeDetail end
}

