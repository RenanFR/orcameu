package br.com.orcameu.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.orcameu.model.BudgetEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class BudgetEntryRepository implements PanacheRepository<BudgetEntry> {

}
