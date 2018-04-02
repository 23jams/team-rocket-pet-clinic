package org.springframework.samples.petclinic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Scheduled(fixedRate = (1000*10))
	public void scheduleTaskWithFixedRate(){
		//insert consistency checker
		
	}
}
