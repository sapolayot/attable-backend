package com.project.attable.dao;

import java.util.List;

import com.project.attable.entity.response.DashboardResponse;
import com.project.attable.entity.response.TableResponse;

public interface DashboardDao {
	
	DashboardResponse getDashBoardDetail(String fromDate, String toDate);
	
	List<String> getlistAllTable();
}
