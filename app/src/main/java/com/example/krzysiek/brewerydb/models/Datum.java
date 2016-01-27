package com.example.krzysiek.brewerydb.models;

import java.util.HashMap;
import java.util.Map;


public class Datum {

    private String id;
    private String name;
    private String nameDisplay;
    private int styleId;
    private String isOrganic;
    private String status;
    private String statusDisplay;
    private String createDate;
    private String updateDate;
    private Style style;
    private String type;
    private String abv;
    private int glasswareId;
    private Labels labels;
    private Glass glass;
    private int availableId;
    private Available available;
    private String description;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
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
     * The nameDisplay
     */
    public String getNameDisplay() {
        return nameDisplay;
    }

    /**
     *
     * @param nameDisplay
     * The nameDisplay
     */
    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    /**
     *
     * @return
     * The styleId
     */
    public int getStyleId() {
        return styleId;
    }

    /**
     *
     * @param styleId
     * The styleId
     */
    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    /**
     *
     * @return
     * The isOrganic
     */
    public String getIsOrganic() {
        return isOrganic;
    }

    /**
     *
     * @param isOrganic
     * The isOrganic
     */
    public void setIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
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

    /**
     *
     * @return
     * The statusDisplay
     */
    public String getStatusDisplay() {
        return statusDisplay;
    }

    /**
     *
     * @param statusDisplay
     * The statusDisplay
     */
    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
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

    /**
     *
     * @return
     * The style
     */
    public Style getStyle() {
        return style;
    }

    /**
     *
     * @param style
     * The style
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The abv
     */
    public String getAbv() {
        return abv;
    }

    /**
     *
     * @param abv
     * The abv
     */
    public void setAbv(String abv) {
        this.abv = abv;
    }

    /**
     *
     * @return
     * The glasswareId
     */
    public int getGlasswareId() {
        return glasswareId;
    }

    /**
     *
     * @param glasswareId
     * The glasswareId
     */
    public void setGlasswareId(int glasswareId) {
        this.glasswareId = glasswareId;
    }

    /**
     *
     * @return
     * The labels
     */
    public Labels getLabels() {
        return labels;
    }

    /**
     *
     * @param labels
     * The labels
     */
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    /**
     *
     * @return
     * The glass
     */
    public Glass getGlass() {
        return glass;
    }

    /**
     *
     * @param glass
     * The glass
     */
    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    /**
     *
     * @return
     * The availableId
     */
    public int getAvailableId() {
        return availableId;
    }

    /**
     *
     * @param availableId
     * The availableId
     */
    public void setAvailableId(int availableId) {
        this.availableId = availableId;
    }

    /**
     *
     * @return
     * The available
     */
    public Available getAvailable() {
        return available;
    }

    /**
     *
     * @param available
     * The available
     */
    public void setAvailable(Available available) {
        this.available = available;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}