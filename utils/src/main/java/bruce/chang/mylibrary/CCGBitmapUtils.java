package bruce.chang.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by: BruceChang
 * Date on : 2017/4/13.
 * Time on: 17:07
 * Progect_Name:MyCommonUtils
 * Source Github：
 * Description:  Bitmap操作的工具类
 */

public class CCGBitmapUtils {


    /**
     * Drawable 转换成 Bitmap
     *
     * @param drawable 源Drawable对象
     * @return null or 生成的 Bitmap 对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * Bitmap 转换成 Drawable
     *
     * @param bitmap 源Bitmap对象
     * @return null or 生成的 Drawable 对象
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(bitmap);
    }

    /**
     * 缩放Bitmap
     *
     * @param org       源Bitmap对象
     * @param newWidth  缩放后 Bitmap 的宽度，整形格式
     * @param newHeight 缩放后 Bitmap 的高度，整形格式
     * @return 缩放后生成的 Bitmap 对象
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * 缩放Bitmap
     *
     * @param src         源Bitmap对象
     * @param scaleWidth  缩放的比例，新宽度/源Bitmap宽度
     * @param scaleHeight 缩放的比例，新高度/源Bitmap高度
     * @return 缩放后生成的 Bitmap 对象
     */
    public static Bitmap scaleImage(Bitmap src, float scaleWidth, float scaleHeight) {
        if (src == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //根据缩放生成的  Matrix 矩阵来生成新的  Bitmap
        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bitmap;
    }

    /**
     * 生成圆形 Bitmap 对象
     *
     * @param bitmap 源Bitmap对象
     * @return 圆形 Bitmap 对象
     */
    public static Bitmap toCircleBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 生成bitmap缩略图
     *
     * @param bitmap      源Bitmap对象
     * @param needRecycle 是否释放源Bitmap
     * @param newHeight   目标宽度,小于源 Bitmap对象的宽度
     * @param newWidth    目标高度
     * @return 缩略图 Bitmap 对象
     */
    public static Bitmap createBitmapThumbnail(Bitmap bitmap, boolean needRecycle, int newHeight, int newWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;//一个小于0的数值
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (needRecycle)
            bitmap.recycle();
        return newBitMap;
    }

    /**
     * 保存Bitmap到文件,压缩质量为100，也即无损压缩
     *
     * @param bitmap 源Bitmap对象
     * @param target 目标文件
     */
    public static void saveBitmap(Bitmap bitmap, File target) {
        if (target.exists()) {
            target.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(target);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存Bitmap到文件，指定压缩质量
     *
     * @param bitmap  源Bitmap对象
     * @param quality 保存质量 0..100
     * @param target  目标文件
     */
    public static void saveBitmap(Bitmap bitmap, int quality, File target) {
        if (target.exists()) {
            target.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(target);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩
     *
     * @param bitmap      源Bitmap对象
     * @param needRecycle 是否释放源Bitmap占有的资源，减少内存消耗
     * @param maxSize     质量压缩的最大尺寸
     * @return 质量压缩后生成的 Bitmap
     */
    public static Bitmap compressBitmapWithQuality(Bitmap bitmap, boolean needRecycle, long maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length > maxSize) {
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bm = BitmapFactory.decodeStream(isBm, null, null);
        if (needRecycle) {
            bitmap.recycle();
        }
        bitmap = bm;
        return bitmap;
    }

    /**
     * 宽高等比缩放,其实和缩放图片、生成缩略图一样
     *
     * @param bitmap      源Bitmap对象
     * @param needRecycle 是否释放源Bitmap占有的资源，减少内存消耗
     * @param targetWidth 目标宽度,
     * @param targeHeight 目标高度,
     * @return 等比缩放后 的 Bitmap对象
     */
    public static Bitmap compressBitmapWithBiLi(Bitmap bitmap, boolean needRecycle, int targetWidth, int targeHeight) {
        float sourceWidth = bitmap.getWidth();
        float sourceHeight = bitmap.getHeight();
        //长和宽放大缩小的比例
        float scaleWidth = targetWidth / sourceWidth;
        float scaleHeight = targeHeight / sourceHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (needRecycle) {
            bitmap.recycle();
        }
        bitmap = bm;
        return bitmap;
    }

    /**
     * 混合压缩,不保存图片
     *
     * @param imageFile       原始图片路径
     * @param qualityCompress 是否进行质量压缩
     * @param maxSize         质量压缩的最大size
     * @param targetWidth     目标宽度
     * @param targeHeight     目标高度
     * @return Bitmap
     */
    public static Bitmap compressBitmap(String imageFile, boolean qualityCompress, long maxSize, int targetWidth, int targeHeight) {
        return compressBitmapWithThree(imageFile, null, false, qualityCompress, maxSize, targetWidth, targeHeight);
    }

    /**
     * 三大混合压缩核心功能
     * (执行步骤sampleSize(宽高压缩)压缩->等比例压缩->质量压缩)
     *
     * @param imageFile       源图片路径
     * @param targetFile      保存目标文件地址，为空表示源地址保存
     * @param isSave          是否保存图片，如果需要保存，当targetFile路径为空的时候，就用源图片路径作为保存的图片路径
     * @param qualityCompress 压缩选中的质量
     * @param maxSize         质量压缩的最大size
     * @param targetWidth     目标宽度
     * @param targeHeight     目标高度
     * @return 混合压缩后的 Bitmap
     */
    private static Bitmap compressBitmapWithThree(String imageFile, String targetFile, boolean isSave, boolean qualityCompress, long maxSize, int targetWidth, int targeHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options); //加载图片信息
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        //计算inSampleSize
        int inSampleSize = 1;
        //先根据宽度进行缩小
        while (sourceWidth / inSampleSize > targetWidth) {
            inSampleSize++;
        }
        //然后根据高度进行缩小
        while (sourceHeight / inSampleSize > targeHeight) {
            inSampleSize++;
        }

        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        //加载真正bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile, options);
        //等比缩放
        bitmap = compressBitmapWithBiLi(bitmap, false, targetWidth, targeHeight);
        if (qualityCompress) {
            bitmap = compressBitmapWithQuality(bitmap, true, maxSize); //压缩质量
        }
        //是否需要保存
        if (isSave) {
            String savePath = imageFile;
            if (!CCGStringUtils.isEmpty(targetFile)) {
                savePath = targetFile;
            }

            saveBitmap(bitmap, new File(savePath));//保存图片
        }

        return bitmap;
    }

    /**
     * 混合压缩  保存图片
     *
     * @param imageFile       原始图片路径
     * @param qualityCompress 是否进行质量压缩
     * @param maxSize         质量压缩的最大size
     * @param targetWidth     目标宽度
     * @param targeHeight     目标高度
     */
    public static void compressImage(String imageFile, boolean qualityCompress, long maxSize, int targetWidth, int targeHeight) {
        compressImage(imageFile, null, qualityCompress, maxSize, targetWidth, targeHeight);
    }

    /**
     * 压缩某张图片  保存图片
     * (执行步骤sampleSize压缩->等比压缩->质量压缩)
     *
     * @param imageFile       原始图片路径
     * @param targetFile      保存目标文件地址，为空表示源地址保存
     * @param qualityCompress 是否做质量压缩
     * @param maxSize         目标图片大小
     * @param targetWidth     目标宽度
     * @param targeHeight     目标高度
     */
    public static void compressImage(String imageFile, String targetFile, boolean qualityCompress, long maxSize, int targetWidth, int targeHeight) {
        Bitmap bitmap = compressBitmapWithThree(imageFile, targetFile, true, qualityCompress, maxSize, targetWidth, targeHeight);
        bitmap.recycle();
    }


    /**
     * 图片缩放-等比压缩
     *
     * @param imageFile   原始图片路径
     * @param targetWidth 目标宽度
     * @param targeHeight 目标高度
     */
    public static void compressImage(String imageFile, int targetWidth, int targeHeight) {
        compressImage(imageFile, null, false, 0L, targetWidth, targeHeight);
    }


    /**
     * 图片缩放-等比压缩，不保存图片
     *
     * @param imageFile   原始图片路径
     * @param targetWidth 目标宽度
     * @param targeHeight 目标高度
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressBitmap(String imageFile, int targetWidth, int targeHeight) {
        return compressBitmap(imageFile, false, 0L, targetWidth, targeHeight);
    }

    /**
     * 图片缩放-尺寸缩放
     *
     * @param imageFile 原始图片路径
     * @param scale     图片缩小倍速
     */
    public static void compressImageSmall(String imageFile, int scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        int targetWidth = options.outWidth / scale;
        int targeHeight = options.outHeight / scale;
        compressImage(imageFile, targetWidth, targeHeight);
    }

    /**
     * 图片缩放-尺寸缩放
     *
     * @param imageFile 原始图片路径
     * @param scale     图片缩小倍速
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressBitmapSmall(String imageFile, int scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        int targetWidth = options.outWidth / scale;
        int targeHeight = options.outHeight / scale;
        return compressBitmap(imageFile, targetWidth, targeHeight);
    }

    /**
     * 图片缩放-尺寸缩放
     *
     * @param imageFile 原始图片路径
     * @param scale     图片放大倍速
     */
    public static void compressImageBig(String imageFile, int scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        int targetWidth = options.outWidth * scale;
        int targeHeight = options.outHeight * scale;
        compressImage(imageFile, targetWidth, targeHeight);
    }

    /**
     * 图片缩放-尺寸缩放
     *
     * @param imageFile 原始图片路径
     * @param scale     图片放大倍速
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressBitmapBig(String imageFile, int scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        int targetWidth = options.outWidth * scale;
        int targeHeight = options.outHeight * scale;
        return compressBitmap(imageFile, targetWidth, targeHeight);
    }

    /**
     * 质量压缩图片
     *
     * @param imageFile
     * @param targetFile
     * @param qualityCompress
     * @param maxSize
     */
    public static void compressImage(String imageFile, String targetFile, boolean qualityCompress, long maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        int targetWidth = options.outWidth / 2;
        int targeHeight = options.outHeight / 2;
        compressImage(imageFile, targetFile, qualityCompress, maxSize, targetWidth, targeHeight);
    }

    /**
     * 质量压缩图片
     *
     * @param imageFile
     * @param qualityCompress
     * @param maxSize
     */
    public static void compressImage(String imageFile, boolean qualityCompress, long maxSize) {
        compressImage(imageFile, null, qualityCompress, maxSize);
    }

    /**
     * 质量压缩图片
     *
     * @param imageFile
     * @param qualityCompress
     * @param maxSize
     * @return
     */
    public static Bitmap compressBitmap(String imageFile, boolean qualityCompress, long maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        int targetWidth = options.outWidth / 2;
        int targeHeight = options.outHeight / 2;
        return compressBitmap(imageFile, qualityCompress, maxSize, targetWidth, targeHeight);
    }

    /**
     * 质量压缩图片-压缩在maxSize以内
     *
     * @param imageFile
     * @param maxSize
     */
    public static void compressImage(String imageFile, long maxSize) {
        compressImage(imageFile, true, maxSize);
    }

    /**
     * 质量压缩图片-压缩在maxSize以内
     *
     * @param imageFile
     * @param maxSize
     * @return
     */
    public static Bitmap compressBimap(String imageFile, long maxSize) {
        return compressBitmap(imageFile, true, maxSize);
    }

    /**
     * 质量压缩图片-压缩在1M以内
     *
     * @param imageFile
     */
    public static void compressImage(String imageFile) {
        compressImage(imageFile, true, (long) (1 * CCGFileUtils.MB));
    }

    /**
     * 质量压缩图片-压缩在1M以内
     *
     * @param imageFile
     * @return
     */
    public static Bitmap compressBitmap(String imageFile) {
        return compressBitmap(imageFile, true, (long) (1 * CCGFileUtils.MB));
    }

    /**
     * 旋转bitmap
     *
     * @param bitmap
     * @param degress     旋转角度
     * @param needRecycle
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress, boolean needRecycle) {
        Matrix m = new Matrix();
        m.postRotate(degress);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        if (needRecycle) {
            bitmap.recycle();
        }
        return bm;
    }

    /**
     * 根据path
     *
     * @param path
     * @return
     */
    public final static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
