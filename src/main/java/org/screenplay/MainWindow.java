package org.screenplay;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import afester.javafx.svg.SvgLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
        List<String> tabs = Arrays.asList("INICIAR", "PREDEFINIÇÕES", "CONSOLE", "CONFIGURAÇÕES");
        List<String> tabsId = Arrays.asList("startMenu", "predefMenu", "consoleMenu", "configMenu");


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

        all.setId("all");
        all.setStyle("-fx-background-image: url(../../resources/main/Logo_dark.png);");

        this.application = all;
    }

    public void start(Stage primaryStage){
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.DECORATED); //Undecorated
        primaryStage.getIcons().add(new Image("../../resources/main/icon.png"));

        // Start Scene
        Scene MainScene = new Scene(this.application, this.windowX, this.windowY);
        MainScene.getStylesheets().add("../../resources/main/styles/btns.css");

        primaryStage.setTitle(">");
        primaryStage.setScene(MainScene);
        primaryStage.show();
    }

    public void setSize(float percent, int differential) {
        this.sizeReduction = percent;
        this.sizeDifference = (float)differential / 10.0F;
    }

    private void createStartMenu(AnchorPane tab){
        System.out.println(tab);

        SvgLoader SVGloader = new SvgLoader();

        // PAGINATION ZONE ---------------------------------------------------------------------------------------------
        //Set children all things

        List<String> tabsId = Arrays.asList("usbNavigator", "wifiNavigator");
        List<String> tabs = Arrays.asList(" USB", " WIFI");
        UIutil usbWifiNavigator = new UIutil(windowX/2,60);

        usbWifiNavigator.setStyleClass("typeConnectionBnt");
        usbWifiNavigator.createLinkId(tabsId);
        HBox usbWifiNavigatorBnts = usbWifiNavigator.createHorizontalButtons(tabs);

        Group usbImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/usb_icon.svg"));
        usbImage.setScaleX(0.03);
        usbImage.setScaleY(0.027);
        Group usbIcon = new Group(usbImage);
        Button NEED_TO_STAY_IN_A_VARIABLE = (Button) usbWifiNavigatorBnts.getChildren().get(0);
        NEED_TO_STAY_IN_A_VARIABLE.setGraphic(usbIcon);
        Group wifiImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/wifi_icon.svg"));
        wifiImage.setScaleX(0.03);
        wifiImage.setScaleY(0.027);
        Group wifiIcon = new Group(wifiImage);
        Button NEED_TO_STAY_IN_A_VARIABLE2 = (Button) usbWifiNavigatorBnts.getChildren().get(1);
        NEED_TO_STAY_IN_A_VARIABLE2.setGraphic(wifiIcon);

        usbWifiNavigatorBnts.setAlignment(Pos.CENTER);

        usbWifiNavigator.setSize(this.windowX, windowY-(windowY * 0.1f + 60));
        usbWifiNavigator.setStyleClass("");
        usbWifiNavigator.createLinkId(tabsId);
        AnchorPane usbWifiNavigatorTabPanes = usbWifiNavigator.createTabPanes(tabs);

        AnchorPane.setLeftAnchor(usbWifiNavigatorTabPanes, 0.0);
        AnchorPane.setTopAnchor(usbWifiNavigatorTabPanes, 60.0);

        usbWifiNavigator.Link();

        //Center the buttons
        HBox centerButtons = new HBox(usbWifiNavigatorBnts);AnchorPane.setLeftAnchor(centerButtons, 0.0);AnchorPane.setTopAnchor(centerButtons, 0.0);
        centerButtons.setMinWidth(windowX);
        centerButtons.setAlignment(Pos.CENTER);
        //centerButtons.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: white;");
        //end
        //set USB open per default
        usbWifiNavigatorBnts.getChildren().get(0).getStyleClass().add("selected");
        usbWifiNavigatorTabPanes.getChildren().get(0).setVisible(true);
        //end

        // END PAGINATION ZONE -----------------------------------------------------------------------------------------

        // USB ZONE ----------------------------------------------------------------------------------------------------

        HBox headerUsb = new HBox(20);
        headerUsb.setMinWidth(windowX);
        AnchorPane.setTopAnchor(headerUsb,20.0);
        AnchorPane.setLeftAnchor(headerUsb,20.0);

        usbServer u = new usbServer();
        //Select
        ChoiceBox<String> scanList_usb = new ChoiceBox<>();

        scanList_usb.setMinWidth(100);
        scanList_usb.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> u.setPortIndex((int)newValue));

        //Scan
        scanList_usb.getItems().addAll(u.scan());
        scanList_usb.getSelectionModel().select(0);
        // Button Scan
        Button scanBtn_usb = new Button("SCAN");
        Group scanImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/lupa.svg"));
        scanImage.setScaleX(0.015);
        scanImage.setScaleY(0.015);
        Group scanIcon = new Group(scanImage);
        scanBtn_usb.setGraphic(scanIcon);
        scanBtn_usb.setOnAction(event -> {
            scanList_usb.getItems().removeAll(scanList_usb.getItems());
            scanList_usb.getItems().addAll(u.scan());
            scanList_usb.getSelectionModel().select(0);
        });
        //Reset
        Button resetBtn_usb = new Button("RESET");

        resetBtn_usb.setOnAction(event -> u.reset());

        Group resetImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/reload.svg"));
        resetImage.setScaleX(0.015);
        resetImage.setScaleY(0.015);
        Group resetIcon = new Group(resetImage);
        resetBtn_usb.setGraphic(resetIcon);

        //Play
        Button playBtn_usb = new Button("PLAY");
        playBtn_usb.setOnAction(event -> u.teste());

        Group playImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/play.svg"));
        playImage.setScaleX(0.015);
        playImage.setScaleY(0.015);
        Group playIcon = new Group(playImage);
        playBtn_usb.setGraphic(playIcon);

        ArrowRobot arrowRobot = new ArrowRobot();

        Button testeThradStart = new Button("TESTE iniciar");
        testeThradStart.setOnAction(event->arrowRobot.execLoadAnimation());
        Button testeThradStop = new Button("TESTE parar");
        testeThradStop.setOnAction(event->arrowRobot.stopStaticUpdate());

        headerUsb.getChildren().addAll(
                scanList_usb,
                scanBtn_usb,
                resetBtn_usb,
                playBtn_usb,
                testeThradStart,
                testeThradStop
        );
        //END USB ZONE -------------------------------------------------------------------------------------------------

        //WIFI ZONE ----------------------------------------------------------------------------------------------------
        HBox headerWifi = new HBox();
        headerWifi.setMinWidth(windowX);
        AnchorPane.setTopAnchor(headerWifi,20.0);
        AnchorPane.setLeftAnchor(headerWifi,20.0);

        // INICIALIZA A CLESSE DO SERVIDOR:
        localhostClient w = new localhostClient();

        // AK PODES INCLUIR OS BOTÕES,
        //exemplo:
        Button playBtn_wifi = new Button("PLAY"); //Cria o botão

        playBtn_wifi.setOnAction(event -> w.start()); // Seta o evento


        headerWifi.getChildren().addAll(playBtn_wifi);
        //END WIFI ZONE ------------------------------------------------------------------------------------------------

        ((AnchorPane)usbWifiNavigatorTabPanes.getChildren().get(0)).getChildren().add(headerUsb);
        ((AnchorPane)usbWifiNavigatorTabPanes.getChildren().get(1)).getChildren().add(headerWifi);

        tab.getChildren().addAll(centerButtons,usbWifiNavigatorTabPanes);
    }
    private void createPredefsMenu(AnchorPane tab){
        System.out.println(tab);
        ArrowSimulator arrowSimulator = new ArrowSimulator(250,250);
        tab.getChildren().add(arrowSimulator.getCanvas());
        arrowSimulator.setRadius(100);
        arrowSimulator.setGiroCoordinates(200,150);
        Button playBtn_usb = new Button("play");
        playBtn_usb.setOnAction(event -> arrowSimulator.playAnimation());
        tab.getChildren().add(playBtn_usb);
        playBtn_usb.setTranslateX(300.0);
    }
    private void createConsoleMenu(AnchorPane tab){
        System.out.println(tab);
    }
    private void createConfigMenu(AnchorPane tab){
        System.out.println(tab);
    }


}
