package org.fiap.app.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private CancelarBaixaEstoqueUseCase cancelarBaixaEstoqueUseCase;

    @InjectMocks
    private Controller pedidoController;

    private PedidoDTO pedidoDTO;
    private PagamentoDTO pagamentoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setStatus(StatusPagamentoEnum.ABERTO);

        pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setPedidoId(1L);
        pagamentoDTO.setStatusPagamento(StatusPagamentoEnum.FECHADO_SEM_CREDITO);
    }

    @Test
    void testBuscarPedido() throws Exception {
        when(pedidoService.findById(1L)).thenReturn(pedidoDTO);

        mockMvc.perform(get("/pedido/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("ABERTO")));
    }

    @Test
    void testRollBackEstoque() throws Exception {
        when(cancelarBaixaEstoqueUseCase.execute(any(PagamentoDTO.class))).thenReturn(pagamentoDTO);

        mockMvc.perform(post("/pedido/rollback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pagamentoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId", is(1)));
    }
}
