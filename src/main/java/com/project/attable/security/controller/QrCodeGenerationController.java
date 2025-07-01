package com.project.attable.security.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.attable.service.QrCodeGenerationService;
import com.project.attable.service.QrCodeGenerationServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class QrCodeGenerationController {
	
	@GetMapping(value = "/qrcode/citizenidortaxid",
            produces = {MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getQrCodeFromCitizenIdOrTaxId(@RequestParam String citizenIdOrTaxId, @RequestParam String paymentAmount) throws Exception {
        QrCodeGenerationService qrCodeGenerationService = new QrCodeGenerationServiceImpl();
        ByteArrayOutputStream outputStream = (ByteArrayOutputStream)qrCodeGenerationService.getQrCodeFromCitizenIdOrTaxId(citizenIdOrTaxId, paymentAmount);

        return outputStream.toByteArray();
    }

    @GetMapping(value = "/qrcode/phone",
            produces = {MediaType.IMAGE_PNG_VALUE})
    public  @ResponseBody byte[] getQrCodeFromPhoneNumber(@RequestParam String paymentAmount) throws Exception {
       QrCodeGenerationService qrCodeGenerationService = new QrCodeGenerationServiceImpl();
       String phoneNumber = "0932681327";
        ByteArrayOutputStream outputStream = (ByteArrayOutputStream)qrCodeGenerationService.getQrCodeFromPhoneNumber(phoneNumber, paymentAmount);
        return outputStream.toByteArray();
    }

}
