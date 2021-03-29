package com.scd.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author chengdu
 */
public class FileUtil {

    public static final int BUFFSIZE = 1024;

    private static final Logger log = Logger.getLogger("lavasoft");

    /**
     * 一次读取整个文件
     * @param filepath
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFileAll(String filepath, String encoding) throws IOException{
        File file = new File(filepath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream inputStream = null;
        String readResult = "";
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(filecontent);
            readResult = new String(filecontent, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
        return readResult;
    }

    /**
     * 文件流读取文件内容
     * @param inputStream
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFileAll(InputStream inputStream, String encoding) throws IOException{
        byte[] filecontent = new byte[inputStream.available()];
        String readResult = "";
        try {
            inputStream.read(filecontent);
            readResult = new String(filecontent, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readResult;
    }

    public static void writeBytearrtoFile(String filepath, byte[] byteArray, boolean append) throws Exception{
        FileOutputStream fos = new FileOutputStream(filepath,append);
        fos.write(byteArray);
        fos.close();
    }

    public static void writeStrtoFile(String filepath, String content, boolean append) throws Exception {
        FileWriter writer = new FileWriter(filepath, append);
        writer.write(content);
        writer.flush();
        writer.close();
    }
    
    /**
     * 写入文件时指定编码
     * @param filepath
     * @param content
     * @param append
     * @param encode
     * @throws Exception
     */
    public static void writeBuffToFile(String filepath, String content, boolean append, String encode) throws Exception{
        BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(
                new FileOutputStream(filepath, append), encode));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }

    /**
     * 写入文件不关闭文件流
     * @param fileWriter
     * @param content
     */
    public static void writeStrToFile(FileWriter fileWriter, String content) throws IOException {
        fileWriter.write(content);
        fileWriter.flush();
    }

    /**
     * byte[] 数组写入文件
     * @param path
     * @param content
     * @param append
     * @throws IOException
     */
    public static void writeByteArraytoFile(String path, byte[] content, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(path, append);
        fos.write(content);
        fos.close();
    }

    /**
     * the traditional io way
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] readByteArrayByfpath(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            byte[] buffer = new byte[BUFFSIZE];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, BUFFSIZE))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
            }
            reader.close();
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
    }

    public static List getListPathByfile(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                if(new File(tempString).exists() && new File(tempString).isFile()) {
                    list.add(tempString);
                }
            }
            reader.close();
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
        return list;
    }

    /**
     * 获取所有文件
     * @param filepath
     * @param pathlist
     */
    public static void getFilePath(String filepath, List<String> pathlist){
        File file = new File(filepath);
        if(!file.exists()){
            throw new RuntimeException("file not exist"+ filepath);
        }
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File efile : files){
                if(efile.isDirectory()){
                    getFilePath(efile.getAbsolutePath(), pathlist);
                }else{
                    pathlist.add(efile.getAbsolutePath());
                }
            }
        }else{
            //传入文件时添加一条
            pathlist.add(filepath);
        }
    }

    public static void createNewDirFile(String filepath) throws IOException{
        filepath = filepath.replace("\\","/");
        int endIndex = filepath.lastIndexOf("/");
        String dirpath = filepath.substring(0,endIndex);
        //创建文件目录
        File dirFile = new File(dirpath);
        if(!dirFile.exists()) {
            if (dirFile.mkdirs()) {
                log.info("create file dir successfully----" + dirpath);
            }
        }else{
            log.info("dir path exists----"+dirpath);
        }
        //创建文件
        File file = new File(filepath);
        if(!file.exists()) {
            if (file.createNewFile()) {
                log.info("create file successfully----" + filepath);
            }
        }else {
            log.info("file path exists----"+filepath);
        }
    }
    
        /**
     * 删除文件
     * @param filepath
     */
    public static void deleteFile(String filepath){
        File file = new File(filepath);
        if(file.exists()){
            if(file.isDirectory()){
                System.out.println("enter dir " + file.getPath());
                File[] files = file.listFiles();
                for(File f : files){
                    deleteFile(f.getAbsolutePath());
                }
            }else if(file.isFile()){
                System.out.println("delete file :"+ file.getAbsolutePath());
                file.delete();
            }
        }
    }
    
    /**
     * 删除文件以及文件夹
     * @param filepath
     */
    public static void deleteDirAndFile(String filepath){
        File file = new File(filepath);
        if(file.exists()){
            if(file.isDirectory()){
                System.out.println("enter dir " + file.getPath());
                File[] files = file.listFiles();
                for(File f : files){
                    deleteDirAndFile(f.getAbsolutePath());
                }
                if(file.delete()){
                    System.out.println("delete dir " + file.getAbsolutePath());
                }
            }else if(file.isFile()){
                System.out.println("delete file :"+ file.getAbsolutePath());
                file.delete();
            }
        }
    }

    public static void makedir(String dir){
        File file = new File(dir);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static void main(String[] args) {
        String inputPath = "E:\\Github\\Activiti";
        List<String> filepaths = new ArrayList<>();
        getFilePath(inputPath, filepaths);
        filepaths.forEach(path -> {
            if (path.endsWith(".iml")) {
                File file = new File(path);
                boolean delresult = file.delete();
                System.out.println("delete " + path + " " + delresult);
            }
        });
    }
}
