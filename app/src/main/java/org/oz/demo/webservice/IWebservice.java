package org.oz.demo.webservice;

import org.oz.demo.webservice.api.IUserService;

public interface IWebservice {

    public IUserService getUserService() throws Exception;

}
