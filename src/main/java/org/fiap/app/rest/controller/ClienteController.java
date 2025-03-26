package org.fiap.app.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.fiap.app.rest.request.ClienteRequest;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.util.HttpStatusCodes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pedidos")
@Slf4j
public class ClienteController {

    private final ClienteGatewayService service;

    public ClienteController(ClienteGatewayService service) {
        this.service = service;
    }


    @Operation(summary = "Buscar Cliente",
            description = "Buscar o Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Busca realizada com sucesso.")
    @GetMapping
    public ResponseEntity<ClienteDTO> buscar(
            @RequestParam(name = "cpf", required = true) String cpf
    ) {
        log.info("requisição para buscar cliente foi efetuada");
        return ResponseEntity.ok(service.findByCpf(cpf));
    }

    @Operation(summary = "Cadastrar Cliente",
            description = "Criação do Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cadastro realizado com sucesso.")
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastro(@Valid @RequestBody ClienteRequest request) {
        log.info("requisição para cadastrar cliente foi efetuada");
        return ResponseEntity.ok(service.save(request));
    }

    @Operation(summary = "Atualizar Cliente",
            description = "Atualização do Cliente.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Atualização realizada com sucesso.")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable(name = "id") Long id,
                                                @Valid @RequestBody ClienteRequest request) {
        log.info("requisição para atualizar cliente foi efetuada");
        return ResponseEntity.ok(service.update(id, request));
    }


}