package org.screenplay;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow {
    private AnchorPane application;
    private float sizeReduction = 1.0F;
    private float sizeDifference = 1.0F;
    private float windowX, windowY;

    public void load() {
        List<String> tabs = Arrays.asList("INICIAR", "PREDEFINIÇÕES", "CONSOLE", "CONFIGURAÇÕES");
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
        createPredefsMenu((AnchorPane) tabPanes.getChildren().get(1));
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
        usbServer u = new usbServer();
        Button scanBtn = new Button("scan");
        scanBtn.setOnAction(event -> u.scan());
        Button resetBtn = new Button("reset");
        resetBtn.setOnAction(event -> u.reset());
        resetBtn.setTranslateX(100.0);
        tab.getChildren().add(scanBtn);
        tab.getChildren().add(resetBtn);


    }
    private void createPredefsMenu(AnchorPane tab){
        System.out.println(tab);
        ArrowSimulator arrowSimulator = new ArrowSimulator(250,250);
        tab.getChildren().add(arrowSimulator.getCanvas());
        arrowSimulator.setRadius(100);
        arrowSimulator.setGiroCoordinates(200,150);
        Button playBtn = new Button("play");
        playBtn.setOnAction(event -> arrowSimulator.playAnimation());
        tab.getChildren().add(playBtn);
        playBtn.setTranslateX(300.0);
    }
    private void createConsoleMenu(AnchorPane tab){
        System.out.println(tab);
    }
    private void createConfigMenu(AnchorPane tab){
        System.out.println(tab);
    }


}
