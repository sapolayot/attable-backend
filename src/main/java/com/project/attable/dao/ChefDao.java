package com.project.attable.dao;

import com.project.attable.entity.Chef;
import com.project.attable.entity.response.ChefEmailResponse;

public interface ChefDao {
	
	/**Mutation*/
	//Chef
	ChefEmailResponse editChefStatus(Chef chef);
}
