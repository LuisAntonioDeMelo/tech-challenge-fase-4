//package com.techchallenge.pedido.service;
//
//import com.techchallenge.pedido.model.Pedido;
//import com.techchallenge.pedido.model.PedidoItem;
//import com.techchallenge.pedido.model.OrderStatus;
//import com.techchallenge.pedido.repository.PedidoRepository;
//import com.techchallenge.pedido.util.TestDataBuilder;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PedidoServiceTest {
//
//    @Mock
//    private PedidoRepository pedidoRepository;
//
//    @InjectMocks
//    private PedidoServiceImpl pedidoService;
//
//    @Test
//    @DisplayName("Should find all orders")
//    void shouldFindAllOrders() {
//        // Given
//        List<Pedido> pedidos = Arrays.asList(
//                TestDataBuilder.createSampleOrder(),
//                TestDataBuilder.createSampleOrder()
//        );
//        when(pedidoRepository.findAll()).thenReturn(pedidos);
//
//        // When
//        List<Pedido> result = pedidoService.findAll();
//
//        // Then
//        assertEquals(2, result.size());
//        verify(pedidoRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Should find order by ID")
//    void shouldFindOrderById() {
//        // Given
//        Long orderId = 1L;
//        Pedido pedido = TestDataBuilder.createSampleOrder();
//        when(pedidoRepository.findById(orderId)).thenReturn(Optional.of(pedido));
//
//        // When
//        Pedido result = pedidoService.findById(orderId);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(orderId, result.getId());
//        verify(pedidoRepository, times(1)).findById(orderId);
//    }
//
//    @Test
//    @DisplayName("Should throw exception when order not found")
//    void shouldThrowExceptionWhenOrderNotFound() {
//        // Given
//        Long orderId = 999L;
//        when(pedidoRepository.findById(orderId)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertThrows(EntityNotFoundException.class, () -> {
//            pedidoService.findById(orderId);
//        });
//
//        verify(pedidoRepository, times(1)).findById(orderId);
//    }
//
//    @Test
//    @DisplayName("Should create new order")
//    void shouldCreateOrder() {
//        // Given
//        Pedido pedido = new Pedido();
//        pedido.setCustomerName("Test Customer");
//        pedido.setCustomerEmail("test@example.com");
//
//        PedidoItem item1 = new PedidoItem();
//        item1.setProductName("Product 1");
//        item1.setQuantity(2);
//        item1.setUnitPrice(new BigDecimal("10.00"));
//
//        PedidoItem item2 = new PedidoItem();
//        item2.setProductName("Product 2");
//        item2.setQuantity(1);
//        item2.setUnitPrice(new BigDecimal("15.00"));
//
//        List<PedidoItem> items = new ArrayList<>();
//        items.add(item1);
//        items.add(item2);
//        pedido.setItems(items);
//
//        Pedido savedPedido = new Pedido();
//        savedPedido.setId(1L);
//        savedPedido.setCustomerName(pedido.getCustomerName());
//        savedPedido.setCustomerEmail(pedido.getCustomerEmail());
//        savedPedido.setStatus(OrderStatus.PENDING);
//        savedPedido.setCreatedAt(LocalDateTime.now());
//        savedPedido.setTotal(new BigDecimal("35.00"));
//        savedPedido.setItems(items);
//
//        when(pedidoRepository.save(any(Pedido.class))).thenReturn(savedPedido);
//
//        // When
//        Pedido result = pedidoService.create(pedido);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Test Customer", result.getCustomerName());
//        assertEquals("test@example.com", result.getCustomerEmail());
//        assertEquals(OrderStatus.PENDING, result.getStatus());
//        assertEquals(new BigDecimal("35.00"), result.getTotal());
//        assertEquals(2, result.getItems().size());
//
//        // Verify relationships
//        for (PedidoItem item : result.getItems()) {
//            assertEquals(result, item.getPedido());
//        }
//
//        verify(pedidoRepository, times(1)).save(any(Pedido.class));
//    }
//
//    @Test
//    @DisplayName("Should calculate order total correctly")
//    void shouldCalculateOrderTotalCorrectly() {
//        // Given
//        Pedido pedido = new Pedido();
//
//        PedidoItem item1 = new PedidoItem();
//        item1.setProductName("Product 1");
//        item1.setQuantity(2);
//        item1.setUnitPrice(new BigDecimal("10.00"));
//        item1.setSubtotal(new BigDecimal("20.00"));
//
//        PedidoItem item2 = new PedidoItem();
//        item2.setProductName("Product 2");
//        item2.setQuantity(3);
//        item2.setUnitPrice(new BigDecimal("5.00"));
//        item2.setSubtotal(new BigDecimal("15.00"));
//
//        List<PedidoItem> items = new ArrayList<>();
//        items.add(item1);
//        items.add(item2);
//        pedido.setItems(items);
//
//        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
//            Pedido savedPedido = invocation.getArgument(0);
//            savedPedido.setId(1L);
//            return savedPedido;
//        });
//
//        // When
//        Pedido result = pedidoService.create(pedido);
//
//        // Then
//        assertEquals(new BigDecimal("35.00"), result.getTotal());
//    }
//
//    @Test
//    @DisplayName("Should update order status")
//    void shouldUpdateOrderStatus() {
//        // Given
//        Long orderId = 1L;
//        Pedido existingPedido = TestDataBuilder.createSampleOrder();
//        existingPedido.setStatus(OrderStatus.PENDING);
//
//        when(pedidoRepository.findById(orderId)).thenReturn(Optional.of(existingPedido));
//        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // When
//        Pedido updatedPedido = pedidoService.updateStatus(orderId, OrderStatus.PAID);
//
//        // Then
//        assertEquals(OrderStatus.PAID, updatedPedido.getStatus());
//        verify(pedidoRepository, times(1)).findById(orderId);
//        verify(pedidoRepository, times(1)).save(existingPedido);
//    }
//
//    @Test
//    @DisplayName("Should delete order")
//    void shouldDeleteOrder() {
//        // Given
//        Long orderId = 1L;
//        Pedido existingPedido = TestDataBuilder.createSampleOrder();
//
//        when(pedidoRepository.findById(orderId)).thenReturn(Optional.of(existingPedido));
//        doNothing().when(pedidoRepository).delete(existingPedido);
//
//        // When
//        pedidoService.delete(orderId);
//
//        // Then
//        verify(pedidoRepository, times(1)).findById(orderId);
//        verify(pedidoRepository, times(1)).delete(existingPedido);
//    }
//
//    @Test
//    @DisplayName("Should set default status for new orders")
//    void shouldSetDefaultStatusForNewOrders() {
//        // Given
//        Pedido pedido = new Pedido();
//        pedido.setCustomerName("Test Customer");
//        pedido.setCustomerEmail("test@example.com");
//        pedido.setItems(new ArrayList<>());
//
//        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
//            Pedido savedPedido = invocation.getArgument(0);
//            savedPedido.setId(1L);
//            return savedPedido;
//        });
//
//        // When
//        Pedido result = pedidoService.create(pedido);
//
//        // Then
//        assertEquals(OrderStatus.PENDING, result.getStatus());
//    }
//
//    @Test
//    @DisplayName("Should set bidirectional relationship between order and items")
//    void shouldSetBidirectionalRelationship() {
//        // Given
//        Pedido pedido = new Pedido();
//        pedido.setCustomerName("Test Customer");
//        pedido.setCustomerEmail("test@example.com");
//
//        PedidoItem item = new PedidoItem();
//        item.setProductName("Product");
//        item.setQuantity(1);
//        item.setUnitPrice(new BigDecimal("10.00"));
//
//        List<PedidoItem> items = new ArrayList<>();
//        items.add(item);
//        pedido.setItems(items);
//
//        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
//            Pedido savedPedido = invocation.getArgument(0);
//            savedPedido.setId(1L);
//            return savedPedido;
//        });
//
//        // When
//        Pedido result = pedidoService.create(pedido);
//
//        // Then
//        for (PedidoItem resultItem : result.getItems()) {
//            assertSame(result, resultItem.getPedido());
//        }
//    }
//
//    @Test
//    @DisplayName("Should handle empty items list")
//    void shouldHandleEmptyItemsList() {
//        // Given
//        Pedido pedido = new Pedido();
//        pedido.setCustomerName("Test Customer");
//        pedido.setCustomerEmail("test@example.com");
//        pedido.setItems(new ArrayList<>());
//
//        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
//            Pedido savedPedido = invocation.getArgument(0);
//            savedPedido.setId(1L);
//            return savedPedido;
//        });
//
//        // When
//        Pedido result = pedidoService.create(pedido);
//
//        // Then
//        assertEquals(BigDecimal.ZERO, result.getTotal());
//    }
//
//}