package com.project.attable.graphql.Query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.DashboardDao;
import com.project.attable.dao.EventDao;
import com.project.attable.entity.response.DashboardResponse;
import com.project.attable.entity.response.EventsResponse;
import com.project.attable.entity.response.TableResponse;

@Component
public class DashboardQuery implements GraphQLQueryResolver {

	@Autowired
	private DashboardDao dashboardDao;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public DashboardResponse getDashBoardDetail(String fromDate, String toDate) {
		return dashboardDao.getDashBoardDetail(fromDate, toDate);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<String> getlistAllTable() {
		return dashboardDao.getlistAllTable();
	}
	
}
