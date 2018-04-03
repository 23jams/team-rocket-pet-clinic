package ConsistencyTester;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.gateways.OwnersGateway;
import org.springframework.samples.petclinic.gateways.PetsGateway;
import org.springframework.samples.petclinic.gateways.VetsGateway;
import org.springframework.samples.petclinic.gateways.VisitsGateway;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerControllerTests;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
public class ConsistencyCheckerTests {
	
	@MockBean
	protected PetRepository petRepo;
	
	@MockBean
	protected OwnerRepository ownersRepo;
	
	@MockBean
	protected VisitRepository visitRepo;

	//verifies that the owner consistency check algorithm is working properly
	@Test
	public void OwnerConsistencyCheck() {
		Collection<Owner> owners = ownersRepo.findAll();
		OwnersGateway ownersGateway = new OwnersGateway();
		for (Owner owner : owners) {
			int ownerId = owner.getId();
			System.out.println(ownerId);
			assert(owner.equals(ownersGateway.findById(ownerId)));
		}
	}
	//verifies that the owner consistency check algorithm is working properly
	@Test
	public void PetConsistencyCheck() {
		Collection<Pet> pets = petRepo.findAll();
		PetsGateway petGateway = new PetsGateway();
		for (Pet pet : pets) {
			int petId = pet.getId();
			System.out.println(petId);
			assert(pet.equals(petGateway.findById(petId)));
		}
	}
	
	//verifies that the owner consistency check algorithm is working properly
	@Test
	public void VisitConsistencyCheck() {
		List<Visit> visits = visitRepo.findAll();
		VisitsGateway visitGateway = new VisitsGateway();
		for (Visit visit : visits) {
			int visitId = visit.getId();
			System.out.println(visitId);
			assert(visit.equals(visitGateway.findById(visitId)));
		}
	}

}
