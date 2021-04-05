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

@Entity
@Table(name = "ajustes_mes")	
public class BudgetAdjustmentEntry {

	@Id
    @SequenceGenerator(name = "budgetAdjustmentEntrySeq", sequenceName = "ajustes_mes_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "budgetAdjustmentEntrySeq")
	private Long id;
	
	@Column(name = "descricao")	
	private String reason;
	
	@Enumerated(EnumType.STRING)	
	@Column(name = "tipo")	
	private BudgetAdjustmentEntryType type;
	
	@Column(name = "valor")
	private BigDecimal value;
	
	@Column(name = "data_ajuste")
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	private BudgetEntryStatus status;	
	
    @ManyToOne
    @JoinColumn(name = "mes_id")
	private BudgetMonth month;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BudgetAdjustmentEntryType getType() {
		return type;
	}

	public void setType(BudgetAdjustmentEntryType type) {
		this.type = type;
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
		return this.reason == null || this.reason.isBlank();
	}

	@Override
	public String toString() {
		return "BudgetAdjustmentEntry [id=" + id + ", reason=" + reason + ", type=" + type + ", value=" + value
				+ ", date=" + date + ", status=" + status + "]";
	}		
	
}
