package com.bookshelf;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import javax.sql.DataSource;
import java.util.List;

public class BookIssuedTemplate implements BookIssuedDAO {
    
    DataSource dataSource;
    JdbcTemplate jdbcTemplate;
    PlatformTransactionManager transactionManager;
    
    public void setDataSource(DataSource dataSource) {
        
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        
    }
    
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        
        this.transactionManager = transactionManager;
    }

    @Override
    public void insert(Integer id, String title, String author, String category, String issuedTo) {
     
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            
            String QUERY = "insert into books_issued values (?, ?, ?, ?, ?)";
            jdbcTemplate.update(QUERY, id, title, author, category, issuedTo);
            transactionManager.commit(status);
            System.out.println("Commit Made");
            
        }
        catch(DataAccessException ex) {
            
            transactionManager.rollback(status);
            System.out.println("Status rolled back");
            System.err.println(ex);
            
        }
    }

    @Override
    public List<BookIssued> getAllIssuedBooks() {
     
        String QUERY = "select * from books_issued";
        List<BookIssued> books = jdbcTemplate.query(QUERY, new BookIssuedMapper());
        
        return books;
    }

    @Override
    public BookIssued getIssuedBook(Integer id) {
    
        String QUERY = "select * from books_issued where id = ?";
        BookIssued book = jdbcTemplate.queryForObject(QUERY, new Object[]{id}, new BookIssuedMapper());
        
        return book;
        
    }

    @Override
    public void returnBookToShelf(Integer id) {
        
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            
            String QUERY = "delete from books_issued where id = ?";
            jdbcTemplate.update(QUERY, id);
            transactionManager.commit(status);
            System.out.println("Commit made");
            
        }
        catch(DataAccessException ex) {
            
            transactionManager.rollback(status);
            System.out.println("Status rolled back");
            System.err.println(ex);
        }
    }
    
    
}