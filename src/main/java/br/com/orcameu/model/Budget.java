package br.com.orcameu.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orcamentos")	
public class Budget {
	
	@Id
    @SequenceGenerator(name = "budgetSeq", sequenceName = "orcamentos_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "budgetSeq")	
	private Long id;
	
	@Column(name = "ano")
	private Integer year;
	
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<BudgetMonth> months = new HashSet<>();
	
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
	private User user;	
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Set<BudgetMonth> getMonths() {
		return months;
	}

	public void setMonths(Set<BudgetMonth> months) {
		this.months = months;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

}
