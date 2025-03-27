package org.fiap.app.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.util.HttpStatusCodes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pedido")
@Slf4j
public class Controller {

    private final PedidoService pedidoService;

    public Controller(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Buscar Pedido",
            description = "Buscar Pedido.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Buscar Pedido.")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPedido(@PathVariable(name = "id") Long id) {
        log.info("requisição para efetuar pagamento foi efetuada");
        return ResponseEntity.ok(pedidoService.findById(id));
    }

}