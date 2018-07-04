
package com.controls;

import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;

public class AdminTemplate implements AdminDao {
    
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void setDataSource(DataSource dataSource) {
        
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public void create(String name, String password) {
        
        String SQL = "insert into admin values(?, ?)";
        jdbcTemplate.update(SQL, name, password);
    }
    
    @Override
    public List<Admin> getAllAdmins() {
        
        String SQL = "select * from admin";
        List<Admin> admins = jdbcTemplate.query(SQL, new AdminMapper());
        return admins;
    }
    
    @Override
    public Admin getAdmin(String name, String password) {
        
        String SQL = "select * from admin where name = ? and pass = ?";
        Admin admin = jdbcTemplate.queryForObject(SQL, new Object[]{name, password}, new AdminMapper());
        return admin;
    }
}