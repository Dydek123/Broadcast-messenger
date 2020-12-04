import java.io.ByteArrayInputStream;

public class ImageElement extends ChainElement {

    public ImageElement() {
        this.dataType = 1;
        this.nextElement = new AudioElement();
    }

    @Override
    protected Object handleConcrete(Message message) {
        String name = getSenderName(message.getName());
        ByteArrayInputStream bis = new ByteArrayInputStream(message.getContent());
        ImageShow imageShow = new ImageShow(bis);
        imageShow.run();
        return "Received image from client: " + name;
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
