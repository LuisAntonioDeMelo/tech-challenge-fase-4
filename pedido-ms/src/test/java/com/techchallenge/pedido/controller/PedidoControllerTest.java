//package com.techchallenge.pedido.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.techchallenge.pedido.infrastructure.controllers.PedidoController;
//import com.techchallenge.pedido.util.TestDataBuilder;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(PedidoController.class)
//class PedidoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private PedidoServiceImpl pedidoService;
//
//    @MockBean
//    private PedidoMapper pedidoMapper;
//
//    @Test
//    @DisplayName("Should create a new order")
//    void shouldCreateOrder() throws Exception {
//        // Given
//        PedidoDTO pedidoDTO = TestDataBuilder.createOrderDTO();
//        Pedido pedido = TestDataBuilder.createSampleOrder();
//        PedidoResponse expectedResponse = TestDataBuilder.createOrderResponse();
//
//        when(pedidoMapper.toEntity(any(PedidoDTO.class))).thenReturn(pedido);
//        when(pedidoService.create(any(Pedido.class))).thenReturn(pedido);
//        when(pedidoMapper.toResponse(any(Pedido.class))).thenReturn(expectedResponse);
//
//        // When & Then
//        mockMvc.perform(post("/api/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(pedidoDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.customerName", is("Test Customer")))
//                .andExpect(jsonPath("$.status", is("PENDING")))
//                .andExpect(jsonPath("$.total", is(40.30)))
//                .andExpect(jsonPath("$.items", hasSize(2)));
//
//        verify(pedidoMapper, times(1)).toEntity(any(PedidoDTO.class));
//        verify(pedidoService, times(1)).create(any(Pedido.class));
//        verify(pedidoMapper, times(1)).toResponse(any(Pedido.class));
//    }
//
//    @Test
//    @DisplayName("Should return validation error for invalid order")
//    void shouldReturnValidationErrorForInvalidOrder() throws Exception {
//        // Given
//        PedidoDTO invalidPedidoDTO = TestDataBuilder.createInvalidOrderDTO();
//
//        // When & Then
//        mockMvc.perform(post("/api/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invalidPedidoDTO)))
//                .andExpect(status().isBadRequest());
//
//        verify(pedidoService, never()).create(any(Pedido.class));
//    }
//
//    @Test
//    @DisplayName("Should get all orders")
//    void shouldGetAllOrders() throws Exception {
//        // Given
//        List<Pedido> pedidos = Arrays.asList(TestDataBuilder.createSampleOrder(), TestDataBuilder.createSampleOrder());
//        List<PedidoResponse> expectedResponses = TestDataBuilder.createOrderResponseList(2);
//
//        when(pedidoService.findAll()).thenReturn(pedidos);
//        when(pedidoMapper.toResponseList(anyList())).thenReturn(expectedResponses);
//
//        // When & Then
//        mockMvc.perform(get("/api/orders"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[1].id", is(2)));
//
//        verify(pedidoService, times(1)).findAll();
//        verify(pedidoMapper, times(1)).toResponseList(anyList());
//    }
//
//    @Test
//    @DisplayName("Should get order by ID")
//    void shouldGetOrderById() throws Exception {
//        // Given
//        Pedido pedido = TestDataBuilder.createSampleOrder();
//        PedidoResponse expectedResponse = TestDataBuilder.createOrderResponse();
//
//        when(pedidoService.findById(1L)).thenReturn(pedido);
//        when(pedidoMapper.toResponse(pedido)).thenReturn(expectedResponse);
//
//        // When & Then
//        mockMvc.perform(get("/api/orders/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.customerName", is("Test Customer")))
//                .andExpect(jsonPath("$.status", is("PENDING")));
//
//        verify(pedidoService, times(1)).findById(1L);
//        verify(pedidoMapper, times(1)).toResponse(pedido);
//    }
//
//    @Test
//    @DisplayName("Should return not found for non-existent order")
//    void shouldReturnNotFoundForNonExistentOrder() throws Exception {
//        // Given
//        when(pedidoService.findById(999L)).thenThrow(new EntityNotFoundException("Order not found with id: 999"));
//
//        // When & Then
//        mockMvc.perform(get("/api/orders/999"))
//                .andExpect(status().isNotFound());
//
//        verify(pedidoService, times(1)).findById(999L);
//    }
//
//    @Test
//    @DisplayName("Should update order status")
//    void shouldUpdateOrderStatus() throws Exception {
//        // Given
//        Pedido pedido = TestDataBuilder.createSampleOrder();
//        pedido.setStatus(OrderStatus.PAID);
//
//        OrderStatusUpdateDTO statusUpdateDTO = TestDataBuilder.createStatusUpdateDTO(OrderStatus.PAID);
//        PedidoResponse expectedResponse = TestDataBuilder.createOrderResponse();
//        expectedResponse.setStatus(OrderStatus.PAID);
//
//        when(pedidoService.updateStatus(1L, OrderStatus.PAID)).thenReturn(pedido);
//        when(pedidoMapper.toResponse(pedido)).thenReturn(expectedResponse);
//
//        // When & Then
//        mockMvc.perform(put("/api/orders/1/status")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(statusUpdateDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.status", is("PAID")));
//
//        verify(pedidoService, times(1)).updateStatus(1L, OrderStatus.PAID);
//        verify(pedidoMapper, times(1)).toResponse(pedido);
//    }
//
//    @Test
//    @DisplayName("Should delete order")
//    void shouldDeleteOrder() throws Exception {
//        // Given
//        doNothing().when(pedidoService).delete(1L);
//
//        // When & Then
//        mockMvc.perform(delete("/api/orders/1"))
//                .andExpect(status().isNoContent());
//
//        verify(pedidoService, times(1)).delete(1L);
//    }
//
//    @Test
//    @DisplayName("Should return not found when deleting non-existent order")
//    void shouldReturnNotFoundWhenDeletingNonExistentOrder() throws Exception {
//        // Given
//        doThrow(new EntityNotFoundException("Order not found with id: 999"))
//                .when(pedidoService).delete(999L);
//
//        // When & Then
//        mockMvc.perform(delete("/api/orders/999"))
//                .andExpect(status().isNotFound());
//
//        verify(pedidoService, times(1)).delete(999L);
//    }
//}
