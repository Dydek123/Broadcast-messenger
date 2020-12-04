public class Message {
    private byte dataType;
    private byte[] content;
    private byte[] name;

    public Message(byte[] receivedMessage) {
        dataType = receivedMessage[0];
        name = new byte[2];
        content = new byte[receivedMessage.length-3];
        for (int i=1 ; i < 3 ; i++)
            name[i-1] = receivedMessage[i];
        for (int i=3 ; i < receivedMessage.length ; i++)
            content[i-3] = receivedMessage[i];
    }

    public byte getDataType() {
        return dataType;
    }

    public byte[] getContent() {
        return content;
    }

    public byte[] getName() {
        return name;
    }
}
