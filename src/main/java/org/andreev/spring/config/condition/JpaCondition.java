package org.andreev.spring.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class JpaCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try{
            Objects.requireNonNull(context.getClassLoader()).loadClass("org.postgresql.Driver");
            return true;
        }
        catch(ClassNotFoundException exc){
            return false;
        }
    }
}
