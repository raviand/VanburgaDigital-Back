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
quantity INT NOT NULL,
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
INSERT INTO category(name, description) values ('Burgas', 'Las mejores hamburguesas para que disfrutes con la concha de tu hermana!');
INSERT INTO category(name, description) values ('Bebidas', 'Pedite una birra para disfrutar con tu Vanburga');
INSERT INTO state(state) values ('El Talar de Pacheco');
INSERT INTO state(state) values ('Altos del Talar');
INSERT INTO state(state) values ('El Zorzal');
INSERT INTO client(name, lastName, cellphone, mail) values ('Franco','Hildt','1123977072', 'franhildt@gmail.com');
INSERT INTO client(name, lastName, cellphone, mail) values ('Nico','Rohland','1133886456', 'nrohland@gmail.com');
INSERT INTO address(idclient, idstate, street, doorNumber, zipcode) values ('1','1','Los Alamos','895', '1667');
INSERT INTO address(idclient, idstate, street, doorNumber, zipcode) values ('2','3','Las Amapolas','123' , '1667');
INSERT INTO product(name, idcategory, price, description) values ('Vanburga Bacon XL','1','300','Doble carne, doble cheddar y mucho bacon');
INSERT INTO product(name, idcategory, price, description) values ('Cerveza Andes IPA','2','80','IPA rubia');
INSERT INTO extra(name, price) values ('Extra cheddar', '40');
INSERT INTO extra(name, price) values ('Extra bacon', '40');
INSERT INTO productByExtra(idExtra, idProduct) values ('1', '1');
INSERT INTO productByExtra(idExtra, idProduct) values ('2', '1');
INSERT INTO `Order`(idclient, comments, amount) values ('1','Haganme la hamburguesa con amor','420');
INSERT INTO OrderDetail(idproduct,idorder,quantity) values ('1','1','1');
INSERT INTO OrderDetail(idproduct,idorder,quantity) values ('2','1','1');
INSERT INTO ExtraOrderDetail(idOrderDetail, idExtra) values ('1', '1');



##VANBERGA INC. TODOS LOS DERECHOS RESERVADOS