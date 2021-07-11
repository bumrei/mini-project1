package net.mini.project1;

public class EnglishWordApplication {
  public static void main(String[] args) {
    WordTest wordTest = new WordTest("ID2", 2);
    wordTest.dbConnect();
    wordTest.wordTest();
  }//main end
}//Class END
