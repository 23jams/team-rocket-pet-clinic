package org.springframework.samples.petclinic.owner;
import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;
import org.springframework.samples.petclinic.visit.Visit;
import java.util.List;
/**
 * 
 * @author Batoul
 *Testing FakePet class
 */
public class FakePetTests {

	@Test
	public void testFakePet() {
		
		
		FakePet fakepet = new FakePet();
		Visit visit = new Visit();
		PetType type = new PetType();
		Owner owner = new Owner();
		
		//setting up an owner
		fakepet.setOwner(owner);
		owner.setId(555);
		int ownerID = fakepet.getOwner().getId();
		
		//setting a visit
		visit.setId(12345);
		visit.setPetId(12345);
		visit.setId(1);
		fakepet.addVisit(visit); //add visit to list of visits
		
		//Setting fakepet name and ID and retrieving it
		fakepet.setName("Ducky");
		String getName = fakepet.getName();
		fakepet.setId(12345);
		int getId = fakepet.getId();
		
		//setting fakepet type
		type.setName("Duck");
		type.setId(2);
		fakepet.setType(type);
		String animalType = fakepet.getType().getName();
		int typeID = fakepet.getType().getId();
	
	
		assertEquals(getId,12345);
		assertEquals(getName,"Ducky");
		assertEquals(fakepet.getVisits().size(),1);
		assertEquals(animalType,"Duck");
		assertEquals(typeID,2);
		assertEquals(ownerID,555);
		
	}
}
