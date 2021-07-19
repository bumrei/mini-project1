drop table answerRate;
drop table transaction ;
drop table message;
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
  cdate date default(sysdate),
  score number(4) default(0) ,
  memLevel varchar(10) default('Bronze'),
  exp number(4) default(0),
  comnt nvarchar2(100),
  com nvarchar2(100),
  ldate date default('2020-01-01') ,
  adate date default('2020-01-01') ,
  point number(5) default(1000),
  mychar number(4) default(1),
  comdate date default(sysdate) ,
  constraint pk_member_ID primary key(ID)
);

create table transaction (
  ID varchar(10) not null,
  irow number(3) ,
  jcolumn number(3) ,
  constraint fk_transaction_ID foreign key(ID)
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

insert into member(memNo, name, ID, psw, email, cdate, ldate, score)
 values(member_seq.nextval, '벌레', 'bumrei', '12345', 'hahahaha@gmail.com',  '2021-07-10', '2021-07-12', 50);

insert into member(memNo, name, ID, psw, email, cdate, score)
 values(member_seq.nextval, '짱아치', 'jeonga', '12345', 'ggggjjj@gmail.com', sysdate, 40);

insert into member(memNo, name, ID, psw, email, cdate, score)
 values(member_seq.nextval, '배', 'bsj308', '12345', 'ggggjjj@gmail.com', sysdate, 33);

insert into member(memNo, name, ID, psw, email, cdate, ldate, score)
 values(member_seq.nextval, '무햐호', 'w3whhhh', '12345', 'ggggjjj@gmail.com', '2021-07-10', '2021-07-14', 35);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '전지현', 'a', '12345', 'asdasdf@adffa.com', 10);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '송혜교', 'b', '12345', 'fjksla@gjdklj.com', 14);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '김태희', 'c', '12345', 'fdjlfak@jjfkld.com', 20);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '송중기', 'd', '12345', 'dkjlkdg@djk.com', 22);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '장기용', 'e', '12345', 'jdksdlagh@dd.com', 10);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '유재석', 'f', '12345', 'jdklf@fkldg.com', 8);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '비', 'g', '12345', 'djfkal@dkl.net', 39);

insert into member(memNo, name, ID, psw, email, score)
 values(member_seq.nextval, '조조', 'h', '12345', 'jfkdld@jkg.com', 44);

insert into member(memNo, name, ID, psw, email)
 values(member_seq.nextval, '김영림', 'rladudfla', '12345', 'jfkl@dkjgl.com');

insert into member(memNo, name, ID, psw, email)
 values(member_seq.nextval, '엄진영', 'djawlsdud', '12345', 'jfdksgak@jkgdas.com');

insert into member(memNo, name, ID, psw, email)
 values(member_seq.nextval, '황주희', 'ghkdwngml', '12345', 'jfdflkda@dd.com');



--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

--transaction insert 부분////////////////////////////////////////////////////////////////////////////////////////


insert into transaction values('bumrei', 0,0) ;

insert into transaction values('jeonga', 0,0) ;

insert into transaction values('bsj308', 0,0) ;

insert into transaction values('w3whhhh', 0,0) ;

--//////////////////////////////////////////////////////////////////////////////////////////////////////////////



--answerRate insert 부분/////////////////////////////////////////////////////////////////////////////////////////
INSERT INTO answerRate(ID) VALUES('bumrei');
INSERT INTO answerRate(ID) VALUES('jeonga');
INSERT INTO answerRate(ID) VALUES('bsj308');
INSERT INTO answerRate(ID) VALUES('w3whhhh');
--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

commit ;
select * from member;

select * from transaction ;

select * from answerRate;