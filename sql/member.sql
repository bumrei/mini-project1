drop table transaction ;
drop table member ;
drop sequence member_seq;

create sequence member_seq ;

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
  ldate date
  constraint pk_member_ID primary key(ID)
);

create table transaction (
  ID varchar(10) not null,
  irow number(3) ,
  jcolumn number(3) ,
  constraint fk_transaction_ID foreign key(ID)
      references member(ID) on delete cascade
);


commit ;

--member insert ºÎºÐ/////////////////////////////////////////////////////////////////////////////////////////////////////

insert into member(memNo, name, ID, psw, email, cdate, ldate)
 values(member_seq.nextval, '±èÇÏÇÏ', 'ID1', '12345', 'hahahaha@gmail.com',  '2021-07-10', '2021-07-12');

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±èÈ£È£', 'ID2', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±èÈ÷È÷', 'ID3', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate, ldate)
 values(member_seq.nextval, 'a', 'a', 'a', 'ggggjjj@gmail.com', '2021-07-10', '2021-07-14');


--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

--transaction insert ºÎºÐ////////////////////////////////////////////////////////////////////////////////////////

insert into transaction values('ID1', 1,1) ;
insert into transaction values('ID1', 1,2) ;
insert into transaction values('ID1', 1,3) ;
insert into transaction values('ID2', 1,2) ;
insert into transaction values('ID2', 1,3) ;
insert into transaction values('ID3', 2,1) ;
insert into transaction values('ID3', 3,3) ;
insert into transaction values('ID3', 2,2) ;
insert into transaction values('ID3', 2,4) ;

--//////////////////////////////////////////////////////////////////////////////////////////////////////////////


commit ;
select * from member;

select * from transaction ;

  