package com.example.jobportal.constants;

public class ApplicationConstants {
    private ApplicationConstants(){
        throw new AssertionError("Utility class cannot be instantiated");
    }
    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = "98hfw9ieuhf9ujasc9uh9r98asjd1234";
    public static final String JWT_HEADER = "Authorization";
}
