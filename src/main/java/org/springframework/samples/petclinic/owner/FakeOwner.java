package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.model.Person;

public class FakeOwner extends Person implements OwnerInterface {
	 
	private String address;
	private String city;
	private String telephone;
	private Set<PetInterface> pets;

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getTelephone() {
		return telephone;
	}

	@Override
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public Set<PetInterface> getPetsInternal() {
		if (this.pets == null) {
            this.pets = new HashSet<>();
        }
        return this.pets;
	}

	@Override
	public void setPetsInternal(Set<PetInterface> pets) {
		this.pets = pets;
	}

	@Override
	public List<PetInterface> getPets() {
		List<PetInterface> sortedPets = new ArrayList<>(getPetsInternal());
        PropertyComparator.sort(sortedPets,
                new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPets);
	}

	@Override
	public void addPet(PetInterface pet) {
		if (((Pet) pet).isNew()) {
			getPetsInternal().add(pet);
	    }
	    pet.setOwner(this);
	}

	@Override
	public PetInterface getPet(String name) {
		return getPet(name, false);
	}

	@Override
	public PetInterface getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
        for (PetInterface pet : getPetsInternal()) {
            if (!ignoreNew || !((Pet) pet).isNew()) {
                String compName = ((Pet) pet).getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
	}
	
	@Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId()).append("new", this.isNew())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName()).append("address", this.address)
                .append("city", this.city).append("telephone", this.telephone).toString();
    }

}
