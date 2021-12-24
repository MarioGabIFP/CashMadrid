create database CashMadrid;
use CashMadrid;

create table if not exists clientes (
	IdCli integer primary key auto_increment comment "identificador interno del cliente",
    DNI varchar(9) not null comment "Documento Nacional de Identidad del cliente",
    Nom varchar(200) not null comment "Nombre del cliente",
    Apel varchar(400) comment "Apellidos del cliente",
    Tel varchar(16) not null comment "Telefono del cliente", 
    Email varchar(200) comment "Correo electronico del cliente",
    Dom varchar(400) not null comment "Domicilio del cliente"
);

create table if not exists cuenta (
	IdCu integer primary key auto_increment comment "identificador interno de la cuenta",
    DigCon varchar(4) not null comment "Codigo de Pais y dígito de control del IBAN",
    Ent varchar(4) not null comment "Codigo de la Entidad bancaria a la que pertenece el IBAN",
    Ofi varchar(4) not null comment "Codigo de la oficina a la que pertece el IBAN",
    DigContr varchar(2) not null comment "digito de control de la cuenta bancaria",
    NCue varchar(10) not null comment "Número de cuenta a la que pertenece le cliente",
    NBanc varchar(200) comment "Nombre del banco al que pertenece la cuenta"
);

create table if not exists asignacion (
	IdCli integer not null comment "identificador interno del cliente",
    IdCu integer not null comment "identificador interno de la cuenta",
    FINI timestamp default current_timestamp not null comment "Fecha de apertura de la cuenta",
    FFIN timestamp comment "Fecha de cierre de la cuenta bancaria (si relleno = cuenta inactiva)",
    constraint asigancion_cliente foreign key (IdCli) references clientes(IdCli),
    constraint asigancion_cuenta foreign key (IdCu) references cuenta(IdCu)
);

create table if not exists transferencias (
	CodTran integer primary key auto_increment comment "Codigo interno de la transferencia",
	COri varchar(23) not null comment "cuenta emisora de la transferencia",
	CDes varchar(23) not null comment "cuenta dstinataria de la transferencia",
	FTran timestamp default current_timestamp not null comment "Fecha y hora de la transferencia",
    Imp double not null comment "Importe de la transferencia"
);