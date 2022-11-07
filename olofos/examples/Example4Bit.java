package olofos.examples;

import olofos.hd44780.HD44780Emu;
import static olofos.examples.LCDPrint.printLCD;

public class Example4Bit {
    public static void main(String[] args) {
        HD44780Emu emu = new HD44780Emu(20, 2, HD44780Emu.Rom.A02);
        System.out.println("Initialized " + emu.getColumns() + " x " + emu.getRows() + " LCD with " + emu.getWidth()
                + " x " + emu.getHeight() + " pixels");
        printLCD(emu);

        emu.sendCommand(0b00100000); // Use 4-bit interface. This command should in general be sent three times to
        emu.sendCommand(0b00100000); // ensure that we go to 4-bit mode no matter if we start in 4- or 8-bit mode.
        emu.sendCommand(0b00100000); // Here the emulator defaults to 8-bit mode, so this is not really necessary.

        emu.sendCommand((0b00001110) & 0xF0); // Turn on display and cursor.
        emu.sendCommand((0b00001110 << 4) & 0xF0); // Send the low nibble.
        emu.sendCommand((0b00000110) & 0xF0); // Increment DDRAM address, but don't shift the display
        emu.sendCommand((0b00000110 << 4) & 0xF0); // Send the low nibble.

        emu.sendCommand((0b10000000 + 0x00) & 0xF0); // Set DDRAM address 0
        emu.sendCommand(((0b10000000 + 0x00) << 4) & 0xF0); // Send the low nibble.

        int str1[] = { 'H', 'e', 'l', 'l', 'o' };
        int str2[] = { 'W', 'o', 'r', 'l', 'd', '!' };

        for (int i = 0; i < str1.length; i++) {
            emu.writeByte(str1[i] & 0xF0); // Send the high nibble.
            emu.writeByte((str1[i] << 4) & 0xF0); // Send the low nibble.
        }
        printLCD(emu);

        emu.sendCommand(((0b10000000 + 0x40)) & 0xF0); // Set DDRAM address 0x40 (beginning of second row)
        emu.sendCommand(((0b10000000 + 0x40) << 4) & 0xF0); // Send the low nibble.
        for (int i = 0; i < str2.length; i++) {
            emu.writeByte((str2[i]) & 0xF0); // Send the high nibble.
            emu.writeByte((str2[i] << 4) & 0xF0); // Send the low nibble.
        }
        printLCD(emu);
    }

}
