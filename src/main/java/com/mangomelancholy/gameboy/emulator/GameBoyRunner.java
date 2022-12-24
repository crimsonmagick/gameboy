package com.mangomelancholy.gameboy.emulator;

import java.io.FileInputStream;
import java.io.IOException;

public class GameBoyRunner {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: GameBoyRunner <rom_path>");
      return;
    }

    // Load the ROM data from the path provided by the user
    String romPath = args[0];
    byte[] romData = loadROMData(romPath);

    // Create the emulator
    GameBoyEmulator emulator = new GameBoyEmulator();

    // Initialize the emulator with the ROM data
    emulator.loadROM(romData);

    // Start the emulator's main loop
    emulator.start();
  }

  private static byte[] loadROMData(String path) {
    try {
      // Open the file at the specified path
      FileInputStream fis = new FileInputStream(path);

      // Read the ROM data into a byte array
      byte[] data = new byte[fis.available()];
      fis.read(data);

      // Close the file input stream
      fis.close();

      return data;
    } catch (IOException e) {
      System.out.println("Error loading ROM: " + e.getMessage());
      return new byte[0];
    }
  }
}
