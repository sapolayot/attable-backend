package com.project.attable.entity.response;

import com.project.attable.entity.ContactUs;
import lombok.Data;

import java.util.List;
@Data
public class ContactUsResponse {
    private int totalPage;
    private Long totalElement;
    private List<ContactUs> contacts;
}
