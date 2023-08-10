package project.stockmanagement.common.config.redis;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisItemStockConfig {

	@Value("${spring.redis.cluster.nodes}")
	private List<String> clusterNodes;

	@Bean
	public RedisConnectionFactory redisItemStockConnectionFactory() {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);

		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration());
	}

	private LettuceClientConfiguration lettuceClientConfiguration() {
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
			.dynamicRefreshSources(true)
			.enablePeriodicRefresh(Duration.ofMinutes(10))
			.enableAllAdaptiveRefreshTriggers()
			.adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30))
			.build();

		ClusterClientOptions clientOptions = ClusterClientOptions.builder()
			.autoReconnect(true)
			.publishOnScheduler(true)
			.disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT)
			.socketOptions(SocketOptions.builder().connectTimeout(Duration.ofSeconds(100)).keepAlive(true).build())
			.topologyRefreshOptions(clusterTopologyRefreshOptions)
			.timeoutOptions(TimeoutOptions.enabled(Duration.ofSeconds(3000)))
			.build();

		return LettuceClientConfiguration.builder()
			.clientOptions(clientOptions)
			.commandTimeout(Duration.ofSeconds(3000))
			.readFrom(ReadFrom.REPLICA_PREFERRED)
			.build();
	}

	@Bean
	public RedisTemplate<String, String> redisItemStockTemplate(
		RedisConnectionFactory redisItemStockConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisItemStockConnectionFactory);

		return redisTemplate;
	}
}
