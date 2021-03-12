package com.frameworktool.test.enums;


import com.frameworktool.basetype.IEnum;

public enum TypeEnum implements IEnum<TypeEnum> {

    ONE(0), TWO(2);;

    TypeEnum(int index) {
        this.index = index;
    }

    private int index;

    @Override
    public int getIndex() {
        return index;
    }
}
