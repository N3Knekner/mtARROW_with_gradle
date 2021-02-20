package org.screenplay;

import java.io.IOException;
import java.net.*;
import java.util.function.Consumer;

public class localhostServer {
    private DatagramSocket socket;
    private int port = 4821;
    private String host_Ip = "10.0.1.102";
    private boolean running = false;
    private ArrowRobot arrowRobot;
    private boolean gotResponse = false;

    public void setPort(int port) {
        this.port = port;
    }
    public int getPort() {
        return port;
    }

    public void setHost_Ip(String host_Ip) {
        this.host_Ip = host_Ip;
    }
    public String getHost_Ip() {
        return host_Ip;
    }
    public void reset(){
        running = false;
        Console.log("Execussão do sistema WIFI resetada!");
    }

    public void setArrowRobot(ArrowRobot arrowRobot) {
        this.arrowRobot = arrowRobot;
    }

    public ArrowRobot getArrowRobot() {
        return arrowRobot;
    }

    public void wifi(int fps){
        InetAddress address;

        Console.log("Iniciando conexão wifi...");
        try {
            address = InetAddress.getByName(host_Ip);
            socket = new DatagramSocket();
            new Thread(() -> {
                int c = 1;
                while(!gotResponse) {
                    try {
                        if (c == 10) break;
                        Console.log("Disparando sinal de coneção...");
                        Thread.sleep(1000);
                        socket.send(new DatagramPacket(new byte[1], 1, address, 4821));
                        read(res-> gotResponse = true);
                        Console.log("Resposta: "+socket.isConnected());
                        c++;
                    } catch (Exception e) {
                        System.out.println(e);
                        Console.log("Ocorreu um erro desconhecido nesta tentativa de conexão.");
                        break;
                    }
                }
                if (!gotResponse) {Console.log("Falha na conexão!");return;}
                running = true;
                if(arrowRobot.getRealTime()) arrowRobot.startStaticUpdate(fps);
                Console.log("Modo de execussão setado em: \n" + (arrowRobot.getRealTime() ? "Tempo Real (O dado mais novo vai ser executado primeiro)":"Preciso (Todos os dados serão executados)"));
                Console.log("Conectado com sucesso!\n Iniciando execussão...");
                awaitNextPointer();

                /*while (running) {
                    try {
                        System.out.println(socket);
                        socket.receive(response);
                        command = (new String(buffer, 0, response.getLength() - 2)).split(",");
                        System.out.println(command[0] + "," + command[1]);
                        mechanics.mouseMove(Integer.parseInt(command[0]), Integer.parseInt(command[1]));

                    } catch (NumberFormatException | IOException e) {
                    }
                }*/
            }).start();

        } catch (SecurityException e){
            Console.log("Acesso ao controle do ponteiro ou ao servidor negado. Tente reiniciar como administrador.");
        }
        catch (UnknownHostException e){
            Console.log("Endereço do servidor não encontrado.");
        }
        catch (SocketException e){
            Console.log("Não foi possível se conectar com o servidor.");
        }
    }
    private void awaitNextPointer(){
        read(r -> {
            String[] res = r.split(",");
            arrowRobot.addToBuffer(new int[]{Integer.parseInt(res[0]), Integer.parseInt(res[2]), Integer.parseInt(res[1])});
            if(running)awaitNextPointer();
        });
    }
    private void read(Consumer<String> callback){
        new Thread(() -> {
            byte[] buffer = new byte[14];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            try {
                this.socket.receive(response);
                String r = new String(buffer, 0, response.getLength() - 2);
                callback.accept(r);
            } catch (PortUnreachableException e) {
                Console.log("A porta " + this.port + " já está sendo utilizada. Tente novamente mais tarde ou atualize para uma não utilizada.");
            } catch (IOException e) {
                Console.log("Algum fator externo interrompeu a leitura.\n" + e.getMessage());
            }
        }).start();
    }
}