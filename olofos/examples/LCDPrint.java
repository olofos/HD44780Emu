package olofos.examples;

import olofos.hd44780.HD44780Emu;

public class LCDPrint {
    public static void printLCD(HD44780Emu emu) {
        int[][] pixels = emu.getPixels();

        System.out.print("+");
        for (int x = 0; x < emu.getWidth(); x++) {
            System.out.print("-");
        }
        System.out.println("+");

        for (int y = 0; y < emu.getHeight(); y++) {
            System.out.print("|");
            for (int x = 0; x < emu.getWidth(); x++) {
                System.out.print((pixels[x][y] == 1) ? '#' : ' ');
            }
            System.out.println("|");
        }
        System.out.print("+");
        for (int x = 0; x < emu.getWidth(); x++) {
            System.out.print("-");
        }
        System.out.println("+");
        System.out.println();
    }

}
