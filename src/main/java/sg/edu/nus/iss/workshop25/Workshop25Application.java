package sg.edu.nus.iss.workshop25;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

import sg.edu.nus.iss.workshop25.Service.MessageProcessor;

@SpringBootApplication
public class Workshop25Application implements CommandLineRunner{
	
	@Autowired
	private MessageProcessor processor;
	public static void main(String[] args) {
		SpringApplication.run(Workshop25Application.class, args);
	}	

	@Override 
	@Async
	public void run(String... args) throws Exception {
		processor.start();
	}

}
