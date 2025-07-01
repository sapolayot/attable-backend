package com.project.attable.graphql.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.ContactUsDao;
import com.project.attable.entity.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactUsMutaion implements GraphQLMutationResolver {

    @Autowired
    ContactUsDao contactUsDao;

    public ContactUs createContact(ContactUs contactUs){
        return contactUsDao.createContact(contactUs);
    }
    
    public ContactUs replyContact(ContactUs contactUs) {
    	return contactUsDao.replyContact(contactUs);
    }
    
    

}
