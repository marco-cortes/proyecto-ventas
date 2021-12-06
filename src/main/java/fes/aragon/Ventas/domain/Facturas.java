package fes.aragon.Ventas.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="factura.todos",query="SELECT u FROM Facturas u")
	//@NamedQuery(name="factura.datos",query="SELECT u FROM Facturas u WHERE u.idClientes=:id")
	//@NamedQuery(name="factura.productos", query = "SELECT u From FacturasProductos u WHERE u.idFacturas=:id")
})
public class Facturas implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_facturas")
	private int idFacturas;

	@JoinColumn(name="id_clientes",referencedColumnName = "id_clientes")
	@ManyToOne(fetch=FetchType.EAGER)
	private Clientes idClientes;

	@NotEmpty
	@Size(min = 1)
	@Pattern(regexp = "[A-Za-z0-9]+")
	@Column(name="referencia_facturas")
	private String referenciaFacturas;

	@NotEmpty
	@Column(name="fecha_facturas")
	private String fechaFacturas;

	@OneToMany(mappedBy = "facturas", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<FacturasProductos> listaProductos;
}
