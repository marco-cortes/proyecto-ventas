package fes.aragon.Ventas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="cliente.todos",query="SELECT u FROM Clientes u"),
	@NamedQuery(name="cliente.datos",query="SELECT u FROM Clientes u WHERE u.idClientes=:id"),
	@NamedQuery(name="cliente.facturas", query = "SELECT u FROM Facturas u WHERE u.idFacturas=:id")
})
public class Clientes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_clientes")
	private int idClientes;
	
	@Column(name="nombre_clientes")
	private String nombreClientes;
	
	@Column(name="apellido_clientes")
	private String apellidoClientes;

	@OneToMany(mappedBy = "idClientes",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Facturas> listaFacturas;

	public void agregarFactura(Facturas f){
		if(listaFacturas == null)
			listaFacturas = new ArrayList<>();
		listaFacturas.add(f);
	}

	public void eliminarFactura(Facturas f){
		listaFacturas.remove(f);
	}
}
