/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaevcode.imageuploader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author @JAEVCODE
 */
@RestController
@CrossOrigin
public class UploadImageController {

    @Autowired
    IUploadsFileService uploadsFileService;

    @GetMapping(value = "/UserUploads/{filename:.+}")
    public ResponseEntity<Resource> mostrarFoto(@PathVariable String filename) {
        Resource recurso = null;
        try {
            recurso = uploadsFileService.load(filename);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UploadImageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @PostMapping("/")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile image) {
        String urlFile = "";
        try {
            urlFile = uploadsFileService.copy(image);
        } catch (IOException ex) {
            Logger.getLogger(UploadImageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(urlFile, HttpStatus.OK);
    }
}
