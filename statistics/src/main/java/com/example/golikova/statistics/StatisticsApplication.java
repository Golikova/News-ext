package com.example.golikova.statistics;

import common.InputOpenException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@SpringBootApplication
public class StatisticsApplication {

	public static void main(String[] args) {

		SpringApplication.run(StatisticsApplication.class, args);

	}

	@EventListener(ApplicationReadyEvent.class)
	public void doAfterStartup() throws InterruptedException {
		DBSerfer dbSerfer = new DBSerfer();
		while (true) {
			try {
				dbSerfer.getAllQuerySet();
				dbSerfer.getPersonQuerySet();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InputOpenException e) {
				e.printStackTrace();
			}

			Thread.sleep(60*60*1000);

		}

	}

}
