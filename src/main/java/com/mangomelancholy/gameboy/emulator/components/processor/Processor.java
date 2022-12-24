package com.mangomelancholy.gameboy.emulator.components.processor;

import com.mangomelancholy.gameboy.emulator.components.input.InputController;
import com.mangomelancholy.gameboy.emulator.components.memory.Memory;

import static com.mangomelancholy.gameboy.emulator.components.processor.Registers.AUX_REGISTER.*;

public class Processor {
    // Constants for the size of the processor's registers and memory
    private static final int STACK_SIZE = 16;

    // Emulated hardware components
    private final Memory memory;
    private final InterruptController interruptController;
    private final InputController inputController;

    // Processor state
    private final int[] stack;
    private int ime; // interrupt master enable flag

    private Registers registers;

    public Processor(final Memory memory, final InputController inputController) {
        this.memory = memory;
        this.inputController = inputController;
        interruptController = new InterruptController();
        registers = new Registers();
        stack = new int[STACK_SIZE];
        ime = 0;
    }

    public void executeNextInstruction() {
        // Fetch the next instruction from memory
        int instruction = memory.readByte(registers.getProgramCounter());
        registers = registers.buildNext()
                .programCounter((short) (registers.getProgramCounter() + 1))
                .build();
        int a, bc, de;

        // Decode and execute the instruction
        switch (instruction) {
            case 0x00:
                // NOP instruction
                break;
            case 0x01:
                // LD BC, d16 instruction
                bc = memory.readShort(registers.getProgramCounter());
                registers.buildNext()
                        .auxiliary(B).apply((byte) (bc & 0xFF))
                        .auxiliary(C).apply((byte) (bc >> 8))
                        .programCounter((short) (registers.getProgramCounter() + 2))
                        .build();
                break;
            case 0x02:
                // LD (BC), A instruction
                memory.writeByte(registers.getAuxiliary(B)
                        | (registers.getAuxiliary(C) << 8), registers.getAccumulator()); // store A in (BC)
                break;
            case 0x03:
                // INC BC instruction
                bc = (registers.getAuxiliary(B) | (registers.getAuxiliary(C) << 8)) + 1; // increment BC
                registers = registers.buildNext()
                        .auxiliary(B).apply((byte) (bc & 0xFF))
                        .auxiliary(C).apply((byte) (bc >> 8))
                        .build();
                break;
            case 0x04:
                // INC B instruction
                registers = registers.buildNext()
                        .auxiliary(B).apply((byte) ((registers.getAuxiliary(B) + 1) & 0xFF))
                        .build();
                break;
            case 0x08:
                // LD (a16), SP instruction
                int address = memory.readShort(registers.getProgramCounter());
                registers = registers.buildNext()
                        .programCounter((byte) (registers.getProgramCounter() + 2))
                        .build();
                memory.writeShort(address, registers.getStackPointer()); // store SP at the specified address
                break;
            case 0x09:
                // ADD HL, BC instruction
                int hl = (registers.getAuxiliary(H) | (registers.getAuxiliary(L) << 8))
                        + (registers.getAuxiliary(B) | (registers.getAuxiliary(C) << 8));
                registers = registers.buildNext()
                        .auxiliary(H).apply((byte) (hl & 0xFF))
                        .auxiliary(L).apply((byte) (hl >> 8))
                        .build();
                break;
            case 0x0A:
                // LD A, (BC) instruction
                registers = registers.buildNext()
                        .accumulator((byte) (memory.readByte(registers.getAuxiliary(B))
                                | (byte) (registers.getAuxiliary(C) << 8))) // load A from (BC))
                        .build();
                break;
            case 0x0B:
                // DEC BC instruction
                bc = (registers.getAuxiliary(B) | (registers.getAuxiliary(C) << 8)) - 1; // decrement BC
                registers.buildNext()
                        .auxiliary(B).apply((byte) (bc & 0xFF)) // set B
                        .auxiliary(C).apply((byte) (bc >> 8))
                        .build();
                break;
            case 0x0C:
                // INC C instruction
                registers.buildNext()
                        .auxiliary(C).apply((byte) ((registers.getAuxiliary(C) + 1) & 0xFF))
                        .build();
                break;
            case 0x0D:
                // DEC C instruction
                registers.buildNext()
                        .auxiliary(C).apply((byte) ((registers.getAuxiliary(C) - 1) & 0xFF))
                        .build();
                break;
            case 0x0E:
                // LD C, d8 instruction
                registers.buildNext()
                        .auxiliary(C).apply((byte) memory.readByte(registers.getProgramCounter()))
                        .programCounter((short) (registers.getProgramCounter() + 1))
                        .build();
                break;
            case 0x0F:
                // RRCA instruction
                a = registers.getAccumulator();
                registers.buildNext()
                        .accumulator((byte) ((a >> 1) | (a << 7) & 0xFF))
                        .build();
                break;
            case 0x10: // FIXME super sus
                // STOP 0 instruction
                while (true) {
                    // Check for input and update input state
                    inputController.update();

                    // Pause the emulator's main loop until a button is pressed
                    if (inputController.isButtonPressed()) {
                        break;
                    }

                    // Add a delay to avoid consuming too much CPU time
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            case 0x11:
                // LD DE, d16 instruction
                de = memory.readShort(registers.getProgramCounter());
                registers = registers.buildNext()
                        .programCounter((short) 2)
                        .auxiliary(D).apply((byte) (de & 0xFF))
                        .auxiliary(E).apply((byte) (de >> 8))
                        .build();
                break;
            case 0x12:
                // LD (DE), A instruction
                memory.writeByte(registers.getAuxiliary(D) | (registers.getAuxiliary(E) << 8),
                        registers.getAccumulator()); // store A in (DE)
                break;
            case 0x13:
                // INC DE instruction
                de = (registers.getAuxiliary(D) | (registers.getAuxiliary(E) << 8)) + 1; // increment DE
                registers = registers.buildNext()
                        .auxiliary(D).apply((byte) (de & 0xFF)) // set D
                        .auxiliary(E).apply((byte) (de >> 8))
                        .build();
                break;
            case 0x14:
                // INC D instruction
                registers = registers.buildNext()
                        .auxiliary(D).apply((byte) ((registers.getAuxiliary(D) + 1) & 0xFF))
                        .build();
                break;
            case 0x15:
                // DEC D instruction
                registers = registers.buildNext()
                        .auxiliary(D).apply((byte) ((registers.getAuxiliary(D) - 1) & 0xFF))
                        .build();
                break;
            case 0x16:
                // LD D, d8 instruction
                registers = registers.buildNext()
                        .programCounter((short) (registers.getProgramCounter() + 1))
                        .auxiliary(D).apply((byte) (memory.readByte(registers.getProgramCounter())))
                        .build();
                break;
            case 0x17:
                // RLA instruction
                a = registers.getAccumulator();
                registers = registers.buildNext()
                        .accumulator((byte) (((a << 1) | (registers.getFlagRegister() & 0x01)) & 0xFF))
                        .flagRegister((byte) (registers.getFlagRegister() & 0xFE | (a >> 7)))
                        .build();
                break;
        }
    }
}
