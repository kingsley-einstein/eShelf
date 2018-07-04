package com.bookshelf;

import javafx.beans.property.*;

public class BookIssued {
    
    StringProperty title = new SimpleStringProperty();
    StringProperty author = new SimpleStringProperty();
    StringProperty category = new SimpleStringProperty();
    StringProperty issuedTo = new SimpleStringProperty();
    IntegerProperty id = new SimpleIntegerProperty();
    
    public void setTitle(String title) {
        
        this.title.set(title);
    }
    
    public String getTitle() {
        
        return title.get();
    }
    
    public void setAuthor(String author) {
        
        this.author.set(author);
    }
    
    public String getAuthor() {
        
        return author.get();
    }
    
    public void setCategory(String category) {
        
        this.category.set(category);
    }
    
    public String getCategory() {
        
        return category.get();
    }
    
    public void setId(Integer id) {
        
        this.id.set(id);
    }
    
    public Integer getId() {
        
        return id.get();
    }
    
    public void setIssuedTo(String issuedTo) {
        
        this.issuedTo.set(issuedTo);
    }
    
    public String getIssuedTo() {
        
        return issuedTo.get();
    }
    
  }