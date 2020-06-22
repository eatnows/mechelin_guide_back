package data.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Component
public class AwsS3 {

    private String accessKey ="액세스키";
    private String secretKey ="시크릿키";

    private AmazonS3 s3Client;

    private AwsS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        this.s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint("s3.ap-northeast-2.amazonaws.com"); //  아시아 태평양 서울
    }


    public String fileupload(String bucketName,String fileName, File file) throws FileNotFoundException{
    	System.out.println(bucketName);
    	System.out.println(fileName);
    	System.out.println(file);
        //String fileExt = file.getName().split("[.]")[1];
        this.s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        String path = "https://버킷이름.s3.ap-northeast-2.amazonaws.com/"+fileName;
        System.out.println(path);
        return path;
    }
    
    
//    public String getS3ObjectContentAsString(String bucketName, String key) {
//        try {
//            if (key.startsWith("/")) {
//                key = key.substring(1);
//            }
//            if (key.endsWith("/")) {
//                key = key.substring(0, key.length());
//            }
//            try (InputStream is = s3Client.getObject(bucketName, key).getObjectContent()) {
//                return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }


}
