package ch.bbw.aal.Procedure_M152.controller;

import ch.bbw.aal.Procedure_M152.model.Image;
import ch.bbw.aal.Procedure_M152.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/upload")
    public Image uplaodImage(@RequestParam("myFile") MultipartFile file) throws IOException {

        Image img = new Image( file.getOriginalFilename(),file.getContentType(),file.getBytes() );
        final Image savedImage = imageRepository.save(img);

        System.out.println("Image saved");
        return savedImage;
    }

    @GetMapping("/Images")
    public ResponseEntity<List<Image>> getAllImages(@RequestParam(required = false) String title) {
        try {
            List<Image> images = new ArrayList<Image>();
            if (title == null)
                imageRepository.findAll().forEach(images::add);
            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/Images/{ImageId}")
    public ResponseEntity<Image> getImageById(@PathVariable("ImageId") long id) {
        Optional<Image> imageData = imageRepository.findById(id);
        if (imageData.isPresent()) {
            return new ResponseEntity<>(imageData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/Images")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        try {
            Image _image = imageRepository
                    .save(new Image(image.getName(), image.getType(), image.getPic()));
            return new ResponseEntity<>(_image, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    @DeleteMapping("/Images")
    public ResponseEntity<HttpStatus> deleteAllImages() {
        try {
            imageRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}