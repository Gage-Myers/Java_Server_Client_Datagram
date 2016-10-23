/**
 * @file    DatagramClient.java
 * @Author  Gage Myers, Charles Duso
 * @brief   CS460: Datagram Client Implementation
 *
 * @date    22 October 2016
 */

/* Import Java Libraries */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A DatagramClient instance communicates to a predefined socket instance
 * on port 4444 of the localhost.
 * The DatagramClient's port number is arbitrarily chosen for each instance.
 */
public class DatagramClient {

  /**
   * Create a new DatagramClient instance and initiate run()
   */
  public static void main(String[] args) {
    new DatagramClient().run();
  }

  /**
   * Initializes a DataGramClientThread and calls its run() method
   *
   * @return none
   */
  public void run() {
    new DatagramClientThread().run();
  }

  /**
   * DatagramClientThread is a private class that extends thread and is used as
   * the sole communicator between client and server for a DatagramClient
   * instance.
   */
  private class DatagramClientThread extends Thread {

    /**
     * Implementation of run() from the Runnable interface
     * Communicates between client and server
     *
     * @return none
     */
    public void run() {
      try {
        byte[]            buf          = new byte[256];
        DatagramSocket    socket       = new DatagramSocket();
        BufferedReader    client       = new BufferedReader(
                                         new InputStreamReader(
                                         System.in));
        byte[]            sendBuf      = new byte[1];
        byte[]            recvBuf      = new byte[1];
        /* Grab the server's address - localhost */
        InetAddress       address      = InetAddress.getByName("localhost");
        DatagramPacket    recvPacket   = new DatagramPacket(recvBuf, 1);
        String            input;
        int               length;
        InputStreamReader reader;
        DatagramPacket    sendPacket;
        while (true) {
          /* Get the client's input from the terminal */
          input   = client.readLine();
          length  = input.length();
          buf     = input.getBytes();
          /* Send a DatagramPacket of each character in the client's input */
          for (int i = 0; i < length; i++) {
            /* Get the current character */
            sendBuf[0] = buf[i];
            /* Create the new DatagramPacket of the current character */
            sendPacket = new DatagramPacket(sendBuf, 1, address, 4444);
            /* Send packet to server */
            socket.send(sendPacket);
            /* Receive server's response */
            socket.receive(recvPacket);
            recvBuf    = recvPacket.getData();
            /* Write the server's response to the client's terminal */
            reader     = new InputStreamReader(new ByteArrayInputStream(recvBuf));
            System.out.printf("\n%c", (char) reader.read());
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }


  }

}
