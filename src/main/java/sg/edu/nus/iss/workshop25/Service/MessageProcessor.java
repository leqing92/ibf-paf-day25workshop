package sg.edu.nus.iss.workshop25.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessor {
    @Autowired 
    @Qualifier("myredis")
    private RedisTemplate<String, String> template;   

    @Async // Create a thread
    public void start() {
        ExecutorService thrPool = Executors.newFixedThreadPool(2);
        thrPool.submit(new MessageService(template));
    }
}
