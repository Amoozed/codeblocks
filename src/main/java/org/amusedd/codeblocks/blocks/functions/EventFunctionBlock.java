package org.amusedd.codeblocks.blocks.functions;

import org.bukkit.event.Event;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class EventFunctionBlock<T extends Event> extends FunctionBlock {
    T event;

    public EventFunctionBlock(String name){
        super(name);
        setFunctionName(getEventType());
        setTag("event", getEventType(), PersistentDataType.STRING);
    }
    @Override
    public void execute() {
        System.out.println("Cannot execute event function block directly!");
    }

    public void onEvent(T event) {
        this.event = event;
        super.execute();
    }

    public T getEvent() {
        if(event == null){
            throw new IllegalStateException("Event has not been called yet!");
        } else {
            return event;
        }
    }

    public String getEventType(){
       return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();
    }

}
