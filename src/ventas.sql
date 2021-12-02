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

DELIMITER $$
create trigger clientes_mayus before insert on clientes for each row
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

DROP PROCEDURE IF EXISTS eliminarFactura;
DELIMITER $$
CREATE PROCEDURE eliminarFactura (factura integer)
BEGIN
	DELETE FROM facturas_productos WHERE id_facturas = factura;
    DELETE FROM facturas WHERE id_facturas = factura;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS eliminarProducto;
DELIMITER $$
CREATE PROCEDURE eliminarProducto (producto integer)
BEGIN
	DELETE FROM facturas_productos WHERE id_producto = producto;
    DELETE FROM productos WHERE id_producto = producto;
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