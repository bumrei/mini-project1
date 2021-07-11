drop table word;

drop sequence word_seq;

create table word(
wordNum number(4) primary key,
wordLevel number(1) not null,
eng varchar2(20) not null,
kor varchar2(24) not null
);

create sequence word_seq;

-----level 1
insert into word values(word_seq.nextval, 1, 'apple', '사과');
insert into word values(word_seq.nextval, 1, 'bee', '벌');
insert into word values(word_seq.nextval, 1, 'mirror', '거울');
insert into word values(word_seq.nextval, 1, 'door', '문');
insert into word values(word_seq.nextval, 1, 'glasses', '안경');
insert into word values(word_seq.nextval, 1, 'umbrella', '우산');

-----level 2
insert into word values(word_seq.nextval, 2, 'language', '언어');
insert into word values(word_seq.nextval, 2, 'society', '사회');
insert into word values(word_seq.nextval, 2, 'education', '교육');
insert into word values(word_seq.nextval, 2, 'experience', '경험');
insert into word values(word_seq.nextval, 2, 'result', '결과');
insert into word values(word_seq.nextval, 2, 'reason', '이유');

-----level 3
insert into word values(word_seq.nextval, 3, 'individual', '개인');
insert into word values(word_seq.nextval, 3, 'technology', '과학기술');
insert into word values(word_seq.nextval, 3, 'objection', '반대');
insert into word values(word_seq.nextval, 3, 'objective', '목적');
insert into word values(word_seq.nextval, 3, 'attitude', '태도');
insert into word values(word_seq.nextval, 3, 'improve', '개선하다');

commit;

desc word