package com.msrit;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;

public class trueConfiguration extends Configuration {

    // abstract of jdbc connection
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }


    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

}
