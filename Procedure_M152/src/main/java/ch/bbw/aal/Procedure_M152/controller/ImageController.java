package ch.bbw.aal.Procedure_M152.controller;

import ch.bbw.aal.Procedure_M152.converter.FileConverter;
import ch.bbw.aal.Procedure_M152.model.Image;
import ch.bbw.aal.Procedure_M152.model.ImageInfo;
import ch.bbw.aal.Procedure_M152.model.ImageInfoResponse;
import ch.bbw.aal.Procedure_M152.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/upload")
    public Image uplaodImage(@RequestParam("myFile") MultipartFile files) throws IOException {

        System.out.println(files.getName());


            if (files.getBytes().length > 0) {
                Image image = new Image();

                image.setName(files.getName());

                image.setType(files.getContentType());
                image.setPic(files.getBytes());

                File inputFile = new File("InputFile");

                try (FileOutputStream outputStream = new FileOutputStream(inputFile)) {
                    outputStream.write(files.getBytes());
                }

                FileConverter fc = new FileConverter();
                //Change Filetype to PNG
                File pngFile = fc.jpgToPng(inputFile);
                //Add Watermark to File
                File waterMarkedFile = fc.addWatermark(pngFile, "@Aaron Laeuchli");
                //Change Resolution for File
                File smallerFile = fc.setWidthTo(500, waterMarkedFile);

                image.setPic(Files.readAllBytes(pngFile.toPath()));
                image.setPicSmall(Files.readAllBytes(smallerFile.toPath()));
                image.setPicWatermarked(Files.readAllBytes(waterMarkedFile.toPath()));

                Image savedImage = imageRepository.save(image);

                System.out.println("Image saved");
                return savedImage;
            }
        return null;
    }

    @GetMapping("/ImagesInfos")
    public ResponseEntity<ImageInfoResponse> getAllImages() {
        try {
            List<Image> images = new ArrayList<>(imageRepository.findAll());
            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ImageInfo> imageInfoList = new LinkedList<>();
            for (Image image: images) {
                imageInfoList.add(new ImageInfo(image.getImageId(), image.getName(), image.getType()));
            }
            return new ResponseEntity<>(new ImageInfoResponse(imageInfoList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getImages/{ImageId}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImageById(@PathVariable("ImageId") long id) throws IOException {
        Optional<Image> imageData = imageRepository.findById(id);
        if (imageData.isPresent()) {
            InputStream inputStream = new ByteArrayInputStream(imageData.get().getPicWatermarked());

            return IOUtils.toByteArray(inputStream);
        } else {
            return null;
        }
    }


    @PutMapping("/Images/{ImageId}")
    public ResponseEntity<Image> updateImage(@PathVariable("ImageId") long id, @RequestBody Image image) {
        Optional<Image> imageData = imageRepository.findById(id);
        if (imageData.isPresent()) {
            Image _image = imageData.get();
            _image.setImageId(image.getImageId());
            return new ResponseEntity<>(imageRepository.save(_image), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Images/{ImageId}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("ImageId") long id) {
        try {
            imageRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/ImagesAll")
    public ResponseEntity<HttpStatus> deleteAllImages() {
        try {
            imageRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}