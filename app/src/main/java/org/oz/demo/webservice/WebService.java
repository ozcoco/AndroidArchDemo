package org.oz.demo.webservice;

import org.oz.demo.webservice.api.IUserService;

public class WebService implements IWebservice {


    private WebService() {
    }

    private static class Singleton {
        private static WebService INSTANCE = new WebService();
    }

    public static WebService getInstance() {
        return Singleton.INSTANCE;
    }


    public final IUserService userService;

    {
        userService = ServiceFactory.getService(UserSe);

    }


    @Override
    public IUserService getUserService() throws Exception {
        return null;
    }
}
