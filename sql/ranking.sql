drop table ranking;

create table ranking(
id varchar2(10) not null,
score number(3) not null
);

insert into ranking values('1', 0);
insert into ranking values('2', 0);
insert into ranking values('3', 0);

commit;