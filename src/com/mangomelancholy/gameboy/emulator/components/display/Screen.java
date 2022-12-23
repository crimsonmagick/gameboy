package com.mangomelancholy.gameboy.emulator.components.display;

public class Screen {
    private final boolean[][] data;
    private final int width;
    private final int height;

    public Screen(int width, int height) {
        data = new boolean[width][height];
        this.width = width;
        this.height = height;
    }

    public void update() {
        // TODO: Update the screenData array with the current state of the screen
    }
}
