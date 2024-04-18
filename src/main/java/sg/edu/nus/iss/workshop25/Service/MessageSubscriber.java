package sg.edu.nus.iss.workshop25.Service;

import java.io.StringReader;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class MessageSubscriber implements MessageListener {

   @Override
   public void onMessage(Message message, @Nullable byte[] pattern) {

      String data = new String(message.getBody());
      System.out.printf(">>>> message: %s\n", data);

      JsonObject jsonObject = Json.createReader(new StringReader(data)).readObject();
      String id = jsonObject.getString("id");
      
   }
   
}
