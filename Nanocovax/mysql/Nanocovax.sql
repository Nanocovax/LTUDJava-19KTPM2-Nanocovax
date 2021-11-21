create database Nanocovax;
use Nanocovax;

create table TAIKHOAN (
	id int not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    phanQuyen INT,
    tinhTrang boolean,
    constraint PK_TAIKHOAN primary key(id)
);

create table TTNGUOIDUNG (
	id int not null auto_increment,
    hoTen nvarchar(255),
    maTTP varchar(255) not null,
    maQH varchar(255) not null,
    maXP varchar(255) not null,
    idMod int,
    constraint PK_TTNGUOIDUNG primary key(id)
);

create table TRANGTHAI (
	id int not null auto_increment,
    trangThai int not null,
    noiDT int not null,
    thoiGian datetime,
    idMod int,
    constraint PK_TRANGTHAI primary key(id)
);

create table LIENQUAN (
	id int not null,
    idNQL int not null,
    constraint PK_LIENQUAN primary key(id, idNQL)
);

create table LICHSU (
	id int not null auto_increment,
    tTCu int not null,
    tTMoi int not null,
    nDTCu int not null,
    nDTMoi int not null,
    thoiGian datetime,
    idMod int,
	constraint PK_LICHSU primary key(id)
);

create table NOIDIEUTRI (
	idNDT int not null auto_increment,
    ten nvarchar(255),
    sucChua int,
    dangChua int,
    idMod int,
    constraint PK_NOIDIEUTRI primary key(idNDT)
);

create table TINHTHANHPHO (
	maTTP varchar(255) not null,
    ten nvarchar(255),
    loai int,
	constraint PK_TINHTHANHPHO primary key(maTTP)
);

create table QUANHUYEN (
	maQH varchar(255) not null,
    ten nvarchar(255),
    loai int,
    maTTP varchar(255) not null,
	constraint PK_QUANHUYEN primary key(maQH)
);

create table XAPHUONG (
	maXP varchar(255) not null,
    ten nvarchar(255),
    loai int,
    maQH varchar(255) not null,
	constraint PK_XAPHUONG primary key(maXP)
);

create table NHUYEUPHAM (
	idNYP int not null auto_increment,
	tenGoi nvarchar(255),
    thoiHan date,
    donGia long,
    gioiHan int,
    idMod int,
	constraint PK_NHUYEUPHAM primary key(idNYP)
);

create table HOADON (
	soHD int not null auto_increment,
    nguoiMua int not null,
    ngayMua date,
    triGia long,
	constraint PK_HOADON primary key(soHD)
);

create table CTHD (
	soHD int not null auto_increment,
    maNYP int not null,
	soLuong int,
	constraint PK_HOADON primary key(soHD)
);

alter table TTNGUOIDUNG
add 
constraint FK_TTNGUOIDUNG_TK foreign key(id) references TAIKHOAN(id);
alter table TTNGUOIDUNG
add
constraint FK_TTNGUOIDUNG_TINHTHANHPHO foreign key(maTTP) references TINHTHANHPHO(maTTP);
alter table TTNGUOIDUNG
add
constraint FK_TTNGUOIDUNG_QUANHUYEN foreign key(maQH) references QUANHUYEN(maQH);
alter table TTNGUOIDUNG
add
constraint FK_TTNGUOIDUNG_XAPHUONG foreign key(maXP) references XAPHUONG(maXP);

alter table TRANGTHAI
add 
constraint FK_TRANGTHAI_TTNGUOIDUNG foreign key(id) references TTNGUOIDUNG(id);
alter table TRANGTHAI
add 
constraint FK_TRANGTHAI_NOIDIEUTRI foreign key(noiDT) references NOIDIEUTRI(idNDT);

alter table LIENQUAN
add 
constraint FK_LIENQUAN_TTNGUOIDUNG foreign key(id) references TTNGUOIDUNG(id);
alter table LIENQUAN
add 
constraint FK_LIENQUAN_TTNQL foreign key(idNQL) references TTNGUOIDUNG(id);

alter table LICHSU
add 
constraint FK_LICHSU_TTNGUOIDUNG foreign key(id) references TTNGUOIDUNG(id);
alter table LICHSU
add 
constraint FK_LICHSU_TTCU foreign key(tTCu) references TRANGTHAI(id);
alter table LICHSU
add 
constraint FK_LICHSU_TTMOI foreign key(tTMoi) references TRANGTHAI(id);
alter table LICHSU
add 
constraint FK_LICHSU_NDTCU  foreign key(nDTCu) references NOIDIEUTRI(idNDT);
alter table LICHSU
add 
constraint FK_LICHSU_NDTMOI  foreign key(nDTMoi) references NOIDIEUTRI(idNDT);

alter table QUANHUYEN
add 
constraint FK_QUANHUYEN_TINHTHANHPHO foreign key(maTTP) references TINHTHANHPHO(maTTP);

alter table XAPHUONG
add 
constraint FK_XAPHUONG_QUANHUYEN foreign key(maQH) references QUANHUYEN(maQH);

alter table CTHD
add 
constraint FK_CTHD_NHUYEUPHAM foreign key(maNYP) references NHUYEUPHAM(idNYP);

insert into TAIKHOAN values(0, 'admin', 'admin', 0, false);

set SQL_SAFE_UPDATES=0;