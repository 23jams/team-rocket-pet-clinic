package ConsistencyTester;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.gateways.MysqlGateway;
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
	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PET_ID = 1;

	@MockBean
	protected PetRepository petRepo;

	@MockBean
	protected OwnerRepository ownersRepo;

	@MockBean
	protected VisitRepository visitRepo;
	
	@InjectMocks
	private MysqlGateway mygate;
	
	@Mock
	private OwnersGateway ownersGateway;

	@Mock
	private PetsGateway petsGateway;
	@Mock
	private VisitsGateway visitGateway;
	
	private Owner george;
	private Pet pet;

	
	 @Before
	    public void Petsetup() {
		 pet = new Pet();
	        pet.setId(TEST_PET_ID);
	        pet.setOwner(george);
	        pet.setName("dora");
	        given(this.petRepo.findById(TEST_PET_ID)).willReturn(pet);
	        given(this.petsGateway.findById(TEST_PET_ID)).willReturn(pet);

	    }

	@Before
	public void Ownersetup() {
		george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");
		given(this.ownersRepo.findById(TEST_OWNER_ID)).willReturn(george);
		given(this.ownersGateway.findById(TEST_OWNER_ID)).willReturn(george);
	}
	

	// verifies that owner objects are stored in same manner, and thus comparable
	@Test
	public void OwnerConsistencyCheck() {
		ownersRepo.save(george);
		ownersGateway.save(george);
		assert(ownersGateway.findById(TEST_OWNER_ID).getId().equals(TEST_OWNER_ID));
		assert(ownersRepo.findById(TEST_OWNER_ID).getId().equals(TEST_OWNER_ID));
		assert(ownersGateway.findById(TEST_OWNER_ID).equals(ownersRepo.findById(TEST_OWNER_ID)));
		
	}

	// verifies that pet objects are stored in the same manner, and comparable 
	@Test
	public void PetConsistencyCheck() {
		petRepo.save(pet);
		petsGateway.save(pet);
		assert(petsGateway.findById(TEST_PET_ID).getId().equals(TEST_PET_ID));
		assert(petRepo.findById(TEST_PET_ID).getId().equals(TEST_PET_ID));
		assert(petsGateway.findById(TEST_PET_ID).equals(petRepo.findById(TEST_PET_ID)));

	}

	// verifies that the visit consistency check algorithm is working properly
	@Test
	public void VisitConsistencyCheck() {
		List<Visit> visits = visitRepo.findAll();
		VisitsGateway visitGateway = new VisitsGateway();
		for (Visit visit : visits) {
			int visitId = visit.getId();
			System.out.println(visitId);
			assert (visit.equals(visitGateway.findById(visitId)));
		}
	}

}
