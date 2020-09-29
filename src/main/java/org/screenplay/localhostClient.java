package org.screenplay;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class localhostClient {

    public void wifi(){
        System.out.println("Conectando..");
        String host_Ip = "10.0.1.104";
        int port = 4821;
        byte[] buffer = new byte[512];

        try {
            InetAddress address = InetAddress.getByName(host_Ip);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket request = new DatagramPacket(new byte[1], 1, address, port);
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.send(request);

            Robot mechanics = new Robot();

            /*if(!socket.isConnected()){
                System.out.println("Nao conectado");
                return;
            }*/

            new Thread() { //thread padrao da versao q eu tenho
                @Override
                public void run() {
                    for (; ; ) {
                        try {
                            String command[] = new String[4];

                            socket.receive(response);
                            command = (new String(buffer, 0, response.getLength())).split(",");

                            mechanics.mouseMove(Integer.parseInt(command[0]), Integer.parseInt(command[1])); //move o marze

                        } catch (Exception e){
                            System.out.println("disconectado");
                            return;
                        }
                    }
                }
            }.start();

        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
}
