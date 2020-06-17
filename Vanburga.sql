CREATE SCHEMA Vanburga;
USE Vanburga;

##RV : Cambie todas las tablas a ingles para manejar una uniformidad con el codigo.
##Modifique las mayusculas por la estructura de codigo (cammelCase)

##CAMBIE EL NOMBRE DE COLUMNA CATEGORIA POR NOMBRE, ES MAS REPRESENTATIVO.
CREATE TABLE IF NOT EXISTS Category(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(70) NOT NULL,
description VARCHAR(2000) NULL
);

CREATE TABLE IF NOT EXISTS state(
id INT AUTO_INCREMENT PRIMARY KEY,
state VARCHAR(70) NOT NULL
);

CREATE TABLE IF NOT EXISTS Client(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
lastName VARCHAR(100),
cellphone VARCHAR(50),
mail VARCHAR(100)
);

##AGREGUE IDDIRECCION. ADEMAS LOS VALORES CP, ALTURA Y PISO LOS HICE VARCHAR.
CREATE TABLE IF NOT EXISTS Address(
id INT AUTO_INCREMENT PRIMARY KEY,
idClient INT,
idState INT,
street VARCHAR(100) NOT NULL,
doorNumber VARCHAR(50) NULL,
zipCode VARCHAR(4) NULL,
floor VARCHAR(50) NULL,
door VARCHAR(10) NULL,
FOREIGN KEY (idState)
        REFERENCES state (id),
FOREIGN KEY(idClient)
		REFERENCES Client(id)
);

##CAMBIE EL NOMBRE DE COLUMNA PRODUCTO POR NOMBRE, ES MAS REPRESENTATIVO.
##AL PRECIO LE PUSE TIPO DE DATO DECIMAL DE 13 DIGITOS ENTEROS Y 2 DECIMALES. ME PARECIO MAS ACORDE PARA LLEVAR LOS PRECIOS.
##A LA COLUMNA DISPONIBLE LE PUSE DEFAULT TRUE
##LA COLUMNA DESCRIPCION LA HICE TEXT POR SI QUEREMOS ALMACENAR MAS DE 255 CARACTERES
CREATE TABLE IF NOT EXISTS product(
id INT PRIMARY KEY,
code varchar (50) null unique,
name VARCHAR(255) NOT NULL,
idCategory INT NOT NULL,
rawMaterial INT DEFAULT 0,
price DECIMAL(13,2) NOT NULL,
description VARCHAR(2000) NULL,
color varchar(10),
available BOOLEAN DEFAULT TRUE,
FOREIGN KEY(idCategory)
		REFERENCES Category(id)
);

##CAMBIE EL NOMBRE DE COLUMNA EXTRA POR NOMBRE, ES MAS REPRESENTATIVO.
CREATE TABLE IF NOT EXISTS Extra(
id INT PRIMARY KEY,
code varchar(50) null unique,
name VARCHAR(150) NOT NULL,
rawMaterial INT DEFAULT 0,
quantityLimit INT DEFAULT 1,
price DECIMAL(13,2) NOT NULL,
available BOOLEAN DEFAULT TRUE
);

##CAMBIE EL NOMBRE AGREGANDOOLE UNA X (LEASE POR) PARA QUE SEA CLARO QUE ES UNA TABLA INTERMEDIA
##RV : Saque la X para eliminar los guiones en las tablas. Se va a tener que interpretar por el "By"
CREATE TABLE IF NOT EXISTS productByExtra(
idExtra INT NOT NULL,
idProduct INT NOT NULL,
PRIMARY KEY(idExtra, idProduct),
FOREIGN KEY(idExtra)
		REFERENCES Extra(id),
FOREIGN KEY(idProduct)
		REFERENCES Product(id)
);

##CAMBIA EL FORMATO DE MONTO POR DECIMAL
##AGREGO DEFAULT PARA LA FECHA EN FORMATO TIMESTAMP (
CREATE TABLE IF NOT EXISTS `Order`(
id INT AUTO_INCREMENT PRIMARY KEY,
idClient INT NOT NULL,
status varchar (50) null,
comments VARCHAR(2000) NULL,
createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
amount DECIMAL(13,2) NOT NULL,
delivery BOOL default false,
FOREIGN KEY(idClient)
		REFERENCES Client(id)
);

CREATE TABLE IF NOT EXISTS OrderDetail(
id INT AUTO_INCREMENT PRIMARY KEY,
idProduct INT NOT NULL,
idOrder INT NOT NULL,
FOREIGN KEY(idProduct)
		REFERENCES Product(id),
FOREIGN KEY(idOrder)
		REFERENCES `Order`(id)
);

CREATE TABLE IF NOT EXISTS ExtraOrderDetail(
id INT AUTO_INCREMENT PRIMARY KEY,
idOrderDetail INT NOT NULL,
idExtra INT NOT NULL,
quantity INT NOT NULL,
FOREIGN KEY(idOrderDetail)
		REFERENCES OrderDetail(id),
FOREIGN KEY(idExtra)
		REFERENCES Extra(id)
);

##TABLE'S POPULATION
INSERT INTO category(name, description) values ('Burgers','Las mejores del condado');
INSERT INTO category(name, description) values ('Bebidas','Tomate una fresca');
INSERT INTO category(name, description) values ('Acompañamientos','No podes pedir la hamburguesa sin unas buenas papas');
INSERT INTO state(state) values ('Don Torcuato');
INSERT INTO state(state) values ('Ricardo Rojas');
INSERT INTO state(state) values ('El Talar');
INSERT INTO state(state) values ('General Pacheco');
INSERT INTO state(state) values ('Benavídez');
INSERT INTO state(state) values ('Los troncos del Talar');
INSERT INTO state(state) values ('Nordelta');
INSERT INTO client(name, lastName, cellphone, mail) values ('Franco','Hildt','1123977072', 'franhildt@gmail.com');
INSERT INTO client(name, lastName, cellphone, mail) values ('Nico','Rohland','1133886456', 'nrohland@gmail.com');
INSERT INTO address(idclient, idstate, street, doorNumber, zipcode) values ('1','1','Los Alamos','895', '1667');
INSERT INTO address(idclient, idstate, street, doorNumber, zipcode) values ('2','3','Las Amapolas','123' , '1667');
INSERT INTO product(id, name, idcategory, price, description, code) values (1, 'Cerveza Andes IPA','2','80','IPA rubia', 'B-CAI');
INSERT INTO product(id, name, idcategory, price, description, code) values (2, 'Cuarto Vanburga','1','400','Doble carne, Doble cheddar, Cebolla cortada, Ketchup Heinz, Mostaza Heinz', 'H-CV');
INSERT INTO product(id, name, idcategory, price, description, code) values (3, 'Clásica','1','400','Doble carne, Doble cheddar, Cebolla cruda, Tomate, Lechuga, Pickles de pepino, Salsa Vanburga', 'H-C');
INSERT INTO product(id, name, idcategory, price, description, code) values (4, 'Cheeseburger','1','400','Triple carne, Sextuple cheddar, Bacon crocante', 'H-CHB');
INSERT INTO product(id, name, idcategory, price, description, code) values (5, 'Argenta','1','400','Doble carne, Doble provoleta, Chimichurri de la Abuela', 'H-A');
INSERT INTO product(id, name, idcategory, price, description, code) values (6, 'Crispy Onion','1','400','Doble carne, Doble cheddar, Cebolla crocante, Bacon crocante, Salsa Vanburga', 'H-CO');
INSERT INTO product(id, name, idcategory, price, description, code) values (7, 'Oklahoma','1','400','Doble carne Smasheada con cebolla, Doble cheddar', 'H-O');
INSERT INTO product(id, name, idcategory, price, description, code) values (8, 'Sweet Onion','1','400','Doble carne, Doble cheddar, Cebolla caramelizada, , Bacon crocante', 'H-SO');
INSERT INTO product(id, name, idcategory, price, description, code) values (9, 'Smoke Shack','1','400','Doble carne, Doble Emmenthal, Bacon crocante, Morrones bañados con tabasco, Salsita Shake Shack', 'H-SS');
INSERT INTO extra(id, name, price, code) values (1, 'Extra Cheddar','50', 'E-CH');
INSERT INTO extra(id, name, price, code) values (2, 'Extra Bacon','50' , 'E-BA');
INSERT INTO extra(id, name, price, code) values (3, 'Extra Medallón','50', 'E-ME');
INSERT INTO extra(id, name, price, code) values (4, 'Extra papas con Cheddar y Bacon','50', 'E-PCB');
INSERT INTO productByExtra(idProduct, idExtra) values (2, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (2, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (2, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (2, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (3, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (3, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (3, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (3, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (4, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (4, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (4, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (4, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (5, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (5, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (5, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (5, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (6, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (6, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (6, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (6, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (7, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (7, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (7, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (7, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (8, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (8, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (8, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (8, 4);
INSERT INTO productByExtra(idProduct, idExtra) values (9, 1);
INSERT INTO productByExtra(idProduct, idExtra) values (9, 2);
INSERT INTO productByExtra(idProduct, idExtra) values (9, 3);
INSERT INTO productByExtra(idProduct, idExtra) values (9, 4);
INSERT INTO `Order`(idclient, comments, amount) values ('1','Haganme la hamburguesa con amor','420');
INSERT INTO OrderDetail(idproduct,idorder) values ('1','1');
INSERT INTO OrderDetail(idproduct,idorder) values ('2','1');
INSERT INTO ExtraOrderDetail(idOrderDetail, idExtra) values ('1', '1');


##TABLAS Y DATOS PARA EL ENVÍO DE MAILS
CREATE TABLE IF NOT EXISTS SystemProperty(
id INT PRIMARY KEY auto_increment NOT NULL,
propertyKey varchar(50) NOT NULL,
name varchar(50) not null,
oldValue  varchar(50) not null,
newValue  varchar(50) not null,
description  varchar(150) not null
);

insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.hostNameString','MailManager Servidor SMTP','','localhost','Dirección IP o nombre del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.portString','MailManager Puerto del Servidor SMTP','','25','Puerto del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpAuthUserName','MailManager Usuario Servidor SMTP','','','Usuario de autenticacion del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpAuthPassword','MailManager Contraseña Servidor SMTP','','','Contraseña de autenticacion del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpAuthRequired','MailManager Autenticacion SMTP','','false','Se requiere de autenticacion en servidor SMTP para envío de mails. Si el valor es true se valida el usuario y contraseña.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpTls','MailManager Usa TLS SMTP','','false','Se enviará comando STARTTLS al servidor SMTP para envío de mails.');


/*
select ord.id, ord.delivery, ord.status, ord.comments, ord.createDate, ord.amount,
cli.id as clientId, cli.name, cli.lastName, cli.cellphone, cli.mail,
addr.id as addressId, addr.street,addr.doornumber, addr.zipcode, addr.floor, addr.door,
st.id as stateId, st.state,
odet.id as detailId,
prod.id as productId, prod.code, prod.name, prod.description,
ext.id as extraId, ext.code, ext.name, ext.price
from `Order` as ord
left join client as cli on ord.idclient=cli.id
left join address as addr on addr.idclient=cli.id
left join state as st on st.id=addr.idState
left join orderdetail as odet on odet.idOrder=ord.id
left join product as prod on prod.id=odet.idproduct
left join extraorderdetail as extd on extd.idorderdetail=odet.id
left join extra as ext on ext.id=extd.idextra

where ord.id=1; */


##VANBERGA INC. TODOS LOS DERECHOS RESERVADOS