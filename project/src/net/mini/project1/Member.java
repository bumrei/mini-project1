package net.mini.project1;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
 
class Member {
 private String id;
 private String psw;  
 private String email;  
 private String score;
 
 public Member() {
 
}

public Member(String id, String psw, String email, String score) {
  super();
  this.id = id;
  this.psw = psw;
  this.email = email;
  this.score = score;
}

public String getId() {
  return id;
}  


public void setId(String id) {
  this.id = id;
}

public String getPsw() {
  return psw;
}

public void setPsw(String psw) {
  this.psw = psw;
}

public String getEmail() {
  return email;
}

public void setEmail(String email) {
  this.email = email;
}

public String getScore() {
  return score;
}
public void setScore(String score) {
  this.score = score;
}
 
 
public void Login() throws Exception {
  Scanner sc = new Scanner(System.in);  
  SqlDb sqlDb= new SqlDb();
  sqlDb.dbConnect();
  System.out.print("ID >> ");
  id = sc.nextLine();
  System.out.print("Password >> ");
  psw = sc.nextLine();
  if (sqlDb.select(this.id , this.psw)) {
    System.out.println("로그인에 성공하였습니다.");
  } else {System.out.println("아이디 혹은 비밀번호가 일치하지 않습니다.");}
  
 
}


 

}