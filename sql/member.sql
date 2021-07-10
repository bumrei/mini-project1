drop table member ;
drop sequence member_seq;

create sequence member_seq ;

create table member (
  memNo number(4) not null ,
  name varchar(9) not null ,
  ID varchar(6) primary key ,
  psw varchar(6) not null ,
  email varchar(20) not null ,
  cdate date not null ,
  score number(4) default(0)
);

set pagesize 120;
set linesize 120;

commit ;

desc member

--insert �κ�/////////////////////////////////////////////////////////////////////////////////////////////////////

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '������', 'ID1', '12345', 'hahahaha@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '��ȣȣ', 'ID2', '12345', 'ggggjjj@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '������', 'ID3', '12345', 'ggggjjj@gmail.com', sysdate);

--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

commit ;
select * from member;

  