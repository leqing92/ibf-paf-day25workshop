package sg.edu.nus.iss.workshop25.Service;

import java.util.Scanner;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.workshop25.constant.Constant;

public class MessageService implements Runnable {  

   private String id;
   private String message;
   private RedisTemplate<String, String> template;

   public MessageService(RedisTemplate<String, String> template) {
      this.template = template;
   }

   public void run() {
      System.out.println("*** Starting thread");

      ListOperations<String, String> listOps = template.opsForList();
      Scanner scan = new Scanner(System.in);
      
         try{
            while (true){               
               System.out.println("*** Input message ");
               message = scan.nextLine();
   
               JsonObjectBuilder jBuilder = Json.createObjectBuilder()
                              .add("id", Constant.ID)
                              .add("message", message);
   
               String jObject = jBuilder.build().toString(); 
               System.out.println("sending message: " + jObject);           
               listOps.leftPush("myqueue", jObject);
   
               Thread.sleep(5000);
            }                        
         } 
         catch (Exception ex) {
            System.err.printf(">>>> exception: %s\n", ex.getMessage());
         } 
         finally {
            scan.close();
         }
   }

   public String getId() {
      return id;
   }
   
}
