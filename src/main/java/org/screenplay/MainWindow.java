package org.screenplay;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import afester.javafx.svg.SvgLoader;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainWindow {
    private AnchorPane application;
    private float sizeReduction = 1.0F;
    private float sizeDifference = 1.0F;
    private float windowX, windowY;
    private ArrowRobot arrowRobot;
    private int _fps;
    private ToggleButton _isRealtime;

    public void load() {
        List<String> tabs = Arrays.asList("INICIAR", "PREDEFINIÇÕES", "CONSOLE", "CONFIGURAÇÕES");
        List<String> tabsId = Arrays.asList("startMenu", "predefMenu", "consoleMenu", "configMenu");

        // Create all classes or get for info
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.windowX = (float) screenSize.getWidth()  *  this.sizeReduction;
        this.windowY = (float) screenSize.getHeight() * (this.sizeReduction - this.sizeDifference);
        AnchorPane all = new AnchorPane();

        Console.getConsole().setPrefWidth(windowX-15);

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

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(1);
            }
        });

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
        resetBtn_usb.setPadding(new Insets(10,20,10,20));

        Group resetImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/reload.svg"));
        resetImage.setScaleX(0.015);
        resetImage.setScaleY(0.015);
        Group resetIcon = new Group(resetImage);
        resetBtn_usb.setGraphic(resetIcon);

        //Play
        Button playBtn_usb = new Button("PLAY");
        playBtn_usb.setOnAction(event -> u.teste());

        playBtn_usb.setPadding(new Insets(10,20,10,20));

        Group playImage = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/play.svg"));
        playImage.setScaleX(0.015);
        playImage.setScaleY(0.015);
        Group playIcon = new Group(playImage);
        playBtn_usb.setGraphic(playIcon);

        arrowRobot = new ArrowRobot();

        /*Button testeThradStart = new Button("TESTE iniciar");
        testeThradStart.setOnAction(event->arrowRobot.execLoadAnimation());
        Button testeThradStop = new Button("TESTE parar");
        testeThradStop.setOnAction(event->arrowRobot.stopStaticUpdate());*/

        Label scanText = new Label("Portas Conectadas: ");
        scanText.getStyleClass().add("labelText");
        scanText.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        headerUsb.getChildren().addAll(
                scanText,
                scanList_usb,
                scanBtn_usb
        );

        HBox usb_startControls = new HBox(20);
        usb_startControls.getChildren().addAll(resetBtn_usb, playBtn_usb);
        usb_startControls.setMinWidth(windowX);

        VBox usbControls = new VBox(usb_startControls, headerUsb);
        usbControls.setSpacing(20);
        usbControls.setPadding(new Insets(20));
        //END USB ZONE -------------------------------------------------------------------------------------------------

        //WIFI ZONE ----------------------------------------------------------------------------------------------------
        HBox headerWifi = new HBox(20);
        headerWifi.setMinWidth(windowX);
        AnchorPane.setTopAnchor(headerWifi,20.0);
        AnchorPane.setLeftAnchor(headerWifi,20.0);

        // INICIALIZA A CLESSE DO SERVIDOR:
        localhostServer w = new localhostServer();
        w.setArrowRobot(arrowRobot);

        //Reset
        Button resetBtn_wifi = new Button("RESET");
        resetBtn_wifi.setOnAction(event -> w.reset());

        resetBtn_wifi.setPadding(new Insets(10,20,10,20));
        Group resetImage2 = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/reload.svg"));
        resetImage2.setScaleX(0.015);
        resetImage2.setScaleY(0.015);
        Group resetIcon2 = new Group(resetImage2);
        resetBtn_usb.setGraphic(resetIcon2);

        //Play
        Button playBtn_wifi = new Button("PLAY");
        playBtn_wifi.setOnAction(event -> {arrowRobot.setRealTime(_isRealtime.isSelected());w.wifi(_fps);});

        playBtn_wifi.setPadding(new Insets(10,20,10,20));

        Group playImage2 = SVGloader.loadSvg(getClass().getResourceAsStream("../../../../resources/main/play.svg"));
        playImage2.setScaleX(0.015);
        playImage2.setScaleY(0.015);
        Group playIcon2 = new Group(playImage2);
        playBtn_wifi.setGraphic(playIcon2);


        HBox wifi_startControls = new HBox(20);
        wifi_startControls.getChildren().addAll(playBtn_wifi, resetBtn_wifi);

        VBox right = new VBox(20);
        VBox left = new VBox(20);
        right.maxWidth(Double.MAX_VALUE);
        left.maxWidth(Double.MAX_VALUE);
        HBox.setHgrow(right,Priority.ALWAYS);
        HBox.setHgrow(left,Priority.ALWAYS);

        left.getChildren().addAll(wifi_startControls, new Button("Desconectar"));

        right.getChildren().addAll(inputGenerator("SSID: ", "123456789"), inputPassowrdGenerator("Senha: "), new Button("Esquecer rede"));
        //right.setStyle("-fx-background-color: green;");

        headerWifi.getChildren().addAll(left,right);
        //END WIFI ZONE ------------------------------------------------------------------------------------------------

        ((AnchorPane)usbWifiNavigatorTabPanes.getChildren().get(0)).getChildren().add(usbControls);
        ((AnchorPane)usbWifiNavigatorTabPanes.getChildren().get(1)).getChildren().add(headerWifi);

        tab.getChildren().addAll(centerButtons,usbWifiNavigatorTabPanes);
    }
    private void createPredefsMenu(AnchorPane tab){
        System.out.println(tab);
        Label realTimeText = textGenerator("Execução em tempo real:");

        _isRealtime = new ToggleButton("✔");
        HBox realTimeCFG = new HBox(realTimeText,_isRealtime);
        realTimeCFG.setSpacing(20);

        VBox right = new VBox(20);
        VBox left = new VBox(20);
        right.maxWidth(Double.MAX_VALUE);
        left.maxWidth(Double.MAX_VALUE);
        HBox.setHgrow(right,Priority.ALWAYS);
        HBox.setHgrow(left,Priority.ALWAYS);

        HBox fps = inputGenerator("FPS: ","60");
        left.getChildren().addAll(realTimeCFG, fps);
        ((TextField)fps.getChildren().get(1)).textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { ((TextField)fps.getChildren().get(1)).setText(newValue.replaceAll("[^\\d]", ""));
            }else if (Integer.parseInt(newValue) > 200) ((TextField)fps.getChildren().get(1)).setText(oldValue);

            _fps = Integer.parseInt(newValue);
        });

        HBox sense = inputGenerator("Sensibilidade: ","2");
        right.getChildren().add(sense);
        ((TextField)sense.getChildren().get(1)).textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { ((TextField)sense.getChildren().get(1)).setText(newValue.replaceAll("[^\\d]", ""));
            }else if (Integer.parseInt(newValue) > 10) ((TextField)sense.getChildren().get(1)).setText(oldValue);
        });
        HBox _tab = new HBox(left,right);
        _tab.setPadding(new Insets(20));
        _tab.setPrefSize(windowX, windowY-windowY*0.1);
        tab.getChildren().add(_tab);

    }
    private void createConsoleMenu(AnchorPane tab){
        System.out.println(tab);

        ScrollPane s = new ScrollPane(Console.getConsole());
        s.setPrefSize(windowX,windowY - windowY*0.1);
        s.setStyle("-fx-background-color: transparent;");
        tab.getChildren().add(s);
    }
    private void createConfigMenu(AnchorPane tab){
        System.out.println(tab);
    }
    private Label textGenerator(String text){
        Label t = new Label(text);
        t.getStyleClass().add("labelText");
        t.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return t;
    }
    private HBox inputGenerator(String label, String value){
        HBox o = new HBox(textGenerator(label), new TextField(value));
        o.setSpacing(20);
        return o;
    }
    private HBox inputPassowrdGenerator(String label){
        HBox o = new HBox(textGenerator(label), new PasswordField());
        o.setSpacing(20);
        return o;
    }


}
