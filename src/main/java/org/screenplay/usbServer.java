package org.screenplay;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

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
        //teste();
        list.sort((e,i)->e.compareTo("Arduino"));
        return (list);
    }
    public void reset(){
        this.comPort[portId].writeBytes(("init").getBytes(),("init").length());
        this.comPort[portId].closePort();
        this.comPort[portId].removeDataListener();
        this.comPort = null;
    }

    public void setPortIndex(int index){
        System.out.println("Selected now: "+ index);
        portId = index;
    }

    public void teste(){
        SerialPort ardulino = comPort[portId];
        ardulino.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        PacketListener listener = new PacketListener();
        ardulino.addDataListener(listener);
        ardulino.openPort();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ardulino.writeBytes(("init").getBytes(),("init").length());
    }
    private static final class PacketListener implements SerialPortPacketListener
    {
        //long last;
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

        @Override
        public int getPacketSize() { return 5; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            //Date calendar = new Date();
            byte[] newData = event.getReceivedData();
            System.out.println(Arrays.toString(newData)); //+ "<- " + (calendar.getTime() - last)
            //last = calendar.getTime();
        }
    }
}
