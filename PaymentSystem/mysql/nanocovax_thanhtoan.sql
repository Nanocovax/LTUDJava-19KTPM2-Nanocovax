create database nanocovax_thanhtoan;
use nanocovax_thanhtoan;

create table taikhoan
(
	id varchar(13),
    sodu  int unsigned,
    primary key (id)
);

create table lichsu
(
	id varchar(13),
    thoigian datetime,
    tienra int unsigned,
    primary key (id,thoigian)
);

alter table lichsu
add constraint FK_lichsu_taikhoan
foreign key (id) references taikhoan(id);