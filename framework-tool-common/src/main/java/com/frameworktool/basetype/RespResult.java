package com.frameworktool.basetype;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RespResult<T> implements Serializable {
    private Integer code;

    private String message;

    private T data;

    private RespResult(Builder<T> builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }


    public static class Builder<T> {
        private Integer code;

        private String message;

        private T data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public RespResult<T> build() {
            return new RespResult<T>(this);
        }
    }
}
