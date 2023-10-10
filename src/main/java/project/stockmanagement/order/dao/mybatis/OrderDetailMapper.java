package project.stockmanagement.order.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.order.dao.domain.OrderDetail;

@Mapper
public interface OrderDetailMapper {

	void save(CombinedEntity<OrderDetail> combinedEntity);

	Optional<OrderDetail> findById(Long id);

	List<OrderDetail> findByOrderId(Long orderId);

	void delete(Long id);
}
