package jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JVMTaskClassLoader extends  ClassLoader {
    private String mLibPath;

    public  JVMTaskClassLoader(String path) {
        // TODO Auto-generated constructor stub
        mLibPath = path;
    }
    public static void main(String[] args) {
        try {
            JVMTaskClassLoader cl = new JVMTaskClassLoader("src/main/resources");
            Class c = cl.loadClass("Hello");
            if(c != null){
                try {
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("hello",null);

                    method.invoke(obj, null);
                } catch (InstantiationException | IllegalAccessException
                        | NoSuchMethodException
                        | SecurityException |
                        IllegalArgumentException |
                        InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {


        byte[] bytes = new byte[10000];
        try {
            bytes = getContent(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<bytes.length; i++) {
            bytes[i] = (byte) ((byte) 255 - bytes[i]);;
        }

        return defineClass(name,bytes,0,bytes.length);
    }
    //获取要加载 的class文件名
    private String getFileName(String name) {
        // TODO Auto-generated method stub
        int index = name.lastIndexOf('.');
        if(index == -1){
            return name+".xlass";
        }else{
            return name.substring(index+1)+".xlass";
        }
    }

    public  byte[] getContent(String name) throws IOException {

        File file = new File(mLibPath,getFileName(name));
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        fi.close();
        return buffer;
    }




}

