package br.com.orcameu.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.orcameu.model.Budget;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class BudgetRepository implements PanacheRepository<Budget> {

}
