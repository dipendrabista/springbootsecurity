package com.punojsoft.springbootsecurity.bootsecurity.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
@Data
@Builder
public class Foo {
    private int id;
    private String name;

}
