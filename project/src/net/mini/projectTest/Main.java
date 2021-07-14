package net.mini.projectTest;

import java.util.Scanner;

public class Main {


  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);
    Main m = new Main();
    Member mem = new Member();
    JoinMember jm = new JoinMember();
    AdminMenu am = new AdminMenu();


    loop: while (true) {
      System.out.print("[1. 로그인]   [2. 회원가입]   [3. 아이디 / 비밀번호 찾기]   [9. 종료] \n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1" :
          mem.dbConnect();
          mem.login();
          //          if(mem.login() == true) {
          //            am.adminWork();
          //            continue loop;
          //          }
          m.gameMenu();

          break;
        case "2" :
          jm.join();
          break;
        case "3":
          mem.dbConnect();
          mem.findDb();
          break;
        case "9" :
          System.out.println("게임을 종료합니다.");
          System.exit(0);
          break;
        default :
          System.out.println("올바른 번호를 입력해 주십시오.");
      }
    }
  }

  public void gameMenu() throws Exception {

    Scanner sc = new Scanner(System.in);
    Lanking lk = new Lanking();
    PlayingGame pg = new PlayingGame();
    pg.dbConnect();
    System.out.println("҉ ٩(๑>ω<๑)۶҉     단어 맞추기 게임 월드에 오신것을 환영합니다.  ꉂ (๑¯ਊ¯)σ \n");

    while(true) {

      System.out.print("[1. 단어 암기] \n\n[2. 단어 테스트]\n\n[3. 랭킹표]\n\n[4. 내 정보]\n\n"
          + "[5. comment to 관리자]\n\n[8. 로그 아웃]\n\n[9. 게임 종료]");

      System.out.print("\n\n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1": pg.selectLevel(); break;
        case "2": // 단어 테스트 게임
          pg.wordTest();
          break;
        case "3": lk.lanking(); pg.back();
        System.out.println("준비중");
        break;
        case "4": // 내 정보
          pg.info();
          return;
        case "5": // comment to 관리자
          pg.comnt();
          break;
        case "8": // 로그아웃
          System.out.println("로그아웃 합니다.");
          return;
        case "9": // 종료
          System.out.println("게임을 종료합니다.");
          System.exit(0);
        default :
          System.out.println("올바른 번호를 입력해 주세요.  !!!( •̀ ᴗ •́ )و!!!");
      }
    }
  }

}
