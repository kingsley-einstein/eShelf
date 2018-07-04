package com.bookshelf;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Book {
    
    IntegerProperty bookId = new SimpleIntegerProperty();
    StringProperty bookTitle = new SimpleStringProperty();
    StringProperty bookAuthor = new SimpleStringProperty();
    StringProperty bookCategory = new SimpleStringProperty();
    
    public void setBookId(Integer bookId) {
        
        this.bookId.set(bookId);
    }
    
    public Integer getBookId() {
        
        return bookId.get();
    }
    
    public void setBookTitle(String bookTitle) {
        
        this.bookTitle.set(bookTitle);
    }
    
    public String getBookTitle() {
        
        return bookTitle.get();
    }
    
    public void setBookAuthor(String bookAuthor) {
        
        this.bookAuthor.set(bookAuthor);
    }
    
    public String getBookAuthor() {
        
        return bookAuthor.get();
    }
    
    public void setBookCategory(String bookCategory) {
        
        this.bookCategory.set(bookCategory);
    }
    
    public String getBookCategory() {
        
        return bookCategory.get();
    }
}