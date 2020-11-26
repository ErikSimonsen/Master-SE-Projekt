package de.dvdrental.jsfBeans.interfaces;

import de.dvdrental.repositories.interfaces.CrudWithFilterRepository;

import javax.faces.model.SelectItem;
import java.util.List;

/**
 * Contains the logic for Pagination of Views, so defines how many pages and rows per page are displayed, increments/decrements the page and renders or does not render a button.
 * Also retrieves new Data through the bound Repository Object. The refreshData/refreshDataResetPage Method should be called whenever a new filter is selected or input is submitted, ideally via ajax. Look
 * at films.xhtml for examples.
 *
 * @param <EntityType>
 * @param <FilterType>
 * @param <RepositoryType>
 */
public abstract class Pagination<EntityType, FilterType extends BeanFilter, RepositoryType extends CrudWithFilterRepository<EntityType, FilterType>> {
    private static int MaxRowCount;
    private int currentPage = 0;
    private int maxPages;
    private boolean renderPrevBtn = false;
    private boolean renderNextBtn = true;

    private List<EntityType> content;
    private RepositoryType repository;
    private FilterType filter;

    public Pagination(Integer maxRowCount) {
        MaxRowCount = maxRowCount;
    }

    /**
     * The assignment of the repository and the filter object has to be done in an init method, because both are Injected in the JSF Bean that extends this Pagination class.
     * However in the constructor of the jsf bean and therefore the call to super() the @Injects are not done yet and therefore the repository etc. are still null there.
     * This init() method should be called in the @PostConstruct Method
     *
     * @param repository RepositoryType
     * @param filter     FilterType
     */
    public void init(RepositoryType repository, FilterType filter) {
        this.repository = repository;
        this.filter = filter;
        refreshData();
    }

    public static int getMaxRowCount() {
        return MaxRowCount;
    }


    public int calcMaxPages(Long totalRows) {
        //round up the pages, otherwise the last data would not be shown, example: 340 total rows / 50 rows per page -> 6.8 (so 6 pages), which would mean that the last 40 rows would not be displayed because there wouldn't be enough pages
        //convert second param to double to get a double value out of the calculation, as double is bigger than long or int
        double maxPages = Math.ceil((totalRows / (double) getMaxRowCount()));
        return (int) maxPages;
    }

    /**
     * Recalculates the maximum pages that can be displayed for the data. Retrieves the data again with the filter object and the (eventually set) sorting object.
     * Also selects the data based on the current Page and the Maximum Rows displayed per page (so if you have a button that executes prevPage/next page and then calls refreshData
     * you get the next 50 rows of the query)
     */
    public void refreshData() {
        Long count = repository.count(filter);
        setMaxPages(calcMaxPages(count));

        content = repository.getAll(filter, getCurrentPage() * getMaxRowCount(), getMaxRowCount());
        checkButtonsRender();
    }

    /**
     * This Method should be called, whenever there is a filter selected or sorting is done, so that the user sees the first page again.
     * Note: In JSF the content still has to be rendered again via "render=''"
     */
    public void refreshDataResetPage() {
        this.setCurrentPage(0);
        refreshData();
    }

    /**
     * Booleans are used, because the Buttons have the attribute "rendered" which is bound to a boolean var, if that is false the button wont get rendered
     */
    public void checkButtonsRender() {
        renderPrevBtn = currentPage > 0;
        //current page starts at 0 -> decrement maxPages
        renderNextBtn = currentPage < (maxPages - 1);
    }

    public void previousPage() {
        currentPage--;
    }

    public void nextPage() {
        currentPage++;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public List<EntityType> getContent() {
        return content;
    }

    public void setContent(List<EntityType> content) {
        this.content = content;
    }

    public Object[] getPages() {
        SelectItem[] items = new SelectItem[maxPages];
        for (int i = 0; i < items.length; i++) {
            //Increment i to display "correct" page, so instead of 0 there is 1 displayed etc.
            items[i] = new SelectItem(i, String.valueOf(i + 1));
        }
        return items;
    }

    public boolean isRenderPrevBtn() {
        return renderPrevBtn;
    }

    public void setRenderPrevBtn(boolean renderPrevBtn) {
        this.renderPrevBtn = renderPrevBtn;
    }

    public boolean isRenderNextBtn() {
        return renderNextBtn;
    }

    public void setRenderNextBtn(boolean renderNextBtn) {
        this.renderNextBtn = renderNextBtn;
    }

}
