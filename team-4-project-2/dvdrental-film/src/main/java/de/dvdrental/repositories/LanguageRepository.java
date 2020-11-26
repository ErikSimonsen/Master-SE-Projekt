package de.dvdrental.repositories;

import de.dvdrental.entities.Language;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class LanguageRepository extends CrudRepository<Language> {
    public LanguageRepository() {
        super(Language.class);
    }

    public Language getByName(String languageName) {
        List<Language> matches =  em.createQuery("select l from Language l where upper(l.name) = upper(:name)", Language.class)
                                     .setParameter("name", languageName).getResultList();
        if (matches.isEmpty())
            return null;
        return matches.get(0);
    }
}
