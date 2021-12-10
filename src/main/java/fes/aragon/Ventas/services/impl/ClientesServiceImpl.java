package fes.aragon.Ventas.services.impl;

import fes.aragon.Ventas.domain.Clientes;
import fes.aragon.Ventas.repo.ClientesRepo;
import fes.aragon.Ventas.services.ClientesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientesServiceImpl implements ClientesService {

    @Autowired
    private final ClientesRepo cr;

    @Override
    public List<Clientes> listaClientes() {
        return (List<Clientes>) cr.findAll();
    }

    @Override
    public void guardar(Clientes cliente) {
        if(!cliente.getNombreClientes().isEmpty() && !cliente.getApellidoClientes().isEmpty())
            cr.save(cliente);
    }

    @Override
    public void eliminar(int idClientes) {
        cr.delete(getCliente(idClientes));
    }

    @Override
    public Clientes getCliente(int idClientes) {
        return cr.findById(idClientes).orElse(null);
    }
}
