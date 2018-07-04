package com.bookshelf;

import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

public class BookMapper implements RowMapper<Book> {
    
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Book book = new Book();
        
        book.setBookId(rs.getInt("id"));
        book.setBookTitle(rs.getString("title"));
        book.setBookAuthor(rs.getString("author"));
        book.setBookCategory(rs.getString("category"));
        
        return book;
    }
}