package com.example.krzysiek.brewerydb.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Brewery {

    private int currentPage;
    private int numberOfPages;
    private int totalResults;
    private List<Datum> data = new ArrayList<Datum>();
    private String status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     * The currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     * The numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     *
     * @param numberOfPages
     * The numberOfPages
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The totalResults
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}