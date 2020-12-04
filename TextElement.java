import java.nio.charset.StandardCharsets;

public class TextElement extends ChainElement{

    public TextElement() {
        this.dataType = 0;
        this.nextElement = new ImageElement();
    }

    @Override
    protected Object handleConcrete(Message message) {
        String name = getSenderName(message.getName());
        String content = new String(message.getContent(), StandardCharsets.UTF_8);
        return "Client " + name + " : " + content;
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
