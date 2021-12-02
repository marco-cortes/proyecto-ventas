package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Facturas;
import fes.aragon.Ventas.domain.FacturasProductos;
import fes.aragon.Ventas.domain.Productos;
import fes.aragon.Ventas.services.ClientesService;
import fes.aragon.Ventas.services.FacturasService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/facturas")
@AllArgsConstructor
@Slf4j
public class FacturasController {
    @Autowired
    private final FacturasService fs;
    @Autowired
    private final ClientesService cs;

    @GetMapping("/{idClientes}")
    public String getFacturas(@PathVariable(value="idClientes") int idClientes, Model model, Facturas factura){
        model.addAttribute("factura", factura);
        model.addAttribute("facturas", fs.getFacturas(idClientes));
        model.addAttribute("idClientes", idClientes);
        model.addAttribute("cliente", cs.getCliente(idClientes));
        return "tabla-facturas";
    }

    @RequestMapping("/{idClientes}/editar/{idFacturas}")
    @ResponseBody
    public Facturas editar(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas){
        Facturas facturas  = new Facturas();
        facturas.setIdFacturas(fs.getFactura(idFacturas).getIdFacturas());
        facturas.setReferenciaFacturas(fs.getFactura(idFacturas).getReferenciaFacturas());
        facturas.setFechaFacturas(fs.getFactura(idFacturas).getFechaFacturas());
        facturas.setIdClientes(cs.getCliente(idClientes));
        return facturas;
    }

    @PostMapping("/{idClientes}/nueva/guardar")
    public String guardar(@PathVariable(value="idClientes") int idClientes, Facturas factura){
        log.info("{}",factura.getIdFacturas());
        if(factura.getIdFacturas() > 0){
            fs.editarFactura(idClientes, factura.getIdFacturas(), factura);
        } else {
            fs.agregarFactura(idClientes, factura);
        }
        return "redirect:/facturas/"+idClientes;
    }

    @PostMapping("/{idClientes}/eliminar/{idFacturas}")
    public String eliminar(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas){
        fs.eliminarFactura(idClientes, idFacturas);
        return "redirect:/facturas/" + idClientes;
    }


    @GetMapping("/{idClientes}/productos/{idFacturas}")
    public String getProductos(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, Model model){
        List<Productos> aux = new ArrayList<>();
        for(Productos p : fs.getProductos()){
            aux.add(p);
        }
        aux.removeAll(fs.getProductosF(idFacturas));
        model.addAttribute("productos", fs.getProductos(idFacturas));
        model.addAttribute("idClientes", idClientes);
        model.addAttribute("idFacturas", idFacturas);
        model.addAttribute("cliente", cs.getCliente(idClientes));
        model.addAttribute("listaproductos", aux);
        return "tabla-facturas-productos";
    }
    /*

    @GetMapping("/{idClientes}/productos/{idFacturas}/nuevo")
    public String nuevoProducto(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, Model model){
        List<Productos> aux = new ArrayList<>();
        for(Productos p : fs.getProductos()){
            for(Productos pp : fs.getProductosF(idFacturas)){
                if(p.getIdProductos() == pp.getIdProductos()){
                    System.out.println(p.getNombreProductos());
                } else {
                    aux.add(p);
                }
            }
        }
        model.addAttribute("productos", aux);
        model.addAttribute("idClientes", idClientes);
        model.addAttribute("idFacturas", idFacturas);
        return "formularios/formularioproducto";
    }
    * */

    @RequestMapping("/{idClientes}/productos/{idFacturas}/editar/{idProductos}")
    @ResponseBody
    public FacturasProductos editarProducto(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, @PathVariable(value="idProductos") int idProductos){
        for(FacturasProductos fp : fs.getProductos(idFacturas)){
            if(fp.getProductos().getIdProductos() == idProductos)
                return new FacturasProductos(fp.getFacturasProductosPK(), fp.getCantidadFacturasProductos());
        }
        return null;
    }

    @PostMapping("/{idClientes}/productos/{idFacturas}/eliminar/{idProductos}")
    public String eliminarProducto(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, @PathVariable(value="idProductos") int idProductos) {
        fs.eliminarProducto(idFacturas, idProductos);
        return "redirect:/facturas/{idClientes}/productos/{idFacturas}";
    }

    @PostMapping("/{idClientes}/productos/{idFacturas}/guardar")
    public String guardar(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, int idProductos, int cantidad){
        fs.agregarProducto(idFacturas, idProductos, cantidad);
        return "redirect:/facturas/{idClientes}/productos/{idFacturas}";
    }

}
