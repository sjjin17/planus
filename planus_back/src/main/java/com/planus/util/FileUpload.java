package com.planus.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileUpload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;


    public String fileUpload(MultipartFile file) throws IOException {
        String s3FileName = "trip/" + file.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType("image/png");
        objMeta.setContentDisposition("inline");
        objMeta.setContentLength(file.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, file.getInputStream(), objMeta);
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public void fileDelete(String fileName) {
        String key = fileName.substring(fileName.lastIndexOf("/"));
        amazonS3.deleteObject(bucket + "/trip", key.substring(1));
    }
}
