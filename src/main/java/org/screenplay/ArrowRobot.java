package org.screenplay;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArrowRobot {
    private boolean isRealTime = false;
    private Thread asyncRealtimeThread;
    private int fps = 100;
    private List<int[]> buffer = new ArrayList<>();
    private Robot robot;

    ArrowRobot(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        asyncRealtimeThread = new Thread(() -> {
            while (true){
                execute(buffer.get(buffer.size()-1));
                buffer.clear();
                try {
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setRealTime(boolean b){
        isRealTime = b;
    }

    public void addToBuffer(int[] data){
        if (isRealTime){
            buffer.add(data);
            return;
        }
        execute(data);
    }

    public void startStaticUpdate(int fps){
        this.fps = (int) Math.ceil(1000.0/fps);
        asyncRealtimeThread.start();
    }
    public void stopStaticUpdate(){
        System.out.println(asyncRealtimeThread.getState());
    }

    private void execute(int[] data){
        robot.mouseMove(data[0],data[2]);
        if (data[1] == 1)robot.mousePress(1); else
        if (data[1] == 2)robot.mouseRelease(1);
    }
}
