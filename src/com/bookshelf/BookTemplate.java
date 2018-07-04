package com.bookshelf;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;

public class BookTemplate implements BookDAO {
    
    DataSource dataSource;
    JdbcTemplate jdbcTemplate;
    PlatformTransactionManager transactionManager;
    
    @Override
    public void setDataSource(DataSource dataSource) {
        
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
    
    @Override
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        
        this.transactionManager = transactionManager;
    }
    
    @Override
    public void create(String title, String author, String category) {
        
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            
            String QUERY = "insert into books(title, author, category) values(?, ?, ?)";
            jdbcTemplate.update(QUERY, title, author, category);
            transactionManager.commit(status);
            System.out.println("Commit made");
        }
        catch(DataAccessException ex) {
            
            transactionManager.rollback(status);
            System.out.println("Status rolled back");
            System.err.println(ex);
            
        }
        
    }
    
    @Override
    public List<Book> getBooks() {
        
        String QUERY = "select * from books";
        List<Book> books = jdbcTemplate.query(QUERY, new BookMapper());
        
        return books;
    }
    
    @Override
    public void update(Integer id, String title, String author, String category) {
        
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            
            String QUERY = "update books set title = ?, author = ?, category = ? where id = ?";
            jdbcTemplate.update(QUERY, title, author, category, id);
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
    public void delete(Integer id) {
        
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            
            String QUERY = "delete from books where id =?";
            jdbcTemplate.update(QUERY, id);
            transactionManager.commit(status);
            System.out.println("Commit Made");
        }
        catch(DataAccessException ex) {
           
            transactionManager.rollback(status);
            System.out.println("Status rolled back");
            System.err.println(ex);
            
        }
    }
}