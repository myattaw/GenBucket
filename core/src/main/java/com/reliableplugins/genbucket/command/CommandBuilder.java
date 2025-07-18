package com.reliableplugins.genbucket.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandBuilder {

    String label();

    String[] alias();

    String permission();

    String description() default "";

    boolean playerRequired() default false;

}