package com.project.attable.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.attable.entity.Allergic;
import com.project.attable.entity.Role;
import com.project.attable.entity.RoleName;
import com.project.attable.entity.User;
import com.project.attable.entity.UserStatus;
import com.project.attable.repository.AllergicRepository;
import com.project.attable.repository.EventRepository;
import com.project.attable.repository.UserRepository;
import com.project.attable.security.repository.RoleRepository;

@Slf4j
@Component
public class DevDataLoader implements ApplicationRunner {

	@Autowired
	AllergicRepository allergicRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (roleRepository.count() == 0) {
			Role admin = new Role();
			admin.setRoleName(RoleName.ROLE_ADMIN);
			roleRepository.save(admin);

			Role user = new Role();
			user.setRoleName(RoleName.ROLE_DINER);
			roleRepository.save(user);
			
			Role chef = new Role();
			chef.setRoleName(RoleName.ROLE_CHEF);
			roleRepository.save(chef);
			
			log.info("Add role success!!");

		}
		if(userRepository.findByRolesRoleNameIn(RoleName.ROLE_ADMIN)==null) {
			User u = new User();
			u.setFirstName("admin");
			u.setLastName("attable");
			u.setEmail("admin@admin.com");
			u.setPassword(passwordEncoder.encode("admin"));
			Role userRole = roleRepository.findByRoleNameIn(RoleName.ROLE_ADMIN);
			u.setRoles(Collections.singleton(userRole));
			u.setStatus(UserStatus.Approved);
			userRepository.save(u);
			log.info("Add Admin success!!");
		}
		if(allergicRepository.count() == 0) {
			allergicRepository.save(new Allergic("No restriction"));
			allergicRepository.save(new Allergic("Allergic to Eggs"));
			allergicRepository.save(new Allergic("Allergic to Milk"));
			allergicRepository.save(new Allergic("Allergic to Gluten"));
			allergicRepository.save(new Allergic("Allergic to Tree Nuts"));
			allergicRepository.save(new Allergic("Allergic to Peanut"));
			allergicRepository.save(new Allergic("Allergic to Soy beans"));
			allergicRepository.save(new Allergic("Allergic to Sesame"));
			allergicRepository.save(new Allergic("Allergic to Seafood"));
			allergicRepository.save(new Allergic("Halal"));
			
			log.info("Add allergic success!!");
		}
		
		// User user = new User();
		// user.setEmail("sapolayot.nif@gmail.com");
		// user.setPassword(passwordEncoder.encode("123456"));
		// user.setFirstName("Sapolayot");
		// user.setLastName("Nantawong");
		// user.setFacebookUser(false);
		// user.setGender(Gender.Male);
		// user.setNationality("Thai");
		// Set<Language> lang = new HashSet<>();
		// Language lan = new Language();
		// lan.setValue("Thai");
		// lang.add(lan);
		// user.setLanguage(lang);
		// user.setPhoneNumber("0932681327");
		// user.setCountry("Thailand");
		// user.setCity("Lampang");
		// user.setAllergic(new HashSet<>());
		// user.setAboutMe("dlsjfdpsk");
		// user.setBirthday(LocalDateTime.of(LocalDate.of(1995, 12, 11), LocalTime.of(11, 55)));
		// Role userRole = roleRepository.findByRoleNameIn(RoleName.ROLE_CHEF);
		// user.setRoles(Collections.singleton(userRole));
		
		// Chef chef = new Chef();
		// chef.setOccupation("Chef");
		// chef.setHomeNo("19");
		// chef.setStreet("Sunkong");
		// chef.setDistrict("Maung");
		// chef.setSubDistrict("Wieang Nuea");
		// chef.setPostalCode("52000");
		// chef.setFavoriteCuisine("favorite");
		// chef.setSignatureDish("dish");
		// chef.setAcc(new Account());
		// chef.setStatus(ChefStatus.Approved);
		// chef.setPassportId("1529900800196");
		// chef.setPassportUrl("asdsafsaoid");
		// chef.setExp(new ArrayList<>());
		// chef.setUser(user);
		// user.setChef(chef);
		
		// userRepository.save(user);
		
		
	}
}
