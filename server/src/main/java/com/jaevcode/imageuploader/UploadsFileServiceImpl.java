/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaevcode.imageuploader;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author @JAEVCODE
 */
@Service
public class UploadsFileServiceImpl implements IUploadsFileService {

    @Autowired
    Environment environment;

    public final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOADS_FOLDER = "IMAGES";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathFoto = getPath(filename);
        log.info("path:" + pathFoto);
        Resource recurso = null;

        recurso = new UrlResource(pathFoto.toUri());
        if (!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error: no se pudo cargar la imagen: " + pathFoto.toString());
        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String urlFile = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(urlFile);
        byte[] bytes = file.getBytes();
        Files.write(rootPath, bytes);
        log.info(rootPath.toString());
        urlFile =  "/UserUploads/" + urlFile;
        return urlFile;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();
        if (archivo.exists() && archivo.canRead()) {
            if (archivo.delete()) {
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void createDirectory() {
        try {
            if (!Files.exists(Paths.get(UPLOADS_FOLDER))) {
                Files.createDirectory(Paths.get(UPLOADS_FOLDER));
                log.info("El folder " + UPLOADS_FOLDER + " fue creado");
            } else {
                log.info("El folder " + UPLOADS_FOLDER + " ya existe");
            }
        } catch (IOException ex) {
            log.info("Error al crear el folder " + UPLOADS_FOLDER);
        }
    }

}
