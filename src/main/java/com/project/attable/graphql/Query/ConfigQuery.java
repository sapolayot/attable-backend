package com.project.attable.graphql.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.ConfigDao;
import com.project.attable.dao.ContactUsDao;
import com.project.attable.entity.response.ConfigResponse;
import com.project.attable.entity.response.ContactUsResponse;

@Component
public class ConfigQuery implements GraphQLQueryResolver {

	@Autowired
	ConfigDao configDao;
	
    public ConfigResponse getConfig(){
        return configDao.getConfig();
    }
}
