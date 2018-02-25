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
import org.springframework.samples.petclinic.model.Person;

public class FakeOwner extends Person implements Owner {
	 
	private String address;
	private String city;
	private String telephone;
	private Set<Pet> pets;

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
	public Set<Pet> getPetsInternal() {
		if (this.pets == null) {
            this.pets = new HashSet<>();
        }
        return this.pets;
	}

	@Override
	public void setPetsInternal(Set<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<>(getPetsInternal());
        PropertyComparator.sort(sortedPets,
                new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPets);
	}

	@Override
	public void addPet(Pet pet) {
		if (pet.isNew()) {
			getPetsInternal().add(pet);
	    }
	    pet.setOwner(this);
	}

	@Override
	public Pet getPet(String name) {
		return getPet(name, false);
	}

	@Override
	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
        for (Pet pet : getPetsInternal()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
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
