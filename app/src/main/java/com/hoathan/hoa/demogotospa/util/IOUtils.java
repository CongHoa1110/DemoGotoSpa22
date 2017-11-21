package com.hoathan.hoa.demogotospa.util;

/**
 * Created by Tungnguyenbk54 on 11/3/2017.
 */
import java.io.InputStream;
import java.io.Reader;

public class IOUtils {

    public static void closeQuietly(InputStream in)  {
        try {
            in.close();
        }catch (Exception e) {

        }
    }

    public static void closeQuietly(Reader reader)  {
        try {
            reader.close();
        }catch (Exception e) {

        }
    }

}
