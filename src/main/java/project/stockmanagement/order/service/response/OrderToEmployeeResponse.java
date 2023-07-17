package project.stockmanagement.order.service.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.stockmanagement.order.service.request.OrderItem;

@Getter
@RequiredArgsConstructor
public class OrderToEmployeeResponse {

	private final List<OrderItem> orderItems;
}
