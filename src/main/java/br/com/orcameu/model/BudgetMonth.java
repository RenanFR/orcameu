package br.com.orcameu.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Transient;

@Entity
@Table(name = "mes_orcamento")
public class BudgetMonth {

	@Id
    @SequenceGenerator(name = "budgetMonthSeq", sequenceName = "mes_orcamento_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "budgetMonthSeq")	
	private Long id;
	
	@Column(name = "nome")
	private String monthName;
	
	@Column(name = "num_mes")
	private Integer monthNumber;
	
	@Transient
	private BigDecimal lastMonthBalance;
	
	@Column(name = "renda_atual")
	private BigDecimal currentIncome;
	
	@Column(name = "renda_foi_paga")
	private Boolean isPaid;	
	
	@Column(name = "saldo_atual")
	private BigDecimal currentBalance;	
	
	@Column(name = "fatura_foi_paga")
	private Boolean isCreditCardBillPaid;
	
	@OneToMany(mappedBy = "month", cascade = CascadeType.ALL)
	private List<BudgetEntry> entries = new ArrayList<>();
	
	@OneToMany(mappedBy = "month", cascade = CascadeType.ALL)
	private List<BudgetAdjustmentEntry> adjustments = new ArrayList<>();
	
    @ManyToOne
    @JoinColumn(name = "orcamento_id", nullable = false)	
	private Budget budget;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public Integer getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(Integer monthNumber) {
		this.monthNumber = monthNumber;
	}

	public BigDecimal getLastMonthBalance() {
		return lastMonthBalance;
	}

	public void setLastMonthBalance(BigDecimal lastMonthBalance) {
		this.lastMonthBalance = lastMonthBalance;
	}

	public BigDecimal getCurrentIncome() {
		return currentIncome;
	}

	public void setCurrentIncome(BigDecimal currentIncome) {
		this.currentIncome = currentIncome;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Boolean getIsCreditCardBillPaid() {
		return isCreditCardBillPaid;
	}

	public void setIsCreditCardBillPaid(Boolean isCreditCardBillPaid) {
		this.isCreditCardBillPaid = isCreditCardBillPaid;
	}

	public List<BudgetEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<BudgetEntry> entries) {
		this.entries = entries;
	}

	public List<BudgetAdjustmentEntry> getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(List<BudgetAdjustmentEntry> adjustments) {
		this.adjustments = adjustments;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	@Override
	public String toString() {
		return "BudgetMonth [id=" + id + ", monthName=" + monthName + ", lastMonthBalance=" + lastMonthBalance
				+ ", currentIncome=" + currentIncome + ", isPaid=" + isPaid + ", currentBalance=" + currentBalance
				+ ", isCreditCardBillPaid=" + isCreditCardBillPaid + ", entries=" + entries + ", adjustments="
				+ adjustments + ", budget=" + budget + "]";
	}

}
