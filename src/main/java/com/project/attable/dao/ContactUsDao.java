package com.project.attable.dao;

import com.project.attable.entity.ContactUs;
import com.project.attable.entity.response.ContactUsResponse;

public interface ContactUsDao {
	ContactUs createContact(ContactUs contactUs);

	ContactUsResponse getAllContacts(int page, int pagesize);

	ContactUs findById(Long Id);

	ContactUsResponse findByEmail(int page, int pagesize, String email);

	ContactUsResponse findByName(int page, int pagesize, String name);
	
	ContactUs replyContact(ContactUs contactUs);
}

