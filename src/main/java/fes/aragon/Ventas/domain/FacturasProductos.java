package fes.aragon.Ventas.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="facturas_productos")
@Data
@NoArgsConstructor
public class FacturasProductos implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected FacturasProductosPK facturasProductosPK;
	
	@Column(name="cantidad_facturas_productos")
	private double cantidadFacturasProductos;
	
	@JoinColumn(name="id_facturas", referencedColumnName = "id_facturas", insertable=false, updatable = false)
	@ManyToOne(fetch=FetchType.EAGER, cascade= CascadeType.ALL)
	private Facturas facturas;
	
	@JoinColumn(name="id_productos", referencedColumnName = "id_productos",insertable=false, updatable = false)
	@ManyToOne(fetch=FetchType.EAGER)
	private Productos productos;


	public FacturasProductos(FacturasProductosPK facturasProductosPK, double cantidadFacturasProductos) {
		this.facturasProductosPK = facturasProductosPK;
		this.cantidadFacturasProductos = cantidadFacturasProductos;
	}

	public FacturasProductosPK getFacturasProductosPK() {
		return facturasProductosPK;
	}


	public Facturas getFacturas() {
		return facturas;
	}

	public void setFacturas(Facturas facturas) {
		this.facturas = facturas;
	}

	public Productos getProductos() {
		return productos;
	}

	public void setProductos(Productos productos) {
		this.productos = productos;
	}


	@Override
	public int hashCode() {
		return Objects.hash(cantidadFacturasProductos, facturas, facturasProductosPK);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturasProductos other = (FacturasProductos) obj;
		return Double.doubleToLongBits(cantidadFacturasProductos) == Double
				.doubleToLongBits(other.cantidadFacturasProductos) && Objects.equals(facturas, other.facturas)
				&& Objects.equals(facturasProductosPK, other.facturasProductosPK);
	}
}
