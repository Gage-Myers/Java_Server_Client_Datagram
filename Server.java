/**
 * @Authors: Gage Myers and Charles Duso
 */

import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Server
 *
 * Class that will run a datagram server and call threads
 * to allow for multiple clients
 */
public class Server {
    private static final int PORT = 25678;
    public static void main(String[] args) throws SocketException {
        new Server().runServer();
    }

    // Run the server and create threads as needed
    private void runServer() throws SocketException{
        try {
            InetAddress host = InetAddress.getLocalHost();

            DatagramSocket dataSocket = new DatagramSocket(PORT, host);

            System.out.println("Server Running...");

            ServerThread thread = new ServerThread(dataSocket);

            thread.run();
        }

        catch (Exception ex) {
            System.out.println("Error creating socket");
        }
    }

    // A server thread that will create a new thread
    // and connect a client
    private class ServerThread extends Thread {
        DatagramSocket socket;

        /* Initiates a thread by initializing a socket */
        ServerThread(DatagramSocket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                byte[] buffer = new byte[1000];

                /* Requests a DatagramPacket from the client and then responds to the client */
                while (true) {
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    socket.receive(request);

                    String[] msgArr = (new String(request.getData())).split(" ");
                    System.out.println(msgArr[0]);
                    byte[] sendMsg = (msgArr[0] + "server processed").getBytes();
                    DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, request.getAddress(), request.getPort());

                    socket.send(reply);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}