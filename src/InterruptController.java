public class InterruptController {
  // Constants for the different interrupt types
  public static final int INTERRUPT_VBLANK = 0;
  public static final int INTERRUPT_LCDC = 1;
  public static final int INTERRUPT_TIMER = 2;
  public static final int INTERRUPT_SERIAL = 3;
  public static final int INTERRUPT_JOYPAD = 4;

  // Data for the interrupt controller's state
  private boolean[] interruptEnabled;
  private boolean[] interruptRequested;

  public InterruptController() {
    interruptEnabled = new boolean[5];
    interruptRequested = new boolean[5];
  }

  public void setInterruptEnabled(int interrupt, boolean enabled) {
    interruptEnabled[interrupt] = enabled;
  }

  public void requestInterrupt(int interrupt) {
    interruptRequested[interrupt] = true;
  }

  public void acknowledgeInterrupt(int interrupt) {
    interruptRequested[interrupt] = false;
  }

  public boolean isInterruptRequested(int interrupt) {
    return interruptRequested[interrupt];
  }
}
