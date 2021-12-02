package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Productos;
import fes.aragon.Ventas.services.impl.ProductosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosServiceImpl productosService;

    @GetMapping("")
    public String inicio(Model modelo, Productos producto) {
        modelo.addAttribute("productos", productosService.getProductos());
        modelo.addAttribute("producto", producto);
        return "tabla-productos";
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

    @RequestMapping("/editar/{idProductos}")
    @ResponseBody
    public Productos editar(@PathVariable(value="idProductos") int idProductos, Model model) {
        Productos p = new Productos();
        p.setIdProductos(productosService.getProducto(idProductos).getIdProductos());
        p.setNombreProductos(productosService.getProducto(idProductos).getNombreProductos());
        p.setPrecioProductos(productosService.getProducto(idProductos).getPrecioProductos());
        return p;
    }

    @PostMapping("/eliminar/{idProductos}")
    public String eliminar(@PathVariable(value="idProductos") int idProductos) {
        productosService.eliminarProducto(idProductos);
        return "redirect:/productos";
    }
}
