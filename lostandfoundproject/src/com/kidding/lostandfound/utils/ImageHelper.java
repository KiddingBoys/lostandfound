package com.kidding.lostandfound.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Author: Administrator
 * Time: 2016-04-10.
 */
public class ImageHelper {

    private Activity context;
    private ImageView imageView;
    private int requestCode;
    private int resultCode;
    private Intent data;

   
    public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/account/";
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
    private static final String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;

    private static final String IMAGE_FILE_NAME = "faceImage.jpeg";
    private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";

    //锟斤拷锟斤拷锟斤拷锟斤拷
    public static final int TAKE_A_PICTURE = 10;
    public static final int SELECT_A_PICTURE = 20;
    public static final int SET_PICTURE = 30;
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
    File fileone = null;
    File filetwo = null;

    //锟芥本锟饺较ｏ拷锟角凤拷锟斤拷4.4锟斤拷锟斤拷锟较版本
    static final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public ImageHelper(){}
    public ImageHelper(Activity context, ImageView imageView, int requestCode, int resultCode, Intent data) {
        this.context = context;
        this.imageView = imageView;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;

        File directory = new File(ACCOUNT_DIR);
        File imagepath = new File(IMGPATH);
        if (!directory.exists()) {
            Log.i("zou", "directory.mkdir()");
            directory.mkdir();
        }
        if (!imagepath.exists()) {
            Log.i("zou", "imagepath.mkdir()");
            imagepath.mkdir();
        }

        fileone = new File(IMGPATH, IMAGE_FILE_NAME);
        filetwo = new File(IMGPATH, TMP_IMAGE_FILE_NAME);

        try {
            if (!fileone.exists() && !filetwo.exists()) {
                fileone.createNewFile();
                filetwo.createNewFile();
            }
        } catch (Exception e) {}

    }

    //选择打开图片方式
    public void openPhoto(final Activity activity){
        new AlertDialog.Builder(activity).setTitle("选择图片")
                .setNegativeButton("图库", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mIsKitKat) {
                            selectImageUriAfterKikat(activity);
                        } else {
                            cropImageUri(activity);
                        }
                    }
                }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
                activity.startActivityForResult(intent, TAKE_A_PICTURE);
                Log.i("zou", "TAKE_A_PICTURE");
            }
        }).show();

    }

    public String getImgPath(){

        String mAlbumPicturePath = "";
        if (requestCode == SELECT_A_PICTURE) {
            if (resultCode == Activity.RESULT_OK && null != data) {

                Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH,
                        TMP_IMAGE_FILE_NAME)));
                imageView.setImageBitmap(bitmap);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this.context, "取消选择", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                //Log.i("zou", "4.4锟斤拷锟较碉拷");
                mAlbumPicturePath = getPath(this.context.getApplicationContext(), data.getData());
                cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this.context, "取消选择", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
            Log.i("zou", "4.4锟斤拷锟斤拷锟较碉拷 RESULT_OK");

            Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
            imageView.setImageBitmap(bitmap);


        } else if (requestCode == TAKE_A_PICTURE) {
            Log.i("zou", "TAKE_A_PICTURE-resultCode:" + resultCode);
            if (resultCode == Activity.RESULT_OK) {
                cameraCropImageUri(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
            } else {
                Toast.makeText(this.context, "取消选择", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SET_PICTURE) {
            //锟斤拷锟秸碉拷锟斤拷锟斤拷头锟斤拷  锟斤拷锟斤拷锟角版本
            //			Log.i("zou", "SET_PICTURE-resultCode:" + resultCode);
            Bitmap bitmap = null;
            //			if (mIsKitKat) {  //锟竭版本
            //				if (null != data) {
            //					bitmap = data.getParcelableExtra("data");
            //					showLoading();
            //					mAccountControl.resetGoUserIcon(bitmap2byte(bitmap), this);
            //				} else {  //锟竭版本锟斤拷锟斤拷通锟斤拷锟斤拷data锟斤拷锟斤拷取锟斤拷图片锟斤拷锟捷的撅拷锟斤拷uri
            //					if (resultCode == RESULT_OK) {
            //						bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
            //						showLoading();
            //						mAccountControl.resetGoUserIcon(bitmap2byte(bitmap), this);
            //					}
            //				}
            //			} else {  //锟酵版本
            if (resultCode == Activity.RESULT_OK && null != data) {
                bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
                imageView.setImageBitmap(bitmap);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this.context, "取消选择", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.context, "取消选择error", Toast.LENGTH_SHORT).show();
            }
            //			}
        }


        return mAlbumPicturePath;
    }






    /** <br>锟斤拷锟杰硷拷锟斤拷:锟矫硷拷图片锟斤拷锟斤拷实锟斤拷---------------------- 锟斤拷锟�
     * <br>锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷:
     * <br>注锟斤拷:
     */
    private void cropImageUri(Activity activity) {
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
        activity.startActivityForResult(intent, SELECT_A_PICTURE);
    }


    /**
     *  <br>锟斤拷锟杰硷拷锟斤拷:4.4锟斤拷锟较裁硷拷图片锟斤拷锟斤拷实锟斤拷---------------------- 锟斤拷锟�
     * <br>锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷:
     * <br>注锟斤拷:
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
    }

    /**
     * <br>锟斤拷锟杰硷拷锟斤拷:锟矫硷拷图片锟斤拷锟斤拷实锟斤拷----------------------锟斤拷锟�
     * <br>锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷:
     * <br>注锟斤拷:
     * @param uri
     */
    private void cameraCropImageUri(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        //		if (mIsKitKat) {
        //			intent.putExtra("return-data", true);
        //			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //		} else {
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //		}
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        this.context.startActivityForResult(intent, SET_PICTURE);
    }

    /**
     * <br>锟斤拷锟杰硷拷锟斤拷: 4.4锟斤拷锟斤拷锟较改讹拷锟斤拷眉锟酵计拷锟斤拷锟绞碉拷锟� --------------------锟斤拷锟�
     * <br>锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷:
     * <br>注锟斤拷:
     * @param uri
     */
    private void cropImageUriAfterKikat(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        //		intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        this.context.startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }

    /**
     * <br>锟斤拷锟杰硷拷锟斤拷:
     * <br>锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷:
     * <br>注锟斤拷:
     * @param uri
     * @return
     */
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(this.context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * <br>锟斤拷锟杰硷拷锟斤拷:4.4锟斤拷锟斤拷锟较伙拷取图片锟侥凤拷锟斤拷
     * <br>锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷:
     * <br>注锟斤拷:
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
