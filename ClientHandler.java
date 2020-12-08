import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

class ClientHandler implements Runnable
{
    private final int name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;

    public ClientHandler(Socket s, int name,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
    }

    public int getName() {
        return name;
    }

    @Override
    public void run() {

        while (true)
        {
            try
            {
                byte[] msgReceived = new byte[dis.readInt()];
                for (int i=0 ; i< msgReceived.length ; i++) {
                    msgReceived[i] = dis.readByte();
                }

                BigInteger bigInt = BigInteger.valueOf(this.name);
                byte[] name = bigInt.toByteArray();
                if (this.name<10)
                    msgReceived[2] = name[0];
                else {
                    msgReceived[1] = name[0];
                    msgReceived[2] = name[1];
                }

                System.out.println("Received message from: " + this.name);

                if(msgReceived[0] == 4){
                    notify(this.name, msgReceived);
                    Server.logout(this.name);
                    this.s.close();
                    break;
                }
                else
                    Server.notifyObservers(this.name, msgReceived);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try
        {
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void notify(int sender, byte[] MsgToSend){
        try {
            this.dos.writeInt(MsgToSend.length);
            this.dos.write(MsgToSend);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
} 