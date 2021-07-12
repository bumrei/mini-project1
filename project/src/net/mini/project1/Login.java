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

<<<<<<< HEAD
  public void executeLogin() {
    JoinMember jm = new JoinMember();

    while (true) {
      try {

        System.out.print("\n[1. 로그인]   [2. 회원가입]   [9. 종료]\n >>> ");
        int command = Integer.parseInt(sc.nextLine());

        switch(command) {
          case 1:
            LoginFrame();
=======
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
>>>>>>> 6e41e02286a134fd0918b9f8494dbfcbd5d537c0
            break;
          case 2:
<<<<<<< HEAD
            jm.join();
=======
            lg.adminLogin();
            lg.adminInfoFromDB();
            lg.adminWork();
>>>>>>> 6e41e02286a134fd0918b9f8494dbfcbd5d537c0
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

<<<<<<< HEAD
=======
  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConnect End

>>>>>>> 6e41e02286a134fd0918b9f8494dbfcbd5d537c0
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

<<<<<<< HEAD
////////////////////////////////////////////////////////////////////////////////////////////////////

// class for 관리자 로그인 ///////////////////////////////////////////////////////////////////////
=======
  public String matchIdWithDBForDelete(String ID) throws Exception {
    msg = "select ID,ADMIN from member where ID = '" + ID + "'";
    RS = ST.executeQuery(msg);
    String delID = null;

>>>>>>> 6e41e02286a134fd0918b9f8494dbfcbd5d537c0

