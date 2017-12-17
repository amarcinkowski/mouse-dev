package io.github.amarcinkowski.ubuntulogitechoptions.command

import io.github.amarcinkowski.ubuntulogitechoptions.keyboard.Keyboard

/**
 * Created by am on 17.12.17.
 */
class BlenderCommand implements Command {

    static Keyboard keyboard = new Keyboard()

    @Override
    void execute(String param) {
        switch (param) {
            case "4":
                keyboard.type("VK_NUMPAD7")
                break
            case "5":
                keyboard.type("VK_NUMPAD1")
                break
            case "0":
                keyboard.type("VK_NUMPAD3")
                break
        }
    }
}
