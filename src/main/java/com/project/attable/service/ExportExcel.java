package com.project.attable.service;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExportExcel {
	Workbook convertTableToExcel(String fileName) throws ClassNotFoundException;

	Workbook writeWorkBookSingleSheet(String startName, String fileName, List<String> listColumnName, List<?> listRecord);

	void createHeaderRow(Sheet sheet, List<String> listColumnName);

	void writeData(Sheet sheet,List<String> listColumnName, List<?> listRecord);
	
	void write(Row row,Object objColumn,int index, Object objRecord);
	
	Workbook getAllTable();

}
