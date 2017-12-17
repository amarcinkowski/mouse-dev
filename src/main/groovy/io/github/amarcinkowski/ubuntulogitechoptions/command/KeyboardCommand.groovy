package io.github.amarcinkowski.ubuntulogitechoptions.command

import groovy.util.logging.Slf4j

import java.awt.Robot
import java.awt.event.KeyEvent

@Slf4j
class KeyboardCommand implements Command {

    def Robot robot = new Robot()
    @Override
    void execute(String param) {
        int ke = KeyEvent."$param"
        robot.keyPress(ke)
        robot.keyRelease(ke)
        log.info "KEYPRESS $ke"
    }
}
