package com.example.krzysiek.brewerydb.models;

/**
 * Created by SlawomirKustra on 27.01.2016.
 */
import java.util.HashMap;
import java.util.Map;



    public class Labels {

        private String icon;
        private String medium;
        private String large;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The icon
         */
        public String getIcon() {
            return icon;
        }

        /**
         *
         * @param icon
         * The icon
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }

        /**
         *
         * @return
         * The medium
         */
        public String getMedium() {
            return medium;
        }

        /**
         *
         * @param medium
         * The medium
         */
        public void setMedium(String medium) {
            this.medium = medium;
        }

        /**
         *
         * @return
         * The large
         */
        public String getLarge() {
            return large;
        }

        /**
         *
         * @param large
         * The large
         */
        public void setLarge(String large) {
            this.large = large;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
