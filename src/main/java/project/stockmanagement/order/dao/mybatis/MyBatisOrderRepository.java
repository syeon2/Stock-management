package project.stockmanagement.order.dao.mybatis;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;

@Repository
@RequiredArgsConstructor
public class MyBatisOrderRepository implements OrderRepository {

	private final OrderMapper orderMapper;

	@Override
	public Order save(Order orders) {
		CombinedEntity<Order> createOrders = CombinedEntity.toCreateData(orders);
		orderMapper.save(createOrders);

		return createOrders.getEntity();
	}

	@Override
	public Order findById(Long id) {
		Optional<Order> findByIdOptional = orderMapper.findById(id);

		return findByIdOptional.orElseThrow(
			() -> new NoSuchElementException("해당 주문 건은 존재하지 않습니다."));
	}

	@Override
	public Long update(Long id, Order updateOrders) {
		CombinedEntity<Order> updateOrder = CombinedEntity.toUpdateData(updateOrders);
		orderMapper.update(id, updateOrder);

		return id;
	}

	@Override
	public void delete(Long id) {
		orderMapper.delete(id);
	}
}
