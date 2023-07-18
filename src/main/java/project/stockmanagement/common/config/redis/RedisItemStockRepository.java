package project.stockmanagement.common.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.error.exception.NotEnoughStockException;

@Component
@RequiredArgsConstructor
public class RedisItemStockRepository {

	private static final String PREFIX = "ITEM-";
	private final RedisTemplate<String, String> redisItemStockTemplate;

	public void increaseItemStock(Long itemId, Integer quantity) {
		redisItemStockTemplate.opsForValue().increment(generateKey(itemId), quantity);
	}

	public void decreaseItemStock(Long itemId, Integer quantity) {
		Long decrement = redisItemStockTemplate.opsForValue().decrement(generateKey(itemId), quantity);

		if (decrement < 0) {
			throw new NotEnoughStockException("재고가 부족합니다.");
		}
	}

	public Long getItemStock(Long itemId) {
		String stock = redisItemStockTemplate.opsForValue().get(generateKey(itemId));
		return Long.parseLong(stock);
	}

	public void setItemStock(Long itemId, Integer stock) {
		redisItemStockTemplate.opsForValue().set(generateKey(itemId), stock.toString());
	}

	public void clearItemStock() {
		redisItemStockTemplate.getConnectionFactory().getConnection().flushAll();
	}

	public String generateKey(Long itemId) {
		return PREFIX.concat(itemId.toString());
	}
}
