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
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import javax.persistence.*;
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
        List<Facturas> f = ((List<Facturas>) em.createNamedQuery("cliente.facturas").setParameter("id", cs.getCliente(id)).getResultList());
        return f;
    }

    @Override
    public Facturas getFactura(int idFacturas) {
        return fr.findById(idFacturas).orElse(null);
    }

    @Override
    public List<Productos> getProductosF(int idFacturas) {
        if (idFacturas <= 0)
            return null;
        List<Productos> p = new ArrayList<>();
        Facturas f = getFactura(idFacturas);
        if(f == null)
            return null;
        List<FacturasProductos> aux = f.getListaProductos();
        if(aux == null)
            return null;

        for(FacturasProductos pp : aux){
            p.add(pp.getProductos());
        }
        return p;
    }

    @Override
    public List<FacturasProductos> getProductos(int idFacturas) {
        if(idFacturas > 0) {
            Facturas f = getFactura(idFacturas);
            List<FacturasProductos> fp;
            if(f == null)
                return null;
            fp = f.getListaProductos();
            if(fp == null)
                return null;
            return fp;
        }
        return null;
    }

    @Override
    public void agregarFactura(int idClientes, Facturas f) {
        if(idClientes > 0) {
            Clientes c = cs.getCliente(idClientes);
            if(c != null && f != null && f.getIdFacturas() == 0 && !f.getFechaFacturas().isEmpty() && !f.getFechaFacturas().isEmpty()){
                c.agregarFactura(f);
                f.setIdClientes(c);
                cs.guardar(c);
            }
        }
    }

    @Override
    public void editarFactura(int idClientes, int idFactura, Facturas facturas) {
        if(idClientes > 0 && idFactura > 0 && facturas != null && facturas.getIdFacturas() > 0
                && !facturas.getReferenciaFacturas().isEmpty() && !facturas.getFechaFacturas().isEmpty()) {
            Facturas aux = getFactura(idFactura);
            Clientes c = cs.getCliente(idClientes);
            if(aux != null && c  != null){
                if(facturas.getFechaFacturas() == null || facturas.getFechaFacturas().isEmpty())
                    aux.setFechaFacturas(LocalDate.now().toString());
                else
                    aux.setFechaFacturas(facturas.getFechaFacturas());
                aux.setReferenciaFacturas(facturas.getReferenciaFacturas());
                cs.guardar(c);
            }
        }
    }

    @Override
    public void eliminarFactura(int idClientes, int idFacturas) {
        if(idClientes > 0 && idFacturas > 0) {
            Clientes c = cs.getCliente(idClientes);
            Facturas aux = getFactura(idFacturas);
            if(c != null && aux != null){
                c.eliminarFactura(aux);
                /*
                PROCEDIMIENTO ALMACENADO
                StoredProcedureQuery proc = em.createStoredProcedureQuery("eliminarFactura");
                proc.registerStoredProcedureParameter("factura", Integer.class, ParameterMode.IN);
                proc.setParameter("factura", idFacturas);
                proc.execute();
                */
                Query query = em.createNativeQuery("DELETE FROM facturas_productos WHERE id_facturas="+idFacturas);
                query.executeUpdate();
                query = em.createNativeQuery("DELETE FROM facturas WHERE id_facturas="+idFacturas);
                query.executeUpdate();
            }
        }
    }

    @Override
    public List<Productos> getProductos() {
        List<Productos> lista = pr.findAll();
        if(lista == null)
            lista = null;
        return lista;
    }

    @Override
    public void agregarProducto(int idFacturas, int idProducto, int cantidad){
        if(cantidad > 0 && idFacturas > 0 && idProducto > 0){
            Facturas aux = getFactura(idFacturas);
            if(aux != null) {
                FacturasProductosPK fpk = new FacturasProductosPK(idFacturas, idProducto);
                FacturasProductos verificar = fpr.findById(fpk).orElse(null);
                if(verificar == null){
                    FacturasProductos fp = new FacturasProductos(fpk, cantidad);
                    fp.setFacturas(aux);
                    aux.getListaProductos().add(fp);
                    try{
                        fr.save(aux);
                    } catch (Exception e){
                        System.out.println("error: " + e.getMessage());
                    }
                } else {
                    verificar.setCantidadFacturasProductos(cantidad);
                    fpr.save(verificar);
                }
            }
        }
    }

    @Override
    public void eliminarProducto(int idFacturas, int idProducto) {
        if(idFacturas > 0 && idProducto > 0){
            Facturas aux = getFactura(idFacturas);
            if(aux != null) {
                FacturasProductos asd = fpr.findById(new FacturasProductosPK(idFacturas, idProducto)).orElse(null);
                if(asd != null){
                    aux.getListaProductos().remove(asd);
                    fpr.delete(asd);
                }
            }
        }
    }
}
