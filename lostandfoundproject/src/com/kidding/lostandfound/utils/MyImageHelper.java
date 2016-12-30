package com.kidding.lostandfound.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-11 下午9:08:12 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyImageHelper {

	private Intent intent;
	private Activity mActivity;
	private Bitmap photoBitmap = null;
	
    public static final int TAKE_A_PICTURE = 10;//拍照
    public static final int SELECT_A_PICTURE = 20;
    public static final int SET_PICTURE = 30;
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
    //判断是否为4.4以上版本
    static final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    
    public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/lostandfound/";
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
    private static final String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;
    private static final String IMAGE_FILE_NAME = "temp.jpeg";
    private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";
    
    
    public MyImageHelper(Activity mActivity){
    	this.mActivity = mActivity;
    }
	
	public void openCamera(){
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(mActivity,"没有sd卡", Toast.LENGTH_LONG).show();
			return;
		}
		
		new AlertDialog.Builder(mActivity).setTitle("选择图片")
        .setNegativeButton("图库", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mIsKitKat) {
                    selectImageUriAfterKikat();
                } else {
                    cropImageUri();
                }
            }
        }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    	intent = new Intent("android.media.action.IMAGE_CAPTURE");
		mActivity.startActivityForResult(intent, TAKE_A_PICTURE);
        Log.i("zou", "TAKE_A_PICTURE");
    }
}).show();
//		destoryBimap();
		

	}
	  @TargetApi(Build.VERSION_CODES.KITKAT)
	    private void selectImageUriAfterKikat() {
	        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
	        intent.addCategory(Intent.CATEGORY_OPENABLE);
	        intent.setType("image/*");
	        mActivity.startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
	    }
	    private void cropImageUri() {
	        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
	        intent.setType("image/*");
	        intent.putExtra("crop", "true");
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);
	        intent.putExtra("outputX", 640);
	        intent.putExtra("outputY", 640);
	        intent.putExtra("scale", true);
	        intent.putExtra("return-data", false);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT,
	                Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
	        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	        intent.putExtra("noFaceDetection", true); // no face detection
	        mActivity.startActivityForResult(intent, SELECT_A_PICTURE);
	    }
	
	/**
	 * android把拍摄的图片封装到bundle中传递回来
	 * 但是根据不同的机器获得相片的方式不太一样
	 * @return
	 */
	public String getPicPath(Intent data,int resultCode,ImageView imageView){
		Uri uri = data.getData();
		
		if (uri != null) {
			photoBitmap = BitmapFactory.decodeFile(uri.getPath());
		}
		if (photoBitmap == null) {
		Bundle bundle = data.getExtras();
		if (bundle != null) {
			photoBitmap = (Bitmap) bundle.get("data");
		} else {
		Toast.makeText(mActivity,"common_msg_get_photo_failure",
		Toast.LENGTH_LONG).show();
		return "";
		}
		}
		
		String pictureDir = "";
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null;
		try {
		baos = new ByteArrayOutputStream();
		photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] byteArray = baos.toByteArray();
	
		File dir = new File(ACCOUNT_DIR);
		if (!dir.exists()) {
		dir.mkdir();
		}
		File file = new File(ACCOUNT_DIR, IMAGE_FILE_NAME);
		file.delete();
		if (!file.exists()) {
		file.createNewFile();
		}
		fos = new FileOutputStream(file);
		bos = new BufferedOutputStream(fos);
		bos.write(byteArray);
		pictureDir = file.getPath();
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		if (baos != null) {
		try {
		baos.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		if (bos != null) {
		try {
		bos.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		if (fos != null) {
		try {
		fos.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		}
		
		imageView.setImageBitmap(photoBitmap);
		return pictureDir;
	}
	
}
