package project.stockmanagement.common.config.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisOrderLockRepository {

	private static final String ORDER_DISPATCH_LOCK = "ORDER_DISPATCH_LOCK";
	private static final Duration EXPIRED_DURATION = Duration.ofMillis(100);

	private final RedisTemplate<String, String> redisOrderLockTemplate;

	public Boolean lock(Long key) {
		return redisOrderLockTemplate.opsForValue()
			.setIfAbsent(generateKey(key), ORDER_DISPATCH_LOCK, EXPIRED_DURATION);
	}

	public Boolean unLock(Long key) {
		return redisOrderLockTemplate.delete(generateKey(key));
	}

	private String generateKey(Long key) {
		return key.toString();
	}
}
