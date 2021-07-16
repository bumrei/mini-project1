drop table answerRate;
drop table transaction ;
drop table member ;
drop sequence member_seq;
drop sequence message_seq;

create sequence member_seq ;
create sequence message_seq ;

create table member (
  memNo number(4) not null ,
  name nvarchar2(10) not null ,
  ID varchar(10) ,
  psw varchar(15) not null ,
  email varchar(25) not null ,
  cdate date not null ,
  score number(4) default(0) ,
  memLevel varchar(10) default('Bronze'),
  exp number(4) default(0),
  comnt nvarchar2(100),
  com nvarchar2(100),
  ldate date,
  point number(5) default(0),
  mychar number(4) default(1),
  constraint pk_member_ID primary key(ID)
);

create table transaction (
  ID varchar(10) not null,
  irow number(3) ,
  jcolumn number(3) ,
  constraint fk_transaction_ID foreign key(ID)
      references member(ID) on delete cascade
);

create table message (
  code number(4) not null,
  ID varchar(10) not null,
  mess nvarchar2(100) ,
  mdate date ,
  ToID varchar(10) not null ,
  constraint fk_message_ID foreign key(ID)
      references member(ID) on delete cascade
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

commit ;

--member insert 부분/////////////////////////////////////////////////////////////////////////////////////////////////////

insert into member(memNo, name, ID, psw, email, cdate, ldate, point)
 values(member_seq.nextval, '김하하', 'ID1', '12345', 'hahahaha@gmail.com',  '2021-07-10', '2021-07-12', 100);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '김호호', 'ID2', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '김히히', 'ID3', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate, ldate)
 values(member_seq.nextval, 'a', 'a', 'a', 'ggggjjj@gmail.com', '2021-07-10', '2021-07-14');

--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

--transaction insert 부분////////////////////////////////////////////////////////////////////////////////////////


insert into transaction values('ID1', 0,0) ;

insert into transaction values('ID2', 0,0) ;

insert into transaction values('ID3', 0,0) ;

--//////////////////////////////////////////////////////////////////////////////////////////////////////////////

--message insert 부분 ///////////////////////////////////////////////////////////////////////////////////////////
insert into message values(message_seq.nextval, 'ID1', 'hi hahahahahahaha', sysdate, 'ID2');

--//////////////////////////////////////////////////////////////////////////////////////////////////////////////

--answerRate insert 부분/////////////////////////////////////////////////////////////////////////////////////////
INSERT INTO answerRate(ID) VALUES('ID1');
INSERT INTO answerRate(ID) VALUES('ID2');
INSERT INTO answerRate(ID) VALUES('ID3');
INSERT INTO answerRate(ID) VALUES('a');
--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

commit ;
select * from member;

select * from transaction ;

select * from message ;

select * from answerRate;