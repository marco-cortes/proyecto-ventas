package fes.aragon.Ventas.services;

import fes.aragon.Ventas.domain.Clientes;

import java.util.List;

public interface ClientesService {
    List<Clientes> listaClientes();
    void guardar(Clientes cliente);
    void eliminar(int idClientes);
    Clientes getCliente(int idClientes);
}
