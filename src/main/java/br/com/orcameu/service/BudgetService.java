package br.com.orcameu.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;

import br.com.orcameu.model.Budget;
import br.com.orcameu.model.BudgetAdjustmentEntry;
import br.com.orcameu.model.BudgetAdjustmentEntryType;
import br.com.orcameu.model.BudgetEntry;
import br.com.orcameu.model.BudgetEntryStatus;
import br.com.orcameu.model.BudgetEntryType;
import br.com.orcameu.model.BudgetMonth;
import br.com.orcameu.repository.BudgetRepository;

@ApplicationScoped
public class BudgetService {
	
	private static final Logger LOG = Logger.getLogger(BudgetService.class);
	
	@Inject
	BudgetRepository budgetRepository;	

	@Transactional
	public void importFromExcel(File budgetFile) throws Exception {
		Budget budget = new Budget();
		
		try (Workbook file = new XSSFWorkbook(budgetFile)) {
			List<BudgetMonth> months = new ArrayList<>();
			budget.setYear(LocalDateTime.ofInstant(Files.readAttributes(budgetFile.toPath(), BasicFileAttributes.class).creationTime().toInstant(), ZoneOffset.UTC).getYear());
			for (int i = 0; i < file.getNumberOfSheets(); i++) {
				BudgetMonth budgetMonth = new BudgetMonth();
				Sheet monthSheet = file.getSheetAt(i);
				double lastMonthBalance = monthSheet.getRow(1).getCell(0).getNumericCellValue();
				double currentIncome = monthSheet.getRow(4).getCell(0).getNumericCellValue();
				boolean isPaid = monthSheet.getRow(4).getCell(1).getStringCellValue().equals("P");
				boolean isCreditCardBillPaid = monthSheet.getRow(6).getCell(1).getStringCellValue().equals("P");
				budgetMonth.setMonthName(file.getSheetAt(i).getSheetName());
				budgetMonth.setLastMonthBalance(new BigDecimal(lastMonthBalance));
				budgetMonth.setCurrentIncome(new BigDecimal(currentIncome));
				budgetMonth.setIsPaid(isPaid);
				budgetMonth.setIsCreditCardBillPaid(isCreditCardBillPaid);
				for (int j = 3; j < monthSheet.getLastRowNum(); j++) {
					Row expenseRow = monthSheet.getRow(j);
					BudgetEntry entry = new BudgetEntry();
					Optional.ofNullable(expenseRow.getCell(3)).ifPresentOrElse(desc -> {
						String expenseDescription = desc.getStringCellValue();
						entry.setDescription(expenseDescription);
					}, () -> { });
					Optional.ofNullable(expenseRow.getCell(4)).ifPresentOrElse(value -> {
						double expenseValue = value.getNumericCellValue();
						entry.setValue(new BigDecimal(expenseValue));
					}, () -> { });
					Optional.ofNullable(expenseRow.getCell(5)).ifPresentOrElse(date -> {
						if (!date.toString().trim().isBlank()) {
							Date expenseDate = date.getDateCellValue();
							entry.setDate(expenseDate.toInstant()
									.atZone(ZoneId.systemDefault())
									.toLocalDateTime());
						}
					}, () -> { });
					Optional.ofNullable(expenseRow.getCell(6)).ifPresentOrElse(type -> {
						String expenseType = type.getStringCellValue();
						if (!expenseType.trim().isBlank()) {
							try {
								entry.setType(BudgetEntryType.valueOf(expenseType));
							} catch (IllegalArgumentException e) {
								LOG.error(expenseType + " nao e um tipo valido de despesa");
							}
						}
					}, () -> { });
					Optional.ofNullable(expenseRow.getCell(7)).ifPresentOrElse(status -> {
						String expenseStatus = status.getStringCellValue();
						if (!expenseStatus.trim().isBlank()) {
							try {
								entry.setStatus(BudgetEntryStatus.valueOf(expenseStatus));
							} catch (Exception e) {
								LOG.error(expenseStatus + " nao e um status valido de despesa");
							}
						}
					}, () -> { });
					if (!entry.isEmptyRow()) {
						LOG.info(entry);
						entry.setMonth(budgetMonth);
						budgetMonth.getEntries().add(entry);
					}
				}
				for (int j = 11; j < monthSheet.getLastRowNum(); j++) {
					Row adjustmentRow = monthSheet.getRow(j);
					BudgetAdjustmentEntry adjustmentEntry = new BudgetAdjustmentEntry();
					Optional.ofNullable(adjustmentRow.getCell(10)).ifPresentOrElse(reason -> {
						String adjustmentReason = reason.getStringCellValue();
						adjustmentEntry.setReason(adjustmentReason);
					}, () -> { });
					Optional.ofNullable(adjustmentRow.getCell(11)).ifPresentOrElse(type -> {
						if (!type.toString().trim().isBlank()) {
							String adjustmentType = type.getStringCellValue();
							try {
								adjustmentEntry.setType(BudgetAdjustmentEntryType.valueOf(adjustmentType.toUpperCase()));
							} catch (Exception e) {
								LOG.error(adjustmentType + " nao e um tipo valido de ajuste");
							}							
						}
					}, () -> { });
					Optional.ofNullable(adjustmentRow.getCell(12)).ifPresentOrElse(value -> {
						double adjustmentValue = adjustmentRow.getCell(12).getNumericCellValue();
						adjustmentEntry.setValue(new BigDecimal(adjustmentValue));
					}, () -> { });
					if (!adjustmentEntry.isEmptyRow()) {
						adjustmentEntry.setMonth(budgetMonth);
						budgetMonth.getAdjustments().add(adjustmentEntry);
						LOG.info(adjustmentEntry);
					}
				}
				budgetMonth.setBudget(budget);
				months.add(budgetMonth);
				budget.getMonths().add(budgetMonth);
			}
			
		}
		budgetRepository.persistAndFlush(budget);

	}
	

}
