package com.cloudclass.demo.test.rtmtoken;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
