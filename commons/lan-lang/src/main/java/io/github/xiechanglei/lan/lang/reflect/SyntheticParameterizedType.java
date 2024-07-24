package io.github.xiechanglei.lan.lang.reflect;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.StringJoiner;

public class SyntheticParameterizedType implements ParameterizedType, Serializable {

    private final Type rawType;

    private final Type[] typeArguments;

    public SyntheticParameterizedType(Type rawType, Type[] typeArguments) {
        this.rawType = rawType;
        this.typeArguments = typeArguments;
    }

    @Override
    public String getTypeName() {
        String typeName = this.rawType.getTypeName();
        if (this.typeArguments.length > 0) {
            StringJoiner stringJoiner = new StringJoiner(", ", "<", ">");
            for (Type argument : this.typeArguments) {
                stringJoiner.add(argument.getTypeName());
            }
            return typeName + stringJoiner;
        }
        return typeName;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return this.rawType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.typeArguments;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other || (other instanceof ParameterizedType that &&
                that.getOwnerType() == null && this.rawType.equals(that.getRawType()) &&
                Arrays.equals(this.typeArguments, that.getActualTypeArguments())));
    }

    @Override
    public int hashCode() {
        return (this.rawType.hashCode() * 31 + Arrays.hashCode(this.typeArguments));
    }

    @Override
    public String toString() {
        return getTypeName();
    }

    public static SyntheticParameterizedType of(Type rawType, Type... typeArguments) {
        return new SyntheticParameterizedType(rawType, typeArguments);
    }

}