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

--insert ∫Œ∫–/////////////////////////////////////////////////////////////////////////////////////////////////////

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±Ë«œ«œ', 'qwert', '12345', 'hahahaha@gmail.com', sysdate);

insert into member(memNo, name, ID, psw, email, cdate)
 values(member_seq.nextval, '±Ë»£»£', 'yjrhr', '12345', 'ggggjjj@gmail.com', sysdate);

--////////////////////////////////////////////////////////////////////////////////////////////////////////////////

commit ;
select * from member;

  