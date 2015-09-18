package com.example.database;

import java.io.Serializable;

import javax.sql.DataSource;

public class DataBaseImpl implements Serializable{
	
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public DataSource getDbConnection(){
		
		return datasource;
	}

}
