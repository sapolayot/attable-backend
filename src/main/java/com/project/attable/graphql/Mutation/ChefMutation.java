package com.project.attable.graphql.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.ChefDao;
import com.project.attable.entity.Chef;
import com.project.attable.entity.ChefStatus;
import com.project.attable.entity.response.ChefEmailResponse;
import com.project.attable.security.responsemodel.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ChefMutation implements GraphQLMutationResolver {

	@Autowired
	private ChefDao chefDao;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ChefEmailResponse editChefStatus(Chef chef) {
		return chefDao.editChefStatus(chef);
	}
}
