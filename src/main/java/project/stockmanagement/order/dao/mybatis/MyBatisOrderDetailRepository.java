package project.stockmanagement.order.dao.mybatis;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.domain.OrderDetail;

@Repository
@RequiredArgsConstructor
public class MyBatisOrderDetailRepository implements OrderDetailRepository {

	private final OrderDetailMapper orderDetailMapper;

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		CombinedEntity<OrderDetail> createOrderDetail = CombinedEntity.toCreateData(orderDetail);
		orderDetailMapper.save(createOrderDetail);

		return createOrderDetail.getEntity();
	}

	@Override
	public OrderDetail findById(Long id) {
		Optional<OrderDetail> findOrderDetail = orderDetailMapper.findById(id);

		return findOrderDetail.orElseThrow(
			() -> new NoSuchElementException("해당하는 주문 상세 내역이 존재하지 않습니다."));
	}

	@Override
	public List<OrderDetail> findByOrderId(Long id) {
		return orderDetailMapper.findByOrderId(id);
	}

	@Override
	public void delete(Long id) {
		orderDetailMapper.delete(id);
	}
}
