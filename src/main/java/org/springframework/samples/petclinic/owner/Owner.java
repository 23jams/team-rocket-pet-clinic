package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

public interface Owner {
	
	public String getAddress();

	public void setAddress(String address) ;

	public String getCity();

	public void setCity(String city);

	public String getTelephone();

	public void setTelephone(String telephone);

	public Set<Pet> getPetsInternal();

	public void setPetsInternal(Set<Pet> pets);

	public List<Pet> getPets();

	public void addPet(Pet pet);

	/**
	* Return the Pet with the given name, or null if none found for this Owner.
	*
	* @param name to test
	* @return true if pet name is already in use
	*/
	public Pet getPet(String name);

	/**
	* Return the Pet with the given name, or null if none found for this Owner.
	*
	* @param name to test
	* @return true if pet name is already in use
	*/
	public Pet getPet(String name, boolean ignoreNew);

	@Override
	public String toString();
	
}
