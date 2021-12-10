package fes.aragon.Ventas.repo;

import fes.aragon.Ventas.domain.Clientes;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientesRepo extends PagingAndSortingRepository<Clientes, Integer> {
}
