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
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
idCategory INT NOT NULL,
price DECIMAL(13,2) NOT NULL,
description VARCHAR(2000) NULL,
available BOOLEAN DEFAULT TRUE,
FOREIGN KEY(idCategory)
		REFERENCES Category(id)
);

##CAMBIE EL NOMBRE DE COLUMNA EXTRA POR NOMBRE, ES MAS REPRESENTATIVO.
CREATE TABLE IF NOT EXISTS Extra(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(150) NOT NULL,
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
comments VARCHAR(2000) NULL,
createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
amount DECIMAL(13,2) NOT NULL,
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
INSERT INTO product(name, idcategory, price, description) values ('Cerveza Andes IPA','2','80','IPA rubia');
INSERT INTO product(name, idcategory, price, description) values ('Cuarto Vanburga','1','400','Doble carne, Doble cheddar, Cebolla cortada, Ketchup Heinz, Mostaza Heinz');
INSERT INTO product(name, idcategory, price, description) values ('Clásica','1','400','Doble carne, Doble cheddar, Cebolla cruda, Tomate, Lechuga, Pickles de pepino, Salsa Vanburga');
INSERT INTO product(name, idcategory, price, description) values ('Cheeseburger','1','400','Triple carne, Sextuple cheddar, Bacon crocante');
INSERT INTO product(name, idcategory, price, description) values ('Argenta','1','400','Doble carne, Doble provoleta, Chimichurri de la Abuela');
INSERT INTO product(name, idcategory, price, description) values ('Crispy Onion','1','400','Doble carne, Doble cheddar, Cebolla crocante, Bacon crocante, Salsa Vanburga');
INSERT INTO product(name, idcategory, price, description) values ('Oklahoma','1','400','Doble carne Smasheada con cebolla, Doble cheddar');
INSERT INTO product(name, idcategory, price, description) values ('Sweet Onion','1','400','Doble carne, Doble cheddar, Cebolla caramelizada, , Bacon crocante');
INSERT INTO product(name, idcategory, price, description) values ('Smoke Shack','1','400','Doble carne, Doble Emmenthal, Bacon crocante, Morrones bañados con tabasco, Salsita Shake Shack');
INSERT INTO extra(name, price) values ('Extra Cheddar','50');
INSERT INTO extra(name, price) values ('Extra Bacon','50');
INSERT INTO extra(name, price) values ('Extra Medallón','50');
INSERT INTO extra(name, price) values ('Extra papas con Cheddar y Bacon','50');
INSERT INTO productByExtra(idExtra, idProduct) values ('1', '1');
INSERT INTO productByExtra(idExtra, idProduct) values ('2', '1');
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





##VANBERGA INC. TODOS LOS DERECHOS RESERVADOS