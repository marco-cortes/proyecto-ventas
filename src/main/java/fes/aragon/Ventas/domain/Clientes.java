package fes.aragon.Ventas.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="cliente.todos",query="SELECT u FROM Clientes u"),
	@NamedQuery(name="cliente.datos",query="SELECT u FROM Clientes u WHERE u.idClientes=:id"),
	@NamedQuery(name="cliente.facturas", query = "SELECT u FROM Facturas u WHERE u.idClientes=:id")
})
public class Clientes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_clientes")
	private int idClientes;

	@NotEmpty
	@Size(min = 1)
	@Pattern(regexp = "[A-Za-z]+")
	@Column(name="nombre_clientes")
	private String nombreClientes;

	@NotEmpty
	@Size(min = 1)
	@Pattern(regexp = "[A-Za-z]+")
	@Column(name="apellido_clientes")
	private String apellidoClientes;

	@JsonBackReference
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
