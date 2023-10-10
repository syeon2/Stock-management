package project.stockmanagement.order.dao;

import java.util.List;

import project.stockmanagement.order.dao.domain.OrderDetail;

public interface OrderDetailRepository {

	OrderDetail save(OrderDetail orderDetail);

	OrderDetail findById(Long id);

	List<OrderDetail> findByOrderId(Long id);

	void delete(Long id);
}
