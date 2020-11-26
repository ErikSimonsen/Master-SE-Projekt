package de.dvdrental.entities.views;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "sales_by_film_category", schema = "public", catalog = "dvdrental")
public class SalesByFilmCategory {
    private String category;
    private BigInteger totalSales;

    @Id
    @Basic
    @Column(name = "category", nullable = false, length = 25)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "total_sales", nullable = true, precision = 0)
    public BigInteger getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigInteger totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesByFilmCategory that = (SalesByFilmCategory) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(totalSales, that.totalSales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, totalSales);
    }
}
