package com.douzi.dd.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {
    private static final String TAG = "BitmapUtils";
    public static float iZoomScaleNum;

    public static Bitmap loadBitmapFromFile(String aFileName) {

        if (aFileName == null) {
            return null;
        }
        Bitmap bitmap = null;
        File imageFile = new File(aFileName);
        if (imageFile != null && imageFile.exists()) {

            /**
             * Options mOptions = new Options(); mOptions.inPreferredConfig =
             * Config.ARGB_8888;
             */
            bitmap = BitmapFactory.decodeFile(aFileName);
        }

        return bitmap;
    }

    public static Bitmap cropBitmapInHeight(Bitmap bitmap, int height) {
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), height, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap cropBitmapWithStart(Bitmap bitmap, int start) {
        if (start >= bitmap.getHeight()) {
            return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, start, bitmap.getWidth(), bitmap.getHeight() - start, null, false);
    }

    public static void compressBitmap(Bitmap image, File file, int maxSize) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, stream);
        // Compress by loop
        while (stream.toByteArray().length / 1024 > maxSize) {
            // Clean up stream
            stream.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, stream);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * 图片按大小压缩
     *
     * @param bmp
     * @param file
     * @param ratio 尺寸压缩倍数,值越大，图片尺寸越小
     */
    public static void compressBitmapToFile(Bitmap bmp, File file, int ratio) {
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crops a circle out of the thumbnail photo.
     */
    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 设置一个图片大小的矩形
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        // bm是一个刚好canvas大小的空Bitmap ，画完后应该会自动保存到bm
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);

        int halfWidth = bitmap.getWidth() / 2;
        int halfHeight = bitmap.getHeight() / 2;
        // 画圆
        canvas.drawCircle(halfWidth, halfHeight, Math.max(halfWidth, halfHeight), paint);
        // 设置为取两层图像交集部门,只显示上层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 画图像
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static boolean saveByteArrayToFile(byte[] data, String aFileName) {
        if (data == null)
            return false;
        FileOutputStream outStream = null;
        boolean ret = false;
        try {
            outStream = new FileOutputStream(aFileName);
            outStream.write(data);
            outStream.close();
            ret = true;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
        return ret;
    }

    public static boolean saveBitmapToFile(Bitmap aBitmap, String aFileName, int aImageQuality,
                                           Bitmap.CompressFormat aForamt, boolean aRelease) {
        boolean ret = false;
        if (aBitmap != null) {

            File outFile = new File(aFileName);
            FileOutputStream outStream = null;

            if (outFile != null) {
                try {
                    new File(aFileName.substring(0, aFileName.lastIndexOf("/"))).mkdirs();
                    ret = outFile.createNewFile();
                } catch (IOException e) {
                    ret = false;
                    e.printStackTrace();
                }
            }

            if (ret) {

                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    ret = false;
                    e.printStackTrace();
                }
            }
            if (outStream != null) {

                ret = aBitmap.compress(aForamt, aImageQuality, outStream);
                try {
                    outStream.flush();
                } catch (IOException e) {
                    ret = false;
                    e.printStackTrace();
                }

                try {
                    outStream.close();
                } catch (IOException e) {
                    ret = false;
                    e.printStackTrace();
                }

                if (aRelease) {
                    releaseBitmap(aBitmap);
                }

            }
        }
        return ret;
    }

    /**
     * 获取图片的旋转角度
     *
     * @return 图片的旋转角度
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getBitmapDegree(byte[] data) {
        if (Build.VERSION.SDK_INT < 24) {
            return 0;
        }

        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(new ByteArrayInputStream(data));
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Throwable e) {
            degree = 0;
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotateBitmap(String aFileName, int rotate) {
        Bitmap bitmap = loadBitmapFromFile(aFileName);
        bitmap = rotateBitmap(bitmap, rotate);
        return bitmap;
    }

    public static Bitmap rotateBitmap(Bitmap bitMap, int rotate) {

        if (bitMap == null) {
            return null;
        }
        int w = bitMap.getWidth();
        int h = bitMap.getHeight();
        Bitmap retBitmap = null;
        Matrix matrix = new Matrix();
        boolean ret = matrix.postRotate(rotate);
        if (ret) {
            try {
                retBitmap = Bitmap.createBitmap(bitMap, 0, 0, w, h, matrix, true);

            } catch (Exception e) {
                e.printStackTrace();
            }

            ret = (retBitmap != null);
        }

        return retBitmap;
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        return zoomBitmap(bitmap, newWidth, newHeight, 0, 0);
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int newWidth, int newHeight, int aScaleStyle, int aRotate) {
        try {
            iZoomScaleNum = 1;
            if (bitmap == null || newWidth <= 0 || newHeight <= 0) {
                return null;
            }

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            if (aScaleStyle == 0) { // 按宽缩放
                scaleHeight = scaleWidth;
                iZoomScaleNum = scaleWidth;
            } else if (aScaleStyle == 1) { // 按高缩放
                scaleWidth = scaleHeight;
                iZoomScaleNum = scaleHeight;
            }

            boolean ret = matrix.postScale(scaleWidth, scaleHeight);
            ret = matrix.postRotate(aRotate);
            Bitmap retBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return retBitmap;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Bitmap getImagePart(String aPath1, String aPath2, int aLeft, int aTop, int aWidth, int aHeight,
                                      int aImageQuality, int aNewWidth, int aNewHeight, Bitmap.CompressFormat format) {
        if (aLeft < 0 || aTop < 0 || aWidth <= 0 || aHeight <= 0) {
            return null;
        }

        boolean ret = false;
        Bitmap bitmap = loadBitmapFromFile(aPath1);
        if (bitmap != null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            if ((aLeft + aWidth) <= w && (aTop + aHeight) <= h) {
                bitmap = Bitmap.createBitmap(bitmap, aLeft, aTop, aWidth, aHeight);
                if (bitmap != null) {
                    bitmap = zoomBitmap(bitmap, aNewWidth, aNewHeight, 0, 0);
                    if (bitmap != null) {
                        deleteFile(aPath2);
                        ret = saveBitmapToFile(bitmap, aPath2, aImageQuality, format, false);
                    }
                }
            }
        }
        return ret ? bitmap : null;
    }

    public static boolean deleteFile(String aPath) {
        boolean ret = true;
        if (aPath != null) {
            File f = new File(aPath);
            if (f != null && f.exists()) {
                ret = f.delete();
            }
        }
        return ret;
    }

    public static void releaseBitmap(Bitmap aBitmap) {
        if (aBitmap == null)
            return;
        try {
            if (!aBitmap.isRecycled()) {
                aBitmap.recycle();
                aBitmap = null;
                System.gc();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * give a round corner to original bitmap.
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null) {
            return null;
        }

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    /**
     * Utility function for decoding an image resource. The decoded bitmap will
     * be optimized for further scaling to the requested destination dimensions
     * and scaling logic.
     *
     * @param res          The resources object containing the image data
     * @param resId        The resource id of the image data
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Decoded bitmap
     */
    public static Bitmap decodeResource(Resources res, int resId, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight,
                scalingLogic);
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, resId, options);

        return unscaledBitmap;
    }

    /**
     * Utility function for creating a scaled version of an existing bitmap
     *
     * @param unscaledBitmap Bitmap to scale
     * @param dstWidth       Wanted width of destination bitmap
     * @param dstHeight      Wanted height of destination bitmap
     * @param scalingLogic   Logic to use to avoid image stretching
     * @return New scaled bitmap object
     */
    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight,
                                            ScalingLogic scalingLogic) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight,
                scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight,
                scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap, Bitmap.CompressFormat format) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(format, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * ScalingLogic defines how scaling should be carried out if source and
     * destination image has different aspect ratio.
     * <p/>
     * CROP: Scales the image the minimum amount while making sure that at least
     * one of the two dimensions fit inside the requested destination area.
     * Parts of the source image will be cropped to realize this.
     * <p/>
     * FIT: Scales the image the minimum amount while making sure both
     * dimensions fit inside the requested destination area. The resulting
     * destination dimensions might be adjusted to a smaller size than
     * requested.
     */
    public static enum ScalingLogic {
        CROP, FIT
    }

    /**
     * Calculate optimal down-sampling factor given the dimensions of a source
     * image, the dimensions of a destination area and a scaling logic.
     *
     * @param srcWidth     Width of source image
     * @param srcHeight    Height of source image
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal down scaling sample size for decoding
     */
    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                          ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcWidth / dstWidth;
            } else {
                return srcHeight / dstHeight;
            }
        } else {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcHeight / dstHeight;
            } else {
                return srcWidth / dstWidth;
            }
        }
    }

    /**
     * Calculates source rectangle for scaling bitmap
     *
     * @param srcWidth     Width of source image
     * @param srcHeight    Height of source image
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal source rectangle
     */
    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                        ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.CROP) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                final int srcRectWidth = (int) (srcHeight * dstAspect);
                final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
            } else {
                final int srcRectHeight = (int) (srcWidth / dstAspect);
                final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
                return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
            }
        } else {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
    }

    /**
     * Calculates destination rectangle for scaling bitmap
     *
     * @param srcWidth     Width of source image
     * @param srcHeight    Height of source image
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal destination rectangle
     */
    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                        ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
            } else {
                return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
            }
        } else {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, Bitmap.CompressFormat format, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(format, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 图片按质量压缩
     *
     * @param src
     * @param size 最终要压缩的大小,单位是KB
     * @return
     */
    public static Bitmap compressImage(Bitmap src, Bitmap.CompressFormat format, int size) {
        return BitmapFactory.decodeStream(compressImageToStream(src, format, size));
    }

    /**
     * 图片按质量压缩,返回流数据
     *
     * @param src
     * @param size 最终要压缩的大小,单位是KB
     * @return
     */
    public static InputStream compressImageToStream(Bitmap src, Bitmap.CompressFormat format, int size) {
        return new ByteArrayInputStream(compressImageToByteArray(src, format, size));
    }

    /**
     * 图片按质量压缩,返回byte数组
     *
     * @param src
     * @param size 最终要压缩的大小,单位是KB
     * @return
     */
    public static byte[] compressImageToByteArray(Bitmap src, Bitmap.CompressFormat format, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(format, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (true) {
            byte[] byteArray = baos.toByteArray();
            float len = byteArray.length * 1.0f / 1024;
            if (len > size && options > 10) {
                baos.reset();// 重置baos即清空baos
                options -= 10;// 每次都减少10
                src.compress(format, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                continue;
            } else {
                return byteArray;
            }
        }
    }

    /**
     * @param drawable :适用一切继承Drawable的对象，PictureDrawable,BitmapDrawable等等
     * @return
     * @author sumirrowu
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 从bitmap转换成流，注意这个默认是png的格式，如果不需要透明通道请用{@link BitmapUtils#bitmap2InputStream(Bitmap, Bitmap.CompressFormat)}
     * 并传入jpg, 能省很大空间！
     *
     * @param bitmap
     * @return
     */
    public static InputStream bitmap2InputStream(Bitmap bitmap) {
        return bitmap2InputStream(bitmap, Bitmap.CompressFormat.PNG);
    }

    /**
     * 从bitmap转换成流
     *
     * @param bitmap
     * @param format
     * @return
     */
    public static InputStream bitmap2InputStream(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static Bitmap readBitmapFromContentResolver(Context context, final Uri imageUri, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            options.inJustDecodeBounds = false;
            calcInSampleSize(maxWidth, maxHeight, options);
            inputStream.close();
            inputStream = context.getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream, null, options);

        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap readBitmapFromRawData(final byte[] raw, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(raw, 0, raw.length, options);
            options.inJustDecodeBounds = false;
            calcInSampleSize(maxWidth, maxHeight, options);
            return BitmapFactory.decodeByteArray(raw, 0, raw.length, options);

        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void calcInSampleSize(int maxWidth, int maxHeight, BitmapFactory.Options options) {
        int inSampleSize;
        if (options.outWidth < options.outHeight && maxWidth < maxHeight) {
            inSampleSize = calculateMaxInSampleSize(options, maxWidth, maxHeight);
        } else {
            inSampleSize = calculateMaxInSampleSize(options, maxHeight, maxWidth);
        }
        options.inSampleSize = inSampleSize;
    }


    private static int calculateMaxInSampleSize(
            BitmapFactory.Options options, int maxWidth, int maxHeight) {

        if (maxWidth == 0 || maxHeight == 0) {
            return 1;
        }

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > maxHeight || width > maxWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > maxHeight
                    || (halfWidth / inSampleSize) > maxWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 计算bitmap尺寸
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        // From KitKat onward use getAllocationByteCount() as allocated bytes can potentially be
        // larger than bitmap byte count.
        if (Build.VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }

        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }

        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 给图片加水印
     *
     * @param watermark
     * @param cover
     * @return
     */
    public static Bitmap addWatermark(Context context, Drawable watermark, Drawable cover) {
        int size;
        int screenDPI = DeviceUtil.getScreenDpi();

        if (screenDPI <= DisplayMetrics.DENSITY_MEDIUM) {
            size = 48;
        } else if (screenDPI <= DisplayMetrics.DENSITY_XHIGH) {
            size = 72;
        } else if (screenDPI <= DisplayMetrics.DENSITY_XXHIGH) {
            size = 96;
        } else {
            size = 144;
        }

        //创建ScaleBitmap,原因下载的封面图可能超大，需要压缩，这里根据写死的宽高scale
        Bitmap bm = BitmapUtils.createScaledBitmap(BitmapUtils.drawable2Bitmap(cover), size, size, ScalingLogic.CROP);
        Canvas c = new Canvas(bm);

        //画水印
        watermark.setBounds(0, 0, size, size);
        watermark.draw(c);
        return bm;
    }

    /**
     * 给图片加水印
     *
     * @param watermark
     * @param cover
     * @return
     */
    public static Bitmap drawDrawable2Drawable(Drawable watermark, Drawable cover) {
        Bitmap bitmap = Bitmap.createBitmap(cover.getIntrinsicWidth(), cover.getIntrinsicHeight(),
                cover.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        //构造原图大小的canvas
        Canvas c = new Canvas(bitmap);

        //先画原图
        cover.setBounds(0, 0, cover.getIntrinsicWidth(), cover.getIntrinsicHeight());
        cover.draw(c);
        //再画水印
        watermark.setBounds(0, 0, cover.getIntrinsicWidth(), cover.getIntrinsicHeight());
        watermark.draw(c);
        return bitmap;
    }


    public static Bitmap getBitmapInSampleSize(int resId, int inSampleSize, Context ctx) {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;//图片质量变差
//        options.inDither = false;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), resId, options);
        return bitmap;
    }
}
