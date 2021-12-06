package fes.aragon.Ventas.repo;

import fes.aragon.Ventas.domain.FacturasProductos;
import fes.aragon.Ventas.domain.FacturasProductosPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturasProductosRepo extends JpaRepository<FacturasProductos, FacturasProductosPK> {


}
