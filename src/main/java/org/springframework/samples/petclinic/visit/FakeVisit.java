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


@Entity
@Table(name = "visits")
public class FakeVisit extends BaseEntity implements VisitInterface{
	    @Column(name = "visit_date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date date;

	    @NotEmpty
	    @Column(name = "description")
	    private String description;

	    @Column(name = "pet_id")
	    private Integer petId;

	    /**
	     * Creates a new instance of Visit for the current date
	     */
	    public FakeVisit() {
	        this.date = new Date();
	    }

	    public Date getDate() {
	        return this.date;
	    }

	    public void setDate(Date date) {
	        this.date = date;
	    }

	    public String getDescription() {
	        return this.description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Integer getPetId() {
	        return this.petId;
	    }

	    public void setPetId(Integer petId) {
	        this.petId = petId;
	    }

	

}
