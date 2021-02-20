package org.screenplay;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public final class Console {
    private static VBox console;
    private static String lastStack = "none";

    public static void log(String value){
        try{
            console.getChildren().add(newLogLabel(value));
        }catch (Exception e) {
            Platform.runLater(()->console.getChildren().add(newLogLabel(value)));
        }
    }
    public static void setConsole(VBox console) {
        Console.console = console;
    }
    public static VBox getConsole() {
        return Console.console;
    }

    private static HBox newLogLabel(String value){
        String stack = Thread.currentThread().getStackTrace()[3].toString().substring(15);
        Label stackTrace = new Label(stack);
        Label x = new Label(" > "+value);
        VBox str = new VBox(x);
        stackTrace.setStyle("-fx-text-fill: #55A; -fx-font-size : 1em !important;");
        str.getChildren().get(0).setStyle("-fx-text-fill: white; -fx-font-size : 1.2em !important;");

        stackTrace.setWrapText(true);
        x.setWrapText(true);
        System.out.println(value);

        HBox.setHgrow(stackTrace, Priority.NEVER);
        HBox.setHgrow(str, Priority.ALWAYS);
        str.maxWidth(Double.MAX_VALUE);

        HBox h = new HBox(str,stackTrace);
        if(stack.equals(Console.lastStack) || stack.contains("javafx")) {
            h.setStyle("-fx-padding: 5; -fx-spacing: 10;-fx-width:100%;");
            stackTrace.setText("");
        }
        else h.setStyle("-fx-padding: 5; -fx-spacing: 10;-fx-width:100%;-fx-border-width: 1 0 0 0; -fx-border-color: rgb(50,50,50);");

        Console.lastStack = stack;
        return h;
    }
}
