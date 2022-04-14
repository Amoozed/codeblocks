package org.amusedd.codeblocks.commands.input.communication;

public class Conversation {
    Receiver receiver;
    Receiver sender;

    public Conversation(Receiver receiver, Receiver sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public Conversation(Receiver sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getSender() {
        return sender;
    }

    public void complete() {
        sender.onComplete(this);
    }

    public void cancel(){
        sender.onCancel(this);
    }
}
