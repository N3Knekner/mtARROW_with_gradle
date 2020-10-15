package org.screenplay;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class localhostClient {

    public void wifi(){
        System.out.flush();
        System.out.println("start");
        InetAddress address;
        DatagramSocket socket;
        DatagramPacket response;
        Robot mechanics;

        String host_Ip = "10.0.1.102";
        int port = 4821;
        byte[] buffer = new byte[14];

        try {
            mechanics = new Robot();
            address = InetAddress.getByName(host_Ip);
            socket = new DatagramSocket();
            response = new DatagramPacket(buffer, buffer.length);

            new Thread() {
                @Override
                public void run() {
                    int x = 0;
                    String command[];

                    while(true) {
                        try {

                            System.out.println("1");
                            sleep(1000);
                            socket.send(new DatagramPacket(new byte[1], 1, address, 4821));
                            socket.receive(response);
                            System.out.println("2");

                       /*if (socket.getInetAddress() == null) {
                           run();
                       }*/
                        } catch (Exception e) {
                            System.out.println(e);
                            break;
                        }
                    }
                   

                    for (; ; ) {
                        try {
                            System.out.println(socket);
                            socket.receive(response);
                            command = (new String(buffer, 0, response.getLength() - 2)).split(",");
                            System.out.println(command[0] + "," + command[1]);
                            mechanics.mouseMove(Integer.parseInt(command[0]), Integer.parseInt(command[1]));

                        } catch (NumberFormatException | IOException e) {
                        }
                    }
                }
            }.start();

        } catch (Exception e){
        }
    }
}
