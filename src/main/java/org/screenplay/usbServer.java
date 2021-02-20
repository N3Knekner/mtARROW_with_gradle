package org.screenplay;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

public class usbServer {
    private int portId = 0;
    private SerialPort[] comPort;

    public ObservableList<String> scan(){
        this.comPort = SerialPort.getCommPorts();
        ObservableList<String> list = FXCollections.observableArrayList();

        for (SerialPort serialPort : comPort) {
            System.out.println(serialPort.getDescriptivePortName());
            String IamArduino = "";
            if(serialPort.getDescriptivePortName().contains("Arduino")) IamArduino = " - Arduino";
            list.add(serialPort.getSystemPortName()+IamArduino);
        }
        list.sort((e,i)->e.compareTo("Arduino"));
        Arrays.sort(this.comPort, (e,i)-> (e.getDescriptivePortName().contains("Arduino")?-1:0));
        if(this.comPort[0].getDescriptivePortName().contains("Arduino"))
        Console.log("Arduino Encontrado na Porta: "+this.comPort[0].getSystemPortName());
        else
        Console.log("Arduino não encontrado automaticamente. Verifique a conexão ou selecione manualmente na lista.");
        return (list);
    }
    public void reset(){
        this.comPort[portId].writeBytes(("init").getBytes(),("init").length());
        this.comPort[portId].closePort();
        this.comPort[portId].removeDataListener();
        Console.log("Execussão do sistema USB resetada!");
        //this.comPort = null;
    }

    public void setPortIndex(int index){
        System.out.println("Selected now: "+ index);
        portId = index;
    }

    public void teste(){
        ArrowRobot arrowRobot = new ArrowRobot();
        SerialPort ardulino = comPort[portId];
        System.out.println(ardulino.getDescriptivePortName());
        ardulino.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        PacketListener listener = new PacketListener();
        boolean getOldData = true;
        arrowRobot.execLoadAnimation();
        Console.log("Iniciando limpeza da conexão...");
        ardulino.openPort(0);

        //GAMBIARRA PRA RESETAR O BUFFER (apesar de ser uma gambiarra, não existe outro meio de resetar o buffer)
        long startTime = new Date().getTime();
        while (getOldData) {
            while (ardulino.bytesAvailable()>0){
                byte[] readBuffer = new byte[ardulino.bytesAvailable()];
                int numRead = ardulino.readBytes(readBuffer, readBuffer.length); //O jeito é ler os dados infinitamente até zerar
                System.out.println("Dado entulhando: "+ numRead);
            }
            if (startTime+1000 <= new Date().getTime())getOldData = false; //Pelos meus testes leva +-600ms pra terminar de ler, então botei 1000 pra não ter problema
        }
        // END GAMBIARRA PRA RESETAR O BUFFER
        Console.log("Conexão limpa!");
        Console.log("Iniciando conexão...");
        try {
            Thread.sleep(1000); // O correto aqui seria 2000, mas como no loop aqui em cima ja leva 1000, funciona.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Console.log("Conectado e iniciando transmissão!");
        arrowRobot.stopStaticUpdate();
        ardulino.addDataListener(listener); //Adiciona o callback
        ardulino.writeBytes(("init").getBytes(),("init").length());//Manda pro arduino uma string aleatória para iniciar a transmissão
    }
    private static final class PacketListener implements SerialPortPacketListener
    {
        ArrowRobot arrowRobot;

        //long last;
        public PacketListener(){
            arrowRobot = new ArrowRobot();
        }
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

        @Override
        public int getPacketSize() { return 5; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            //Date calendar = new Date();
            byte[] newData = event.getReceivedData();
            if (newData[2] != 32 && newData[2] != 33) {
                Console.log("Dessincronização detectada, resetando automatimente... \n"+ Arrays.toString(newData));
                event.getSerialPort().closePort();
                event.getSerialPort().removeDataListener();
                return;
            }
            //System.out.println(Arrays.toString(newData)); //+ "<- " + (calendar.getTime() - last)
            //System.out.println(Integer.toBinaryString((newData[0]<<8) | Byte.toUnsignedInt(newData[1])));
            //System.out.println((newData[0]<<8) | Byte.toUnsignedInt(newData[1]));
            arrowRobot.addToBuffer(new int[]{((newData[0]<<8) | Byte.toUnsignedInt(newData[1])),(newData[2] == 32 ? 2 : 1),((newData[3]<<8) | Byte.toUnsignedInt(newData[4]))});
            //last = calendar.getTime();
        }
    }
}
