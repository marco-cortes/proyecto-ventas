package fes.aragon.Ventas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="producto.todos",query="SELECT u FROM Productos u"),
	@NamedQuery(name="producto.datos",query="SELECT u FROM Productos u WHERE u.idProductos=:id")
})
public class Productos implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_productos")
	private int idProductos;
	
	@Column(name="nombre_productos")
	private String nombreProductos;
	
	@Column(name="precio_productos")
	private String precioProductos;

	@OneToMany(mappedBy = "productos", fetch=FetchType.EAGER)
	private List<FacturasProductos> listaFacturas;
}
