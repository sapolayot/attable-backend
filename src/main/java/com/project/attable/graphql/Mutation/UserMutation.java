package com.project.attable.graphql.Mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.UserDao;
import com.project.attable.entity.Chef;
import com.project.attable.entity.User;
import com.project.attable.entity.response.UserResponse;

@Component
public class UserMutation implements GraphQLMutationResolver{
	
	@Autowired
	private UserDao userDao;
	
    @PreAuthorize("isAuthenticated()")
	public UserResponse updateProfileDiner(User user) {
		return userDao.updateProfileDiner(user);
	}
	
	@PreAuthorize("hasRole('ROLE_DINER')")
	public UserResponse editProfileDiner(User user) {
		return userDao.editProfileDiner(user);
	}

	@PreAuthorize("isAuthenticated()")
	public UserResponse updateProfileChef(Chef chef) {
		return userDao.updateProfileChef(chef);
	}
	
	@PreAuthorize("hasRole('ROLE_CHEF')")
	public UserResponse editProfileChef(Chef chef) {
		return userDao.editProfileChef(chef);
	}
}
