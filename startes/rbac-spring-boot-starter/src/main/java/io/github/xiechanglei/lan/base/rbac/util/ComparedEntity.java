package io.github.xiechanglei.lan.base.rbac.util;

public interface ComparedEntity {
    Object buildUniqueId();

     default boolean changeIfNotSame(ComparedEntity comparedEntity){
         return false;
     }
}
