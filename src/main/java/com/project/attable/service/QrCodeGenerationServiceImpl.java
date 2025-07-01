package com.project.attable.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class QrCodeGenerationServiceImpl implements QrCodeGenerationService {

	@Override
	public OutputStream getQrCodeFromPhoneNumber(String phoneNumber, String paymentAmount) throws Exception {

		phoneNumber = phoneNumber.substring(1, 10);
		String digits = String.format("%02d", paymentAmount.length());
//        System.out.println("payment amount = "+ paymentAmount);
//        System.out.println("payment length = "+ paymentAmount.length());
		String data = "00020101021129370016A00000067701011101130066" + phoneNumber + "5802TH530376454" + digits
				+ paymentAmount + "6304";

		long ccittCrc = CRC.calculateCRC(CRC.Parameters.CCITT, data.getBytes());
		String crc = Long.toHexString(ccittCrc).toUpperCase();

		ByteArrayOutputStream byteArrayOutputStream = QRCode.from(data + crc).withSize(250, 250).to(ImageType.PNG)
				.stream();
//        try {
//            OutputStream out = new FileOutputStream("qr-code.png");
//            byteArrayOutputStream.writeTo(out);
//            out.flush();
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

		return byteArrayOutputStream;
	}

	@Override
	public OutputStream getQrCodeFromCitizenIdOrTaxId(String citizenIdOrTaxId, String paymentAmount) throws Exception {

		String digits = String.format("%02d", paymentAmount.length());
		String data = "00020101021129370016A0000006770101110213" + citizenIdOrTaxId + "5802TH530376454" + digits
				+ paymentAmount + "6304";

		long ccittCrc = CRC.calculateCRC(CRC.Parameters.CCITT, data.getBytes());
		String crc = Long.toHexString(ccittCrc).toUpperCase();

		ByteArrayOutputStream byteArrayOutputStream = QRCode.from(data + crc).withSize(250, 250).to(ImageType.PNG)
				.stream();
		return byteArrayOutputStream;
	}

}
