package com.mangomelancholy.gameboy.emulator.components.processor;

import java.util.function.Function;

public class Registers {

    public enum AUX_REGISTER {
        B((byte) 0),
        C((byte) 1),
        D((byte) 2),
        E((byte) 3),
        H((byte) 4),
        L((byte) 5);

        private final byte index;

        AUX_REGISTER(final byte index) {
            this.index = index;
        }

        public byte index() {
            return index;
        }
    }

    private final byte accumulator;
    private final byte[] auxiliaries; // not including flagRegister
    private final short programCounter;
    private final short stackPointer;
    private final byte flagRegister;

    public Registers() {
        accumulator = 0x0;
        auxiliaries = new byte[6];
        stackPointer = 0x0;
        programCounter = 0x0; // TODO should this be initialized to something else????
        flagRegister = 0x0;
    }

    private Registers(final byte accumulator, final byte[] auxiliaries, final short stackPointer,
                     final short programCounter, final byte flagRegister) {
        this.accumulator = accumulator;
        this.auxiliaries = auxiliaries;
        this.stackPointer = stackPointer;
        this.programCounter = programCounter;
        this.flagRegister = flagRegister;
    }

    public class Builder {
        private byte accumulator;
        private final byte[] auxiliaries;
        private short programCounter;
        private short stackPointer;
        private byte flagRegister;

        private Builder() {
            this.accumulator = Registers.this.accumulator;
            this.auxiliaries = Registers.this.auxiliaries;
            this.programCounter = Registers.this.programCounter;
            this.stackPointer = Registers.this.stackPointer;
            this.flagRegister = Registers.this.flagRegister;
        }

        Builder accumulator(final byte value) {
            accumulator = value;
            return this;
        }

        public Function<Byte, Builder> auxiliary(final AUX_REGISTER register) {
            return value -> {
                auxiliaries[register.index] = value;
                return this;
            };
        }

        public Builder programCounter(final short value) {
            programCounter = value;
            return this;
        }

        public Builder stackPointer(final short value) {
            stackPointer = value;
            return this;
        }

        public Builder flagRegister(final byte value) {
            flagRegister = value;
            return this;
        }

        public Registers build() {
            return new Registers(accumulator, auxiliaries, stackPointer, programCounter, flagRegister);
        }
    }


    public byte getAccumulator() {
        return accumulator;
    }

    public short getProgramCounter() {
        return programCounter;
    }

    public short getStackPointer() {
        return stackPointer;
    }

    public byte getFlagRegister() {
        return flagRegister;
    }

    public byte getAuxiliary(final AUX_REGISTER register) {
        return auxiliaries[register.index()];
    }

    public Builder buildNext() {
        return new Builder();
    }

}
