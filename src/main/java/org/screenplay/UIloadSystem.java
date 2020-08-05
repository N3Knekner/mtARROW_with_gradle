package org.screenplay;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class UIloadSystem extends Preloader {

    private Pane loadBar;
    private Scene scene;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start UI LOAD SCREEN");

        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();

        this.stage = stage;
    }

    @Override
    public void init() throws Exception {
        System.out.println("Initiate UI LOAD SCREEN");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float windowX = (float) screenSize.getWidth()  *  0.3f;
        float windowY = (float) screenSize.getHeight() * 0.3f;

        AnchorPane all = new AnchorPane();
        all.setPrefSize(windowX, windowY);
        all.setMaxSize(windowX, windowY);
        all.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 80% 80%, #f00, #000);");

        this.loadBar = new Pane();
        loadBar.setStyle("-fx-background-color: white;");
        loadBar.setPrefSize(100, windowY * 0.02);

        Label title = new Label("controllerSX");

        all.getChildren().add(loadBar);
        all.getChildren().add(title);

        all.setLeftAnchor(loadBar, 0.0);
        all.setBottomAnchor(loadBar, windowY * 0.15);

        this.scene = new Scene(all, windowX, windowY);
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            System.out.println("Value :" + ((ProgressNotification) info).getProgress());
            loadBar.setPrefWidth(scene.getWidth() * (((ProgressNotification) info).getProgress() / 100));

        }
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                System.out.println("BEFORE_START");
                this.stage.close();
                break;
        }


    }
}
