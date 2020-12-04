import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client
{
    final static int ServerPort = 9999;

    public static void main(String[] args) throws IOException
    {
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, ServerPort);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread(() -> {
            while (true) {
                BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));
                int preambula;
                try {
                    String type = scn.readLine();
                    if (type.equals("text")){
                        preambula = 0;
                        System.out.print("Enter the text: ");
                        String msg = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        byte[] msgByte = msg.getBytes(  StandardCharsets.UTF_8);
                        byte[] toSend = messageToSend(msgByte, preambula);
                        dos.writeInt(toSend.length);
                        dos.write(toSend);
                    } else if (type.equals("image")){
                        preambula = 1;
                        System.out.print("Enter the name of image from jpg folder (f.e 1.jpg): ");
                        String path = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        BufferedImage bImage = ImageIO.read(new File("jpg/" + path));
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ImageIO.write(bImage, "jpg", bos);
                        byte[] msgByte = bos.toByteArray();
                        byte[] toSend = messageToSend(msgByte, preambula);
                        dos.writeInt(toSend.length);
                        dos.write(toSend);
                    } else if (type.equals("sound")){
                        preambula = 2;
                        System.out.print("Enter the name of .wav file from wav folder(f.e sound.wav): ");
                        String path = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("wav/" + path));
                        AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        AudioSystem.write(ais, targetType, bos);
                        byte[] msgByte = bos.toByteArray();
                        byte[] toSend = messageToSend(msgByte, preambula);
                        dos.writeInt(toSend.length);
                        dos.write(toSend);
                    } else if (type.equals("logout")){
                        preambula = 4;
                        byte[] msgByte = "logout".getBytes(  StandardCharsets.UTF_8);
                        byte[] toSend = messageToSend(msgByte, preambula);
                        dos.writeInt(toSend.length);
                        dos.write(toSend);
                        break;
                    }
                } catch (IOException | UnsupportedAudioFileException e) {
                    System.out.println("Invalid input");
                }
            }
        });

        Thread readMessage = new Thread(() -> {
            while (true) {
                try {
                    byte[] msgReceived = new byte[dis.readInt()];
                    for (int i=0 ; i< msgReceived.length ; i++) {
                        msgReceived[i] = dis.readByte();
                    }

                    Message messsage = new Message(msgReceived);
                    ChainElement chainElement = new TextElement();
                    System.out.println(chainElement.handleRequest(messsage));
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }

    private static byte[] messageToSend(byte[] msgByte, int preambula){
        byte[] toSend = new byte[msgByte.length + 3];
        toSend[0] = (byte) preambula;
        //2 free byte for client name
        toSend[1] = (byte) 0;
        toSend[2] = (byte) 0;
        for(int i=3 ; i < toSend.length ; i++)
            toSend[i] = msgByte[i-3];
        return toSend;
    }
}