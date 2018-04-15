package org.springframework.samples.petclinic.system;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.gateways.OwnersGateway;
import org.springframework.samples.petclinic.gateways.PetTypeGateway;
import org.springframework.samples.petclinic.gateways.PetsGateway;
import org.springframework.samples.petclinic.gateways.SpecialtiesGateway;
import org.springframework.samples.petclinic.gateways.VetSpecialtiesGateway;
import org.springframework.samples.petclinic.gateways.VetsGateway;
import org.springframework.samples.petclinic.gateways.VisitsGateway;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForkliftController {
	
	//Repositories for HSQLDB
	final OwnerRepository ownerRepo;
	final PetRepository petRepo;
	final VetRepository vetRepo;
	final VisitRepository visitRepo;
	
	@Autowired
    public ForkliftController(OwnerRepository clinicService, PetRepository petRepo, VetRepository vetRepo, VisitRepository visitRepo) {
        this.ownerRepo = clinicService;
        this.petRepo = petRepo;
        this.vetRepo = vetRepo;
        this.visitRepo = visitRepo;
    }
	
	@GetMapping("/forklift")
	public String forkliftCall() {
		this.forklift();
		return "forklift";
	}
	
	/**
	 * Method will forklift all data from HSQLDB to MySQL using the
	 * repositories and gateways.
	 */
	public void forklift() {	
		//Fetch all data from HSQLDB
		Collection<Owner> owners = ownerRepo.findAll();
		List<Pet> pets = petRepo.findAll();
		Collection<Vet> vets = vetRepo.findAll();
		List<Visit> visits = visitRepo.findAll();
		
		//Gateways for MySQL
		VetsGateway vetsGateway = new VetsGateway();
		SpecialtiesGateway specGateway = new SpecialtiesGateway();
		OwnersGateway ownersGateway = new OwnersGateway();
		PetTypeGateway petTypeGateway = new PetTypeGateway();
		PetsGateway petsGateway = new PetsGateway();
		VisitsGateway visitsGateway = new VisitsGateway();
		VetSpecialtiesGateway vetSpecGateway = new VetSpecialtiesGateway();
		
		//Migrate all data retrieved from HSQLDB to MySQL
		for (Vet vet: vets) {
			vetsGateway.save(vet);
			//Check for new specialties and insert them
			for (Specialty specialty: vet.getSpecialties()) {
				specGateway.save(specialty);
			}
	        vetSpecGateway.save(vet);
		}
		
		for (Pet pet: pets) {
			//Check for new types and insert them
			petTypeGateway.save(pet.getType());
			petsGateway.save(pet);
		}
		
		for (Owner owner: owners) {
			ownersGateway.save(owner);
		}
		
		for (Visit visit: visits) {
			visitsGateway.save(visit);
		}
		
		//Disconnect the gateways
		vetsGateway.disconnect();
		specGateway.disconnect();
        vetSpecGateway.disconnect();
		ownersGateway.disconnect();
		petsGateway.disconnect();
		visitsGateway.disconnect();
		
	}
	

}
