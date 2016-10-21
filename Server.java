/**
 * @Authors: Gage Myers and Charles Duso
 */

import java.net.SocketException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 *
 *
 */
public class Server {
    private static final int PORT = 25678;
    public static void main(String[] args) throws SocketException {
        new Server().runServer();
    }

    private void runServer() throws SocketException{
        try {
            DatagramSocket dataSocket = new DatagramSocket(PORT);

            System.out.println("Server Running...");

            ServerThread thread = new ServerThread(dataSocket);

            thread.run();
        }

        catch (Exception ex) {
            System.out.println("Error creating socket");
        }
    }

    private class ServerThread extends Thread {
        DatagramSocket socket;

        /* Initiates a thread by initializing a socket */
        ServerThread(DatagramSocket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                byte[] buffer = new byte[1000];

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