package net.mini.project1;

import java.util.Scanner;

public class EnglishWordApplication {
  public static void main(String[] args) {
    WordTest wordTest = new WordTest("ID2", 2);
    JoinMember joinmember = new JoinMember();
    Scanner sc = new Scanner(System.in);

    System.out.println("1.회원가입 2.단어시험");
    System.out.print("메뉴>> ");
    try{
      String menu = sc.nextLine();

      switch(menu) {
        case "1":
          joinmember.dbConnect();
          joinmember.join();
          break;
        case "2":
          wordTest.dbConnect();
          wordTest.wordTest();
          break;
      }//switch end
    }catch(Exception ex) { }
  }//main end
}//Class END
