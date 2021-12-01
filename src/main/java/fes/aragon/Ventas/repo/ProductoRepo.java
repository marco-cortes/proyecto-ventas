package fes.aragon.Ventas.repo;

import fes.aragon.Ventas.domain.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepo extends JpaRepository<Productos, Integer> {
}
