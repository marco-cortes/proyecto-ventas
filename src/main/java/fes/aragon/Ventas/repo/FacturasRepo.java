package fes.aragon.Ventas.repo;

import fes.aragon.Ventas.domain.Facturas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturasRepo extends JpaRepository<Facturas, Integer> {
}
