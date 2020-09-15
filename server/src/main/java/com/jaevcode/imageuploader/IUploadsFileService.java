/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaevcode.imageuploader;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author @JAEVCODE
 */
public interface IUploadsFileService {
    
    public Resource load (String filename)throws MalformedURLException;
    
    public String copy(MultipartFile file) throws IOException;
    
    public boolean  delete(String filename);
            
    public void deleteAll();
    
    public void createDirectory();
}
