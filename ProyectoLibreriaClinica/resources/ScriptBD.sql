
create table Fisioterapeuta(
id_fisio int primary key,
nombre varchar(30),
especialidad varchar (35),
localidad varchar (30),
constraint chk_especialidad check (especialidad='Traumatología' or especialidad='Neurología'),
constraint chk_localidad check (localidad='Sevilla' or localidad='Córdoba')
);

create table Paciente(
id_paciente int primary key,
nombre varchar(30),
edad int,
constraint chk_edad check (edad>0)
);

create table diagnostica(
fecha date primary key,
id_digfisio int,
id_digPacient int,
descripcion varchar(200) not null,
constraint fk_digFisio foreign key (id_digfisio) references Fisioterapeuta(id_fisio),
constraint fk_digPacient foreign key (id_digPacient) references Paciente (id_paciente),
constraint chk_fecha check (fecha<'2024-01-01' and fecha>'2022-01-01')
);