package com.steven.oschina.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 * Data：7/23/2018-4:08 PM
 *
 * @author yanzhiwen
 */
public final class BitmapUtil {
    public static final int DEFAULT_BUFFER_SIZE = 65536;

    public BitmapUtil() {
    }

    public static BitmapFactory.Options createOptions() {
        return new BitmapFactory.Options();
    }

    @TargetApi(11)
    public static void resetOptions(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        if (11 <= Build.VERSION.SDK_INT) {
            options.inBitmap = null;
            options.inMutable = true;
        }

    }

    public static String getExtension(String filePath) {
        BitmapFactory.Options options = createOptions();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        String mimeType = options.outMimeType;
        return mimeType.substring(mimeType.lastIndexOf("/") + 1);
    }

    public static Bitmap decodeBitmap(File file, int maxWidth, int maxHeight, byte[] byteStorage, BitmapFactory.Options options, boolean exactDecode) {
        BufferedInputStream is;
        try {
            is = new BufferedInputStream(new FileInputStream(file), byteStorage == null ? 65536 : byteStorage.length);
        } catch (FileNotFoundException var9) {
            var9.printStackTrace();
            return null;
        }

        if (options == null) {
            options = createOptions();
        } else {
            resetOptions(options);
        }

        options.inJustDecodeBounds = true;
        is.mark(5242880);
        BitmapFactory.decodeStream(is, (Rect )null, options);

        try {
            is.reset();
        } catch (IOException var8) {
            var8.printStackTrace();
            StreamUtil.close(new Closeable[]{is});
            resetOptions(options);
            return null;
        }

        calculateScaling(options, maxWidth, maxHeight, exactDecode);
        if (byteStorage == null) {
            byteStorage = new byte[65536];
        }

        options.inTempStorage = byteStorage;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, (Rect)null, options);
        StreamUtil.close(new Closeable[]{is});
        resetOptions(options);
        bitmap = scaleBitmap(bitmap, maxWidth, maxHeight, true);
        return bitmap;
    }

    public static Bitmap scaleBitmap(Bitmap source, float scale, boolean recycleSource) {
        if (scale > 0.0F && scale < 1.0F) {
            Matrix m = new Matrix();
            int width = source.getWidth();
            int height = source.getHeight();
            m.setScale(scale, scale);
            Bitmap scaledBitmap = Bitmap.createBitmap(source, 0, 0, width, height, m, false);
            if (recycleSource) {
                source.recycle();
            }

            return scaledBitmap;
        } else {
            return source;
        }
    }

    public static Bitmap scaleBitmap(Bitmap source, int targetMaxWidth, int targetMaxHeight, boolean recycleSource) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        Bitmap scaledBitmap = source;
        if (sourceWidth > targetMaxWidth || sourceHeight > targetMaxHeight) {
            float minScale = Math.min((float)targetMaxWidth / (float)sourceWidth, (float)targetMaxHeight / (float)sourceHeight);
            scaledBitmap = Bitmap.createScaledBitmap(source, (int)((float)sourceWidth * minScale), (int)((float)sourceHeight * minScale), false);
            if (recycleSource) {
                source.recycle();
            }
        }

        return scaledBitmap;
    }

    private static BitmapFactory.Options calculateScaling(BitmapFactory.Options options, int requestedMaxWidth, int requestedMaxHeight, boolean exactDecode) {
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        if (sourceWidth <= requestedMaxWidth && sourceHeight <= requestedMaxHeight) {
            return options;
        } else {
            float maxFloatFactor = Math.max((float)sourceHeight / (float)requestedMaxHeight, (float)sourceWidth / (float)requestedMaxWidth);
            int maxIntegerFactor = (int)Math.floor((double)maxFloatFactor);
            int lesserOrEqualSampleSize = Math.max(1, Integer.highestOneBit(maxIntegerFactor));
            options.inSampleSize = lesserOrEqualSampleSize;
            if (exactDecode && Build.VERSION.SDK_INT >= 19) {
                float scaleSize = (float)sourceWidth / (float)lesserOrEqualSampleSize;
                float outSize = (float)sourceWidth / maxFloatFactor;
                options.inTargetDensity = 1000;
                options.inDensity = (int)((double)(1000.0F * (scaleSize / outSize)) + 0.5D);
                if (options.inTargetDensity != options.inDensity) {
                    options.inScaled = true;
                } else {
                    options.inDensity = options.inTargetDensity = 0;
                }
            }

            return options;
        }
    }

    public static final class Compressor {
        public Compressor() {
        }

        public static File compressImage(File sourceFile, long maxSize, int minQuality, int maxWidth, int maxHeight) {
            return compressImage(sourceFile, maxSize, minQuality, maxWidth, maxHeight, true);
        }

        public static File compressImage(File sourceFile, long maxSize, int minQuality, int maxWidth, int maxHeight, boolean exactDecode) {
            return compressImage(sourceFile, maxSize, minQuality, maxWidth, maxHeight, (byte[])null, (BitmapFactory.Options )null, exactDecode);
        }

        public static File compressImage(File sourceFile, long maxSize, int minQuality, int maxWidth, int maxHeight, byte[] byteStorage, BitmapFactory.Options options, boolean exactDecode) {
            if (sourceFile != null && sourceFile.exists() && sourceFile.canRead()) {
                File tempFile = new File(sourceFile.getParent(), String.format("compress_%s.temp", System.currentTimeMillis()));
                if (!tempFile.exists()) {
                    try {
                        if (!tempFile.createNewFile()) {
                            return null;
                        }
                    } catch (IOException var22) {
                        var22.printStackTrace();
                        return null;
                    }
                }

                Bitmap bitmap = BitmapUtil.decodeBitmap(sourceFile, maxWidth, maxHeight, byteStorage, options, exactDecode);
                if (bitmap == null) {
                    return null;
                } else {
                    Bitmap.CompressFormat compressFormat = bitmap.hasAlpha() ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
                    boolean isOk = false;

                    for(int i = 1; i <= 10; ++i) {
                        int quality = 92;

                        while(true) {
                            BufferedOutputStream outputStream = null;

                            label139: {
                                Object var17;
                                try {
                                    outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
                                    bitmap.compress(compressFormat, quality, outputStream);
                                    break label139;
                                } catch (IOException var23) {
                                    var23.printStackTrace();
                                    bitmap.recycle();
                                    var17 = null;
                                } finally {
                                    StreamUtil.close(new Closeable[]{outputStream});
                                }

                                return (File )var17;
                            }

                            long var16 = tempFile.length();
                            if (var16 <= maxSize) {
                                isOk = true;
                                break;
                            }

                            if (quality < minQuality) {
                                break;
                            }

                            --quality;
                        }

                        if (isOk) {
                            break;
                        }

                        bitmap = BitmapUtil.scaleBitmap(bitmap, 1.0F - 0.2F * (float)i, true);
                    }

                    bitmap.recycle();
                    return !isOk ? null : tempFile;
                }
            } else {
                return null;
            }
        }
    }
}
