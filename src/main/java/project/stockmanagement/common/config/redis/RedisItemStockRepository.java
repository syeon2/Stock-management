package project.stockmanagement.common.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.error.exception.NotEnoughStockException;

@Component
@RequiredArgsConstructor
public class RedisItemStockRepository {

	private static final String PREFIX = "STOCK";
	private final RedisTemplate<String, String> redisItemStockTemplate;

	public void increaseItemStock(Long itemId, String itemCategory, Integer quantity) {
		redisItemStockTemplate.opsForValue().increment(generateKey(itemId, itemCategory), quantity);
	}

	public void decreaseItemStock(Long itemId, String itemCategory, Integer quantity) {
		Long decrement = redisItemStockTemplate.opsForValue().decrement(generateKey(itemId, itemCategory), quantity);

		if (decrement < 0) {
			throw new NotEnoughStockException("재고가 부족합니다.");
		}
	}

	public Long getItemStock(Long itemId, String itemCategory) {
		String stock = redisItemStockTemplate.opsForValue().get(generateKey(itemId, itemCategory));
		return Long.parseLong(stock);
	}

	public void setItemStock(Long itemId, String itemCategory, Integer stock) {
		redisItemStockTemplate.opsForValue().set(generateKey(itemId, itemCategory), stock.toString());
	}

	public void clearItemStock() {
		redisItemStockTemplate.getConnectionFactory().getConnection().flushAll();
	}

	public String generateKey(Long itemId, String itemCategory) {
		return PREFIX.concat(":")
			.concat(itemCategory).concat(":")
			.concat(String.valueOf(itemId));
	}
}
