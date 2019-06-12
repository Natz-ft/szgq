package com.icfcc.credit.platform.util.jpa;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSource();
    }

}