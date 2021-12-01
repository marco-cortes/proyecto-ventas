package fes.aragon.Ventas.repo;

import fes.aragon.Ventas.domain.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepo extends JpaRepository<Clientes, Integer> {
}
