package sg.edu.nus.iss.workshop25.Service;

import java.io.StringReader;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.workshop25.constant.Constant;

@Service
public class MessageSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {

        String data = new String(message.getBody());        

        JsonObject jsonObject = Json.createReader(new StringReader(data)).readObject();
        String id = jsonObject.getString("id");
        if(id.equals(Constant.ID)){
            System.out.printf(">>>> message in if: %s\n", data);
        }
        else{
            System.out.printf(">>>> message in else: %s\n", data);
            System.out.println("Constant.id: " + Constant.ID);
            System.out.println("Json id: " + id);
        }
    }
   
}
