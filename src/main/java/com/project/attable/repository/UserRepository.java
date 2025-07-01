package com.project.attable.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.firebase.auth.FirebaseToken;
import com.project.attable.entity.RoleName;
import com.project.attable.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email); 

    Boolean existsByPassword(String password);

    User findByIdIn(Long id);
    
    User findByEmailIn(String email);

    User findByEmailAndPasswordIn(String email, String password);
    
    User findByRolesRoleNameIn(RoleName roleName);

	List<User> findByRolesId(Long id);
	
	Page<User> findByRolesIdIn(Pageable pageable,Long roleId);
	
    @Query("Select count(u.id) from User u join u.roles r where u.createdAt between ?1 and ?2 and r.id = '2'")
    long countDinerSignUp(LocalDateTime fromDate, LocalDateTime toDate);

}
