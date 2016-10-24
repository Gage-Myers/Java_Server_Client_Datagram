/**
 * @Authors: Gage Myers and Charles Duso
 */

import java.net.*;

/* A client class that will use UDP to connect to a server */
public class Client {
    private static final int PORT = 25690;

    public static void main(String[] args) {
        DatagramSocket socket;

        /* Creates a new Socket and then sends a
            packet to the server and waits for a response */
        try {
            socket = new DatagramSocket();
            String message = "";

            while (!(message.equals("close()"))) {
                message = System.in.toString();
                byte[] buff = message.getBytes();
                InetAddress host = InetAddress.getLocalHost();

                // Creates and sends a request to the server
                DatagramPacket request = new DatagramPacket(buff, buff.length, host, PORT);
                socket.send(request);

                // Creates a buffer to allow for a reply from the server
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                socket.receive(reply);

                // Prints out the Server reply
                System.out.println(reply.getData());
            }
            socket.close();
        }
        catch (Exception ex) {
            System.out.println("Error: Could not link socket");
        }
    }
}
