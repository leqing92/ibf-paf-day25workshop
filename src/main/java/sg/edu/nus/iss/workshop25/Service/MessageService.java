package sg.edu.nus.iss.workshop25.Service;

import java.util.Scanner;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

public class MessageService implements Runnable {
   private final RedisTemplate<String, String> template;
   private final String name;

   public MessageService(RedisTemplate<String, String> template, String name) {
      this.template = template;
      this.name = name;
   } 

   public void run() {
      System.out.println("*** Starting thread");

      ListOperations<String, String> listOps = template.opsForList();
      Scanner scan = new Scanner(System.in);
      
         try{
            while (true){
               System.out.println("*** Input ID ");
               String id = scan.nextLine();
               System.out.println("*** Input message ");
               String message = scan.nextLine();
   
               JsonObjectBuilder jBuilder = Json.createObjectBuilder()
                              .add("id", id)
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

}
