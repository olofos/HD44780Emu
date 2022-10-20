# HD44780Emu - HD44780 Character LCD Emulator in Java

HD44780Emu is a port of the [vrEmuLcd](https://github.com/visrealm/vrEmuLcd) LCD emulator to Java. 

HD44780Emu supports most commands of the commands listed in the [HD44780 datasheet](https://www.sparkfun.com/datasheets/LCD/HD44780.pdf). However, the supports for 12864B graphics LCDs has been dropped.

HD44780Emu does support reading and writing in both 8-bit and 4-bit modes.


## Usage

```Java
import olofos.hd44780;
public class LcdTest {
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

    public void test8bits() {
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

    public void test4bits() {
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

    public static void main(String[] args) {
        test8bits();
        test4bits();
	}
}
```
## License
This code is licensed under the [MIT](https://opensource.org/licenses/MIT "MIT") license
