package org.screenplay;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ArrowSimulator {
    private Canvas canvas;
    private GraphicsContext ctx;
    private int width,height;
    private int giroX,giroY;
    private int arrowX,arrowY;
    private int radius;

    public ArrowSimulator(int width, int height){
        this.width = width;
        this.height = height;
        canvas = new Canvas(width,height);
        ctx = canvas.getGraphicsContext2D();
        setArrowCoordinates(width/2,height/2);
    }

    public Canvas getCanvas(){return canvas;}

    public void setRadius(int radius){
        this.radius = radius;
        update();
    }
    public void setGiroCoordinates(int x, int y){
        giroX = x;
        giroY = y;
        update();
    }
    private void setArrowCoordinates(int x, int y){
        arrowX = x;
        arrowY = y;
        update();
    }

    public void playAnimation(){
        for (int i = 0; i < 20;i++) {
            System.out.println("oxi"+i);
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(i));
            pauseTransition.setOnFinished(e->setGiroCoordinates((int) (Math.random() * width), (int) (Math.random() * height)));
            pauseTransition.play();
        }
    }


    private void update(){
        //Clear
        ctx.clearRect(0,0, width, height);

        //Background
        ctx.setFill(new Color(0.2,0.2,0.2,1));
        ctx.fillRect(0,0, width, height);

        //Action circle
        ctx.setStroke(Color.BLACK);
        ctx.setLineWidth(2);
        final int diameter = radius*2;
        ctx.strokeOval((double) width/2-radius,(double) height/2-radius,diameter,diameter);

        //Cord
        int hip = ((giroX-arrowX)*(giroX-arrowX))+((giroY-arrowY)*(giroY-arrowY));
        double gravity = (radius - Math.sqrt(hip));
        if(Math.sqrt(hip)>=radius)gravity=0;
        ctx.setStroke(Color.BLUE);
        ctx.beginPath();
        ctx.moveTo(arrowX, arrowY);
        ctx.quadraticCurveTo((double)(giroX-arrowX)/2+arrowX,((double)(giroY+arrowY)/2) + gravity,giroX,giroY);
        ctx.stroke();
        System.out.println("AQUI ->"+gravity);

        ctx.setFill(Color.YELLOW);
        ctx.fillRect((double)(giroX-arrowX)/2+arrowX,((double)(giroY+arrowY)/2) + gravity, 4,4); //Arrumar a gravidade

        //Giroscope pointer
        ctx.setFill(Color.YELLOW);
        ctx.fillRect(giroX-2,giroY-2, 4,4);

        //Arrow
        ctx.setFill(Color.WHITE);
        ctx.fillRect((double)width/2-2,(double)height/2-2, 4,4);
    }
}
