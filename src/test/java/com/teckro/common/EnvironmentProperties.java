package com.teckro.common;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

public class EnvironmentProperties{

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("environment", Locale.ENGLISH);


    public static String getBaseUrl() {
        return getParam("baseUrl");


    }

    public static String getPort() {
        return getParam("port");

    }

    public static String getCheckAvailabilityUrl() {
        return getParam("checkAvailability");

    }

    public static String getBookRoomUrl() {
        return getParam("bookRoom");

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