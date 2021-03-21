import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class test {



    public static void main(String[] args) {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println("bootstrap:" + url.toExternalForm());
        }
        printClassLoader("扩展类加载器" , test.class.getClassLoader().getParent());

        printClassLoader("应用类加载器" , test.class.getClassLoader());



    }

    public static void printClassLoader(String s,ClassLoader cl) {
        if (cl != null ) {
            System.out.println("name "+ s + " classloader ->" + cl.toString());
            printURLClassLoader(cl);
        } else {
            System.out.println(s + " classloader -> null") ;

        }


    }

    public static void printURLClassLoader (ClassLoader cl) {
        Object ucp = insightField(cl,"ucp");
        Object path = insightField(ucp,"path");
        ArrayList  ps = (ArrayList) path;
        for (Object p:ps) {
            System.out.println("   ===>" + p.toString()) ;
        }

    }

    private static Object insightField (Object obj,String fname) {
        try {
            Field f = null;
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fname);
            } else {
                f = obj.getClass().getDeclaredField(fname);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
