//package com.techchallenge.pedido.util;
//
//import com.techchallenge.pedido.dto.PedidoDTO;
//import com.techchallenge.pedido.dto.OrderItemDTO;
//import com.techchallenge.pedido.dto.PedidoResponse;
//import com.techchallenge.pedido.dto.OrderStatusUpdateDTO;
//import com.techchallenge.pedido.model.Pedido;
//import com.techchallenge.pedido.model.PedidoItem;
//import com.techchallenge.pedido.model.OrderStatus;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Utility class for creating test data objects
// */
//public class TestDataBuilder {
//
//    /**
//     * Creates a sample order entity with default values
//     */
//    public static Pedido createSampleOrder() {
//        Pedido pedido = new Pedido();
//        pedido.setId(1L);
//        pedido.setCustomerName("Test Customer");
//        pedido.setCustomerEmail("test@example.com");
//        pedido.setStatus(OrderStatus.PENDING);
//        pedido.setCreatedAt(LocalDateTime.now());
//        pedido.setTotal(new BigDecimal("40.30"));
//
//        List<PedidoItem> items = new ArrayList<>();
//        PedidoItem item1 = createOrderItem(1L, "Hamburger", "Classic burger", 2, new BigDecimal("15.90"));
//        PedidoItem item2 = createOrderItem(2L, "French Fries", "Large portion", 1, new BigDecimal("8.50"));
//
//        item1.setPedido(pedido);
//        item2.setPedido(pedido);
//
//        items.add(item1);
//        items.add(item2);
//        pedido.setItems(items);
//
//        return pedido;
//    }
//
//    /**
//     * Creates a sample order item entity
//     */
//    public static PedidoItem createOrderItem(Long id, String productName, String description,
//                                             Integer quantity, BigDecimal unitPrice) {
//        PedidoItem item = new PedidoItem();
//        item.setId(id);
//        item.setProductName(productName);
//        //item.setDescription(description);
//        item.setQuantity(quantity);
//        item.setUnitPrice(unitPrice);
//        item.setSubtotal(unitPrice.multiply(new BigDecimal(quantity)));
//        return item;
//    }
//
//    /**
//     * Creates a sample OrderDTO for testing
//     */
//    public static PedidoDTO createOrderDTO() {
//        PedidoDTO dto = new PedidoDTO();
//        dto.setCustomerName("Test Customer");
//        dto.setCustomerEmail("test@example.com");
//
//        List<OrderItemDTO> items = new ArrayList<>();
//        items.add(createOrderItemDTO("Hamburger", "Classic burger", 2, new BigDecimal("15.90")));
//        items.add(createOrderItemDTO("French Fries", "Large portion", 1, new BigDecimal("8.50")));
//
//        dto.setItems(items);
//        return dto;
//    }
//
//    /**
//     * Creates a sample OrderItemDTO for testing
//     */
//    public static OrderItemDTO createOrderItemDTO(String productName, String description,
//                                                 Integer quantity, BigDecimal unitPrice) {
//        OrderItemDTO dto = new OrderItemDTO();
//        dto.setProductName(productName);
//        dto.setDescription(description);
//        dto.setQuantity(quantity);
//        dto.setUnitPrice(unitPrice);
//        return dto;
//    }
//
//    /**
//     * Creates a sample OrderResponse for testing
//     */
//    public static PedidoResponse createOrderResponse() {
//        PedidoResponse response = new PedidoResponse();
//        response.setId(1L);
//        response.setCustomerName("Test Customer");
//        response.setCustomerEmail("test@example.com");
//        response.setStatus(OrderStatus.PENDING);
//        response.setTotal(new BigDecimal("40.30"));
//        response.setCreatedAt(LocalDateTime.now());
//
//        List<OrderItemDTO> items = new ArrayList<>();
//        items.add(createOrderItemDTO("Hamburger", "Classic burger", 2, new BigDecimal("15.90")));
//        items.add(createOrderItemDTO("French Fries", "Large portion", 1, new BigDecimal("8.50")));
//
//        response.setItems(items);
//        return response;
//    }
//
//    /**
//     * Creates an OrderStatusUpdateDTO with the specified status
//     */
//    public static OrderStatusUpdateDTO createStatusUpdateDTO(OrderStatus status) {
//        OrderStatusUpdateDTO dto = new OrderStatusUpdateDTO();
//        dto.setStatus(status);
//        return dto;
//    }
//
//    /**
//     * Creates a list of sample OrderResponse objects
//     */
//    public static List<PedidoResponse> createOrderResponseList(int count) {
//        List<PedidoResponse> responses = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            PedidoResponse response = createOrderResponse();
//            response.setId((long) (i + 1));
//            responses.add(response);
//        }
//        return responses;
//    }
//
//    /**
//     * Creates an invalid OrderDTO (missing required fields)
//     */
//    public static PedidoDTO createInvalidOrderDTO() {
//        PedidoDTO dto = new PedidoDTO();
//        // Missing customer name and email
//        dto.setItems(new ArrayList<>());
//        return dto;
//    }
//}
