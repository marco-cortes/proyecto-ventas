package fes.aragon.Ventas.services.impl;

import fes.aragon.Ventas.domain.*;
import fes.aragon.Ventas.repo.FacturasProductosRepo;
import fes.aragon.Ventas.repo.FacturasRepo;
import fes.aragon.Ventas.repo.ProductoRepo;
import fes.aragon.Ventas.services.ClientesService;
import fes.aragon.Ventas.services.FacturasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FacturasServiceImpl implements FacturasService {

    @Autowired
    private final ClientesService cs;

    @Autowired
    private final FacturasRepo fr;

    @Autowired
    private final ProductoRepo pr;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private final FacturasProductosRepo fpr;

    @Override
    public List<Facturas> getFacturas(int id) {
        return ((Clientes) em.createNamedQuery("cliente.datos").setParameter("id", id).getSingleResult()).getListaFacturas();
    }

    @Override
    public Facturas getFactura(int idFacturas) {
        return fr.getById(idFacturas);
    }

    @Override
    public List<Productos> getProductos(int idFacturas) {
        List<Productos> aux = new ArrayList<>();
        for(FacturasProductos p : getFactura(idFacturas).getListaProductos()){
            aux.add(p.getProductos());
        }
        return aux;
    }

    @Override
    public void agregarFactura(int idClientes, Facturas f) {
        Clientes c = cs.getCliente(idClientes);
        f.setFechaFacturas(LocalDate.now().toString());
        c.agregarFactura(f);
        f.setIdClientes(c);
        cs.guardar(c);
    }

    @Override
    public void editarFactura(int idClientes, int idFactura, Facturas facturas) {
        Facturas aux = getFactura(idFactura);
        Clientes c = cs.getCliente(idClientes);
        aux.setFechaFacturas(LocalDate.now().toString());
        aux.setReferenciaFacturas(facturas.getReferenciaFacturas());
        cs.guardar(c);
    }

    @Override
    public void eliminarFactura(int idClientes, int idFacturas) {
        Clientes c = cs.getCliente(idClientes);
        Facturas aux = getFactura(idFacturas);
        List<FacturasProductos> productosEliminar = new ArrayList<>();

        for(FacturasProductos f : aux.getListaProductos()){
            productosEliminar.add(fpr.findById(f.getFacturasProductosPK()).orElse(null));
        }

        aux.getListaProductos().clear();
        for(FacturasProductos f: productosEliminar){
            fpr.delete(f);
        }

        c.eliminarFactura(aux);
        fr.delete(aux);

    }

    @Override
    public List<Productos> getProductos() {
        return pr.findAll();
    }

    @Override
    public void agregarProducto(int idFacturas, int idProducto){
        Facturas aux = getFactura(idFacturas);
        FacturasProductosPK fpk = new FacturasProductosPK(idFacturas, idProducto);
        FacturasProductos fp = new FacturasProductos(fpk, 150);
        fp.setFacturas(aux);
        aux.getListaProductos().add(fp);
        fr.save(aux);
    }

    @Override
    public void eliminarProducto(int idFacturas, int idProducto) {
        Facturas aux = getFactura(idFacturas);
        FacturasProductos asd = fpr.findById(new FacturasProductosPK(idFacturas, idProducto)).orElse(null);
        if(asd != null){
            aux.getListaProductos().remove(asd);
            fpr.delete(asd);
        }
    }
}
