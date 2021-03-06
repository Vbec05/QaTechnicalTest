package com.tickets.common;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

public class DataProperties {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("data", Locale.ENGLISH);



    public static String getPassword() {
        return getParam("password");


    }

    public static String getUserName() {
        return getParam("userName");


    }




    /**
     * Gets the param from the properties file
     *
     * @param paramName the param name
     * @return the param
     */
    // Read properties from file as string
    public static String getParam(String paramName) {
        Charset UTF_8 = Charset.forName("UTF-8");
        byte ptext[] = BUNDLE.getString(paramName).getBytes(UTF_8);

        String value = new String(ptext, UTF_8);
        if (value.length() < 1) {
            throw new IllegalArgumentException("Required parameter " + paramName + " was not set");
        }
        return value;
    }


}
