SET NAMES 'utf8';
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE IF NOT EXISTS ventas DEFAULT CHARACTER SET utf8;
USE ventas;
CREATE TABLE clientes(
id_clientes					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_clientes				VARCHAR(25) NOT NULL,
apellido_clientes			VARCHAR(25) NOT NULL
)DEFAULT CHARACTER SET utf8;

CREATE TABLE facturas(
id_facturas					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
id_clientes					INTEGER NOT NULL,
referencia_facturas		    VARCHAR(40) NOT NULL,
fecha_facturas				DATE NOT NULL,
FOREIGN KEY(id_clientes) REFERENCES clientes(id_clientes)
)DEFAULT CHARACTER SET utf8;

CREATE TABLE productos(
id_productos					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_productos				VARCHAR(80) NOT NULL,
precio_productos				DOUBLE NOT NULL
)DEFAULT CHARACTER SET utf8;

CREATE TABLE facturas_productos(
id_facturas					INTEGER NOT NULL,
id_productos					INTEGER NOT NULL,
cantidad_facturas_productos	DOUBLE NOT NULL,
PRIMARY KEY(id_facturas,id_productos),
FOREIGN KEY(id_facturas) REFERENCES facturas(id_facturas),
FOREIGN KEY(id_productos) REFERENCES productos(id_productos)
)DEFAULT CHARACTER SET utf8;

drop table if exists usuario;

CREATE TABLE usuario(
	id_usuario		INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre			VARCHAR(80),
	email			VARCHAR(250) UNIQUE,
	password		VARCHAR(250),
    CONSTRAINT notacion_no_valida_correo CHECK(email LIKE '%@%.%')
);

INSERT INTO usuario(nombre,email,password) values('Marco Cortes', 'marco@correo.com', '$2a$10$5VIBnUXuRN5X.GG9.Mcy7.ODs8Bs.DgMBM5G6ZrYokfFJ3Acarb4a');
select * from usuario;

DELIMITER $$
create trigger clientes_mayus before insert on clientes for each row
begin
  set new.nombre_clientes=upper(new.nombre_clientes);
  set new.apellido_clientes=upper(new.apellido_clientes);
end $$
DELIMITER ;

DELIMITER $$
create trigger clientes_mayus_update before update on clientes for each row
begin
  set new.nombre_clientes=upper(new.nombre_clientes);
  set new.apellido_clientes=upper(new.apellido_clientes);
end $$
DELIMITER ;

DELIMITER $$
create trigger productos_mayus before insert on productos for each row
begin
  set new.nombre_productos=upper(new.nombre_productos);
end $$
DELIMITER ;

DELIMITER $$
create trigger productos_mayus_update before update on productos for each row
begin
  set new.nombre_productos=upper(new.nombre_productos);
end $$
DELIMITER ;

DROP PROCEDURE IF EXISTS eliminarFactura;
DELIMITER $$
CREATE PROCEDURE eliminarFactura (factura integer)
BEGIN
	DELETE FROM facturas_productos WHERE id_facturas = factura;
    DELETE FROM facturas WHERE id_facturas = factura;
END $$
DELIMITER ;

use ventas;

DROP PROCEDURE IF EXISTS eliminarProducto;
DELIMITER $$
CREATE PROCEDURE eliminarProducto (producto integer)
BEGIN
	DELETE FROM facturas_productos WHERE id_productos = producto;
    DELETE FROM productos WHERE id_productos = producto;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS listaClientes;
DELIMITER $$
CREATE PROCEDURE listaClientes()
BEGIN
	SELECT * FROM clientes;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarClientes(in nombre varchar(25),in apellido varchar(25))
BEGIN
	insert into clientes(nombre_clientes,apellido_clientes) values(nombre,apellido);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE eliminarClientes(in id int)
BEGIN
	delete from clientes where id_clientes=id;
END $$

DELIMITER $$
CREATE  PROCEDURE modificarClientes(in id int,in nombre varchar(25),in apellido varchar(25))
BEGIN
	update clientes set nombre_clientes=nombre,apellido_clientes=apellido where id_clientes=id;
END $$


DROP PROCEDURE IF EXISTS listaProductos;
DELIMITER $$
CREATE PROCEDURE listaProductos ()
BEGIN
	SELECT * FROM productos;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE insertarProductos(in nombre varchar(25),in precio double)
BEGIN
	insert into productos(nombre_productos, precio_productos) values(nombre, precio);
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE eliminarProductos(in id int)
BEGIN
	delete from productos where id_productos=id;
END $$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE actualizarProductos(in id int,in nombre varchar(25),in precio double)
BEGIN
	update productos set nombre_productos=nombre,precio_productos=precio where id_productos=id;
END $$
DELIMITER ;






INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente1','Apellido1');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente2','Apellido2');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente3','Apellido3');

INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(1,'FAC1231',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(2,'FAC1131',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(3,'FAC1331',NOW());

INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto1',10.23);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto2',1.12);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto3',23.30);

INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,1,120);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,2,20);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,2,10);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,1,70);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,3,7);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(3,1,17);

select * from clientes;
select * from facturas;
select * from productos;
select * from facturas_productos;