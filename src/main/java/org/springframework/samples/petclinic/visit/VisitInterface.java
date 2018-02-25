package org.springframework.samples.petclinic.visit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;

public interface VisitInterface {

	    public Date getDate();

	    public void setDate(Date date);

	    public String getDescription();

	    public void setDescription(String description);

	    public Integer getPetId();

	    public void setPetId(Integer petId);

}
