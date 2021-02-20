package org.screenplay;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private MainWindow mainWindow;

    @Override
    public void start(Stage primaryStage) {
        mainWindow.start(primaryStage);
    }
    @Override
    public void init() throws InterruptedException {
        notifyPreloader(new Preloader.ProgressNotification(10.0));

        mainWindow = new MainWindow();
        mainWindow.setSize(0.7F, 1);

        Console.setConsole(new VBox());

        notifyPreloader(new Preloader.ProgressNotification(20.0));

        String dataFolder = System.getProperty("user.dir"); // Tentativa de fazer funcionar em linux
        if (System.getProperty("os.name").contains("Windows")){
            dataFolder = System.getenv("APPDATA");
            System.out.println("Running in Windows");
            Console.log("Sistema: Windows");
        }
        else Console.log("Sistema: Linux");
        dataFolder += "\\.controllerSX";
        System.out.println(System.getProperty("user.dir"));
        System.out.println(dataFolder);
        Console.log("Pasta padrão: "+dataFolder);

        mainWindow.load();
        //System.out.println(System.getProperties());

        /*boolean mainDataFolder = new File(dataFolder).mkdir();

        boolean projects = new File(dataFolder+"\\projects").mkdir();

        boolean builds = new File(dataFolder+"\\builds").mkdir();

        boolean languages = new File(dataFolder+"\\languages").mkdir();

        boolean plugins = new File(dataFolder+"\\plugins").mkdir();*/

        //notifyPreloader(new Preloader.ProgressNotification(70.0));

        //new xmlLoad(dataFolder);

        for (int i = 21; i < 100; i++){
            Thread.sleep(10);
            notifyPreloader(new Preloader.ProgressNotification(i));
        }
    }


    public static void main(String[] args) {
        System.setProperty("javafx.preloader", UIloadSystem.class.getCanonicalName());
        launch(args);
    }
}

