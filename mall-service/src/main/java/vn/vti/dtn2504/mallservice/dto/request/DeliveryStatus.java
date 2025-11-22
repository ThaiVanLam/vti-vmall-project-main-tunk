package vn.vti.dtn2504.mallservice.dto.request;

public enum DeliveryStatus {
    PENDING,            // Chờ xử lý
    PICKED_UP,          // Đã lấy hàng
    IN_TRANSIT,         // Đang vận chuyển
    OUT_FOR_DELIVERY,   // Đang giao
    DELIVERED,          // Đã giao
    FAILED,             // Giao thất bại
    RETURNED            // Hoàn hàng
}
