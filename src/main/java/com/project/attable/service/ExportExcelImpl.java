package com.project.attable.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportExcelImpl implements ExportExcel {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;

	@Override
	public Workbook convertTableToExcel(String name) throws ClassNotFoundException {
		String startName = name.substring(0, name.lastIndexOf("."));
		Class<?> objClass = Class.forName("com.project.attable.entity." + startName);
		EntityType<?> et = entityManager.getMetamodel().entity(objClass);
		List<String> listColumnName = new ArrayList<>();
		for (Attribute<?, ?> attr : et.getAttributes()) {
			if (attr.getPersistentAttributeType().equals(attr.getPersistentAttributeType().BASIC)) {
				listColumnName.add(attr.getName());
			}
		}
		List<?> listRecord = entityManager.createQuery("From " + startName).getResultList();
		Workbook workbook = writeWorkBookSingleSheet(startName, name, listColumnName, listRecord);
		return workbook;
	}

	@Override
	public Workbook writeWorkBookSingleSheet(String startName, String fileName, List<String> listColumnName,
			List<?> listRecord) {
		Workbook workbook = null;
		if (fileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		Sheet sheet = workbook.createSheet(startName);
		createHeaderRow(sheet, listColumnName);
		writeData(sheet, listColumnName, listRecord);
		return workbook;

	}

	@Override
	public void createHeaderRow(Sheet sheet, List<String> listColumnName) {
		Row header = sheet.createRow(0);
		for (int i = 0; i < listColumnName.size(); i++) {
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(listColumnName.get(i));
		}
	}

	@Override
	public void writeData(Sheet sheet, List<String> listColumnName, List<?> listRecord) {
		for (int i = 0; i < listRecord.size(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < listColumnName.size(); j++) {
				write(row, listColumnName.get(j), j, listRecord.get(i));
			}
		}
	}

	@Override
	public void write(Row row, Object objColumn, int index, Object objectRecord) {
		try {
			Field field = null;
			boolean found = false;
			for (Field f : objectRecord.getClass().getDeclaredFields()) {
				if (f.getName().equalsIgnoreCase(objColumn.toString())) {
					found = true;
					break;
				}
			}
			if (found) {
				field = objectRecord.getClass().getDeclaredField(objColumn.toString());
			} else if (objectRecord.getClass().getSuperclass().getDeclaredFields().length > 0) {
				field = objectRecord.getClass().getSuperclass().getDeclaredField(objColumn.toString());
			}

			field.setAccessible(true);
			Object value = (Object) field.get(objectRecord);
			row.createCell(index).setCellValue(value != null ? value.toString() : "");
//			boolean checkId = false;
//			if (value != null && value.getClass().getTypeName().contains("entity")) {
//				for (Field f : value.getClass().getDeclaredFields()) {
//					if (f.getName().equals("id")) {
//						checkId = true;
//						break;
//					}
//				}
//			}
//			if (checkId) {
//				Field f = value.getClass().getDeclaredField("id");
//				f.setAccessible(true);
//				Object v = (Object) f.get(value);
//				row.createCell(index).setCellValue(v != null ? v.toString() : "");
//			} else {
//				row.createCell(index).setCellValue(value != null ? value.toString() : "");
//			}

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Workbook getAllTable() {
		Workbook workbook = new XSSFWorkbook();
		for (EntityType<?> et : entityManagerFactory.getMetamodel().getEntities()) {
			Sheet sheet = workbook.createSheet(et.getName());
			List<String> listColumnName = new ArrayList<>();
			for (Attribute<?, ?> attr : et.getAttributes()) {
				if (attr.getPersistentAttributeType().equals(attr.getPersistentAttributeType().BASIC)) {
					listColumnName.add(attr.getName());
				}
			}
			List<?> listRecord = entityManager.createQuery("From " + et.getName()).getResultList();
			createHeaderRow(sheet, listColumnName);
			writeData(sheet, listColumnName, listRecord);
		}
		return workbook;
	}

}
