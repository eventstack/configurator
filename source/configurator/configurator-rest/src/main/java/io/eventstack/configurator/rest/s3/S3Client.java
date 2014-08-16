package io.eventstack.configurator.rest.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.StringInputStream;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 8/11/14.
 */
public class S3Client {
    private final AWSCredentials creds;
    private final String bucketName;
    private final Region s3region;

    private static final S3Client instance;

    static {
        String accessKey = System.getProperty("accessKey");
        String secret = System.getProperty("secretKey");
        String bucketName = System.getProperty("s3bucket");
        String s3region = System.getProperty("s3region");
        instance = new S3Client(accessKey, secret, bucketName, s3region);
    }

    public static S3Client getInstance() {
        return instance;
    }

    public S3Client(String accessKey, String secret, String bucketName, String region) {
        creds = new BasicAWSCredentials(accessKey, secret);
        this.bucketName = bucketName;
        s3region = Region.getRegion(Regions.valueOf(region));
    }

    public void writeMap(String key, Map<String, String> map) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(creds);
        s3.setRegion(s3region);
        ObjectMetadata metadata = new ObjectMetadata();
        String s = toJson(map);
        s3.putObject(new PutObjectRequest(bucketName, key, new StringInputStream(s), metadata));
    }

    public Map<String, String> read(String key) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(creds);
        s3.setRegion(s3region);

        S3Object obj = s3.getObject(new GetObjectRequest(bucketName, key));

        Map<String,String> map = getMapper().readValue(obj.getObjectContent(), Map.class);
        return map;
    }

    private static String toJson(Object o) throws IOException {
        return getMapper().writeValueAsString(o);
    }

    private static ObjectMapper getMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationConfig(mapper.getSerializationConfig()
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL));
        return mapper;
    }

    public static void main(String[] args) {
        S3Client client = S3Client.getInstance();

        Map<String, String> map = new HashMap<String, String>();
        map.put("hello", "world");
        try {
            client.writeMap("testapp/dev", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
