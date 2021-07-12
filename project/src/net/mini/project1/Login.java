package net.mini.project1;

import java.util.Date;
import java.util.Scanner;


public class Login {


  String msg = null;
  int uMemno;
  String uName = null;
  String uID = null;
  String uPsw = null;
  String uEmail = null;
  Date uCdate = null;
  int uScore;
  int adminPin;
  String userID = null;
  String userPsw = null;
  int userAdmin;
  Scanner sc = new Scanner(System.in);
  SqlDb sqlDb= new SqlDb();

  public void executeLogin() {
    JoinMember jm = new JoinMember();

    while (true) {
      try {

        System.out.print("\n[1. 로그인]   [2. 회원가입]   [9. 종료]\n >>> ");
        int command = Integer.parseInt(sc.nextLine());

        switch(command) {
          case 1:
            LoginFrame();
            break;
          case 2:
            jm.join();
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

  // class for 일반회원 로그인//////////////////////////////////////////////////////////////////////

  public void LoginFrame() throws Exception {
    sqlDb.dbConnect();
    System.out.print("ID >> ");
    userID = sc.nextLine();
    if(userID.equals("Admin")) {
      System.out.println("Password >> ");
      userPsw = sc.nextLine();
      System.out.println("관리자 Pin >> ");
      adminPin = Integer.parseInt(sc.nextLine());


    }

    System.out.print("Password >> ");
    userPsw = sc.nextLine();
    if (sqlDb.select(this.userID , this.userPsw)) {
      System.out.println("로그인에 성공하였습니다.");
      System.out.println("게임 로딩중...");
      Thread.sleep(2000);
      System.out.println("그다음 게임이 시작됩니다.");
    } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}
  }
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// class for 관리자 로그인 ///////////////////////////////////////////////////////////////////////

