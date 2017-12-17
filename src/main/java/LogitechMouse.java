import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogitechMouse implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {

    // TODO refactor https://en.wikipedia.org/wiki/State_pattern
    // TODO add proper logging (lombok) https://projectlombok.org/api/lombok/extern/slf4j/Slf4j.html
    // TODO check current window (idea, ecl, bl) https://superuser.com/questions/382616/detecting-currently-active-window
    // TODO add system tray https://stackoverflow.com/questions/758083/how-do-i-put-a-java-app-in-the-system-tray
    // TODO run ass ervice https://stackoverflow.com/questions/11203483/run-a-java-application-as-a-service-on-linux
    // TODO add deb package http://blog.noizeramp.com/2005/08/31/packaging-java-applications-for-ubuntu-and-other-debians/
    // TODO add gestures https://github.com/fjkfwz/mouse-gesture-recognition-java-hidden-markov-model
    // TODO add gui configuration - map gestures / clicks to actions in selected apps

    Robot r;
    int isLogitechSpecial = 0;
    boolean isLogitechSpecialPressed = false;
    boolean isLogitechSpecialReleased = false;
    int getIsLogitechSpecialStartX = 0;
    int getIsLogitechSpecialStartY = 0;
    int getIsLogitechSpecialEndX = 0;
    int getIsLogitechSpecialEndY = 0;

    public LogitechMouse() {
        try {
            r = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {
//        System.out.println("WHEEL " + nativeMouseWheelEvent.paramString());
    }

    private boolean isLogitechSpecial(int keycode) {
        if ((isLogitechSpecial == 0 && keycode == 29)
                || (isLogitechSpecial == 1 && keycode == 56)
                || (isLogitechSpecial == 2 && keycode == 15)) {
            isLogitechSpecial++;
        }
        isLogitechSpecialPressed = isLogitechSpecial == 3;
        return isLogitechSpecialPressed;
    }

    private boolean isLogitechSpecialReleased(int keycode) {
        if ((isLogitechSpecialPressed && isLogitechSpecial == 1 && keycode == 29)
                || (isLogitechSpecialPressed && isLogitechSpecial == 2 && keycode == 56)
                || (isLogitechSpecialPressed && isLogitechSpecial == 3 && keycode == 15)) {
            isLogitechSpecial--;
        }

        isLogitechSpecialReleased = isLogitechSpecial == 0;
        if (isLogitechSpecialPressed && isLogitechSpecialReleased) {
            isLogitechSpecialPressed = false;
            return true;
        }
        return false;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
//        System.out.println("KEY " + nativeKeyEvent.getKeyCode());
        if (isLogitechSpecial(nativeKeyEvent.getKeyCode())) {
            System.out.println("SPECIAL PRESSED" + isLogitechSpecial);
            getIsLogitechSpecialStartX = -1;
            getIsLogitechSpecialStartY = -1;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
//        System.out.println("KEY REL " + nativeKeyEvent.getKeyCode() + nativeKeyEvent.paramString());
        if (isLogitechSpecialReleased(nativeKeyEvent.getKeyCode())) {
            System.out.println("SPECIAL RELEASED ");
        }
    }

    @Override

    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
//        System.out.println("KEY typed " + nativeKeyEvent.getKeyCode() + " | " + nativeKeyEvent.getRawCode());
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
        //System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        //System.out.println("Mouse Pressed: " + e.getButton());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        System.out.println("Mouse Released: " + e.getButton() + " " + e.paramString() + " " + e.getID() + " " + e.getModifiers());
        if (e.getButton() == 4) {
            r.keyPress(KeyEvent.VK_NUMPAD7);
            r.keyRelease(KeyEvent.VK_NUMPAD7);
        }
        if (e.getButton() == 5) {
            r.keyPress(KeyEvent.VK_NUMPAD1);
            r.keyRelease(KeyEvent.VK_NUMPAD1);
        }
        if (e.getButton() == 0) {
            r.keyPress(KeyEvent.VK_NUMPAD3);
            r.keyRelease(KeyEvent.VK_NUMPAD3);
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
//        System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        if (isLogitechSpecialPressed && isLogitechSpecial == 3) {
            getIsLogitechSpecialStartX = e.getX();
            getIsLogitechSpecialStartY = e.getY();
            System.out.println("GESTURE XY " + getIsLogitechSpecialStartX + " " + getIsLogitechSpecialStartY);
        }
        if (isLogitechSpecialReleased && isLogitechSpecial == 1) {
            isLogitechSpecialReleased = false;
            getIsLogitechSpecialEndX = e.getX();
            getIsLogitechSpecialEndY = e.getY();

            if (getIsLogitechSpecialStartX < 0 && getIsLogitechSpecialStartY < 0) {
                // special no gesture
                System.out.println("SPECIAL NO GESTURE");
            } else {
                // gesture
                int deltax = getIsLogitechSpecialEndX - getIsLogitechSpecialStartX;
                int deltay = getIsLogitechSpecialEndY - getIsLogitechSpecialStartY;
                String gesture = "GESTURE ";
                gesture += Math.abs(deltax) > 10 && Math.abs(deltax) > Math.abs(deltay) * 3 ? (deltax > 0 ? "R" : "L") : "";
                gesture += Math.abs(deltay) > 10 && Math.abs(deltay) > Math.abs(deltax) * 3 ? (deltay > 0 ? "D" : "U") : "";
                System.out.println("SPECIAL WITH " + gesture);
            }

            System.out.println("GESTURE END XY " + getIsLogitechSpecialEndX + " " + getIsLogitechSpecialEndY);
        }
//        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }

    private static void clearLoggers() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.SEVERE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.SEVERE);
        }
    }

    private static void registerHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
    }

    public static void main(String[] args) {
        clearLoggers();
        registerHook();

        // Construct the monitor object.
        LogitechMouse monitor = new LogitechMouse();

        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(monitor);
        GlobalScreen.addNativeKeyListener(monitor);
        GlobalScreen.addNativeMouseMotionListener(monitor);
        GlobalScreen.addNativeMouseWheelListener(monitor);
    }
}

