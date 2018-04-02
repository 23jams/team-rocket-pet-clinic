package org.springframework.samples.petclinic;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;


@Controller
public class ConsistencyChecker {
	// contains consistency checkers
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	// Repositories for HSQLDB

	final OwnerRepository ownerRepo;
	final PetRepository petRepo;
	final VetRepository vetRepo;
	final VisitRepository visitRepo;

	// Gateways for MySQL
	private VetsGateway vetsGateway;
	private SpecialtiesGateway specGateway;
	private OwnersGateway ownersGateway;
	private PetTypeGateway petTypeGateway;
	private PetsGateway petsGateway;
	private VisitsGateway visitsGateway;
	private VetSpecialtiesGateway vetSpecGateway;

	@Autowired
	public ConsistencyChecker(OwnerRepository clinicService, PetRepository petRepo, VetRepository vetRepo,
			VisitRepository visitRepo, VetsGateway vetsGate, SpecialtiesGateway specialties, OwnersGateway ownersgate,
			PetTypeGateway petTypeGate, PetsGateway petGate, VisitsGateway visitGate,
			VetSpecialtiesGateway vetSpecGate) {
		this.ownerRepo = clinicService;
		this.petRepo = petRepo;
		this.vetRepo = vetRepo;
		this.visitRepo = visitRepo;

		this.vetsGateway = vetsGate;
		this.specGateway = specialties;
		this.ownersGateway = ownersgate;
		this.petTypeGateway = petTypeGate;
		this.petsGateway = petGate;
		this.visitsGateway = visitGate;
		this.vetSpecGateway = vetSpecGate;
	}

	@Async
	@Scheduled(fixedRate = (1000*10))
	public void checkConsistency() {
		// Fetch all data from HSQLDB
		Collection<Owner> owners = ownerRepo.findAll();
		List<Pet> pets = petRepo.findAll();
		Collection<Vet> vets = vetRepo.findAll();
		List<Visit> visits = visitRepo.findAll();

		Collection<Vet> vetsSql = vetsGateway.findAll();

		// Verify that data between both databases is consistent
		if (vets != vetsSql) {
			// if not consistent, save the data from HSQLDB to mySQLDB
			for (Vet vet : vets) {
				vetsGateway.save(vet);
				// Check for new specialties and insert them
				for (Specialty specialty : vet.getSpecialties()) {
					specGateway.save(specialty);
				}
				vetSpecGateway.save(vet);
			}
		}

		for (Pet pet : pets) {
			// Checks for consistency in pets
			int petId = pet.getId();
			if (pet != petsGateway.findById(petId)) {
				petTypeGateway.save(pet.getType());
				petsGateway.save(pet);
				break;
			}
		}

		for (Owner owner : owners) {
			int ownerId = owner.getId();
			if (owner != ownersGateway.findById(ownerId)) {
				ownersGateway.save(owner);
				break;
			}
		}

		for (Visit visit : visits) {
			int visitId = visit.getId();
			if (visit != visitsGateway.findById(visitId)) {
				visitsGateway.save(visit);
				break;
			}
		}

		// Disconnect the gateways
		vetsGateway.disconnect();
		specGateway.disconnect();
		vetSpecGateway.disconnect();
		ownersGateway.disconnect();
		petsGateway.disconnect();
		visitsGateway.disconnect();

	}

}
