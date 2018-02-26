/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitInterface;

/**
 * Simple business object representing a fakepet.
 */
@Entity
@Table(name = "pets")
public class FakePet extends NamedEntity implements PetInterface {

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerInterface owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "petId", fetch = FetchType.EAGER)
    private Set<VisitInterface> visits = new LinkedHashSet<>();

    @Override
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public Date getBirthDate() {
        return this.birthDate;
    }

    @Override
    public PetType getType() {
        return this.type;
    }

    @Override
    public void setType(PetType type) {
        this.type = type;
    }

    @Override
    public OwnerInterface getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(OwnerInterface owner) {
        this.owner = owner;
    }

    @Override
    public Set<VisitInterface> getVisitsInternal() {
        if (this.visits == null) {
            this.visits = new HashSet<>();
        }
        return this.visits;
    }

    @Override
    public void setVisitsInternal(Set<VisitInterface> visits) {
        this.visits = visits;
    }

    @Override
    public List<VisitInterface> getVisits() {
        List<VisitInterface> sortedVisits = new ArrayList<>(getVisitsInternal());
        PropertyComparator.sort(sortedVisits,
                new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedVisits);
    }

    @Override
    public void addVisit(VisitInterface visit) {
        getVisitsInternal().add(visit);
        visit.setPetId(this.getId());
    }
}