package com.bookshelf;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.*;
//import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.Modality;
import javafx.application.Platform;
//import javafx.collections.ListChangeListener;
//import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import com.controls.MainGUI;

public class BookGUI {
    
    ApplicationContext context = new ClassPathXmlApplicationContext("shelfconfig.xml");
    
    BookTemplate bookTemplate = (BookTemplate) context.getBean("bookTemplate");
    
    BookIssuedTemplate bookIssuedTemplate = (BookIssuedTemplate) context.getBean("bookIssuedTemplate");
    
    //MainGUI mainGUI = new MainGUI();
    
    public void createGUI(String text) {
        
        AnchorPane root = new AnchorPane();
        Pane sidePane = new Pane();
        Pane detailsPane = new Pane();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField categoryField = new TextField();
        Button addBtn = new Button("Add");
        Button reloadBtn = new Button("Reload");
        Button exitBtn = new Button("X");
        Label headerLabel = new Label("New Book");
        Label welcomeLabel = new Label("Welcome, "+text);
        Label issuedBookLabel = new Label("Issued Books");
        Label detailLabel = new Label("Book Details");
        Label bookTitle = new Label("Book Title:");
        Label author = new Label("Author:");
        Label category = new Label("Category:");
        Label titleLabel = new Label("Book Title Goes Here");
        Label authorLabel = new Label("Author's Name Goes Here");
        Label categoryLabel = new Label("Book Category Goes Here");
        TableView<Book> bookTable = new TableView<>();
        ObservableList<Book> booksObservable = FXCollections.<Book>observableArrayList();
        TableColumn<Book, Integer> idCol = new TableColumn<>("ID");
        TableColumn<Book, String> titleCol = new TableColumn<>("TITLE");
        TableColumn<Book, String> authorCol = new TableColumn<>("AUTHOR");
        TableColumn<Book, String> categoryCol = new TableColumn<>("CATEGORY");
        TableColumn<Book, Void> issueAction = new TableColumn<>("ISSUE");
        TableColumn<Book, Void> editAction = new TableColumn<>("EDIT");
        TableColumn<Book, Void> deleteAction = new TableColumn<>("DELETE");
        TableView<BookIssued> issuedTable = new TableView<>();
        ObservableList<BookIssued> issuedObservable = FXCollections.<BookIssued>observableArrayList();
        TableColumn<BookIssued, Integer> issuedIdCol = new TableColumn<>("ID");
        TableColumn<BookIssued, String> issuedTitleCol = new TableColumn<>("TITLE");
        TableColumn<BookIssued, String> issuedToCol = new TableColumn<>("ISSUED TO");
        TableColumn<BookIssued, Void> returnAction = new TableColumn<>("RETURN");
        //URL styleURL = this.getClass().getResource("bookgui.css");
        Scene scene = new Scene(root, 809, 663);
        Stage stage = new Stage();
        
        scene.getStylesheets().add("resources/css/bookgui.css");
        sidePane.getStyleClass().add("side-pane");
        detailsPane.getStyleClass().add("details-pane");
        addBtn.getStyleClass().add("add-button");
        reloadBtn.getStyleClass().add("reload-button");
        exitBtn.getStyleClass().add("round-exit-button");
        headerLabel.getStyleClass().add("header-label");
        welcomeLabel.getStyleClass().add("welcome-label");
        issuedBookLabel.getStyleClass().add("label-boldened");
        detailLabel.getStyleClass().add("label-boldened");
        bookTitle.getStyleClass().add("label-boldened");
        author.getStyleClass().add("label-boldened");
        category.getStyleClass().add("label-boldened");
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        issuedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        issuedTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        issuedToCol.setCellValueFactory(new PropertyValueFactory<>("issuedTo"));
        
        issueAction.setCellFactory(col -> {
          
            TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                
                @Override
                public void updateItem(Void item, boolean empty) {
                  
                    super.updateItem(item, empty);
                    
                    this.setText(null);
                    this.setGraphic(null);
                    
                    Button issueBtn = new Button("Issue");
                    
                    issueBtn.getStyleClass().add("action-buttons");
                    
                    
                    
                    issueBtn.setOnAction(e -> {
                        
                        int rowIndex = this.getTableRow().getIndex();
                        Book book = this.getTableView().getItems().get(rowIndex);
                        
                        issueBook(book.getBookId(), book.getBookTitle(), book.getBookAuthor(), book.getBookCategory(), stage);
                        issueBtn.setDisable(true);
                    });
                    
                    if(!empty) {
                        
                        this.setGraphic(issueBtn);
                        
                        Book book = this.getTableView().getItems().get(this.getTableRow().getIndex());
                        
                        try {
                            
                            BookIssued bookIssued = bookIssuedTemplate.getIssuedBook(book.getBookId());
                            issueBtn.setDisable(true);
                        }
                        catch(Exception ex) {
                            System.out.println("Book not issued yet");
                            issueBtn.setDisable(false);
                        }
                    }
                }
                
            };
            
            return cell;
        });
        
        editAction.setCellFactory(col -> {
          
            TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                
                @Override
                public void updateItem(Void item, boolean empty) {
                  
                    super.updateItem(item, empty);
                    
                    this.setText(null);
                    this.setGraphic(null);
                    
                    Button editBtn = new Button("Edit");
                    editBtn.getStyleClass().add("action-buttons");
                    
                    editBtn.setOnAction(e -> {
                        
                        int rowIndex = this.getTableRow().getIndex();
                        Book book = this.getTableView().getItems().get(rowIndex);
                        updateBook(book.getBookId(), book.getBookTitle(), book.getBookAuthor(), book.getBookCategory(), stage);
                    });
                    
                    if(!empty) {
                        
                        this.setGraphic(editBtn);
                    }
                }
                
            };
            
            return cell;
        });
        
        deleteAction.setCellFactory(col -> {
          
            TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                
                @Override
                public void updateItem(Void item, boolean empty) {
                  
                    super.updateItem(item, empty);
                    
                    this.setText(null);
                    this.setGraphic(null);
                    
                    Button deleteBtn = new Button("Delete");
                    deleteBtn.getStyleClass().add("action-buttons");
                    deleteBtn.setOnAction(e -> {
                        
                        int rowIndex = this.getTableRow().getIndex();
                        Book book = this.getTableView().getItems().get(rowIndex);
                        deleteBook(book.getBookId());
                    });
                    
                    if(!empty) {
                        
                        this.setGraphic(deleteBtn);
                    }
                }
                
            };
            
            return cell;
        });
        
        returnAction.setCellFactory(col -> {
          
            TableCell<BookIssued, Void> cell = new TableCell<BookIssued, Void>() {
                
                @Override
                public void updateItem(Void item, boolean empty) {
                  
                    super.updateItem(item, empty);
                    
                    this.setText(null);
                    this.setGraphic(null);
                    
                    Button returnBtn = new Button("Return");
                    returnBtn.getStyleClass().add("action-buttons");
                    returnBtn.setOnAction(e -> {
                        
                        int rowIndex = this.getTableRow().getIndex();
                        BookIssued book = this.getTableView().getItems().get(rowIndex);
                        returnBook(book.getId());
                    });
                    
                    if(!empty) {
                        
                        this.setGraphic(returnBtn);
                    }
                }
                
            };
            
            return cell;
        });
        
        bookTable.getSelectionModel().selectedItemProperty().addListener((b, oldValue, newValue) -> {
            
            if (newValue != null) {
                
            titleLabel.setText(newValue.getBookTitle());
            authorLabel.setText(newValue.getBookAuthor());
            categoryLabel.setText(newValue.getBookCategory());
            }
        });
        
        issuedTable.getSelectionModel().selectedItemProperty().addListener((b, oldValue, newValue) -> {
            
            if(newValue != null) {
                
                titleLabel.setText(newValue.getTitle());
                authorLabel.setText(newValue.getAuthor());
                categoryLabel.setText(newValue.getCategory());
            }
        });
        
        bookTable.getColumns().addAll(idCol, titleCol, authorCol, categoryCol, issueAction, editAction, deleteAction);
        issuedTable.getColumns().addAll(issuedIdCol, issuedTitleCol, issuedToCol, returnAction);
        
        root.getChildren().addAll(sidePane, welcomeLabel, issuedBookLabel, detailLabel, reloadBtn, bookTable, issuedTable, detailsPane, exitBtn);
        sidePane.getChildren().addAll(headerLabel, titleField, authorField, categoryField, addBtn);
        detailsPane.getChildren().addAll(bookTitle, titleLabel, author, authorLabel, category, categoryLabel);
        
        sidePane.setLayoutX(0);
        sidePane.setLayoutY(0);
        headerLabel.setLayoutX(23);
        headerLabel.setLayoutY(65);
        titleField.setLayoutX(6);
        titleField.setLayoutY(192);
        authorField.setLayoutX(6);
        authorField.setLayoutY(272);
        categoryField.setLayoutX(6);
        categoryField.setLayoutY(363);
        addBtn.setLayoutX(40);
        addBtn.setLayoutY(462);
        welcomeLabel.setLayoutX(167);
        welcomeLabel.setLayoutY(17);
        issuedBookLabel.setLayoutX(261);
        issuedBookLabel.setLayoutY(52);
        detailLabel.setLayoutX(594);
        detailLabel.setLayoutY(52);
        reloadBtn.setLayoutX(435);
        reloadBtn.setLayoutY(25);
        bookTitle.setLayoutX(14);
        bookTitle.setLayoutY(29);
        author.setLayoutX(14);
        author.setLayoutY(92);
        category.setLayoutX(14);
        category.setLayoutY(161);
        exitBtn.setLayoutX(785);
        exitBtn.setLayoutY(7);
        issuedTable.setLayoutX(169);
        issuedTable.setLayoutY(84);
        bookTable.setLayoutX(169);
        bookTable.setLayoutY(310);
        titleLabel.setLayoutX(119);
        titleLabel.setLayoutY(29);
        authorLabel.setLayoutX(119);
        authorLabel.setLayoutY(92);
        categoryLabel.setLayoutX(119);
        categoryLabel.setLayoutY(161);
        detailsPane.setLayoutX(501);
        detailsPane.setLayoutY(84);
        
        issuedTable.setPrefWidth(327);
        issuedTable.setPrefHeight(200);
        bookTable.setPrefWidth(560);
        bookTable.setPrefHeight(339);
        
        titleField.setPromptText("Enter Book Title");
        authorField.setPromptText("Enter Author's Name");
        categoryField.setPromptText("Enter Book Category");
        
        exitBtn.setOnAction(e -> {
            
            Platform.exit();
        });
        
        reloadBtn.setOnAction(e -> {
            
            getAllBooks(booksObservable, bookTable);
            getIssuedBooks(issuedObservable, issuedTable);
        });
        
        addBtn.setOnAction(e -> {
            
            addBook(titleField.getText(), authorField.getText(), categoryField.getText(), stage);
            
        });
        
        stage.setScene(scene);
        stage.setTitle("eShelf");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.sizeToScene();
        stage.show();
    }
    
    public void getAllBooks(ObservableList<Book> list, TableView<Book> bookTable) {
        
        list.clear();
            
        try {
            
            bookTemplate.getBooks().stream().forEach((book) -> {
                list.add(book);
                bookTable.setItems(list);
            });
        }
        catch(Exception ex) {
            
            System.err.println(ex);
            System.out.println("Exception Occurred");
        }
        
        
        
    }
    
    public void getIssuedBooks(ObservableList<BookIssued> list, TableView<BookIssued> table) {
        
        list.clear();
        
        try {
            
            bookIssuedTemplate.getAllIssuedBooks().stream().map((bookIssued) -> {
                list.add(bookIssued);
                return bookIssued;
            }).forEach((_item) -> {
                table.setItems(list);
            });
        }
        catch(Exception ex) {
            
            System.out.println("Exception Occured");
        }
    }
    
    public void addBook(String title, String author, String category, Window owner) {
        
        bookTemplate.create(title, author, category);
        //mainGUI.showSuccess(owner, "New Book Added To Shelf");
        showSuccess(owner, "New Book Added To Shelf");
    }
    
    public void updateBook(Integer id, String title, String author, String category, Window owner) {
        
        AnchorPane root = new AnchorPane();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField categoryField = new TextField();
        Label headerLabel = new Label("EDIT INFORMATION");
        Button closeBtn = new Button("X");
        Button editBtn = new Button("Edit");
        Scene scene = new Scene(root, 346, 441);
        //URL styleURL = this.getClass().getResource("editgui.css");
        Stage stage = new Stage();
        
        titleField.setPromptText("Enter Book Title");
        authorField.setPromptText("Enter Author's Name");
        categoryField.setPromptText("Enter Category");
        titleField.setText(title);
        authorField.setText(author);
        categoryField.setText(category);
      
        root.getChildren().addAll(headerLabel, closeBtn, titleField, authorField, categoryField, editBtn);
        scene.getStylesheets().add("resources/css/editgui.css");
        closeBtn.getStyleClass().add("round-exit-button");
        editBtn.getStyleClass().add("edit-button");
        
        closeBtn.setLayoutX(319);
        closeBtn.setLayoutY(1);
        headerLabel.setLayoutX(121);
        headerLabel.setLayoutY(28);
        titleField.setLayoutX(99);
        titleField.setLayoutY(99);
        authorField.setLayoutX(99);
        authorField.setLayoutY(194);
        categoryField.setLayoutX(99);
        categoryField.setLayoutY(303);
        editBtn.setLayoutX(144);
        editBtn.setLayoutY(383);
        
        closeBtn.setOnAction(e -> {
            
            stage.close();
        });
        
        editBtn.setOnAction(e -> {
            
            bookTemplate.update(id, titleField.getText(), authorField.getText(), categoryField.getText());
            stage.close();
            showSuccess(owner, "Information Was Successfully Updated!");
        });
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        
    }
    
    public void issueBook(Integer id, String title, String author, String category, Window owner) {
        
        AnchorPane root = new AnchorPane();
        TextField issueField = new TextField();
        Button closeBtn = new Button("x");
        Button issueBtn = new Button("Issue");
        Label issueToLabel = new Label("Issue To:");
        //URL styleURL = this.getClass().getResource("issuegui.css");
        Scene scene = new Scene(root, 325, 143);
        scene.getStylesheets().add("resources/css/issuegui.css");
        root.getChildren().addAll(closeBtn, issueToLabel, issueField, issueBtn);
        closeBtn.getStyleClass().add("round-exit-button");
        issueBtn.getStyleClass().add("issue-button");
        
        closeBtn.setLayoutX(300);
        closeBtn.setLayoutY(2);
        
        issueToLabel.setLayoutX(140);
        issueToLabel.setLayoutY(30);
        
        issueField.setLayoutX(88);
        issueField.setLayoutY(72);
        
        issueBtn.setLayoutX(137);
        issueBtn.setLayoutY(104);
         
        Stage stage = new Stage();
        
        issueBtn.setOnAction(e -> {
            
            bookIssuedTemplate.insert(id, title, author, category, issueField.getText());
            stage.close();
        });
        
        closeBtn.setOnAction(e -> {
            
            stage.close();
        });
        
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    
    public void deleteBook(Integer id) {
        
        bookIssuedTemplate.returnBookToShelf(id);
        bookTemplate.delete(id);
    }
    
    public void returnBook(Integer id) {
        
        bookIssuedTemplate.returnBookToShelf(id);
    }
    
    public void showSuccess(Window owner, String labelText) {
        
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 263, 173);
        Stage stage = new Stage();
        Text exit = new Text("X");
        Text successText = new Text(labelText);
        //URL imgUrl = getClass().getResource("success.png");
        Image success_img = new Image("resources/images/success.png");
        ImageView successImgView = new ImageView(success_img);
        //URL css = getClass().getResource("confirm.css");
        
        root.getChildren().addAll(exit, successText, successImgView);
        scene.getStylesheets().add("resources/css/confirm.css");
        
        successImgView.setLayoutX(14);
        successImgView.setLayoutY(37);
        successImgView.setFitWidth(70);
        successImgView.setFitHeight(76);
        
        exit.getStyleClass().add("x-cancel");
        exit.setLayoutX(249);
        exit.setLayoutY(13);
        exit.setOnMousePressed(e -> {
            
            stage.close();
        });
        
        successText.getStyleClass().add("error-text");
        successText.setLayoutX(112);
        successText.setLayoutY(55);
        successText.setWrappingWidth(117.546875);
        
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
