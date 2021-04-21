package br.com.orcameu.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "movimentacoes_mes")	
public class BudgetEntry {
	
	@Id
    @SequenceGenerator(name = "budgetEntrySeq", sequenceName = "movimentacoes_mes_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "budgetEntrySeq")	
	private Long id;
	
	@Column(name = "descricao")
	private String description;
	
	@Column(name = "valor")
	private BigDecimal value;
	
	@Column(name = "data_mov")
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private BudgetEntryType type;
	
	@Enumerated(EnumType.STRING)
	private BudgetEntryStatus status;

    @ManyToOne
    @JoinColumn(name = "mes_id")
    @JsonBackReference
	private BudgetMonth month;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BudgetEntryType getType() {
		return type;
	}

	public void setType(BudgetEntryType type) {
		this.type = type;
	}

	public BudgetEntryStatus getStatus() {
		return status;
	}

	public void setStatus(BudgetEntryStatus status) {
		this.status = status;
	}

	public BudgetMonth getMonth() {
		return month;
	}

	public void setMonth(BudgetMonth month) {
		this.month = month;
	}

	public Boolean isEmptyRow() {
		return this.description == null || this.description.isBlank();
	}

	@Override
	public String toString() {
		return "BudgetEntry [id=" + id + ", description=" + description + ", value=" + value + ", date=" + date
				+ ", type=" + type + ", status=" + status + "]";
	}	
	
}
