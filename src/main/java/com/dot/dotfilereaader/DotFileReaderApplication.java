package com.dot.dotfilereaader;

import com.dot.dotfilereaader.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DotFileReaderApplication implements CommandLineRunner {

	final Logger logger = LoggerFactory.getLogger(DotFileReaderApplication.class);

	@Autowired
	private FileReaderService fileReaderService;

	public static void main(String[] args) {
		SpringApplication.run(DotFileReaderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		logger.info("Running.....");
		if(args.length == 4){
			fileReaderService.requestLimitChecker(args[0], args[1], args[2], args[3]);
			logger.info("Application Completed Successfully");
			System.exit(0);
		}
		logger.info("Invalid number of arguments");
		System.exit(1);
	}
}
