package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Facturas;
import fes.aragon.Ventas.services.ClientesService;
import fes.aragon.Ventas.services.FacturasService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getFacturas(@PathVariable(value="idClientes") int idClientes, Model model){
        model.addAttribute("facturas", fs.getFacturas(idClientes));
        model.addAttribute("idClientes", idClientes);
        return "facturas";
    }

    @GetMapping("/{idClientes}/nueva")
    public String nueva(@PathVariable(value="idClientes") int idClientes, Facturas factura, Model model){
        model.addAttribute("factura", factura);
        model.addAttribute("idClientes", idClientes);
        return "formularios/formulariofactura";
    }

    @GetMapping("/{idClientes}/editar/{idFacturas}")
    public String editar(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, Facturas factura, Model model){
        factura = fs.getFactura(idFacturas);
        model.addAttribute("factura", factura);
        model.addAttribute("idClientes", idClientes);
        model.addAttribute("idFacturas", idFacturas);
        return "formularios/formulariofactura";
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
        model.addAttribute("productos", fs.getProductos(idFacturas));
        model.addAttribute("idClientes", idClientes);
        model.addAttribute("idFacturas", idFacturas);
        return "productos";
    }

    @GetMapping("/{idClientes}/productos/{idFacturas}/nuevo")
    public String nuevoProducto(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, Model model){
        model.addAttribute("productos", fs.getProductos());
        model.addAttribute("idClientes", idClientes);
        model.addAttribute("idFacturas", idFacturas);
        return "formularios/formularioproducto";
    }

    @GetMapping("/{idClientes}/productos/{idFacturas}/editar/{idProductos}")
    public String editarProducto(){
        return "formularios/formularioproducto";
    }

    @PostMapping("/{idClientes}/productos/{idFacturas}/eliminar/{idProductos}")
    public String eliminarProducto(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, @PathVariable(value="idProductos") int idProductos) {
        fs.eliminarProducto(idFacturas, idProductos);
        return "redirect:/facturas/{idClientes}/productos/{idFacturas}";
    }

    @PostMapping("/{idClientes}/productos/{idFacturas}/guardar")
    public String guardar(@PathVariable(value="idClientes") int idClientes, @PathVariable(value="idFacturas") int idFacturas, int idProductos){
        fs.agregarProducto(idFacturas, idProductos);
        return "redirect:/facturas/{idClientes}/productos/{idFacturas}";
    }

}
