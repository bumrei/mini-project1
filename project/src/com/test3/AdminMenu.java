package com.test3;

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
  String sql , uName , uID,  uPsw , uEmail , userID , ucmt , delId , userPsw ;
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
    System.out.print("  Passwords>>> ");
    String adminPsw = sc.nextLine();
    System.out.print("\n  Pin number>>> ");
    String adminPin = sc.nextLine();

    if (adminPsw.equals("12345") && adminPin.equals("1234")) {
      System.out.println("\n\n관리자로 로그인하셨습니다.");
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
        System.out.println("\n수행하실 작업을 선택해 주십시오.");
        System.out.print("\n[1. 회원 관리]   [2. 건의사항 확인]   [3. 단어 관리]   [4. 공지 관리]   "
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
          case 4:
            notice();
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
    System.out.println("[No.]\t[Name]\t[ID]\t[Email]\t\t\t[Created Date]\t[Score]");
     d.select(userID);
  }//listMember end

  //1. 회원 삭제//////////////////////////////////////////////////
  public void deleteMember() throws Exception {
 
    System.out.println("\n[회원 삭제]");
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
            sql = "delete from member where ID = '" + deleteID + "'";
            System.out.print("\n삭제하려는 아이디가 '"+ deleteID +"' 맞습니까? (y/N)>>> ");
            String a = sc.nextLine();
                    if (a.equals("y")) {
                      d.ST.executeUpdate(sql);
                      System.out.println("\n회원 삭제가 완료되었습니다.");
                      return;
                      }  else {System.out.println("회원 삭제를 취소하셨습니다.");break;
                     } 
          }//else
         } 
    }//deleteMember end
 
      
      
      
      
      
      
      
//      String deleteID = sc.next();
//      Dao d = new Dao(deleteID);
//      d.select(deleteID);
//      if (deleteID.equals("Admin")) {
//        System.out.println("관리자는 삭제하실 수 없습니다.");
//        return;
//      } else if (d.getuId() != null) {
//        sql = "delete from member where ID = '" + deleteID + "'";
//        System.out.print("\n삭제하려는 아이디가 '"+ deleteID +"' 맞습니까? (y/N)>>> ");
//         String p = sc.nextLine();
//            if (p.equals("y")) {
//              ST.executeUpdate(sql);
//              System.out.println("\n회원 삭제가 완료되었습니다.");}
//      } else {
//        deleteID ="삭제불가";
//        System.out.println("\n아이디를 다시 확인하십시오.");
//        }
       


  //테스트용 임시삽입 delete(SqlDb)
//  public void delete(String id) {
//    try {
////      sql = "select * from member where ID = '" + id + "'";
////      RS = ST.executeQuery(sql);
////      while(RS.next() == true) {
////        delId = RS.getString("ID");
////      }
////      
//
////      String p = sc.nextLine();
//      Dao d = new Dao(id);
//      if () {
//      
//     }
//    } catch(Exception e) { System.out.println("error delete:" + e);  }
//  }//Delete 


  //[2. 코멘트 확인]////////////////////////////////////////////////
  public void cmtMember() throws Exception {
    System.out.println("\n[건의사항 확인]");
    sql = " select id, comnt from member where comnt is not null";
    RS = ST.executeQuery(sql);
    System.out.println("\n  ID    \t Comment");

    while (RS.next() == true) {
      ucmt = RS.getString("comnt");
      uID = RS.getString("ID");
      System.out.print("  "+uID);  
      printcomnt(ucmt);
    }

    loop : while(true) {
      System.out.println("\n수행하실 작업을 선택해 주십시오.");
      System.out.print("[1. 건의사항 답변 ]   [2. 건의사항 삭제]   [8. 뒤로가기]\n >>> ");
      int command = Integer.parseInt(sc.nextLine());
      switch (command) {
        case 1: com(); break;
        case 2: delcomnt(); continue;
        case 8: break loop;
        default: System.out.println("번호를 잘못 입력하셨습니다."); continue;
      }
    }
  }
  
  public void delcomnt() throws Exception {
    System.out.println("\n[건의사항 삭제]");
    while(true) {
      System.out.print("\n삭제하실 건의사항 ID를 입력하여 주십시오.\n >>> ");
      String comntID  = sc.nextLine();
      sql = "select comnt from member where ID = '"+comntID+"'";
      RS = ST.executeQuery(sql);
      if (RS.next() == true) {
        String dcomnt = RS.getString("comnt");      
        if (dcomnt != null) {
          dbConnect();   
          sql = "update member set comnt = null where id = '" + comntID + "'";
          ST.executeUpdate(sql);
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
        System.out.println("답변할 ID를 입력해주세요");
        tid = sc.nextLine();

          if(d.getUcomnt()!=null && d.getuId().equals(tid)) {
            System.out.print("\n답변을 입력해주세요.\n >>> ");
           dbConnect();    
            com = sc.nextLine();
            while(com.length()>100) {
              System.out.println("\n100자이내로 작성해주세요");
              System.out.print("\n답변을 입력해주세요.\n >>> ");
              com = sc.nextLine();
              continue; } 
            sql = "update member set com = '"+ com +"' where id = '"+tid+"'";
            d.ST.executeUpdate(sql);
            System.out.println("\n답변을" + tid +"에게 전송했습니다."); return; }
          else {
            System.out.println("ID를 다시 확인해주세요");
          }
        

      } 
    } catch(Exception e) {System.out.println("errorcom: "+e);}
  }

  public void printcomnt(String ucmt) {
    int len = ucmt.length();
    if (len>25) {
      System.out.println("\t"+ucmt.substring(0,25));
      if(len>50) {
        System.out.println("\t"+ucmt.substring(25,50));
        if(len>75) {
          System.out.println("\t"+ucmt.substring(50,75));
          System.out.println("\t"+ucmt.substring(75,len));
        } else {System.out.println("\t"+ ucmt.substring(50,len));}
      } else {System.out.println("\t"+ ucmt.substring(25,len));}

    } else {System.out.println("\t"+ucmt.substring(0,len)); 
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

  public void notice() {
    try {
      Loop: while(true) {
        System.out.println("\n[공지 관리]");
        System.out.print("[1. 공지 등록]   [2. 공지 목록]   [8. 뒤로가기]\n >>> ");
        String cmd = sc.nextLine();
        switch(cmd) {
          case "1":
            System.out.println("\n[공지 등록]");      
            System.out.print("  공지 제목 >>> ");
            String nTitle = sc.nextLine();
            System.out.print("  공지 내용 >>> ");
            String nContent = sc.nextLine();
            String msg = "insert into notice(code, title, content) values(notice_seq.nextval, '"+nTitle+"', '"+nContent+"')";
            ST.executeUpdate(msg);
            System.out.println("제목: " + nTitle);
            System.out.println("내용: " + nContent);
            System.out.println("공지 등록 완료");
            break;
          case "2":
            msg = "select code, title, content from notice order by code";
            RS = ST.executeQuery(msg);
            System.out.println("\nNo. \t Title \t\t\t\t\t Content");
            while(RS.next() == true) {
              int pcode = RS.getInt("code");
              String ptitle = RS.getString("title");
              String pcontent = RS.getString("content");
              System.out.println(pcode + "\t" + ptitle + " \t\t\t\t\t " + pcontent);
            }
            break;
          case "8":
            break Loop;
          default:
            System.out.println("\n메뉴 번호 확인");
            break;
        }
      }
    } catch(Exception e) {System.out.println("error notice:" + e);}
  }
}
