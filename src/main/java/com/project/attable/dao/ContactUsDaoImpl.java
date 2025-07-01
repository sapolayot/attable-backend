package com.project.attable.dao;

import com.project.attable.entity.ContactStatus;
import com.project.attable.entity.ContactUs;
import com.project.attable.entity.response.ContactUsResponse;
import com.project.attable.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class ContactUsDaoImpl implements ContactUsDao{

    @Autowired
    ContactUsRepository contactUsRepository;

    @Override
    public ContactUs createContact(ContactUs contactUs) {
        contactUs.setContactDate(new Date());
        contactUs.setContactStatus(ContactStatus.Contact);
        return contactUsRepository.save(contactUs);
    }

    @Override
    public ContactUsResponse getAllContacts(int page, int pagesize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
        Page<ContactUs> contactus = contactUsRepository.findByContactStatus(pageRequest, ContactStatus.Contact);
        ContactUsResponse response = new ContactUsResponse();
        response.setTotalPage(contactus.getTotalPages());
        response.setTotalElement(contactus.getTotalElements());
        response.setContacts(contactus.getContent());

        return response;
    }

    @Override
    public ContactUs findById(Long Id) {
        return contactUsRepository.findByIdIn(Id);
    }

    @Override
    public ContactUsResponse findByEmail(int page, int pagesize,String email) {
        PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
        Page<ContactUs> contactus = contactUsRepository.findByEmailIsContaining(pageRequest,email);
        ContactUsResponse response = new ContactUsResponse();
        response.setTotalPage(contactus.getTotalPages());
        response.setTotalElement(contactus.getTotalElements());
        response.setContacts(contactus.getContent());
        return response;
    }


    @Override
    public ContactUsResponse findByName(int page, int pagesize,String name) {
        PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
        Page<ContactUs> contactus = contactUsRepository.findByNameIsContaining(pageRequest,name);
        ContactUsResponse response = new ContactUsResponse();
        response.setTotalPage(contactus.getTotalPages());
        response.setTotalElement(contactus.getTotalElements());
        response.setContacts(contactus.getContent());

        return response;
    }

	@Override
	public ContactUs replyContact(ContactUs contactUs) {
		ContactUs contact = contactUsRepository.findByIdIn(contactUs.getId());
		contact.setAnswer(true);
		contactUsRepository.save(contact);
		ContactUs newContact = new ContactUs();
		newContact.setName(contactUs.getName());
		newContact.setEmail(contactUs.getEmail());
		newContact.setPhoneNumber(contactUs.getPhoneNumber());
		newContact.setSubject(contactUs.getSubject());
		newContact.setMessages(contactUs.getMessages());
		newContact.setContactDate(new Date());
		newContact.setContactStatus(ContactStatus.Reply);
		newContact.setMainContactId(contactUs.getId());
        return contactUsRepository.save(newContact);
	}
}
