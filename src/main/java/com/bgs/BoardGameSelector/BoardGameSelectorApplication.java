package com.bgs.BoardGameSelector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class BoardGameSelectorApplication {

	public static void main(String[] args) {
		// Clear out temporary files on startup
		File tempDir = new File(Paths.get(System.getProperty("user.dir"), ".tmp").toString());
		if (tempDir.exists())
		{
			for(File file: tempDir.listFiles())
				if (!file.isDirectory())
					file.delete();
		}


		SpringApplication.run(BoardGameSelectorApplication.class, args);
	}
}