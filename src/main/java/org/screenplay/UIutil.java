package org.screenplay;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.Normalizer;
import java.util.*;

public class UIutil {
    public UIutil(float width, float height){
        this.x = width;
        this.y = height;
    }

    private float x, y;
    private String styleClass;
    private boolean generateId;
    private List<String> IDs;
    private ArrayList<Node> items = new ArrayList<>();

    public String normalizeString(String str){
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void setSize(float width, float height){
        this.x = width;
        this.y = height;
    }

    public void setStyleClass(String name){
        this.styleClass = name;
    }

    public void createId(boolean toggle, List<String> List){
        this.generateId = toggle;
        this.IDs =  List;
    }

    public void createLinkId(List<String> List){
        this.IDs = List;
    }

    /**
     * Link Buttons to AnchorPanes when the name and id are the same
     */
    public void Link(){
        ArrayList<Button> bnts;
        bnts = new ArrayList<>();
        ArrayList<AnchorPane> panes;
        panes = new ArrayList<>();

        for (Node a: items) {
            Parent b = (Parent) a;
            for (Node c : b.getChildrenUnmodifiable()) {
                if      (c.getClass().isAssignableFrom(Button.class))       bnts.add((Button) c);
                else if (c.getClass().isAssignableFrom(AnchorPane.class))   panes.add((AnchorPane) c);
                else System.out.println("UI.Link ERROR: NODE is not Linkable, invalid type");
            }
        }

        for (Button d: bnts) {
            for (AnchorPane e : panes){
                if (normalizeString(d.getStyleClass().get(d.getStyleClass().size()-1)).compareTo(e.getId()) == 0){
                    d.setOnAction(event -> {
                        for (AnchorPane f : panes){ f.setVisible(false);}
                        for (Button g : bnts){ g.getStyleClass().remove("selected");}
                        e.setVisible(true);
                        d.getStyleClass().add("selected");
                    });
                }
            }
        }

    }

    public HBox createHorizontalButtons(List<String> names){
        HBox h = new HBox();
        h.setPrefWidth(this.x);
        h.setPrefHeight(this.y);
        h.setAlignment(Pos.CENTER);

        for (String name: names) {
            Button bnt = new Button(name);

            if (!this.styleClass.isEmpty()) bnt.getStyleClass().add(this.styleClass);
            if (generateId){
                bnt.setId(IDs.get(names.indexOf(name)));
            }
            if (!this.IDs.isEmpty()){
                bnt.getStyleClass().add(IDs.get(names.indexOf(name)));
            }


            bnt.setPrefSize(this.x / names.size(), this.y);
            bnt.setCursor(Cursor.HAND);

            //bnt.setOnAction(e -> bnt.setStyle("-fx-border-color: gray; -fx-border-width : 0 1 0 1")); // Teste

            h.getChildren().add(bnt);
        }
        this.items.add(h);
        return (HBox) this.items.get(items.size()-1);
    }

    public AnchorPane createTabPanes(List<String> names){
        AnchorPane a = new AnchorPane();
        a.setPrefWidth(this.x);
        a.setPrefHeight(this.y);

        for (String name : names) {
            AnchorPane p = new AnchorPane();
            p.setId(IDs.get(names.indexOf(name)));
            p.setPrefSize(this.x, this.y);
            p.setVisible(false);

            a.getChildren().add(p);
            AnchorPane.setLeftAnchor(p, 0.0);
            AnchorPane.setTopAnchor(p, 0.0);
        }
        this.items.add(a);
        return (AnchorPane) this.items.get(items.size()-1);
    }

}
