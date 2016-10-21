import java.net.*;

public class Client {

    public static void main(String[] args) {
        DatagramSocket socket;

        try {
            socket = new DatagramSocket();
            String message = "Testing...";
            byte [] b = message.getBytes();
            InetAddress host = InetAddress.getByName("localhost");
            int serverSocket = 6780;
            DatagramPacket request = new DatagramPacket(b, b.length, host, serverSocket);
            socket.send(request);

            byte [] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);

            System.out.println(reply.getData());
            socket.close();
        }
        catch (Exception ex) {
            System.out.println("Error: Could not link socket");
        }
    }
}
