public abstract class ChainElement {
    protected byte dataType;
    protected ChainElement nextElement;

    public ChainElement(){
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public Object handleRequest(Message message){
        byte type = getDataType(message);
        if(!checkDataType(type))
            return nextElement.handleRequest(message);
        else 
            return handleConcrete(message);
    }

    protected abstract Object handleConcrete(Message message);

    private byte getDataType(Message message){
        return message.getDataType();
    }

    protected boolean checkDataType(byte dataType){
        return this.dataType == dataType;
    }
}
