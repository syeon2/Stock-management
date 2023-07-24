package project.stockmanagement.common.config.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.error.exception.NotEnoughStockException;
import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.item.dao.domain.Item;
import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
import project.stockmanagement.order.dao.domain.OrderDetail;

@Component
@RequiredArgsConstructor
public class RedisItemStockRepository {

	private static final String PREFIX = "STOCK";
	private final RedisTemplate<String, String> redisItemStockTemplate;

	private final ItemRepository itemRepository;
	private final ItemCategoryRepository itemCategoryRepository;

	public void increaseItemStock(Long itemId, Integer quantity) {
		redisItemStockTemplate.opsForValue().increment(generateItemKey(itemId), quantity);
	}

	public void decreaseItemStock(List<OrderDetail> orderDetails) {
		Map<String, Integer> backupItem = new HashMap<>();

		for (OrderDetail orderDetail : orderDetails) {
			String generatedKey = generateItemKey(orderDetail.getItemId());

			Long decrement = redisItemStockTemplate.opsForValue().decrement(generatedKey, orderDetail.getCount());
			backupItem.put(generatedKey, orderDetail.getCount());

			backupItemStock(backupItem, decrement);
		}
	}

	private void backupItemStock(Map<String, Integer> backupItem, Long decrement) {
		if (decrement == null || decrement < 0) {
			for (String itemKey : backupItem.keySet()) {
				redisItemStockTemplate.opsForValue().increment(itemKey, backupItem.get(itemKey));
			}

			throw new NotEnoughStockException("재고가 부족합니다.");
		}
	}

	public Long getItemStock(Long itemId) {
		String stock = redisItemStockTemplate.opsForValue().get(generateItemKey(itemId));
		return Long.parseLong(stock);
	}

	public void setItemStock(Long itemId, Integer stock) {
		redisItemStockTemplate.opsForValue().set(generateItemKey(itemId), stock.toString());
	}

	public void clearItemStock() {
		redisItemStockTemplate.getConnectionFactory().getConnection().flushAll();
	}

	public String generateItemKey(Long itemId) {
		Item findItem = itemRepository.findById(itemId);
		String category = itemCategoryRepository.findById(findItem.getItemCategoryId()).getName();

		return PREFIX.concat(":")
			.concat(category).concat(":")
			.concat(String.valueOf(itemId));
	}

}
