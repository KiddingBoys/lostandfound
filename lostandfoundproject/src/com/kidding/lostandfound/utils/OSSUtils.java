package com.kidding.lostandfound.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.aliyun.mbaas.oss.OSSClient;
import com.aliyun.mbaas.oss.model.AccessControlList;
import com.aliyun.mbaas.oss.model.OSSException;
import com.aliyun.mbaas.oss.model.TokenGenerator;
import com.aliyun.mbaas.oss.storage.OSSBucket;
import com.aliyun.mbaas.oss.storage.OSSFile;
import com.aliyun.mbaas.oss.util.OSSToolKit;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OSSUtils {
	
	private static final String accessKey = "nsuEL7a6Fw5CumEo";
	private static final String secretKey = "xGlyEMiz1mMrIz0Us9jTVIo4ep6X3B";
	private static final String bucketName= "smartdresspic";
	
	
	private OSSBucket bucket;
	
	public OSSUtils(Context context)
	{
		OSSClient.setApplicationContext(context);
		bucket = new OSSBucket(bucketName);
	}
	
	@SuppressLint("SimpleDateFormat")
	public String uploadFile(String path) throws FileNotFoundException, OSSException
	{
		Date now = new Date();   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String fileKey=sdf.format(now)+"_"+String.valueOf(new Random().nextInt(899) + 100)+".jpg";

		OSSFile ossFile = new OSSFile(bucket,fileKey);
		ossFile.setUploadFilePath(path, "image/jpg");
		ossFile.upload();
		return "http://"+bucketName+".oss-cn-beijing.aliyuncs.com/"+fileKey;
	}

	public String uploadHead(String path, long id) throws FileNotFoundException, OSSException {
		String fileKey = "smartdress-user" + id + ".jpg";
		OSSFile ossFile = new OSSFile(bucket, fileKey);
		ossFile.setUploadFilePath(path, "image/jpg");
		ossFile.upload();
		
		return "http://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + fileKey;
	}
	
	public String uploadTempPic(String path) throws FileNotFoundException, OSSException {
		String fileKey = "smartdress" + path + ".jpg";
		OSSFile ossFile = new OSSFile(bucket, fileKey);
		ossFile.setUploadFilePath(path, "image/jpg");
		ossFile.upload();

		return "http://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + fileKey;
	}
	
	static {
        OSSClient.setGlobalDefaultTokenGenerator(new TokenGenerator() {
            @Override
            public String generateToken(String httpMethod, String md5, String type, String date,
                    String ossHeaders, String resource) {

                String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date + "\n" + ossHeaders
                        + resource;

                return OSSToolKit.generateToken(accessKey, secretKey, content);
            }
        });
        OSSClient.setGlobalDefaultACL(AccessControlList.PUBLIC_READ);
        OSSClient.setGlobalDefaultHostId("oss-cn-beijing.aliyuncs.com");
	}
}
