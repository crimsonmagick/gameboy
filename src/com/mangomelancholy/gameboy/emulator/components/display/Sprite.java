package com.mangomelancholy.gameboy.emulator.components.display;

public class Sprite {
  // Constants for the size of a sprite
  private static final int WIDTH = 8;
  private static final int HEIGHT = 8;

  // Data for the sprite's pixel data and properties
  private final boolean[][] data;
  private int x;
  private int y;
  private int paletteIndex;
  private boolean flipX;
  private boolean flipY;
  private boolean priority;

  public Sprite() {
    data = new boolean[WIDTH][HEIGHT];
  }

  // Getters and setters for the sprite's properties
  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getPaletteIndex() {
    return paletteIndex;
  }

  public void setPaletteIndex(int paletteIndex) {
    this.paletteIndex = paletteIndex;
  }

  public boolean isFlipX() {
    return flipX;
  }

  public void setFlipX(boolean flipX) {
    this.flipX = flipX;
  }

  public boolean isFlipY() {
    return flipY;
  }

  public void setFlipY(boolean flipY) {
    this.flipY = flipY;
  }

  public boolean isPriority() {
    return priority;
  }

  public void setPriority(boolean priority) {
    this.priority = priority;
  }
}
