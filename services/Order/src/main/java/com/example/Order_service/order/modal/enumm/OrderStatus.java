package com.example.Order_service.order.modal.enumm;

public enum OrderStatus {
    WAITING_PAYMENT, //chờ thanh toán
    WAITING_CONFIRMATION, //chờ xác nhận
    CONFIRMED,  // đã xác nhận
    IN_TRANSIT, //đang giao
    DELIVERED,  // đã giao
    CANCELLED,  //đã huỷ
    DELIVERY_FAILED, //gioa thất bại
    SUCCESS, //THÀNH CÔNG
}
