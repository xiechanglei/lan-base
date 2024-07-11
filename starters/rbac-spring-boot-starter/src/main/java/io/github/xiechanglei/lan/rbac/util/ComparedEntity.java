package io.github.xiechanglei.lan.rbac.util;

public interface ComparedEntity {
    Object buildUniqueId();

     default boolean changeIfNotSame(ComparedEntity comparedEntity){
         return false;
     }
}
