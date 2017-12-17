package io.github.amarcinkowski.ubuntulogitechoptions

import groovy.util.logging.Slf4j
import io.github.amarcinkowski.ubuntulogitechoptions.mouse.LogitechMouse
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException

import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

@Slf4j
class Main {

    def static clearLoggers() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.SEVERE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.SEVERE);
        }
    }

    def static registerHook() {
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
