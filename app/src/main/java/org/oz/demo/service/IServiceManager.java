package org.oz.demo.service;

import org.oz.demo.rest.IWebservice;
import org.oz.demo.sql.ISQLService;

public interface IServiceManager {

    IWebservice getWebservice();

    ISQLService getSQLService();

}
