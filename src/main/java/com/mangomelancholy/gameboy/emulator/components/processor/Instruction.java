package com.mangomelancholy.gameboy.emulator.components.processor;

@FunctionalInterface
public interface Instruction {
    public short execute(byte[] n);
}
