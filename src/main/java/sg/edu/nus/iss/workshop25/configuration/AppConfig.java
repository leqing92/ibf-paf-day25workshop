package sg.edu.nus.iss.workshop25.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.edu.nus.iss.workshop25.Service.MessageSubscriber;

@Configuration
public class AppConfig {
    @Value ("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;
    
    @Value("${spring.data.redis.user}")
    private String redisUser;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Autowired
	private MessageSubscriber subscriber;

	@Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);

        if(!"NOT_SET".equals(redisUser.trim())){
            redisConfig.setUsername(redisUser);
            redisConfig.setPassword(redisPassword);
        }

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisConnFac = new JedisConnectionFactory(redisConfig, jedisClient);
        jedisConnFac.afterPropertiesSet();

        return jedisConnFac;
    }

	@Bean("myredis")
	public RedisTemplate<String, String> createRedisTemplate() {
		RedisConnectionFactory fac = jedisConnectionFactory();
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(fac);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		return template;
	}

    @Bean
	public RedisMessageListenerContainer createMessageListener() {
		RedisConnectionFactory fac = jedisConnectionFactory();
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(fac);

		container.addMessageListener(subscriber, ChannelTopic.of("mytopic"));
		return container;
	}
}
