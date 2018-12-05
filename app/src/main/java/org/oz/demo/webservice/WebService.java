package org.oz.demo.webservice;

import org.oz.demo.webservice.api.ITestService;
import org.oz.demo.webservice.api.IUserService;

public enum WebService implements IWebservice
{

    INSTANCE
            {
                private ITestService testService;

                @Override
                public ITestService getTestService()
                {
                    if (testService == null)
                        testService = ServiceFactory.getWebService(ITestService.class);

                    return testService;
                }


                private IUserService userService;

                @Override
                public IUserService getUserService()
                {
                    if (userService == null)
                        userService = ServiceFactory.getWebService(IUserService.class);

                    return userService;
                }
            };

    private WebService()
    {
    }

    public static WebService getInstance()
    {
        return INSTANCE;
    }


}
