package org.screenplay;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private MainWindow mainWindow;

    @Override
    public void start(Stage primaryStage) {
        mainWindow.start(primaryStage);
    }
    @Override
    public void init() throws Exception {
        notifyPreloader(new Preloader.ProgressNotification(1.0));

        mainWindow = new MainWindow();
        mainWindow.setSize(0.7F, 1);
        mainWindow.load();

        notifyPreloader(new Preloader.ProgressNotification(20.0));

        String dataFolder = System.getProperty("user.dir"); // Tentativa de fazer funcionar em linux
        if (System.getProperty("os.name").contains("Windows")){
            dataFolder = System.getenv("APPDATA");
            System.out.println("Running in Windows");
        }
        dataFolder += "\\.controllerSX";
        System.out.println(System.getProperty("user.dir"));
        System.out.println(dataFolder);

        //System.out.println(System.getProperties());

        boolean mainDataFolder = new File(dataFolder).mkdir();

        boolean projects = new File(dataFolder+"\\projects").mkdir();

        boolean builds = new File(dataFolder+"\\builds").mkdir();

        boolean languages = new File(dataFolder+"\\languages").mkdir();

        boolean plugins = new File(dataFolder+"\\plugins").mkdir();

        notifyPreloader(new Preloader.ProgressNotification(22.0));

        new xmlLoad(dataFolder);



        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(30.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(40.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(50.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(60.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(70.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(80.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(90.0));
        Thread.sleep(10);
        notifyPreloader(new Preloader.ProgressNotification(100.0));
        Thread.sleep(10);
    }


    public static void main(String[] args) {
        System.setProperty("javafx.preloader", UIloadSystem.class.getCanonicalName());
        launch(args);
    }
}

