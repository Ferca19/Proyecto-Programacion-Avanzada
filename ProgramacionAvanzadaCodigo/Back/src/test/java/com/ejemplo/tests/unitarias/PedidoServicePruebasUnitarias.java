package com.ejemplo.tests.unitarias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.EjemploApplication;
import programacion.ejemplo.EjemploApplicationPruebas;
import programacion.ejemplo.model.Estado;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.service.*;


@SpringBootTest(classes = EjemploApplicationPruebas.class)
@ExtendWith(MockitoExtension.class)
public class PedidoServicePruebasUnitarias {

    @Autowired
    private PedidoService pedidoService;



    private List<DetallePedidoDTO> detallesPedido;
    private Pedido pedidoCreado;
    private Usuario usuario;

    @Given("un usuario con ID {int} existe")
    public void un_usuario_con_ID_existe(Integer id) {
        // Simular el objeto Usuario
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Nombre Usuario");

    }

    @Given("los detalles del pedido son:")
    public void los_detalles_del_pedido_son(DataTable dataTable) {
        if (detallesPedido == null) {
            detallesPedido = new ArrayList<>();
        }

        // Convertir la tabla en una lista de mapas para procesar cada fila
        List<Map<String, String>> detalles = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> detalle : detalles) {
            Integer productoId = Integer.parseInt(detalle.get("productoId"));
            Integer cantidad = Integer.parseInt(detalle.get("cantidad"));

            DetallePedidoDTO detallePedidoDTO = new DetallePedidoDTO();
            detallePedidoDTO.setProductoId(productoId);
            detallePedidoDTO.setCantidad(cantidad);
            detallesPedido.add(detallePedidoDTO);
        }
    }

    @When("se crea un pedido con el usuario y los detalles del pedido")
    public void se_crea_un_pedido_con_el_usuario_y_los_detalles_del_pedido() {
        // Simula el estado inicial como un objeto de tipo Estado
        Estado estadoPendiente = new Estado();
        estadoPendiente.setNombre("Pendiente");

        // Llamar al método que estamos probando
        pedidoCreado = pedidoService.crearPedido(usuario, detallesPedido);
    }

    @Then("el pedido debe ser creado con estado inicial")
    public void el_pedido_debe_ser_creado_con_estado_inicial() {
        // Verificar que el pedido tiene el estado inicial asignado
        assertEquals("Pendiente", pedidoCreado.getEstado().getNombre());
    }

    @Then("el importe total del pedido debe estar calculado")
    public void el_importe_total_del_pedido_debe_estar_calculado() {
        // Verificar que el importe total fue calculado y asignado
        assertEquals(100000.0, pedidoCreado.getImporteTotal());
    }
}
