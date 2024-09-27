package com.bgs.BoardGameSelector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    String tester = "file:///C:/Users/caleb/PycharmProjects/java/tester/board-game-selector/src/main/upload/";

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String uploadPathResource = getSystemDepPath();
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(tester);
    }

    public String getSystemDepPath() {
        String os = System.getProperty("os.name");
        String result = Paths.get(System.getProperty("user.dir"), "src", "main", "upload").toString();
        if (os.startsWith("Win")) {
            result = result.replace("\\", "/");
            result = "file:///" + result + "/";
        }
        else {
            result = "file:" + result + "/";
        }
        return result;
    }
}