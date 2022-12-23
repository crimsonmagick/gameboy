public class Memory {

  private byte[] romData;
  private final byte[] data;

  public Memory(int size) {
    data = new byte[size];
  }

  public void loadROM(byte[] romData) {
    // Load the ROM data into the memory
    this.romData = romData;
    System.arraycopy(romData, 0, data, 0, data.length);
  }

  public int readByte(int address) {
    return data[address] & 0xFF;
  }

  public void writeByte(int address, int value) {
    data[address] = (byte) value;
  }

  public int readShort(int address) {
    int low = readByte(address);
    int high = readByte(address + 1);
    return (high << 8) | low;
  }

  public void writeShort(int address, int value) {
    writeByte(address, value & 0xFF);
    writeByte(address + 1, value >> 8);
  }
}
