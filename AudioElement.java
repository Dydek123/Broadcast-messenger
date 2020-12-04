import java.io.ByteArrayInputStream;

public class AudioElement extends ChainElement {
    public AudioElement() {
        this.dataType = 2;
        super.setDataType((byte)2);
    }

    @Override
    protected Object handleConcrete(Message message) {
        String name = getSenderName(message.getName());
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(message.getContent());
            SoundPlay audioPlayer = new SoundPlay(bis);
            audioPlayer.play();
        } catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        return "Received audio file from client: " + name;
    }

    private String getSenderName(byte[] name){
        String senderName;
        int name1 = name[0];
        int name2 = name[1];
        if (name1 == 0){
            senderName = String.valueOf(name2);
        }
        else
            senderName = String.valueOf(name1) + String.valueOf(name2);
        return senderName;
    }
}
