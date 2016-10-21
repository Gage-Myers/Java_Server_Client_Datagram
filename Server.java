import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    private static final int PORT = 25678;
    public static void main(String[] args) throws SocketException {
        new Server().runServer();
    }

    private void runServer() throws SocketException{
        DatagramSocket dataSocket = new DatagramSocket(PORT);
        System.out.println("Server Running...");
        dataSocket.close();
    }
}