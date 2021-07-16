drop table message ;
drop table transaction ;
drop table member ;
drop sequence member_seq;
drop sequence message_seq;

create sequence member_seq ;
create sequence message_seq ;

create table member (
  memNo number(4) not null ,
  name varchar(9) not null ,
  ID varchar(10) ,
  psw varchar(6) not null ,
  email varchar(20) not null ,
  cdate date not null ,
  score number(4) default(0) ,
  comnt varchar(100) ,
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


commit ;

--member insert ºÎºÐ/////////////////////////////////////////////////////////////////////////////////////////////////////

insert into member(memNo, name, ID, psw, email, cdate, ldate, point)
 values(member_seq.nextval, '±èÇÏÇÏ', 'ID1', '12345', 'hahahaha@gmail.com',  '2021-07-10', '2021-07-12', 100);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±èÈ£È£', 'ID2', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±èÈ÷È÷', 'ID3', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate, ldate)
 values(member_seq.nextval, 'a', 'a', 'a', 'ggggjjj@gmail.com', '2021-07-10', '2021-07-14');


--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

--transaction insert ºÎºÐ////////////////////////////////////////////////////////////////////////////////////////


insert into transaction values('ID1', 0,0) ;

insert into transaction values('ID2', 0,0) ;

insert into transaction values('ID3', 0,0) ;

--//////////////////////////////////////////////////////////////////////////////////////////////////////////////

--message insert ºÎºÐ ///////////////////////////////////////////////////////////////////////////////////////////
insert into message values(message_seq.nextval, 'ID1', 'hi hahahahahahaha', sysdate, 'ID2');

--//////////////////////////////////////////////////////////////////////////////////////////////////////////////


commit ;
select * from member;

select * from transaction ;

select * from message ;

  