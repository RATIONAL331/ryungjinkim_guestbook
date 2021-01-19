package nhn.rookieHAMATF.ryungjinkim_guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RyungjinkimGuestbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RyungjinkimGuestbookApplication.class, args);
	}

}
