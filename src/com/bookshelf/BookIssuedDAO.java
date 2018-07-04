package com.bookshelf;

import java.util.List;

public interface BookIssuedDAO {
    
    /**
     * Method to insert newly issued book
     * @param id
     * @param title
     * @param author
     * @param category
     * @param issuedTo
     */
    public void insert(Integer id, String title, String author, String category, String issuedTo);
    
    /**
     * Method to get all issued books
     * @return
     */
    public List<BookIssued> getAllIssuedBooks();
    
    /**
     * Method to get an issued book based on parameter passed
     * @param id
     * @return
     */
    public BookIssued getIssuedBook(Integer id);
    
    /**
     * Method to delete issued book indicating that it has been returned
     * @param id
     */
    public void returnBookToShelf(Integer id);
}