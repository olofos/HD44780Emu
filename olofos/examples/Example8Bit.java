package olofos.examples;

import olofos.hd44780.HD44780Emu;
import static olofos.examples.LCDPrint.printLCD;

public class Example8Bit {
    public static void main(String[] args) {
        HD44780Emu emu = new HD44780Emu(20, 2, HD44780Emu.Rom.A02);
        System.out.println("Initialized " + emu.getColumns() + " x " + emu.getRows() + " LCD with " + emu.getWidth()
                + " x " + emu.getHeight() + " pixels");
        printLCD(emu);

        emu.sendCommand(0b00110000); // Use 8-bit interface
        emu.sendCommand(0b00001110); // Turn on display and cursor
        emu.sendCommand(0b00000110); // Increment DDRAM address, but don't shift the display

        emu.sendCommand(0b10000000 + 0x00); // Set DDRAM address 0
        int str1[] = { 'H', 'e', 'l', 'l', 'o' };
        int str2[] = { 'W', 'o', 'r', 'l', 'd', '!' };

        for (int i = 0; i < str1.length; i++) {
            emu.writeByte(str1[i]);
        }
        printLCD(emu);

        emu.sendCommand(0b10000000 + 0x40); // Set DDRAM address 0x40 (beginning of second row)
        for (int i = 0; i < str2.length; i++) {
            emu.writeByte(str2[i]);
        }
        printLCD(emu);
    }
}
