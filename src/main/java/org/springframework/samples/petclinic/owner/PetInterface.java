package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitInterface;

public interface PetInterface {


    public void setBirthDate(Date birthDate);

    public Date getBirthDate();

    public PetType getType();

    public void setType(PetType type);

    public OwnerInterface getOwner();

    public void setOwner(OwnerInterface owner);

    public Set<VisitInterface> getVisitsInternal();

    public void setVisitsInternal(Set<VisitInterface> visits);

    public List<VisitInterface> getVisits();

    public void addVisit(VisitInterface visit);
}
