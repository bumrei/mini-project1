package net.mini.project1;

import java.sql.*;
import java.util.Scanner;

public class SqlDb {

  Connection CN = null; //DB서버연결정보 서버ip주소 계정id,pwd
  Statement ST = null; //ST=CN, createStatement() 명령어생성 삭제, 신규등록, 조회하라
  ResultSet RS = null; //select조회결과값 전체데이터를 기억
  PreparedStatement pst = null;
  String sql = "select * from word";
 Scanner sc = new Scanner(System.in);
  

  public void dbConnect() {
    try {
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
      Class.forName("oracle.jdbc.driver.OracleDriver"); //오라클드라이브로드
      CN = DriverManager.getConnection( url, "system", "1234");
      System.out.println("오라클 드라이브 및 서버연결성공");
      ST = CN.createStatement();
      RS = ST.executeQuery(sql);
     }catch(Exception ex) { System.out.println("에러이유 " + ex);}
  }//dbconnect end

   
  public Boolean select(String id, String psw) throws Exception {
    String uid = null, upsw = null ;
    sql = "select * from member where ID = ? ";
   // RS = ST.executeQuery(sql);
    pst = CN.prepareStatement(sql);
    pst.setString( 1 , id );
    RS = pst.executeQuery();
    if (RS.next()) { 
      uid = RS.getString("ID");
      upsw = RS.getString("PSW");
    } 
    if (id.equals(uid) && psw.equals(upsw)) {
     return true ;
    } else { return false ;}
    }// select End 
  
  public int select(String id) throws Exception {
    String uid = null ;
    int uscore = 0 ;
    sql = "select * from member order by score";
    pst = CN.prepareStatement(sql);
    pst.setString( 1 , id );
    RS = pst.executeQuery();
        
    
    while (RS.next() == true) {
      uid = RS.getString("ID");
      uscore = RS.getInt("score");
     }
     return uscore ;
  }
    
      
  public String selectWord(String type, int number, int level, int question[]) {
    String word = "단어" ;
    try {
      sql = "select * from "
          + "(select row_number() over(partition by wordlevel order by wordlevel) as row_num, b.* "
          + "from word b) a "
          + "where wordlevel = " + level + " and a.row_num = " + question[number];
      RS = ST.executeQuery(sql);
      if( RS.next() == true) {
        word = RS.getString(type);
      }//if end
    }catch(Exception ex) { }
    return word; 
  }//getWord end

 
 
  public boolean delete(String id) throws Exception {
    sql = "delect from member where ID = ? ";
    pst = CN.prepareStatement(sql);
    pst.setString( 1 , id );
    String delId = null;
    if (RS.next()) {
      delId = RS.getString("ID");
      String uadmin = RS.getString("ADMIN");}
    if (id.equals(delId) ) {
      System.out.println("정말 삭제하시겠습니까? (y/N)");
      String command = sc.nextLine();
         if (command.equals("y")) {
           pst.executeUpdate();
            return true ;
         } else {
           System.out.println("삭제를 취소합니다.\n");
           return false ;
           }
     } else { return false ; }
  }//Delete 
  
  

  public void insert(String name, String id, String psw, String email, String bdate) {
    sql = "INSERT INTO member(memNo, name, ID, psw, email, cdate) "
        + "VALUES(member_seq.nextval,'"+ name +"','"+ id +"','"+ psw +"','"+ email +"'," + bdate + "',sysdate)";
    try {
      ST.executeUpdate(sql);
    }catch(Exception ex) { }
  }
  


 
}
