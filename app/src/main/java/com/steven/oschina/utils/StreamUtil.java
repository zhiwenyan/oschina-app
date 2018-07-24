package com.steven.oschina.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 * Data：7/23/2018-4:09 PM
 *
 * @author yanzhiwen
 */
public final class StreamUtil {
    public StreamUtil() {
    }

    public static void close(Closeable... closeables) {
        if (closeables != null && closeables.length != 0) {
            Closeable[] var1 = closeables;
            int var2 = closeables.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Closeable closeable = var1[var3];
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException var6) {
                        var6.printStackTrace();
                    }
                }
            }

        }
    }

    public static boolean copyFile(File srcFile, File saveFile) {
        File parentFile = saveFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            return false;
        } else {
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;

            boolean var6;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(srcFile));
                outputStream = new BufferedOutputStream(new FileOutputStream(saveFile));
                byte[] buffer = new byte[4096];

                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }

                outputStream.flush();
                return true;
            } catch (IOException var10) {
                var10.printStackTrace();
                var6 = false;
            } finally {
                close(inputStream, outputStream);
            }

            return var6;
        }
    }
}

