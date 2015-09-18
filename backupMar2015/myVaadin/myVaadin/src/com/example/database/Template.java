package com.example.database;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("serial")
public class Template implements Serializable{

	public static JdbcTemplate getTemplate(){
		
		try{
			
			Resource resource = new ClassPathResource("DataSourceDetails.xml");
			BeanFactory factory = new XmlBeanFactory(resource);
			DataBaseImpl ds = (DataBaseImpl) factory.getBean("s1");
			DataSource dbconn = ds.getDbConnection();
		
			return new JdbcTemplate(dbconn);

		} catch (Exception e){
			e.printStackTrace();
		}
				
		return null;
	}
}
