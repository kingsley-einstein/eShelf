package com.controls;

import javax.sql.DataSource;
import java.util.List;

public interface AdminDao {
    
    /**
     * The data source for the database connection
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource);
    
    /**
     * The method to create a new administrator
     * @param name
     * @param password
     */
    public void create(String name, String password);
    
    /**
     * Method to read database for registered administrators
     * @return
     */
    public List<Admin> getAllAdmins();
    
    /**
     * The method used to query for a registered administrator
     * @param name
     * @param password
     * @return
     */
    public Admin getAdmin(String name, String password);
    
}