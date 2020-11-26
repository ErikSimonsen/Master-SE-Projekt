package de.dvdrental.jsfBeans.filter;

public class SortingField {
    private String fieldName;
    private Boolean asc = true;

    public SortingField(String fieldName) {
        this.fieldName = fieldName;
    }

    public void toggleSortAsc() {
        setAsc(!getAsc());
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }
}
