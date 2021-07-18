package com.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Dao {
  int questionTotalCnt , answerTotalCnt, questionCnt1, questionCnt2,questionCnt3 ;
  int answerCnt1, answerCnt2, answerCnt3, exp  ; 
  double answerRate1, answerRate2, answerRate3, answerTotalRate ;
  Date uDate, lDate, nDate;
  String uId , uPsw ,uName , uEmail, msg, ucomnt, com, memLevel ;
  int uScore, uMemno, point ;
  String userID;
  Connection CN ;
  Statement ST ;
  ResultSet RS ;

 Dao(String userID) {
   this.userID = userID;
  }
  
  public void dbConnect() throws Exception {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    CN = DriverManager.getConnection(url, "system", "1234");
    ST = CN.createStatement(); 
  }// dbConneuMemno = RS.getInt("MEMNO");ct End
  
  public void dbclose() throws Exception {
    RS.close();
    ST.close();
    CN.close();
  }


  public void select(String userID) throws Exception{
    dbConnect();
    
    if(userID.equals("Admin")) {
      msg = "select MEMNO,NAME,ID,EMAIL,CDATE,SCORE from member";
      RS = ST.executeQuery(msg);
      while(RS.next() == true) {
        System.out.println(RS.getInt("MEMNO") + "\t" + 
            RS.getString("NAME")  + "\t" + 
            RS.getString("ID")    + "\t" + 
            RS.getString("EMAIL") + "\t" + 
            RS.getDate("CDATE") + "\t" + 
            RS.getInt("SCORE"));}
      return;
      }
    
    msg = "select * from member, answerRate where member.ID = answerRate.ID and member.ID = '" + userID + "'";
    RS = ST.executeQuery(msg);
    while (RS.next() == true) {
      setuMemno(RS.getInt("MEMNO"));
      setuName( RS.getString("NAME"));
      setuId( RS.getString("ID"));
      setuEmail(RS.getString("EMAIL"));
      setuScore(RS.getInt("SCORE"));
      setUcomnt(RS.getString("comnt"));
      setuDate(RS.getDate("cdate"));
      setlDate( RS.getDate("ldate"));    
      setuPsw(RS.getString("psw"));
      setPoint( RS.getInt("point"));
      setQuestionTotalCnt(RS.getInt("questionTotalCnt"));
      setAnswerTotalCnt(RS.getInt("answerTotalCnt"));
      setQuestionCnt1(RS.getInt("questionCnt1"));
      setQuestionCnt2(RS.getInt("questionCnt2"));
      setQuestionCnt3(RS.getInt("questionCnt3"));
      setAnswerCnt1(RS.getInt("answerCnt1"));
      setAnswerCnt2(RS.getInt("answerCnt2"));
      setAnswerCnt3(RS.getInt("answerCnt3"));
      setAnswerTotalRate(RS.getInt("answerTotalRate"));
      setAnswerRate1(RS.getInt("answerRate1"));
      setAnswerRate2(RS.getInt("answerRate2"));
      setAnswerRate3(RS.getInt("answerRate3"));
      setExp(RS.getInt("exp"));
      setMemLevel(RS.getString("memLevel"));    
    }
    dbclose();
    
  }


  
  

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public int getuMemno() {
    return uMemno;
  }

  public void setuMemno(int uMemno) {
    this.uMemno = uMemno;
  }

  public Date getuDate() {
    return uDate;
  }

  public void setuDate(Date uDate) {
    this.uDate = uDate;
  }

  public Date getlDate() {
    return lDate;
  }

  public void setlDate(Date lDate) {
    this.lDate = lDate;
  }

  public Date getnDate() {
    return nDate;
  }

  public void setnDate(Date nDate) {
    this.nDate = nDate;
  }

  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }

  public String getuPsw() {
    return uPsw;
  }

  public void setuPsw(String uPsw) {
    this.uPsw = uPsw;
  }

  public String getuName() {
    return uName;
  }

  public void setuName(String uName) {
    this.uName = uName;
  }

  public String getuEmail() {
    return uEmail;
  }

  public void setuEmail(String uEmail) {
    this.uEmail = uEmail;
  }

  public String getUcomnt() {
    return ucomnt;
  }

  public void setUcomnt(String ucomnt) {
    this.ucomnt = ucomnt;
  }

  public String getCom() {
    return com;
  }

  public void setCom(String com) {
    this.com = com;
  }

  public int getuScore() {
    return uScore;
  }

  public void setuScore(int uScore) {
    this.uScore = uScore;
  }

  
  public String getMemLevel() {
    return memLevel;
  }

  public void setMemLevel(String memLevel) {
    this.memLevel = memLevel;
  }

  public int getQuestionTotalCnt() {
    return questionTotalCnt;
  }

  public void setQuestionTotalCnt(int questionTotalCnt) {
    this.questionTotalCnt = questionTotalCnt;
  }

  public int getAnswerTotalCnt() {
    return answerTotalCnt;
  }

  public void setAnswerTotalCnt(int answerTotalCnt) {
    this.answerTotalCnt = answerTotalCnt;
  }

  public int getQuestionCnt1() {
    return questionCnt1;
  }

  public void setQuestionCnt1(int questionCnt1) {
    this.questionCnt1 = questionCnt1;
  }

  public int getQuestionCnt2() {
    return questionCnt2;
  }

  public void setQuestionCnt2(int questionCnt2) {
    this.questionCnt2 = questionCnt2;
  }

  public int getQuestionCnt3() {
    return questionCnt3;
  }

  public void setQuestionCnt3(int questionCnt3) {
    this.questionCnt3 = questionCnt3;
  }

  public int getAnswerCnt1() {
    return answerCnt1;
  }

  public void setAnswerCnt1(int answerCnt1) {
    this.answerCnt1 = answerCnt1;
  }

  public int getAnswerCnt2() {
    return answerCnt2;
  }

  public void setAnswerCnt2(int answerCnt2) {
    this.answerCnt2 = answerCnt2;
  }

  public int getAnswerCnt3() {
    return answerCnt3;
  }

  public void setAnswerCnt3(int answerCnt3) {
    this.answerCnt3 = answerCnt3;
  }

  public double getAnswerTotalRate() {
    return answerTotalRate;
  }

  public void setAnswerTotalRate(int answerTotalRate) {
    this.answerTotalRate = answerTotalRate;
  }

  public double getAnswerRate1() {
    return answerRate1;
  }

  public void setAnswerRate1(int answerRate1) {
    this.answerRate1 = answerRate1;
  }

  public double getAnswerRate2() {
    return answerRate2;
  }

  public void setAnswerRate2(int answerRate2) {
    this.answerRate2 = answerRate2;
  }

  public double getAnswerRate3() {
    return answerRate3;
  }

  public void setAnswerRate3(int answerRate3) {
    this.answerRate3 = answerRate3;
  }

  public int getExp() {
    return exp;
  }

  public void setExp(int exp) {
    this.exp = exp;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}// class
  
  