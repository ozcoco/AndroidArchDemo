package org.oz.demo.webservice;

import org.oz.demo.webservice.api.ITestService;
import org.oz.demo.webservice.api.IUserService;

public interface IWebservice
{
    ITestService getTestService();

    IUserService getUserService();

}
