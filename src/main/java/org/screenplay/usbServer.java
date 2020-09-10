package org.screenplay;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class usbServer {
    private int portId = 1;
    private SerialPort[] comPort;

    public void scan(){
        this.comPort = SerialPort.getCommPorts();
        for (SerialPort serialPort : comPort) {
            System.out.println(serialPort.getSystemPortName());
        }
        teste();
    }
    public void reset(){
        this.comPort[portId].writeBytes(("init").getBytes(),("init").length());
        this.comPort[portId].closePort();
        this.comPort[portId].removeDataListener();
        this.comPort = null;
    }

    public SerialPort selectPort(){
        return comPort[portId];
    }

    public void teste(){
        SerialPort ardulino = this.selectPort();
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
