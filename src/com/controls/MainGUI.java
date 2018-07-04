package com.controls;

import com.bookshelf.BookGUI;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
//import javafx.scene.Group;
//import java.net.URL;
//import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainGUI extends Application {
    
    ApplicationContext context = new ClassPathXmlApplicationContext("shelfconfig.xml");
    
    AdminTemplate adminTemplate = (AdminTemplate) context.getBean("adminTemplate");
    
    BookGUI mainGUI = new BookGUI();
    
    @Override
    public void start(Stage stage) {
        
        initApp(stage);
    }
    
    public void initApp(Stage stage) {
        
        AnchorPane root = new AnchorPane();
        Pane sidePane = new Pane();
        TextField usernameFld = new TextField();
        PasswordField passFld = new PasswordField();
        //URL admin_img_url = getClass().getResource("resources/images/admin.jpg");
        //URL lock_img_url = getClass().getResource("th-4.jpg");
        //URL addAdminUrl = getClass().getResource("add-admin.jpg");
        Label headerLabel = new Label("eShelf");
        Label defLabel = new Label("The Electronic Shelf");
        Button loginBtn = new Button("Login");
        Button addNewAdminBtn = new Button("Register New Admin");
        Circle circle = new Circle(7.0);
        Text exit = new Text("X");
        Image adminImg = new Image("resources/images/admin.jpg");
        Image lockImg = new Image("resources/images/th-4.jpg");
        //Image addAdminImg = new Image("resources/images/add-admin.jpg");
        ImageView adminImgView = new ImageView(adminImg);
        ImageView lockImgView = new ImageView(lockImg);
        //ImageView addImgView = new ImageView(addAdminImg);
        //Group group = new Group();
        Scene scene = new Scene(root, 517, 412, Color.TRANSPARENT);
        
        root.getChildren().addAll(sidePane, circle, exit, adminImgView, lockImgView, usernameFld, passFld, loginBtn, addNewAdminBtn);
        
        setStyles(scene, sidePane, usernameFld, passFld, adminImgView, lockImgView, loginBtn, addNewAdminBtn, circle, exit, headerLabel, defLabel);
        
        bindAction(loginBtn, usernameFld, passFld, exit, stage, addNewAdminBtn);
        
        stage.setScene(scene);
        stage.setTitle("iShelf");
        stage.setResizable(false);
        stage.sizeToScene();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOpacity(1.0);
        stage.toBack();
        stage.show();
    }
    
    public void setStyles(Scene scene, Pane sidePane, TextField usernameFld, PasswordField passFld, ImageView adminImg, ImageView lockImg, Button logBtn, Button regBtn, Circle circle, Text exit, Label headerLabel, Label defLabel) {
        
        //URL styleUrl = getClass().getResource("loginstyle.css");
        
        scene.getStylesheets().addAll("resources/css/loginstyle.css");
        
        sidePane.getStyleClass().add("side-pane");
        
        logBtn.getStyleClass().add("login-button");
        
        regBtn.getStyleClass().add("reg-button");
        
        adminImg.setFitHeight(25);
        
        adminImg.setFitWidth(25);
        
        lockImg.fitHeightProperty().bind(adminImg.fitHeightProperty());
        
        lockImg.fitWidthProperty().bind(adminImg.fitWidthProperty());
        
        //addImg.fitWidthProperty().bind(adminImg.fitWidthProperty().subtract(5));
        
        //addImg.fitHeightProperty().bind(adminImg.fitHeightProperty().subtract(10));
        
        //addImg.getStyleClass().add("add-img");
        
        circle.getStyleClass().add("exit-circle");
        
        exit.getStyleClass().add("xit");
        
        headerLabel.getStyleClass().add("header-label");
        
        defLabel.getStyleClass().add("def-label");
        
        sidePane.setLayoutX(1);
        
        sidePane.setLayoutY(-1);
        
        logBtn.setLayoutX(219);
        
        logBtn.setLayoutY(300);
        
        regBtn.setLayoutX(331);
        
        regBtn.setLayoutY(40);
        
        //regBtn.setGraphic(addImg);
        
        adminImg.setLayoutX(185);
        
        adminImg.setLayoutY(145);
        
        lockImg.setLayoutX(185);
        
        lockImg.setLayoutY(227);
        
        //group.setLayoutX(510);
        
        //group.setLayoutY(7);
        
        usernameFld.setLayoutX(217);
        
        usernameFld.setLayoutY(144);
        
        passFld.setLayoutX(217);
        
        passFld.setLayoutY(226);
        
        headerLabel.setLayoutX(12);
        
        headerLabel.setLayoutY(122);
        
        defLabel.setLayoutX(8);
        
        defLabel.setLayoutY(188);
        
        circle.setLayoutX(510);
        
        circle.setLayoutY(7);
        
        exit.setLayoutX(507);
        
        exit.setLayoutY(11);
        
        //group.getChildren().addAll(circle, exit);
        
        sidePane.getChildren().addAll(headerLabel, defLabel);
    }
    
    public void bindAction(Button loginBtn, TextField usernameFld, PasswordField passFld, Text exit, Stage stage, Button regBtn) {
        
        
        
        loginBtn.setOnAction(e -> {
            
            try {
                
                Admin admin = adminTemplate.getAdmin(usernameFld.getText(), passFld.getText());
                
                System.out.println("Name: "+admin.getName()+", Password: "+admin.getPassword());
                
                mainGUI.createGUI(admin.getName());
                stage.hide();
                
            }
            catch (Exception ex) {
                
                showError(stage, "Login not approved! Details not found");
            }
        });
        
        exit.setOnMousePressed(e -> {
            
            Platform.exit();
        });
        
        regBtn.setOnAction(e -> {
           
            showRegistrationWindow(stage);
        });
        
    }
    
    public void showRegistrationWindow(Stage stage) {
        
        Stage primaryStage = new Stage();
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);
        //URL imageURL = getClass().getResource("add2.png");
        //URL stylesUrl = getClass().getResource("reg.css");
        ImageView image = new ImageView(new Image("resources/images/add2.png"));
        TextField nameFld = new TextField();
        PasswordField passFld = new PasswordField();
        Button addBtn = new Button("Add");
        Text exit = new Text("X");
        Label nameLbl = new Label("Name");
        Label passLbl = new Label("Password");
        
        scene.getStylesheets().add("resources/css/reg.css");
        root.getChildren().addAll(image, nameLbl, nameFld, passLbl, passFld, addBtn, exit);
        exit.getStyleClass().add("exit-text");
        image.setFitWidth(96);
        image.setFitHeight(67);
        image.setLayoutX(102);
        image.setLayoutY(40);
        exit.setLayoutX(267);
        exit.setLayoutY(19);
        nameLbl.setLayoutX(120);
        nameLbl.setLayoutY(115);
        nameFld.setLayoutX(63);
        nameFld.setLayoutY(144);
        passLbl.setLayoutX(111);
        passLbl.setLayoutY(194);
        passFld.setLayoutX(63);
        passFld.setLayoutY(222);
        addBtn.setLayoutX(74);
        addBtn.setLayoutY(291);
        
        exit.setOnMousePressed(e -> {
           
            primaryStage.close();
            stage.show();
            
        });
        
        addBtn.setOnAction(e -> {
           
            try {
                
                Admin admin = adminTemplate.getAdmin(nameFld.getText() , passFld.getText());
                
                showError(primaryStage, "Admin with entered username already exists! Try registering one with a different name! ");
            }
            catch(Exception ex) {
                
                adminTemplate.create(nameFld.getText(), passFld.getText());
                showSuccess(primaryStage, "New admin successfully created!");
            }
            
            
        });
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register New Admin");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.sizeToScene();
        stage.hide();
        primaryStage.show();
        
    }
    
    public void showError(Window owner, String labelText) {
        
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 263, 173);
        Stage stage = new Stage();
        Text exit = new Text("X");
        Text errorText = new Text(labelText);
        //URL imgUrl = getClass().getResource("errorimg.png");
        Image error_img = new Image("resources/images/errorimg.png");
        ImageView errorImgView = new ImageView(error_img);
        //URL css = getClass().getResource("confirm.css");
        
        root.getChildren().addAll(exit, errorText, errorImgView);
        scene.getStylesheets().add("resources/css/confirm.css");
        
        errorImgView.setLayoutX(14);
        errorImgView.setLayoutY(37);
        errorImgView.setFitWidth(70);
        errorImgView.setFitHeight(76);
        
        exit.getStyleClass().add("x-cancel");
        exit.setLayoutX(249);
        exit.setLayoutY(13);
        exit.setOnMousePressed(e -> {
            
            stage.close();
        });
        
        errorText.getStyleClass().add("error-text");
        errorText.setLayoutX(112);
        errorText.setLayoutY(55);
        errorText.setWrappingWidth(117.546875);
        
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
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
    
    public static void main(String[] args) {
        
        Application.launch(args);
    }
}