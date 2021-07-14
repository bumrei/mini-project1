drop table notice;
drop sequence notice_seq;

create table notice(
code number(3) primary key,
title nvarchar2(20) not null,
content nvarchar2(100) not null,
id varchar2(10) default('Admin')
);

create sequence notice_seq;

insert into notice(code, title, content) values(notice_seq.nextval, '제목1', '내용...............');
insert into notice(code, title, content) values(notice_seq.nextval, '제목2', '내용내용');
insert into notice(code, title, content) values(notice_seq.nextval, '제목3', '내용');
insert into notice(code, title, content) values(notice_seq.nextval, '제목4', '내용내용.........');
insert into notice(code, title, content) values(notice_seq.nextval, '제목5', '내용');

commit;