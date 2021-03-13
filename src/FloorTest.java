import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Floor class
 *
 * @author Group 5
 * @version 2021-02-06
 */
class FloorTest{


	@Test
	/**
	 * Test FloorSocket() method
	 */

	void FloorSocketTest() {
		boolean result = false;
		try {
			// ServerSocket try to open a LOCAL port
			new ServerSocket(22).close();
			// local port can be opened, it's available
			assertFalse(result);

		} catch(IOException e) {
			result = true;
		}
	}

}
