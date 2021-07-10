package net.mini.project1;

public class EnglishWordApplication {
  public static void main(String[] args) {
    WordTest wordTest = new WordTest();
    wordTest.dbConnect();
    wordTest.wordTest();
  }//main end
}//Class END
