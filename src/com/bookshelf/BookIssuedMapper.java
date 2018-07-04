package com.bookshelf;

import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

public class BookIssuedMapper implements RowMapper<BookIssued> {
    
    @Override
    public BookIssued mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        BookIssued bookIssued = new BookIssued();
        
        bookIssued.setTitle(rs.getString("title"));
        bookIssued.setAuthor(rs.getString("author"));
        bookIssued.setCategory(rs.getString("category"));
        bookIssued.setId(rs.getInt("id"));
        bookIssued.setIssuedTo(rs.getString("issuedto"));
        
        return bookIssued;
    }
}