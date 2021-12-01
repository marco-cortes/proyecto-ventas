package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Productos;
import fes.aragon.Ventas.services.impl.ProductosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosServiceImpl productosService;

    @GetMapping("")
    public String inicio(Model modelo) {
        modelo.addAttribute("productos", productosService.getProductos());
        return "todosproductos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Productos productos, Model modelo){
        modelo.addAttribute("producto", productos);
        return "formularios/formularioproductosdos";
    }

    @PostMapping("/guardar")
    public String guardar(Productos producto){
        productosService.guardarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{idProductos}")
    public String editar(@PathVariable(value="idProductos") int idProductos, Model model) {
        Productos producto = productosService.getProducto(idProductos);
        model.addAttribute("producto", producto);
        return "formularios/formularioproductosdos";
    }

    @PostMapping("/eliminar/{idProductos}")
    public String eliminar(@PathVariable(value="idProductos") int idProductos) {
        productosService.eliminarProducto(idProductos);
        return "redirect:/productos";
    }
}
