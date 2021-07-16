drop table answerRate;
drop table member ;
drop sequence member_seq;

create sequence member_seq ;

create table member2 (
  memNo number(4) not null ,
  name nvarchar2(10) not null ,
  ID varchar(10) primary key ,
  psw varchar(15) not null ,
  email varchar(25) not null ,
  score number(4) default(0) ,
  memLevel varchar(10) default('Bronze'),
  exp number(4) default(0),
  cdate date not null ,
  comnt nvarchar2(100),
  com nvarchar2(100),
  ldate date
);

create table answerRate(
  ID varchar(10) primary key ,
  questionTotalCnt number(4) default(0),
  questionCnt1 number(4) default(0),
  questionCnt2 number(4) default(0),
  questionCnt3 number(4) default(0),
  answerTotalCnt number(4) default(0),
  answerCnt1 number(4) default(0),
  answerCnt2 number(4) default(0),
  answerCnt3 number(4) default(0),
  answerTotalRate number(4,2) default(0),
  answerRate1 number(4,2) default(0),
  answerRate2 number(4,2) default(0),
  answerRate3 number(4,2) default(0),
  CONSTRAINT fk_ID FOREIGN KEY(ID) REFERENCES member(ID) ON DELETE CASCADE
);


set pagesize 1000;
set linesize 1000;

commit ;

desc member

--member2 insert ºÎºÐ/////////////////////////////////////////////////////////////////////////////////////////////////////

insert into member(memNo, name, ID, psw, email, cdate,ldate)
 values(member_seq.nextval, '±èÇÏÇÏ', 'ID1', '12345', 'hahahaha@gmail.com', '2021-07-10', '2021-07-12');

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±èÈ£È£', 'ID2', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate, score)
 values(member_seq.nextval, '±èÈ÷È÷', 'ID3', '12345', 'ggggjjj@gmail.com', sysdate, 10);

insert into member(memNo, name, ID, psw, email, cdate, ldate)
 values(member_seq.nextval, 'a', 'a', 'a', 'ggggjjj@gmail.com', '2021-07-10', '2021-07-12');

--////////////////////////////////////////////////////////////////////////////////////////////////////////////////


--answerRate insert ºÎºÐ//////////////////////////////////////////////////////////////
INSERT INTO answerRate(ID) VALUES('ID1');
INSERT INTO answerRate(ID) VALUES('ID2');
INSERT INTO answerRate(ID) VALUES('ID3');
INSERT INTO answerRate(ID) VALUES('a');
--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

commit ;
select * from member;

  