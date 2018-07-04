package com.controls;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<Admin> {
    
    @Override
    public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Admin admin = new Admin();
        admin.setName(rs.getString("name"));
        admin.setPassword(rs.getString("pass"));
        
        return admin;
    }
}