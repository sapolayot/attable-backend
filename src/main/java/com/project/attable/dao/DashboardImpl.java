package com.project.attable.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.EventStatus;
import com.project.attable.entity.response.DashboardResponse;
import com.project.attable.entity.response.TableResponse;
import com.project.attable.repository.ChefRepository;
import com.project.attable.repository.SubEventRepository;
import com.project.attable.repository.UserRepository;

@Repository
public class DashboardImpl implements DashboardDao {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChefRepository chefRepository;

	@Autowired
	private SubEventRepository subEventRepository;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	public DashboardResponse getDashBoardDetail(String fromDate, String toDate) {
		DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter timeFormatFrom = new DateTimeFormatterBuilder().append(dateformat)
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter();

		DateTimeFormatter timeFormatTo = new DateTimeFormatterBuilder().append(dateformat)
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 23).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 59).toFormatter();

		LocalDateTime fDate = LocalDateTime.parse(fromDate, timeFormatFrom);
		LocalDateTime tDate = LocalDateTime.parse(toDate, timeFormatTo);

		long chefSignUp = chefRepository.countChefSignUp(fDate, tDate);
		long dinerSignUp = userRepository.countDinerSignUp(fDate, tDate);
		long eventRegistered = subEventRepository.countEventRegistered(fDate, tDate);
		long eventCompleted = subEventRepository.countByStatus(fDate, tDate, EventStatus.Completed);
		long eventCancelled = subEventRepository.countByStatus(fDate, tDate, EventStatus.Cancelled);
		long chefCharge = subEventRepository.sumChefCharge(fDate, tDate);
		long attableFee = subEventRepository.sumAttableFee(fDate, tDate);
		attableFee = attableFee - chefCharge;
		DashboardResponse dashboardResponse = new DashboardResponse();
		dashboardResponse.setChefSignUp(chefSignUp);
		dashboardResponse.setDinerSignUp(dinerSignUp);
		dashboardResponse.setEventRegistered(eventRegistered);
		dashboardResponse.setEventCompleted(eventCompleted);
		dashboardResponse.setEventCancelled(eventCancelled);
		dashboardResponse.setChefCharge(chefCharge);
		dashboardResponse.setAttableFee(attableFee);

		return dashboardResponse;
	}

	@Override
	public List<String> getlistAllTable() {
		List<String> listTable = new ArrayList<>();
		for (EntityType<?> et : entityManagerFactory.getMetamodel().getEntities()) {
			listTable.add(et.getName());
		}
		return listTable;
	}

}
