drop table notice;
drop sequence notice_seq;

create table notice(
code number primary key,
title nvarchar2(20) not null,
content nvarchar2(100) not null,
id varchar2(10) default 'Admin',
cdate date default(sysdate)
);

create sequence notice_seq;

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목1', '내용...............', '19/07/16');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목2', '내용내용', '19/12/25');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목3', '내용', '20/01/01');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목4', '내용내용.........', '20/03/01');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목5', '내용', '20/08/15');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목6', '내용', '20/10/09');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목7', '내용', '20/12/25');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목8', '내용', '21/06/07');
insert into notice(code, title, content, cdate) values(notice_seq.nextval, '제목9', '내용', '21/07/15');

commit;


select * from notice;