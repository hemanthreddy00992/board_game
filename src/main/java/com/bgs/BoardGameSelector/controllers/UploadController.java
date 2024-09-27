package com.bgs.BoardGameSelector.controllers;

import com.bgs.BoardGameSelector.dao.CommentDao;
import com.bgs.BoardGameSelector.dao.UserDao;
import com.bgs.BoardGameSelector.model.Comment;
import com.bgs.BoardGameSelector.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
public class UploadController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserDao userDao;

//    private String uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "upload").toString();
    private String tempDir = Paths.get(System.getProperty("user.dir"), ".tmp").toString();

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile uploadedFile,
                                   RedirectAttributes redirectAttributes) {
        try {
            String res = imgurUpload(uploadedFile);
            JSONObject json = new JSONObject(res);
            // Check for successful upload
            if (json.getBoolean("success")) {
                // Get upload details for database
                String uploadUrl = json.getJSONObject("data").getString("link");
                String deleteHash = json.getJSONObject("data").getString("deletehash");
                // Update DB
                User user = userDao.findByUsername(getLoggedInUsername());
                user.setAvatar(uploadUrl);
                user.setDeletehash(deleteHash);
                userDao.save(user);

                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + uploadedFile.getOriginalFilename() + "'");
            }

            return "redirect:/account";

        } catch (Exception e) {
            return "redirect:/account";
        }

        /* === FOR STORING FILE LOCALLY === */
        /*
        try {

            // Get the file and save it somewhere
            if(! new File(uploadDir).exists())
            {
                new File(uploadDir).mkdir();
            }

            // Write file to system
            File destDir = new File(uploadDir);
            File dest = File.createTempFile("img_", ".jpg", destDir);
            uploadedFile.transferTo(dest);

            // Update DN
            User user = userDao.findByUsername(getLoggedInUsername());
            user.setAvatar(dest.getName());
            userDao.save(user);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + uploadedFile.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/account";
    }
    */
    }

    private String getLoggedInUsername() {
        // Get username of logged-in user
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        return username;
    }

    private long getLoggedInId() {
        String username = getLoggedInUsername();
        if (username.equals("anonymousUser"))
            return -1;
        User user = userDao.findByUsername(username);
        return user.getId();
    }

    private String imgurUpload(MultipartFile uploadedFile) throws Exception{

        URL url = new URL("https://api.imgur.com/3/image");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArray);
        byte[] byteImage = byteArray.toByteArray();
        String dataImage = Base64.getEncoder().encodeToString(byteImage);
        String data = URLEncoder.encode("image", "UTF-8") + "="
                + URLEncoder.encode(dataImage, "UTF-8");

        String clientId = System.getenv("IMGUR_CLIENT_ID");
        System.out.println(clientId);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Client-ID " + clientId);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        conn.connect();
        StringBuilder stb = new StringBuilder();
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        // Get the response
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            stb.append(line).append("\n");
        }
        wr.close();
        rd.close();

        return stb.toString();
    }

}
