package org.oz.demo.webservice;

public class WebService
{
    private static final WebService ourInstance = new WebService();

    public static WebService getInstance()
    {
        return ourInstance;
    }

    private WebService()
    {
    }
}
