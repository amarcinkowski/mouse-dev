package io.github.amarcinkowski.ubuntulogitechoptions.mouse

import groovy.util.logging.Slf4j
import io.github.amarcinkowski.ubuntulogitechoptions.command.BlenderCommand
import io.github.amarcinkowski.ubuntulogitechoptions.command.Command
import io.github.amarcinkowski.ubuntulogitechoptions.keyboard.Keyboard
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import org.jnativehook.mouse.NativeMouseEvent
import org.jnativehook.mouse.NativeMouseInputListener
import org.jnativehook.mouse.NativeMouseWheelEvent
import org.jnativehook.mouse.NativeMouseWheelListener

@Slf4j
class LogitechMouse implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {

    // TODO refactor https://en.wikipedia.org/wiki/State_pattern
    // TODO check current window (idea, ecl, bl) https://superuser.com/questions/382616/detecting-currently-active-window
    // TODO add system tray https://stackoverflow.com/questions/758083/how-do-i-put-a-java-app-in-the-system-tray
    // TODO run ass ervice https://stackoverflow.com/questions/11203483/run-a-java-application-as-a-service-on-linux
    // TODO add deb package http://blog.noizeramp.com/2005/08/31/packaging-java-applications-for-ubuntu-and-other-debians/
    // TODO add gestures https://github.com/fjkfwz/mouse-gesture-recognition-java-hidden-markov-model
    // TODO add gui configuration - map gestures / clicks to actions in selected apps

    MouseAction action = new MouseAction()
    Keyboard keyboard = new Keyboard()
    // choose app based on active window
    Command command = new BlenderCommand()

    public LogitechMouse() {
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {
        log.debug 'wheel ' + nativeMouseWheelEvent.paramString()
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        log.debug 'key' + nativeKeyEvent.getKeyCode()
        action.pressed(nativeKeyEvent.getKeyCode());
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        log.debug 'key released ' + nativeKeyEvent.getKeyCode() + nativeKeyEvent.paramString()
        action.released(nativeKeyEvent.getKeyCode());
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        log.debug 'KEY typed ' + nativeKeyEvent.getKeyCode() + " | " + nativeKeyEvent.getRawCode()
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
        log.debug 'Mouse Clicked: ' + e.getClickCount()
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        log.debug 'Mouse Pressed: ' + e.getButton()
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        log.debug 'Mouse Released: ' + e.getButton() + ' ' + e.paramString()
        command.execute("${e.getButton()}")

    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        // doesn't occur? only drag
        log.trace 'Mouse Moved: ' + e.getX() + ", " + e.getY()
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        log.trace 'Mouse Dragged: ' + e.getX() + ", " + e.getY()
        action.dragged(e.getX(), e.getY());
    }


}

