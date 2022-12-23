import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputController implements KeyListener {
  // Constants for the keys on the Gameboy's input controller
  private static final int KEY_UP = KeyEvent.VK_UP;
  private static final int KEY_DOWN = KeyEvent.VK_DOWN;
  private static final int KEY_LEFT = KeyEvent.VK_LEFT;
  private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
  private static final int KEY_A = KeyEvent.VK_Z;
  private static final int KEY_B = KeyEvent.VK_X;
  private static final int KEY_SELECT = KeyEvent.VK_SHIFT;
  private static final int KEY_START = KeyEvent.VK_ENTER;

  // Data for the current state of the input controller
  private final boolean[] keys;

  public InputController() {
    keys = new boolean[8];
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // Update the key state when a key is pressed
    int keyCode = e.getKeyCode();
    if (keyCode == KEY_UP) {
      keys[0] = true;
    } else if (keyCode == KEY_DOWN) {
      keys[1] = true;
    } else if (keyCode == KEY_LEFT) {
      keys[2] = true;
    } else if (keyCode == KEY_RIGHT) {
      keys[3] = true;
    } else if (keyCode == KEY_A) {
      keys[4] = true;
    } else if (keyCode == KEY_B) {
      keys[5] = true;
    } else if (keyCode == KEY_SELECT) {
      keys[6] = true;
    } else if (keyCode == KEY_START) {
      keys[7] = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Update the key state when a key is released
    int keyCode = e.getKeyCode();
    if (keyCode == KEY_UP) {
      keys[0] = false;
    } else if (keyCode == KEY_DOWN) {
      keys[1] = false;
    } else if (keyCode == KEY_LEFT) {
      keys[2] = false;
    } else if (keyCode == KEY_RIGHT) {
      keys[3] = false;
    } else if (keyCode == KEY_A) {
      keys[4] = false;
    } else if (keyCode == KEY_B) {
      keys[5] = false;
    } else if (keyCode == KEY_SELECT) {
      keys[6] = false;
    } else if (keyCode == KEY_START) {
      keys[7] = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Not used in this implementation
  }

  public void update() {
    // TODO: Update the input state based on the current key states
    // For example, you might set a byte array representing the input state for the emulator,
    // with each bit representing a different button on the input controller.
  }

  public boolean isButtonPressed() {
    // Check if any key is currently pressed
    for (boolean key : keys) {
      if (key) {
        return true;
      }
    }
    return false;
  }

}
