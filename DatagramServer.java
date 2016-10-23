/**
 * @file    DatagramServer.java
 * @Author  Gage Myers, Charles Duso
 * @brief   CS460: Datagram Server Implementation
 *
 * @date    22 October 2016
 */

/* Import Java Libraries */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A DatagramServer instance communicates to a socket instance that has
 * contacted the server on port 4444.
 */
public class DatagramServer {

    /* Arbitrarily choesn port number for use as the hosting socket port */
    private static final int PORT = 4444;

    /**
     * Create a new DatagramServer instance and initiate run()
     */
    public static void main(String[] args) throws IOException {
      new DatagramServer().run();
    }

    /**
     * Initializes a DataGramServerThread and calls its run() method
     *
     * @return none
     */
    private void run() throws IOException {
      new DatagramServerThread(new DatagramSocket(PORT)).run();
    }

    /**
     * DatagramServerThread is a private class that extends thread and is used as
     * the sole communicator between client and server for a DatagramServer
     * instance.
     */
    public class DatagramServerThread extends Thread {

      /* The server's socket */
      DatagramSocket socket;

      DatagramServerThread(DatagramSocket socket) {
        this.socket = socket;
      }

      /**
       * Implementation of run() from the Runnable interface
       * Communicates between client and server
       *
       * @return none
       */
      public void run() {
        try {
          byte[]            buf        = new byte[256];
          DatagramPacket    recvPacket = new DatagramPacket(buf, buf.length);
          int               port;
          InputStreamReader reader;
          InetAddress       address;
          DatagramPacket    sendPacket;
          /* Listen for packets from clients and echo their input back */
          while (true) {
            /* Listen for a packet */
            socket.receive(recvPacket);
            /* Get the source port and address from the client */
            address    = recvPacket.getAddress();
            port       = recvPacket.getPort();
            buf        = recvPacket.getData();
            sendPacket = new DatagramPacket(buf, buf.length, address, port);
            reader     = new InputStreamReader(new ByteArrayInputStream(buf));
            /* Print what the client sent */
            System.out.printf("\nClient %d, has sent a message: %c",
                               port, (char) reader.read());
            /* Send back what the client sent */
            socket.send(sendPacket);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }

}
