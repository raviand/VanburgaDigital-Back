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

CREATE TABLE IF NOT EXISTS Role(
id INT PRIMARY KEY,
role VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS User(
id 			INT AUTO_INCREMENT PRIMARY KEY,
loginId		varchar(100) NULL,
name 		VARCHAR(70) NOT NULL,
email  		VARCHAR(100) NULL,
provider 	varchar(100)NULL,
providerId 	varchar (100) NULL,
imageUrl 	varchar(500) NULL,
token 		varchar (500) NULL,
idToken 	varchar(1500) NULL,
password 	varchar(100) Null,
phone		varchar(100) Null,
roleId		INT NULL,
FOREIGN KEY(roleId)
		REFERENCES Role(id)
);

CREATE TABLE IF NOT EXISTS State(
id INT AUTO_INCREMENT PRIMARY KEY,
state VARCHAR(70) NOT NULL,
amount DECIMAL(13,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Client(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
lastName VARCHAR(100),
cellphone VARCHAR(50),
mail VARCHAR(100),
userId INT NULL,
FOREIGN KEY (userId)
		REFERENCES User(id)
);

##AGREGUE IDDIRECCION. ADEMAS LOS VALORES CP, ALTURA Y PISO LOS HICE VARCHAR.
CREATE TABLE IF NOT EXISTS Address(
id INT AUTO_INCREMENT PRIMARY KEY,
idClient INT,
idState INT,
street VARCHAR(100) NOT NULL,
doorNumber VARCHAR(50) NULL,
reference VARCHAR(50) NULL,
floor VARCHAR(50) NULL,
door VARCHAR(10) NULL,
FOREIGN KEY (idState)
        REFERENCES State (id),
FOREIGN KEY(idClient)
		REFERENCES Client(id)
);

##CAMBIE EL NOMBRE DE COLUMNA ProductO POR NOMBRE, ES MAS REPRESENTATIVO.
##AL PRECIO LE PUSE TIPO DE DATO DECIMAL DE 13 DIGITOS ENTEROS Y 2 DECIMALES. ME PARECIO MAS ACORDE PARA LLEVAR LOS PRECIOS.
##A LA COLUMNA DISPONIBLE LE PUSE DEFAULT TRUE
##LA COLUMNA DESCRIPCION LA HICE TEXT POR SI QUEREMOS ALMACENAR MAS DE 255 CARACTERES
CREATE TABLE IF NOT EXISTS Product(
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

##CAMBIE EL NOMBRE DE COLUMNA Extra POR NOMBRE, ES MAS REPRESENTATIVO.
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
CREATE TABLE IF NOT EXISTS ProductByExtra(
idExtra INT NOT NULL,
idProduct INT NOT NULL,
##PRIMARY KEY(idExtra, idProduct),
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
whatsappLink VARCHAR(2000) NULL,
amount DECIMAL(13,2) NOT NULL,
delivery BOOL default false,
deliverTime VARCHAR (10) null,
paymentType VARCHAR (50) null,
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

CREATE TABLE IF NOT EXISTS Recipe(
id INT PRIMARY KEY,
description VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS MenuRecipe(
id INT AUTO_INCREMENT PRIMARY KEY,
code varchar(50),
idRecipe INT,
quantity INT,
FOREIGN KEY(idRecipe)
		REFERENCES Recipe(id)
);


##TABLAS Y DATOS PARA EL ENVÍO DE MAILS
CREATE TABLE IF NOT EXISTS SystemProperty(
id INT PRIMARY KEY auto_increment NOT NULL,
propertyKey varchar(50) NOT NULL,
name varchar(50) not null,
oldValue  varchar(50) not null,
newValue  varchar(50) not null,
description  varchar(150) not null
);

CREATE TABLE IF NOT EXISTS BusinessSchedule(
	id INT PRIMARY KEY auto_increment NOT NULL,
    day varchar(10) not null,
    openTime Time not null,
    closeTime Time not null,
    available boolean not null
);


##TABLE'S POPULATION
INSERT INTO Category(name, description) values ('Burgers','Las mejores del condado');
INSERT INTO Category(name, description) values ('Bebidas y otros','Tomate una fresca');
INSERT INTO State(state, amount) values ('Gral. Pacheco',40);
INSERT INTO State(state, amount) values ('Talar',50);
INSERT INTO State(state, amount) values ('Los Troncos',50);
INSERT INTO State(state, amount) values ('Ricardo Rojas',70);
INSERT INTO State(state, amount) values ('Nordelta',60);
INSERT INTO State(state, amount) values ('Don Torcuato',60);
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (1, 'Cheeseburger','1',340,'Medallones 130 gr, doble cheddar, bacon crocante. Porción de papas','H-CH', 1, 'FFEB3B');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (2, 'Sweet onion','1',340,'Medallones 130 gr, cheddar, cebolla caramelizada, bacon crocante. Porción de papas','H-SW', 1, 'E91E63');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (3, 'Crisp onion','1',340,'Medallones 130 gr, cheddar, cebolla crujiente, salsa vanburga, bacon crocante. Porción de papas','H-CR', 1, '9E9E9E');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (4, 'Cuarto Vanburga','1',340,'Medallones 130 gr, cheddar, cebolla cruda, ketchup, mostaza. Porción de papas','H-CV', 1, 'FFFFFF');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (5, 'Clasica','1',340,'Medallones 130 gr, cheddar, lechuga, tomate, pickles de pepino, cebolla cruda, salsa vanburga. Porción de papas','H-CL', 1, '4CAF50');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (6, 'Smokedshack','1',360,'Medallones 130 gr, doble queso emmenthal, morrones bañados con tabasco, salsa shakeshack, bacon crocante. Porción de papas','H-SM', 1, 'F44336');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (7, 'Argenta','1',360,'Medallones 130 gr, provoleta, chimichurri de la abuela. Porción de papas','H-AR', 1, 'B3E5FC');
INSERT INTO Product(id, name, idCategory, price, description, code, rawMaterial, color) values (8, 'Oklahoma','1',320,'Medallones 100 gr smasheado, cheddar, cebolla grilled. Porción de papas','H-OK', 1, 'FF5722');
INSERT INTO Product(id, name, idCategory, price, description, code) values (9, 'Tacuara Blonde','2',170,'Lata de 473 ml - Blonde (Cerveceria Tacuara)','B-BLO');
INSERT INTO Product(id, name, idCategory, price, description, code) values (10, 'Tacuara IPA Sanfer','2',180,'Lata de 473 ml - American IPA (Cervecería Tacuara)','B-ISF');
INSERT INTO Product(id, name, idCategory, price, description, code) values (11, 'Tacuara IPA Malvinas','2',170,'Lata de 473 ml - English IPA (Cervecería Tacuara)','B-IMA');
INSERT INTO Product(id, name, idCategory, price, description, code) values (12, 'Tacuara Honey','2',170,'Lata de 473 ml - Honey (Cervecería Tacuara)','B-HON');
INSERT INTO Product(id, name, idCategory, price, description, code) values (13, 'Tacuara Irish red ale','2',170,'Lata de 473 ml - English red ale (Cervecería Tacuara)','B-RED');
INSERT INTO Product(id, name, idCategory, price, description, code) values (14, 'Tacuara Jim Morrison','2',180,'Lata de 473 ml - Blend (Cervecería Tacuara)','B-JIM');
INSERT INTO Product(id, name, idCategory, price, description, code) values (15, 'Tacuara Porter','2',170,'Lata de 473 ml - Porter (Cervecería Tacuara)','B-POR');
INSERT INTO Product(id, name, idCategory, price, description, code) values (16, 'Tacuara APA Sorachi','2',180,'Lata de 473 ml - American pale ale (Cervecería Tacuara)','B-APA');
INSERT INTO Product(id, name, idCategory, price, description, code) values (17, 'Porción de papas','1',130,'Porción de papas','A-PPC');
INSERT INTO Product(id, name, idCategory, price, description, code) values (18, 'Franui','2',270,'Frambuesas bañadas en chocolate','P-FRA');
INSERT INTO Extra(id, name, price, code, quantityLimit) values (1,'Extra cheddar por medallón',15,'E-CH',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (2,'Extra bacon',50,'E-BA',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (3,'Extra pickles de pepino',30,'E-PP',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (4,'Extra cebolla cruda',30,'E-CC',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (5,'Extra salsa Vanburga',30,'E-SV',1);
INSERT INTO Extra(id, name, price, code, quantityLimit, rawMaterial) values (6,'Extra medallon',80,'E-ME',4,1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (7,'Cambio por queso Emmenthal',0,'E-CQE',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (8,'Extra cheddar a las papas',60,'E-PC',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (9,'Extra cheddar y bacon a las papas',90,'E-PCB',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (10,'Sin cheddar',0,'Q-CH',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (11,'Sin bacon',0,'Q-BA',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (12,'Extra cebolla caramelizada',30,'E-SW',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (13,'Sin cebolla caramelizada',0,'Q-SW',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (14,'Sin salsa Vanburga',0,'Q-SV',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (15,'Sin cebolla crujiente',0,'Q-CR',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (16,'Sin ketchup',0,'Q-KE',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (17,'Sin mostaza',0,'Q-MO',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (18,'Sin tomate',0,'Q-TO',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (19,'Sin lechuga',0,'Q-LE',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (20,'Extra queso Emmenthal por medallón',15,'E-EM',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (21,'Extra morrones con tabasco',30,'E-MT',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (22,'Cambio por queso cheddar',0,'E-CQC',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (23,'Sin cebolla cruda',0,'Q-CC',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (24,'Sin queso Emmenthal',0,'Q-EM',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (25,'Sin morrones con tabasco',0,'Q-MT',1);
INSERT INTO Extra(id, name, price, code, quantityLimit) values (26,'Sin pickles de pepino',0,'Q-PP',1);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,1);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,2);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,3);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,4);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,5);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,6);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,7);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,9);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,10);
INSERT INTO ProductByExtra(idProduct, idExtra) values (1,11);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,1);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,2);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,6);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,12);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,7);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,9);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,10);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,11);
INSERT INTO ProductByExtra(idProduct, idExtra) values (2,13);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,1);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,2);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,6);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,7);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,9);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,10);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,11);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,14);
INSERT INTO ProductByExtra(idProduct, idExtra) values (3,15);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,1);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,2);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,3);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,4);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,6);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,7);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,9);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,10);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,23);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,16);
INSERT INTO ProductByExtra(idProduct, idExtra) values (4,17);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,1);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,2);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,3);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,4);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,6);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,7);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,9);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,10);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,14);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,26);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,23);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,18);
INSERT INTO ProductByExtra(idProduct, idExtra) values (5,19);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,20);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,2);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,21);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,6);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,22);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,9);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,24);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,11);
INSERT INTO ProductByExtra(idProduct, idExtra) values (6,25);
INSERT INTO ProductByExtra(idProduct, idExtra) values (17,8);
INSERT INTO ProductByExtra(idProduct, idExtra) values (17,9);
INSERT INTO Role(id, role) values (1, "Admin");
INSERT INTO Role(id, role) values (2, "Manager");
INSERT INTO Role(id, role) values (3, "Seller");
INSERT INTO Recipe (ID, DESCRIPTION) VALUES (1,'Cheddar');
INSERT INTO Recipe (ID, DESCRIPTION) VALUES (2,'Emmenthal');
INSERT INTO Recipe (ID, DESCRIPTION) VALUES (3,'Medallon');
INSERT INTO Recipe (ID, DESCRIPTION) VALUES (4,'Papas');
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CH',1,2);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CH',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CH',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-SW',1,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-SW',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-SW',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CR',1,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CR',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CR',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CV',1,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CV',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CV',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CL',1,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CL',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-CL',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-SM',2,2);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-SM',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('H-SM',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('A-PPC',4,250);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-CH',1,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-ME',3,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-CQE',1,0);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('Q-CH',1,-1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-EM',2,1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-CQC',1,0);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('Q-EM',2,-1);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-CQE',2,0);
INSERT INTO MenuRecipe (CODE, IDRECIPE, QUANTITY) VALUES ('E-CQC',2,0);


insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.hostNameString','MailManager Servidor SMTP','','localhost','Dirección IP o nombre del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.portString','MailManager Puerto del Servidor SMTP','','25','Puerto del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpAuthUserName','MailManager Usuario Servidor SMTP','','','Usuario de autenticacion del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpAuthPassword','MailManager Contraseña Servidor SMTP','','','Contraseña de autenticacion del servidor SMTP para envío de mails.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpAuthRequired','MailManager Autenticacion SMTP','','false','Se requiere de autenticacion en servidor SMTP para envío de mails. Si el valor es true se valida el usuario y contraseña.');
insert into SystemProperty (propertyKey,name,oldValue,newValue,description) values ('MailManager.smtpTls','MailManager Usa TLS SMTP','','false','Se enviará comando STARTTLS al servidor SMTP para envío de mails.');

##UPDATES -- ARTICULOS SIN STOCK
UPDATE Product SET AVAILABLE = FALSE WHERE ID=7; ## Argenta
UPDATE Product SET AVAILABLE = FALSE WHERE ID=8; ##Oklahoma
UPDATE Extra SET AVAILABLE = FALSE WHERE ID=8; ##Cheddar papas
UPDATE Extra SET AVAILABLE = FALSE WHERE ID=9; ##Cheddar y bacon papas

##Sets businessSchedule
insert into BusinessSchedule (day, openTime, closeTime, available) values ('Jueves', '19:00:00', '01:00:00', true);
insert into BusinessSchedule (day, openTime, closeTime ,available) values ('Viernes', '19:00:00', '01:00:00', true);
insert into BusinessSchedule (day, openTime, closeTime,available) values ('Sabado', '19:00:00', '01:00:00', true);
insert into BusinessSchedule (day, openTime, closeTime,available) values ('Domingo', '19:00:00', '01:00:00', true);
/*
select ord.id, ord.delivery, ord.status, ord.comments, ord.createDate, ord.amount,
cli.id as clientId, cli.name, cli.lastName, cli.cellphone, cli.mail,
addr.id as addressId, addr.street,addr.doornumber, addr.zipcode, addr.floor, addr.door,
st.id as stateId, st.state,
odet.id as detailId,
prod.id as ProductId, prod.code, prod.name, prod.description,
ext.id as ExtraId, ext.code, ext.name, ext.price
from `Order` as ord
left join client as cli on ord.idclient=cli.id
left join address as addr on addr.idclient=cli.id
left join state as st on st.id=addr.idState
left join orderdetail as odet on odet.idOrder=ord.id
left join Product as prod on prod.id=odet.idProduct
left join Extraorderdetail as extd on extd.idorderdetail=odet.id
left join Extra as ext on ext.id=extd.idExtra

where ord.id=11; */


##VANBERGA INC. TODOS LOS DERECHOS RESERVADOS