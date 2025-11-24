package com.example.pokeplushback.Servicios;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlfajcgv2",
                "api_key", "699489118588246",
                "api_secret", "UsB-Z3QYq7rMGoewKXujYL0mCY8"
        ));
    }


    public String upload(byte[] fileBytes, String fileName) throws Exception {
        Map uploadResult = cloudinary.uploader().upload(fileBytes, ObjectUtils.asMap("public_id", fileName));
        return (String) uploadResult.get("secure_url");
    }

}
