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
import com.amazonaws.services.s3.model.ObjectMetadata;
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

    /*
     * 리뷰글 이미지 업로드
     */
    public String fileupload(String bucketName, String fileName, InputStream input, ObjectMetadata metadata) throws FileNotFoundException{
    	System.out.println(bucketName);
    	System.out.println(fileName);
    	//System.out.println(file);
        //String fileExt = file.getName().split("[.]")[1];
        this.s3Client.putObject(bucketName, fileName, input, metadata);
                //.withCannedAcl(CannedAccessControlList.PublicRead);
        String path = "https://버킷이름.s3.ap-northeast-2.amazonaws.com/"+fileName;
        System.out.println(path);
        return path;
    }
    /*
     *  S3에 이미지 업로드된 것 삭제
     */
    public void filedelete(String bucketName, String keyName) {
    	this.s3Client.deleteObject(bucketName, keyName);
    }
    
    /*
     * 프로필 이미지 업로드
     */
    public String profileUpload(String bucketName, String fileName, InputStream input, ObjectMetadata metadata) throws FileNotFoundException{
    	this.s3Client.putObject(bucketName, fileName, input, metadata);
    	String path = "https://버킷이름.s3.ap-northeast-2.amazonaws.com/"+fileName;
    	return path;
    }



}
