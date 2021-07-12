package net.mini.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Login {

  Connection CN = null;
  Statement ST = null;
  ResultSet RS = null;
  String msg = null;
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

  public static void main(String[] args) {
    Login lg = new Login();
    while (true) {
      try {
        int controlThread = 800;
        lg.dbConnect();
        System.out.println("                    ◤----------------◥ ");
        System.out.println("================= * | 단어 암기 게임 | * ===================");
        System.out.print("                    ◣----------------◢ ");
        Thread.sleep(controlThread);
        System.out.println("\n       *            *              *         *         *");
        Thread.sleep(controlThread);
        System.out.println("\n*         apple           *              *          Haha");
        Thread.sleep(controlThread);
        System.out.println("\n      Sweet           *        자동차             *");
        Thread.sleep(controlThread);
        System.out.println("\n    *        양초               *         Fly          *");
        Thread.sleep(controlThread);
        System.out.println("\n*           *         Amazing        *                장난감 ");
        Thread.sleep(controlThread);
        System.out.println("\n        Car             *                 Happy      *");
        Thread.sleep(controlThread);
        System.out.print("\n[1. 일반회원 로그인]   [2. 관리자 로그인]   [9. 종료]\n >>> ");
        int command = Integer.parseInt(lg.sc.nextLine());

        switch(command) {
          case 1:
            lg.loginFrame();
            lg.selectFromDB();
            lg.goIntoTheGame();
            break;

          case 2:
            lg.adminLogin();
            lg.adminInfoFromDB();
            lg.adminWork();
            break;

          case 9:
            System.out.println("게임을 종료합니다.");
            System.exit(0);
          default:
            System.out.println("번호를 잘못 입력하셨습니다.");

        }
      }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
    }
  }//main END

  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

  // class for 일반회원 로그인//////////////////////////////////////////////////////////////////////

  public void loginFrame() {
    System.out.print("ID >> ");
    userID = sc.nextLine();

    System.out.print("Password >> ");
    userPsw = sc.nextLine();
  }// loginFrame End

  public void selectFromDB() throws Exception {
    msg = "select ID, PSW from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    if (RS.next()==true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
    }
  }// selectFromDB End

  public void goIntoTheGame() throws Exception {
    if (userID.equals(uID) && userPsw.equals(uPsw)) {
      System.out.println("로그인에 성공하였습니다.");
      System.out.println("게임 로딩중...");
      Thread.sleep(2000);
      // WordTest.java 삽입.
      System.out.println("그다음 게임이 시작됩니다. 여기에 게임 실행 메서드가 들어갈겁니다.");
      System.out.println("모두 화이팅");
      // 여기에 게임실행 메서드가 들어갈 것이다.
    } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}
  }// matchWithDB End

  ////////////////////////////////////////////////////////////////////////////////////////////////////

  // class for 관리자 로그인 ///////////////////////////////////////////////////////////////////////

  public void adminLogin() {
    while(true) {
      try {
        System.out.print("ID >> ");
        userID = sc.nextLine();

        System.out.print("\nPassword >> ");
        userPsw = sc.nextLine();

        System.out.print("\nAdmin Pin >> ");
        userAdmin = Integer.parseInt(sc.nextLine());

        break;
      }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
    }
  }// adminLogin End

  public void adminInfoFromDB() throws Exception {
    msg = "select ID, PSW, ADMIN from member where ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    if (RS.next()==true) {
      uID = RS.getString("ID");
      uPsw = RS.getString("PSW");
      uadmin = RS.getInt("ADMIN");
    }
  }// adminInfoFromDB End

  public void adminWork() throws Exception {
    if (uadmin == 0) {
      System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");
      return;
    } else if (userID.equals(uID) && userPsw.equals(uPsw) && userAdmin == uadmin) {
      loop : while(true) {
        try {
          System.out.println("관리자로 로그인 하셨습니다. 수행하실 작업을 선택해 주십시오.");
          System.out.print("[1. 회원 관리]   [2. 게임 설정 관리]"
              + "   [3. 단어장 관리]   [9. 관리자 계정 로그아웃]\n >>> ");

          int command = Integer.parseInt(sc.nextLine());

          switch(command) {
            case 1:
              System.out.println("회원 관리입니다.\n");
              System.out.println("수행하실 작업을 선택해 주십시오.");
              loop2 : while(true) {
                try {
                  System.out.println("[1. 회원 리스트]   [2. 회원 추방]   [8. 뒤로가기]");
                  int command2 = Integer.parseInt(sc.nextLine());

                  switch (command2) {
                    case 1:
                      listMember();
                      break;
                    case 2:
                      deleteMember();
                      break;
                    case 8:
                      break loop2;
                    default:
                      System.out.println("번호를 잘못 입력하셨습니다.");
                      continue;
                  }
                  break;
                }catch (Exception e) {System.out.println("숫자만 입력 가능합니다.");}
              }
              break;

            case 2:
              System.out.println("게임 설정 관리 입니다.\n");
              System.out.println("Apologise, the system is not ready.\n");
              break;
            case 3:
              System.out.println("단어장 관리 입니다.\n");
              System.out.println("Apologise, the system is not ready.\n");

              // 단어 추가

              // 단어 삭제

              // 점수 조작(?)

              break;
            case 9:
              break loop;
            default:
              System.out.println("잘못된 번호를 입력하셨습니다.");
          }
        }catch (Exception e) {System.out.println("숫자만 입력해 주세요.");}
      }
    } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}

  }// matchAdminInfoWithDB() End

  //////////////////////////////////////////////////////////////////////////////////////////////////

  // 클래스 3 for [1. 회원관리] in matchAdminInfoWithDB() 메서드////////////////////////////////////
  public void listMember() throws Exception {
    System.out.println("현재 회원 목록입니다.");
    msg = "select MEMNO,NAME,ID,EMAIL,CDATE,SCORE from member";
    RS = ST.executeQuery(msg);
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
    if (command == 8) return;
  }

  public void deleteMember() throws Exception {
    System.out.println("회원 삭제 입니다.");
    while(true) {
      System.out.print("삭제하실 아이디를 입력하여 주십시오.\n >>> ");
      String deleteID = sc.nextLine();
      if (deleteID.equals(matchIdWithDBForDelete(deleteID))) {
        if (uadmin != 0) {
          System.out.println("관리자는 삭제하실 수 없습니다.");
          return;
        }
        msg = "delete from member where ID= '" + deleteID + "'";
        System.out.println("정말 삭제하시겠습니까? (y/N)");
        String command = sc.nextLine();
        if (command.equals("y")) {
          ST.executeUpdate(msg); 
        } else {
          System.out.println("삭제를 취소합니다.\n");
          return;
        }
      } else {
        System.out.println("입력하신 아이디는 존재하지 않습니다.\n");
        continue;
      }
      System.out.println("회원 삭제가 완료되었습니다.");
      return;
    }
  }

  public String matchIdWithDBForDelete(String ID) throws Exception {
    msg = "select ID,ADMIN from member where ID = '" + ID + "'";
    RS = ST.executeQuery(msg);
    String delID = null;


}//Class End
