package org.andreev.spring.entity.listener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

    @Order(10)
    @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
    public void acceptEvent(EntityEvent entityEvent){
        System.out.println("Event: " + entityEvent);
    }
}
