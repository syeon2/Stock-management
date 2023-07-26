package project.stockmanagement.common.config.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisItemStockConfig {

	// @Value("${spring.redis.host}")
	// private String host;
	//
	// @Value("${spring.redis.port}")
	// private Integer port;

	@Value("${spring.redis.cluster.nodes}")
	private List<String> clusterNodes;

	@Bean
	public RedisConnectionFactory redisItemStockConnectionFactory() {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
		return new LettuceConnectionFactory(redisClusterConfiguration);
	}

	@Bean
	public RedisTemplate<String, String> redisItemStockTemplate(
		RedisConnectionFactory redisItemStockConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisItemStockConnectionFactory);

		return redisTemplate;
	}
}
