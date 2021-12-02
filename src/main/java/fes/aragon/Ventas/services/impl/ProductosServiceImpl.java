package fes.aragon.Ventas.services.impl;

import fes.aragon.Ventas.domain.Productos;
import fes.aragon.Ventas.repo.ProductoRepo;
import fes.aragon.Ventas.services.ProductosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductosServiceImpl implements ProductosService {

    @Autowired
    private final ProductoRepo pr;
    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardarProducto(Productos p) {
        pr.save(p);
    }

    @Override
    public void eliminarProducto(int idProductos) {
        //fr.delete(aux);
        StoredProcedureQuery proc = em.createStoredProcedureQuery("eliminarProducto");
        proc.registerStoredProcedureParameter("producto", Integer.class, ParameterMode.IN);
        proc.setParameter("producto", idProductos);
        proc.execute();
    }

    @Override
    public List<Productos> getProductos() {
        return pr.findAll();
    }

    @Override
    public Productos getProducto(int idProductos) {
        return pr.findById(idProductos).orElse(null);
    }
}
