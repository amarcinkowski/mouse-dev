package io.github.amarcinkowski.ubuntulogitechoptions.mouse

class MouseAction {
    int isLogitechSpecial = 0;
    boolean isLogitechSpecialPressed = false;
    boolean isLogitechSpecialReleased = false;
    int getIsLogitechSpecialStartX = 0;
    int getIsLogitechSpecialStartY = 0;
    int getIsLogitechSpecialEndX = 0;
    int getIsLogitechSpecialEndY = 0;

    def pressed(int code) {
        if (isLogitechSpecial(code)) {
            System.out.println("SPECIAL PRESSED" + isLogitechSpecial);
            getIsLogitechSpecialStartX = -1;
            getIsLogitechSpecialStartY = -1;
        }
    }

    def released(int code) {
        if (isLogitechSpecialReleased(code)) {
            System.out.println("SPECIAL RELEASED ");
        }
    }

    def boolean isLogitechSpecial(int keycode) {
        if ((isLogitechSpecial == 0 && keycode == 29)
                || (isLogitechSpecial == 1 && keycode == 56)
                || (isLogitechSpecial == 2 && keycode == 15)) {
            isLogitechSpecial++;
        }
        isLogitechSpecialPressed = isLogitechSpecial == 3;
        return isLogitechSpecialPressed;
    }

    def boolean isLogitechSpecialReleased(int keycode) {
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

    def dragged(int x, int y) {
        if (isLogitechSpecialPressed && isLogitechSpecial == 3) {
            getIsLogitechSpecialStartX = x;
            getIsLogitechSpecialStartY = y;
            System.out.println("GESTURE XY " + getIsLogitechSpecialStartX + " " + getIsLogitechSpecialStartY);
        }
        if (isLogitechSpecialReleased && isLogitechSpecial == 1) {
            isLogitechSpecialReleased = false;
            getIsLogitechSpecialEndX = x;
            getIsLogitechSpecialEndY = y;

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
    }

}
