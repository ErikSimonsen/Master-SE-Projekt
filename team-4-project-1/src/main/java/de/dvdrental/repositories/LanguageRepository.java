package de.dvdrental.repositories;

import de.dvdrental.entities.Language;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class LanguageRepository extends CrudRepository<Language> { //TODO: Create tests
    public LanguageRepository() {
        super(Language.class);
    }
}
