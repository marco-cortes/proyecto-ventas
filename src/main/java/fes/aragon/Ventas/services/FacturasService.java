package fes.aragon.Ventas.services;

import fes.aragon.Ventas.domain.Facturas;
import fes.aragon.Ventas.domain.FacturasProductos;
import fes.aragon.Ventas.domain.Productos;

import java.util.List;

public interface FacturasService {

    List<Facturas> getFacturas(int id);
    Facturas getFactura(int idFacturas);
    List<Productos> getProductosF(int idFacturas);
    List<FacturasProductos> getProductos(int idFacturas);
    void agregarFactura(int idClientes, Facturas facturas);
    void editarFactura(int idClientes, int idFactura, Facturas facturas);
    void eliminarFactura(int idClientes, int idFactura);

    List<Productos> getProductos();
    void agregarProducto(int idFactura, int idProducto, int cantidad);
    void eliminarProducto(int idFactura, int idProducto);
}
