package com.sushil.todo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {

    Logger logger= LoggerFactory.getLogger(FileController.class);
    @PostMapping("/single")
    public String uploadSingle(@RequestParam("image")MultipartFile file){

        logger.info("Name : {}",file.getName());
        logger.info("ContentType : {}",file.getContentType());
        logger.info("Original File Name : {}",file.getOriginalFilename());
        logger.info("File Size : {}",file.getSize());
        return "File Test";
    }

    @PostMapping("/multiple")
    public String uploadMultiple(@RequestParam("files") MultipartFile[] files){
        Arrays.stream(files).forEach(file->{
            logger.info("File name {}",file.getOriginalFilename());
            logger.info("File Type {}",file.getContentType());
            System.out.println("-------------------------------------------------");
        });
           return "Handling multiple file";
    }
    // serving image file in response

    @GetMapping("/serve-image")
    public void serveImageHandler(HttpServletResponse response){

        try{

            InputStream fileInputStream=new FileInputStream("images/6.jpg");
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(fileInputStream,response.getOutputStream());


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
