package net.mini.project1;

public class EnglishWordApplication {
  public static void main(String[] args) {
    WordTest wordTest = new WordTest();
    wordTest.dbconnect();
    wordTest.wordTest();
  }//main end
}//Class END
