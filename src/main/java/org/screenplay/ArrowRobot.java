package org.screenplay;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrowRobot {
    private boolean isRealTime = false;
    private boolean threadRunning = false;
    private final List<int[]> buffer = new ArrayList<>();
    private Robot robot;
    private int lastBtnStatus = 2;
    //Animation parameters
    private int animationProgress = 0;
    private int direction = 5; //direction (1,-1) and steps
    Point p;

    ArrowRobot(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            Console.log("Acesso ao controle profundo do ponteiro negado. Tente reiniciar como administrador.");
        }
    }

    public void setRealTime(boolean b){
        isRealTime = b;
    }
    public boolean getRealTime(){
        return isRealTime;
    }

    public void addToBuffer(int[] data){
        if (isRealTime){
            buffer.add(data);
            return;
        }
        execute(data);
    }
    public void startStaticUpdate(int fps){
        startStaticUpdate(fps, ()->{});
    }
    /**
     *
     * @param fps frames per second
     * @param callback lambda function
     */
    public void startStaticUpdate(int fps, Runnable callback){
        int msPerFrame = (int) Math.ceil(1000.0/fps);

        if (threadRunning) return;
        threadRunning = true;
        new Thread(() -> {
            while (threadRunning){
                try {
                    Thread.sleep(msPerFrame);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (buffer.size() == 0) continue;
                execute(buffer.get(buffer.size()-1));
                buffer.clear();
                callback.run();
            }
        }).start();
    }
    public void stopStaticUpdate(){
        threadRunning = false;
    }

    private void execute(int[] data){
        System.out.println(Arrays.toString(data));
        if(data[0]!=0 && data[2]!=0)     //Isso abre a possibilidade de se ter apenas um controlador de cliques, talvez um por USB e outro por WIFI
        robot.mouseMove(data[0],data[2]);
        if (data[1] == 1 && lastBtnStatus == 2)robot.mousePress(1); else
        if (data[1] == 2 && lastBtnStatus == 1)robot.mouseRelease(1);
    }

    /**
     * No exist better loading
     */
    public void execLoadAnimation(){

        p = MouseInfo.getPointerInfo().getLocation();
        final int radius = 50;
        long init = System.currentTimeMillis();

        startStaticUpdate(25,()->{
            //Timeout, para caso bugue em algum lugar o mouse não fique infinitamente rodando
            if(init - System.currentTimeMillis() >= 5000)stopStaticUpdate();

            animationProgress+=direction;
            if(animationProgress >= radius || animationProgress <= (radius*-1)) direction=direction*(-1);
            int x2 = animationProgress;
            int y2 = (int) Math.round(Math.sqrt(radius*radius-animationProgress*animationProgress));
            buffer.add(new int[]{x2+p.x, 0, p.y+y2*(direction/Math.abs(direction))});
        });

        //Seta o valor inicial, poderia estar em uma função, mas fiquei com preguissa
        int x = animationProgress;
        int y = (int) Math.round(Math.sqrt(radius*radius-animationProgress*animationProgress));
        buffer.add(new int[]{x+p.x, 0, p.y+y*(direction/Math.abs(direction))});
    }
}
