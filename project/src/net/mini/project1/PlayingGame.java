package net.mini.project1;

import java.util.Scanner;

public class PlayingGame {
  Lanking lk = new Lanking();

  Scanner sc = new Scanner(System.in);

  public void game() throws Exception {

    System.out.println("҉ ٩(๑>ω<๑)۶҉     단어 맞추기 게임 월드에 오신것을 환영합니다.  ꉂ (๑¯ਊ¯)σ \n");

    while(true) {

      System.out.print("[1. 단어 암기] \n\n[2. 단어 테스트]\n\n[3. 랭킹표]\n\n[4. 내 정보]\n\n"
          + "[5. comment to 관리자]\n\n[8. 로그 아웃]\n\n[9. 게임 종료]");

      System.out.print("\n\n>>> ");
      String command = sc.nextLine();

      switch (command) {
        case "1": // 단어 암기 (깜박이)
          System.out.println("준비중");
          break;
        case "2": // 단어 테스트 게임
          System.out.println("준비중");
          break;
        case "3": lk.lanking(); back(); break;
        case "4": // 내 정보
          System.out.println("준비중");
          break;
        case "5": // comment to 관리자
          System.out.println("준비중");
          break;
        case "8": // 로그아웃
          System.out.println("로그아웃 합니다.");
          return;
        case "9": // 종료
          System.out.println("게임을 종료합니다.");
          System.exit(0);
        default :
          System.out.println("옳바른 번호를 입력해 주세요.  !!!( •̀ ᴗ •́ )و!!!");
      }
    }
  }

  public void back() {
    System.out.println("[8. 뒤로가기]");
    String command = sc.nextLine();
    if (command.equals("8")) {
      return;
    }
  }
}


