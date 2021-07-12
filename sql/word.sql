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
insert into word values(word_seq.nextval, 1, 'address', '주소');
insert into word values(word_seq.nextval, 1, 'afternoon', '오후');
insert into word values(word_seq.nextval, 1, 'age', '나이');
insert into word values(word_seq.nextval, 1, 'air', '공기');
insert into word values(word_seq.nextval, 1, 'baby', '아기');
insert into word values(word_seq.nextval, 1, 'bad', '나쁜');
insert into word values(word_seq.nextval, 1, 'ball', '공');
insert into word values(word_seq.nextval, 1, 'bank', '은행');
insert into word values(word_seq.nextval, 1, 'basket', '바구니');
insert into word values(word_seq.nextval, 1, 'bath', '목욕');
insert into word values(word_seq.nextval, 1, 'capital', '수도');
insert into word values(word_seq.nextval, 1, 'chair', '의자');
insert into word values(word_seq.nextval, 1, 'chance', '기회');
insert into word values(word_seq.nextval, 1, 'church', '교회');
insert into word values(word_seq.nextval, 1, 'cloud', '구름');
insert into word values(word_seq.nextval, 1, 'clock', '시계');
insert into word values(word_seq.nextval, 1, 'color', '색');
insert into word values(word_seq.nextval, 1, 'dark', '어두운');
insert into word values(word_seq.nextval, 1, 'dream', '꿈');
insert into word values(word_seq.nextval, 1, 'drink', '마시다');

-----level 2
insert into word values(word_seq.nextval, 2, 'language', '언어');
insert into word values(word_seq.nextval, 2, 'society', '사회');
insert into word values(word_seq.nextval, 2, 'education', '교육');
insert into word values(word_seq.nextval, 2, 'experience', '경험');
insert into word values(word_seq.nextval, 2, 'result', '결과');
insert into word values(word_seq.nextval, 2, 'reason', '이유');
insert into word values(word_seq.nextval, 2, 'nation', '국가');
insert into word values(word_seq.nextval, 2, 'view', '견해');
insert into word values(word_seq.nextval, 2, 'develop', '발전하다');
insert into word values(word_seq.nextval, 2, 'matter', '문제');
insert into word values(word_seq.nextval, 2, 'carry', '운반하다');
insert into word values(word_seq.nextval, 2, 'perhaps', '아마도');
insert into word values(word_seq.nextval, 2, 'machine', '기계');
insert into word values(word_seq.nextval, 2, 'pleasure', '기쁨');
insert into word values(word_seq.nextval, 2, 'idea', '생각');
insert into word values(word_seq.nextval, 2, 'value', '가치');
insert into word values(word_seq.nextval, 2, 'sense', '감각');
insert into word values(word_seq.nextval, 2, 'character', '성격');
insert into word values(word_seq.nextval, 2, 'grow', '성장하다');
insert into word values(word_seq.nextval, 2, 'modern', '현대');
insert into word values(word_seq.nextval, 2, 'culture', '문화');
insert into word values(word_seq.nextval, 2, 'picture', '그림');
insert into word values(word_seq.nextval, 2, 'history', '역사');
insert into word values(word_seq.nextval, 2, 'system', '체계');
insert into word values(word_seq.nextval, 2, 'cause', '원인');
insert into word values(word_seq.nextval, 2, 'allow', '허락하다');

-----level 3
insert into word values(word_seq.nextval, 3, 'individual', '개인');
insert into word values(word_seq.nextval, 3, 'technology', '과학기술');
insert into word values(word_seq.nextval, 3, 'objection', '반대');
insert into word values(word_seq.nextval, 3, 'objective', '목적');
insert into word values(word_seq.nextval, 3, 'attitude', '태도');
insert into word values(word_seq.nextval, 3, 'improve', '개선하다');
insert into word values(word_seq.nextval, 3, 'objectively', '객관적으로');
insert into word values(word_seq.nextval, 3, 'unemployment','실업');
insert into word values(word_seq.nextval, 3, 'tendency', '경향');
insert into word values(word_seq.nextval, 3, 'affection', '영향');
insert into word values(word_seq.nextval, 3, 'affectionate', '애정깊은');
insert into word values(word_seq.nextval, 3, 'beneficiary', '수익자');
insert into word values(word_seq.nextval, 3, 'occasion', '경우');
insert into word values(word_seq.nextval, 3, 'recognition', '인식');
insert into word values(word_seq.nextval, 3, 'property', '재산');
insert into word values(word_seq.nextval, 3, 'behavior', '행동');
insert into word values(word_seq.nextval, 3, 'determination', '결정');
insert into word values(word_seq.nextval, 3, 'generate', '발생시키다');
insert into word values(word_seq.nextval, 3, 'existence', '존재');
insert into word values(word_seq.nextval, 3, 'existentialism', '실존주의');
insert into word values(word_seq.nextval, 3, 'conexistence', '공존');
insert into word values(word_seq.nextval, 3, 'regrettable', '슬픈');
insert into word values(word_seq.nextval, 3, 'available', '쓸모있는');
insert into word values(word_seq.nextval, 3, 'severity', '엄격함');
insert into word values(word_seq.nextval, 3, 'negation', '부정');
insert into word values(word_seq.nextval, 3, 'biomechanical', '생물역학의');

commit;

desc word