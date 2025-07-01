package com.project.attable.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.attable.service.ExportExcel;

@RestController
@RequestMapping("/export")
public class ExportExcelController {

	@Autowired
	private ExportExcel exportExcel;

	@GetMapping("/all")
	public ResponseEntity<?> exportAllTable() throws IOException {
		Workbook workbook = null;
		try {
			workbook = exportExcel.getAllTable();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCause());
		}
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		workbook.write(stream);
		workbook.close();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attable.xlsx");

		return ResponseEntity.ok().contentLength(stream.size())
				.contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.headers(headers).body(new ByteArrayResource(stream.toByteArray()));
	}

	@GetMapping("/filename/{name}")
	public ResponseEntity<?> exportTableByName(@PathVariable("name") String fileName) throws IOException {
		Workbook workbook;
		try {
			workbook = exportExcel.convertTableToExcel(fileName);
		} catch (ClassNotFoundException | IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCause());
		}
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		workbook.write(stream);
		workbook.close();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

		return ResponseEntity.ok().contentLength(stream.size())
				.contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.headers(headers).body(new ByteArrayResource(stream.toByteArray()));
	}
}
