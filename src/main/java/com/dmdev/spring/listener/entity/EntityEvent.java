package com.dmdev.spring.listener.entity;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class EntityEvent extends ApplicationEvent {

    @Getter
    private final AccessType accessType;
    public EntityEvent(Object entity, AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }

    public EntityEvent(Object entity, Clock clock, AccessType accessType) {
        super(entity, clock);
        this.accessType = accessType;
    }

}
