package com.example.krzysiek.brewerydb.models;

import java.util.HashMap;
import java.util.Map;

public class Style {

    private int id;
    private int categoryId;
    private Category category;
    private String name;
    private String shortName;
    private String description;
    private String ibuMin;
    private String ibuMax;
    private String abvMin;
    private String abvMax;
    private String srmMin;
    private String srmMax;
    private String ogMin;
    private String fgMin;
    private String fgMax;
    private String createDate;
    private String updateDate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     *
     * @param shortName
     * The shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The ibuMin
     */
    public String getIbuMin() {
        return ibuMin;
    }

    /**
     *
     * @param ibuMin
     * The ibuMin
     */
    public void setIbuMin(String ibuMin) {
        this.ibuMin = ibuMin;
    }

    /**
     *
     * @return
     * The ibuMax
     */
    public String getIbuMax() {
        return ibuMax;
    }

    /**
     *
     * @param ibuMax
     * The ibuMax
     */
    public void setIbuMax(String ibuMax) {
        this.ibuMax = ibuMax;
    }

    /**
     *
     * @return
     * The abvMin
     */
    public String getAbvMin() {
        return abvMin;
    }

    /**
     *
     * @param abvMin
     * The abvMin
     */
    public void setAbvMin(String abvMin) {
        this.abvMin = abvMin;
    }

    /**
     *
     * @return
     * The abvMax
     */
    public String getAbvMax() {
        return abvMax;
    }

    /**
     *
     * @param abvMax
     * The abvMax
     */
    public void setAbvMax(String abvMax) {
        this.abvMax = abvMax;
    }

    /**
     *
     * @return
     * The srmMin
     */
    public String getSrmMin() {
        return srmMin;
    }

    /**
     *
     * @param srmMin
     * The srmMin
     */
    public void setSrmMin(String srmMin) {
        this.srmMin = srmMin;
    }

    /**
     *
     * @return
     * The srmMax
     */
    public String getSrmMax() {
        return srmMax;
    }

    /**
     *
     * @param srmMax
     * The srmMax
     */
    public void setSrmMax(String srmMax) {
        this.srmMax = srmMax;
    }

    /**
     *
     * @return
     * The ogMin
     */
    public String getOgMin() {
        return ogMin;
    }

    /**
     *
     * @param ogMin
     * The ogMin
     */
    public void setOgMin(String ogMin) {
        this.ogMin = ogMin;
    }

    /**
     *
     * @return
     * The fgMin
     */
    public String getFgMin() {
        return fgMin;
    }

    /**
     *
     * @param fgMin
     * The fgMin
     */
    public void setFgMin(String fgMin) {
        this.fgMin = fgMin;
    }

    /**
     *
     * @return
     * The fgMax
     */
    public String getFgMax() {
        return fgMax;
    }

    /**
     *
     * @param fgMax
     * The fgMax
     */
    public void setFgMax(String fgMax) {
        this.fgMax = fgMax;
    }

    /**
     *
     * @return
     * The createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     * The createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     * The updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     *
     * @param updateDate
     * The updateDate
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}