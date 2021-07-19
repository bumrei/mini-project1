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

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '6월 7일 서버 오픈 안내', '21년 6월 7일자로 서버를 오픈할 계획입니다. 회원분들의 많은 이용 바랍니다.', '21/06/05');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '게임 이용 안내', '단어 암기 게임은 단어장을 보고 단어를 외우며, 랜덤으로 출력되는 영단어의 뜻을 맞추는 게임입니다. 재미있게 즐겨주세요!', '21/06/07');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '단어 Level 추가', '단어를 수준별로 테스트하면 좋겠다는 회원분들의 의견을 반영해 level을 추가하였습니다.', '21/06/10');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '6월 15일 화요일 정기점검 안내', '매달 진행하는 정기점검 중에는 게임 실행이 불가능합니다. 미리 확인하시어 이용에 불편이 없으시길 바랍니다. ', '21/06/12');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '오류 신고 안내', '게임 진행 중 오류를 발견하신 분은 건의사항으로 신고하여 주시기 바랍니다. 유의한 신고 시 5000G를 드립니다. ', '21/06/12');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '회원 Level 설정', '기본 Bronze, exp 40~ Silver, exp 100~ Gold, exp 200~ Platinum, exp 400~ Diamond, exp 800~ Mster', '21/06/25');


insert into notice(code, title, content, cdate) values(notice_seq.nextval, '게임 포인트 추가', '게임 단계별로 포인트를 설정하였습니다. ( 1단계: 10G, 2단계: 20G, 3단계: 30G ) 상점 기능은 앞으로 추가 예정입니다.', '21/06/28');


insert into notice(code, title, content, cdate) values(notice_seq.nextval, '이모티콘과 상점추가', '닉네임 앞에 설정할 이모티콘을 파는 상점을 오픈하였습니다. 게임에서 얻은 포인트로 구매 가능합니다.', '21/07/01');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '게임 포인트 랜덤화', '각 단계별로 일정하던 포인트를 난수로 변경하였습니다. ( 1단계: 1~10G, 2단계: 11~20G, 3단계: 21~30G )', '21/07/07');

insert into notice(code, title, content, cdate) values(notice_seq.nextval, '7월 15일 목요일 정기점검 안내', '매달 진행하는 정기점검 중에는 게임 실행이 불가능합니다. 미리 확인하시어 이용에 불편이 없으시길 바랍니다.', '21/07/12');


commit;


select * from notice;