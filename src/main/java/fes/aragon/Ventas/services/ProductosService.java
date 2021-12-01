package fes.aragon.Ventas.services;

import fes.aragon.Ventas.domain.Productos;

import java.util.List;

public interface ProductosService {
    void guardarProducto(Productos p);
    void eliminarProducto(int idProductos);
    List<Productos> getProductos();
    Productos getProducto(int idProductos);
}
