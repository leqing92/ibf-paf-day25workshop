package sg.edu.nus.iss.workshop25;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.workshop25.Service.MessageProcessor;

@SpringBootApplication
public class Workshop25Application implements CommandLineRunner{
	
	@Autowired
	private MessageProcessor processor;

	// @Autowired 
    // @Qualifier("myredis")
    // private RedisTemplate<String, String> template;  
	public static void main(String[] args) {
		SpringApplication.run(Workshop25Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		processor.start();

		// JsonObjectBuilder jBuilder = Json.createObjectBuilder()
		// 							.add("id", "lq")
		// 							.add("message", "something");

		// String jObject = jBuilder.build().toString();

		// ListOperations<String, String> listOps = template.opsForList();
		// listOps.leftPush("myqueue", jObject);
		// template.convertAndSend("mytopic", jObject);
	}

}
