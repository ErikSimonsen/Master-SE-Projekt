package de.dvdrental.jsfBeans.filter;

import de.dvdrental.jsfBeans.interfaces.BeanFilter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;


public class RentalBeanFilter extends BeanFilter {
    @Size(max = 60, message = "Maximal 60 Zeichen erlaubt")
    private String filmTitle;
    @Positive(message = "Geben Sie einen positiven Wert an")
    private Integer customerId;
    @Past(message = "Geben Sie ein Datum in der Vergangenheit an")
    private Date dateFrom;
    @Past(message = "Geben Sie ein Datum in der Vergangenheit an")
    private Date dateTo;
    private Returned returned;

    @Override
    public String toString() {
        return "RentalBeanFilter{" +
                "filmTitle='" + filmTitle + '\'' +
                ", customerId=" + customerId +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", returned=" + returned +
                '}';
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Returned getReturned() {
        return returned;
    }

    public void setReturned(Returned returned) {
        this.returned = returned;
    }

    public enum Returned {
        YES("Ja"),
        NO("Nein");

        private final String shortName;

        Returned(String shortName) {
            this.shortName = shortName;
        }

        public String getShortName() {
            return shortName;
        }
    }
}
