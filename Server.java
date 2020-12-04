import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends ClientHandler
{
    static List<ClientHandler> observerCollection = new ArrayList<>();
    static int i = 1;

    public Server(Socket socket, int name, DataInputStream input, DataOutputStream output) {
        super(socket, name, input, output);
    }

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket;

        while (true)
        {
            socket = serverSocket.accept();
            System.out.println("New client request received : " + socket);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            ClientHandler mtch = new ClientHandler(socket,i, dis, dos);

            Thread t = new Thread(mtch);
            observerCollection.add(mtch);
            t.start();
            i++;
        }
    }

    public static void notifyObservers(int sender, byte[] MsgToSend){
        for (ClientHandler mc : Server.observerCollection)
        {
            mc.notify(sender, MsgToSend);
        }
    }

    public static void logout(int name) throws IOException {
        for (int i=0 ; i<Server.observerCollection.size() ; i++){
            ClientHandler mc = Server.observerCollection.get(i);
            if (mc.getName()==(name)) {
                Server.observerCollection.remove(i);
                System.out.println("Client : " + name + " has logged out");
            }
        }
        System.out.println(Server.observerCollection);
    }
}
