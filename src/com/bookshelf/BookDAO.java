package com.bookshelf;

import javax.sql.DataSource;
import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;

public interface BookDAO {
    
    /**
     * Method to set the data source
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource);
    
    /**
     * Method to set the platform transaction manager
     * @param transactionManager
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager);
    
    /**
     * Method to create a new record in the book table
     * @param title
     * @param author
     * @param category
     */
    public void create(String title, String author, String category);
    
    /**
     * Method to get all books
     * @return
     */
    public List<Book> getBooks();
    
    /**Method to update book based on passed parameters
     * @param id
     * @param title
     * @param author
     * @param category
     */
    public void update(Integer id, String title, String author, String category);
    
    /**
     * Method to delete book based on passed id
     * @param id
     */
    public void delete(Integer id);
    
}