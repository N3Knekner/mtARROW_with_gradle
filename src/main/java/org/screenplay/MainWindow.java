package org.screenplay;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow {
    private AnchorPane application;
    private float sizeReduction = 1.0F;
    private float sizeDifference = 1.0F;
    private float windowX, windowY;

    public void load() {
        List<String> tabs = Arrays.asList("INICIAR", "CONSTRUIR", "CONSOLE", "CONFIGURA\u00c7\u00D5ES");
        List<String> tabsId = Arrays.asList("startMenu", "constructMenu", "consoleMenu", "configMenu");


        // Create all classes or get for info
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.windowX = (float) screenSize.getWidth()  *  this.sizeReduction;
        this.windowY = (float) screenSize.getHeight() * (this.sizeReduction - this.sizeDifference);
        AnchorPane all = new AnchorPane();

        // Create Tab Menu
        UIutil UImenu = new UIutil(this.windowX, this.windowY * 0.1f);

        // Create a list of Buttons in a HBox
        UImenu.setStyleClass("menuBnt");
        UImenu.createLinkId(tabsId);
        HBox tabBnts = UImenu.createHorizontalButtons(tabs);


        // HBox Menu Styles
        tabBnts.setAlignment(Pos.CENTER);
        tabBnts.setStyle(tabBnts.getStyle() + " -fx-background-color: white");
        AnchorPane.setLeftAnchor(tabBnts, 0.0);
        AnchorPane.setBottomAnchor(tabBnts, 0.0);

        // Create a Tab with AnchorPane and Panes
        UImenu.setSize(this.windowX, windowY * 0.9f);
        UImenu.setStyleClass("");
        UImenu.createLinkId(tabsId);
        AnchorPane tabPanes = UImenu.createTabPanes(tabs);


        // AnchorPane Styles
        AnchorPane.setLeftAnchor(tabPanes, 0.0);
        AnchorPane.setTopAnchor(tabPanes, 0.0);

        UImenu.Link(); // Link the Buttons and the AnchorPanes, toggling between disable and enable


        //-------------------------------------------------------------------------------
        createStartMenu((AnchorPane) tabPanes.getChildren().get(0));
        createConstructMenu((AnchorPane) tabPanes.getChildren().get(1));
        createConsoleMenu((AnchorPane) tabPanes.getChildren().get(2));
        createConfigMenu((AnchorPane) tabPanes.getChildren().get(3));
        //-------------------------------------------------------------------------------

        // Insert the constructed
        all.getChildren().add(tabPanes);
        all.getChildren().add(tabBnts);

        all.setStyle("-fx-background-color: #0B0B0B;");

        this.application = all;
    }

    public void start(Stage primaryStage){
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.DECORATED); //Undecorated
        primaryStage.getIcons().add(new Image("../../resources/main/icon.png"));

        // Start Scene
        Scene MainScene = new Scene(this.application, this.windowX, this.windowY);
        MainScene.getStylesheets().add("../../resources/main/styles/btns.css");

        primaryStage.setTitle("controllerSX");
        primaryStage.setScene(MainScene);
        primaryStage.show();
    }

    public void setSize(float percent, int differential) {
        this.sizeReduction = percent;
        this.sizeDifference = (float)differential / 10.0F;
    }

    private void createStartMenu(AnchorPane tab){
        System.out.println(tab);
    }
    private void createConstructMenu(AnchorPane tab){
        System.out.println(tab);
    }
    private void createConsoleMenu(AnchorPane tab){
        System.out.println(tab);
    }
    private void createConfigMenu(AnchorPane tab){
        System.out.println(tab);
    }


}
