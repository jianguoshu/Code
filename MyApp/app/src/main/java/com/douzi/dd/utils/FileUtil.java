package com.douzi.dd.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 文件操作工具
 *
 * @author lvxiangnan
 */
public class FileUtil {


    private static final String TAG = "FileUtil";


    public static void copyStream(String fileOut, InputStream inputStream) throws IOException {
        FileOutputStream fileOutputStream = openOutputStream(new File(fileOut), false);
        copyStream(fileOutputStream, inputStream);
    }

    /**
     * 字节流读写复制文件
     */
    public static void copyStream(OutputStream outputStream, InputStream inputStream) throws IOException {
        try {
            byte[] bytes = new byte[1024];
            int num = 0;
            while ((num = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, num);
                outputStream.flush();
            }
        } finally {
            close(outputStream);
            close(inputStream);
        }

    }

    /**
     * @param path
     * @param fileName
     * @return
     */
    public byte[] getByteFromFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (!file.exists()) {
            return null;
        }
        ByteArrayOutputStream bo = null;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            bo = new ByteArrayOutputStream();
            byte[] buffer = new byte[256];
            int readLen = -1;
            while ((readLen = stream.read(buffer)) != -1) {
                bo.write(buffer, 0, readLen);
                buffer = new byte[256];
            }
            stream.close();
            file = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bo == null ? null : bo.toByteArray();
    }

    /**
     * 根据文件名称和路径获取文件
     *
     * @param path
     * @param fileName
     * @return
     */
    public File getFileByPath(String path, String fileName) {
        File file = new File(path, fileName);
        if (!file.exists())
            return null;
        return file;
    }

    /**
     * 检测sd卡是否可用
     */
    public static boolean isSDcardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取挂载路径
     *
     * @return
     * @deprecated by sumirrowu
     */
    public static String getExternalStoragePath() {
        // Environment.getExternalStorageDirectory().getPath();

        boolean bExists = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        String path = null;
        if (bExists) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (path == null) {
                return "/";
            }
            if ("/mnt/flash".equalsIgnoreCase(path)) {
                path = "/mnt/sdcard";
                File file = new File(path);
                if (!file.exists()) {
                    path = "/sdcard";
                    file = new File(path);
                    if (!file.exists()) {
                        path = "/";
                    }
                }
            }
            return path;
        } else {
            return "/";
        }
    }

    /**
     * 一个文件是否存在
     *
     * @param path 文件路径
     * @return
     */
    public static boolean hasFile(String path) {
        return new File(path).exists();
    }

    /**
     * 为重复文件生成后缀 例如: SogouSearch(1).apk,SogouSearch(2).apk
     *
     * @param filePath
     * @param fileName
     * @param fileType
     * @return
     */
    public static String genFileNameWithSuffix(String filePath, String fileName, String fileType) {
        String targetFileName = fileName;

        // 检测文件是否存在，如果存在，则添加后缀
        int tryTimes = 1;
        File tempFile = new File(filePath + targetFileName + fileType);
        while (tempFile.exists()) {
            targetFileName = fileName + "(" + tryTimes + ")";
            tempFile = new File(filePath + targetFileName + fileType);
            tryTimes++;
        }

        return targetFileName;
    }

    /**
     * check the size can be used
     *
     * @return
     */
    public static long getAvailableBytes() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        if (sdcardPath != null) {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            // put a bit of margin (in case creating the file grows the system
            // by a few blocks)
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            return stat.getBlockSize() * availableBlocks;
        } else {
            return 0;
        }
    }

    // 检测文件是否存在
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.isFile() && file.exists();
    }

    // 检测目录是否存在
    public static boolean isDirectoryExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.isDirectory() && file.exists();
    }

    // 以字符的方式读取文件
    public static String readStringFromFile(String path) {
        if (!isFileExist(path)) {
            return "";
        }
        File file = new File(path);
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                content.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }

        }
        return content.toString();
    }

    public static String getFolderSizeString(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "0 KB";
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return "0 KB";
        }

        return getFormatSize(getFolderSize(file));
    }

    public static long getFolderSize(java.io.File file) {
        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0 KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " TB";
    }

    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void deleteDir(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return;
        }

        try {
            File file = new File(dir);
            if (file.exists()) {
                File[] files = file.listFiles();
                if (null == files) {
                    file.delete();
                } else {
                    for (File item : files) {
                        item.delete();
                        item = null;
                    }
                }
                file.delete();
            }
            file = null;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public synchronized static void deleteFile(String filePathName) {
        if (TextUtils.isEmpty(filePathName)) {
            return;
        }

        File file = new File(filePathName);
        if (file.exists()) {
            file.delete();
        }
        file = null;
    }

    public synchronized static void deleteFile(String dir, String fileName) {
        if (TextUtils.isEmpty(dir) || TextUtils.isEmpty(fileName)) {
            return;
        }

        File file = new File(new File(dir), fileName);
        if (file.exists()) {
            file.delete();
        }
        file = null;
    }

    // 如果文件大写超过maxSize，则不写入
    public static void writeFile(String filePath, String content, boolean isAppend, long maxSize) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        File file = new File(filePath);
        if (file.exists() && file.length() >= maxSize) {
            return;
        }
        writeFile(filePath, content, isAppend);
    }

    public static void writeFile(String filePath, String content, boolean isAppend) {
        if (filePath == null) {
            return;
        }
        if (content == null) {
            return;
        }
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, isAppend);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String inputStream2String(InputStream inStream, String charsetName) {

        byte[] buf = inputStream2Bytes(inStream);

        if (null == buf) {
            return null;
        }

        return bytes2String(buf, charsetName);
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    public static String convertInputStreamToString(InputStream is) {
        String result = null;
        final int BUFFER_SIZE = 1024;
        byte[] readBuf = new byte[BUFFER_SIZE];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            int len = -1;
            while ((len = is.read(readBuf)) != -1) {
                bos.write(readBuf, 0, len);
            }
            result = new String(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] inputStream2Bytes(InputStream inStream) {
        final int BUFFER_SIZE = 1024;
        byte[] readBuf = new byte[BUFFER_SIZE];
        int count = 0;
        byte[] retBuf = null;

        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();

            while ((count = inStream.read(readBuf, 0, BUFFER_SIZE)) > 0) {
                baos.write(readBuf, 0, count);
            }

            if (baos.size() > 0) {
                retBuf = baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                baos = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        readBuf = null;

        return retBuf;
    }

    public static String bytes2String(byte[] inBuf, String charsetName) {
        try {
            return new String(inBuf, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject inputStream2Json(InputStream inStream, String charsetName) {
        byte[] buf = inputStream2Bytes(inStream);
        if (null == buf)
            return null;

        JSONObject json = null;
        try {
            json = new JSONObject(new String(buf, charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * 写文件
     *
     * @param path
     * @param filename
     * @param data
     * @return
     */
    public void writeFile(String path, String filename, byte[] data) {
        File file = new File(path, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (null != fos) {
                fos.write(data);
                fos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fos = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file = null;
    }

    /**
     * 把数据写入文件
     *
     * @param is       数据流
     * @param path     文件路径
     * @param recreate 如果文件存在，是否需要删除重建
     * @return 是否写入成功
     */
    public static boolean writeFile(InputStream is, String path, boolean recreate) {
        boolean res = false;
        File f = new File(path);
        FileOutputStream fos = null;
        try {
            if (recreate && f.exists()) {
                f.delete();
            }
            if (!f.exists() && null != is) {
                File parentFile = new File(f.getParent());
                parentFile.mkdirs();
                int count = -1;
                byte[] buffer = new byte[1024];
                fos = new FileOutputStream(f);
                while ((count = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fos);
            close(is);
        }
        return res;
    }

    /**
     * 改名
     */
    public static boolean copy(String src, String des, boolean delete) {
        if (TextUtils.isEmpty(src)) {
            return false;
        }

        File file = new File(src);
        if (!file.exists()) {
            return false;
        }
        File desFile = new File(des);

        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(in);
            close(out);
        }
        if (delete) {
            file.delete();
        }
        return true;
    }

    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Deprecated
    /**
     * 最后多了一个\n
     * 不直接在这里改，是有可能业务依赖了这个实现
     */
    public static String read(String path, String encoding) {
        String content = "";
        String line;
        try {
            File file = new File(path);

            if (!file.exists()) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), encoding));

            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String read2(String path, String encoding) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                return null;
            }
            return inputStream2String(new FileInputStream(path), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void createAndWrite(String path, String content, String encoding) {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        write(path, content, encoding);
    }


    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    public static void write(String path, byte[] binaryData) throws IOException {
        FileOutputStream fileOutputStream = openOutputStream(new File(path), false);
        fileOutputStream.write(binaryData);
        fileOutputStream.close();
    }

    public static void write(String path, String content, String encoding) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), encoding));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzip(String zipFile, String targetDir) {
        try {
            unzipOrThrow(zipFile, targetDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unzipOrThrow(String zipFile, String targetDir) throws IOException {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称

        BufferedOutputStream dest = null; //缓冲输出流
        FileInputStream fis = new FileInputStream(zipFile);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry; //每个zip条目的实例

        while ((entry = zis.getNextEntry()) != null) {

            try {
                //Log.d(TAG, "Unzip file = " + entry);

                int count;
                byte data[] = new byte[BUFFER];
                strEntry = entry.getName();

                File entryFile = new File(targetDir + strEntry);
                //Log.d(TAG, "unzip: entryFile = " + entryFile.getAbsolutePath());
                File entryDir = new File(entryFile.getParent());
                if (!entryDir.exists()) {
                    entryDir.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(entryFile);
                dest = new BufferedOutputStream(fos, BUFFER);
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        zis.close();
    }


    /**
     * 递归删除目录
     *
     * @param f
     */
    public static void deleteDir(File f) {
        if (f.exists()) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null) {
                    for (File subFile : files) {
                        if (subFile.isDirectory()) {
                            deleteDir(subFile);
                        } else {
                            subFile.delete();
                        }
                    }
                }
            }
            f.delete();
        }
    }


    /**
     * 输入的字符是否是汉字
     *
     * @param a char
     * @return boolean
     */
    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim())) return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))) return true;
        }
        return false;
    }
}
