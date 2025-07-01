package com.project.attable.dao;

import com.project.attable.entity.Chef;
import com.project.attable.entity.response.ChefEmailResponse;
import com.project.attable.repository.ChefRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChefDaoImpl implements ChefDao {

    @Autowired
    ChefRepository chefRepository;

    @Override
    public ChefEmailResponse editChefStatus(Chef chef) {
         Chef oldChef = chefRepository.findByIdIn(chef.getId());
         if(oldChef != null){
        	 oldChef.setStatus(chef.getStatus());
        	 if(chef.getStatus().toString().equals("Approved")) {
        		 oldChef.setApproveDate(LocalDateTime.now());
        		 return new ChefEmailResponse(true,null,chefRepository.save(oldChef));
        	 }else if(chef.getStatus().toString().equals("Reject")) {
				if (oldChef.getDetailReject() == null) {
					oldChef.setDetailReject(new ArrayList<>());
				}
        		 chef.getDetailReject().get(0).setRejectDate(LocalDateTime.now());
        		 oldChef.getDetailReject().add(chef.getDetailReject().get(0));
        		 return new ChefEmailResponse(true,oldChef.getDetailReject().get(oldChef.getDetailReject().size()-1), chefRepository.save(oldChef));
        	 }
         }
         return new ChefEmailResponse(false,null,null);
    }
}
