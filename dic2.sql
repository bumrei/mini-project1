drop table dic ;

create table dic (
id number(4) primary key ,
words varchar(15) not null 
);

commit;
insert  into dic
  values(7789, 'ÇÑ±Û'); 

insert  into dic
  values(7389, 'hong'); 

desc dic ;
