package io.github.amarcinkowski.ubuntulogitechoptions.keyboard

import groovy.util.logging.Slf4j

import java.awt.*
import java.awt.event.KeyEvent

@Slf4j
class Keyboard {

    def Robot robot = new Robot()

    void type(String param) {
        int ke = KeyEvent."$param"
        robot.keyPress(ke)
        robot.keyRelease(ke)
        log.info "TYPED: $ke"
    }
}
