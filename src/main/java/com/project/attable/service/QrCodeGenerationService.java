package com.project.attable.service;

import java.io.OutputStream;

public interface QrCodeGenerationService {
	OutputStream getQrCodeFromPhoneNumber(String phoneNumber, String paymentAmount) throws Exception;
    OutputStream getQrCodeFromCitizenIdOrTaxId(String citizenIdOrTaxId, String paymentAmount) throws Exception;
}
