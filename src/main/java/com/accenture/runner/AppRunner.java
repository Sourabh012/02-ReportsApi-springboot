package com.accenture.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.accenture.entity.EligibilityDetails;
import com.accenture.repo.EligibilityDtlsRepo;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired
	private EligibilityDtlsRepo repo;
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(1);
		entity1.setName("ajay");
		entity1.setMobile(1234564566L);
		entity1.setGender('M');
		entity1.setSsn(6563255L);
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		repo.save(entity1);
		
		EligibilityDetails entity2 = new EligibilityDetails();
		entity2.setEligId(2);
		entity2.setName("yash");
		entity2.setMobile(683783378L);
		entity2.setGender('M');
		entity2.setSsn(65622222L);
		entity2.setPlanName("Rls");
		entity2.setPlanStatus("Approved");
		repo.save(entity2);
		
		EligibilityDetails entity3 = new EligibilityDetails();
		entity3.setEligId(3);
		entity3.setName("akash");
		entity3.setMobile(6476458723L);
		entity3.setGender('M');
		entity3.setSsn(65622222L);
		entity3.setPlanName("hoo");
		entity3.setPlanStatus("denied");
		repo.save(entity3);
		
	}

}
