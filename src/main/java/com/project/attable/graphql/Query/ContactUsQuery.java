package com.project.attable.graphql.Query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.ContactUsDao;
import com.project.attable.entity.ContactUs;
import com.project.attable.entity.response.ContactUsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ContactUsQuery implements GraphQLQueryResolver {

    @Autowired
    ContactUsDao contactUsDao;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ContactUsResponse getAllContacts(int page, int pagesize){
        return contactUsDao.getAllContacts(page,pagesize);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ContactUs getContactByID(Long id){
        return contactUsDao.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ContactUsResponse getContactsByEmail(int page, int pagesize,String email){
        return contactUsDao.findByEmail(page,pagesize,email);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ContactUsResponse getContactsByName(int page, int pagesize,String name){
        return contactUsDao.findByName(page,pagesize,name);
    }

}
