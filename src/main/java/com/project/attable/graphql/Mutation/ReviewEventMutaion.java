package com.project.attable.graphql.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.ReviewEventDao;
import com.project.attable.entity.ReviewEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ReviewEventMutaion  implements GraphQLMutationResolver {

    @Autowired
    ReviewEventDao reviewEventDao;

    @PreAuthorize("hasAnyRole('ROLE_DINER','ROLE_CHEF')")
    public ReviewEvent createReviewEvent(ReviewEvent reviewEvent,Long reserveId,Long chefId){ 
    	return reviewEventDao.createReviewEvent(reviewEvent,reserveId,chefId); 
    }
}
