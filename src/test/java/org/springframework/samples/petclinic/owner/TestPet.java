package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for the Pet class
 *
 * @author Olivier Trépanier-Desfossés
 */
public class TestPet {

	@Test
	public void test() {
		//Create FakeOwner Object
		FakeOwner fakeOwner = new FakeOwner();
		fakeOwner.setAddress("123 Street");
		fakeOwner.setCity("Montreal");
		fakeOwner.setTelephone("999-999-9999");
		
		//Create Pet Object
		Pet pet = new Pet();
		
		//Assign Pet Object to FakeOwner Object
		fakeOwner.addPet(pet); //Calls pet.setOwner(fakeOwner)
		
		//Test values
		assertEquals("123 Street", pet.getOwner().getAddress());
		assertEquals("Montreal", pet.getOwner().getCity());
		assertEquals("999-999-9999", pet.getOwner().getTelephone());
	}

}
