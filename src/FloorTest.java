import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Floor class
 *
 * @author Group 5
 * @version 2021-03-13
 */
class FloorTest{
	@Test
	void parseLineTest(){
		Floor floor = new Floor(1, 5000);
		String string = "14:05:15.0 2 Up 4 0";
		float[] arr = new float[]{Float.parseFloat("14"), Float.parseFloat("5"), Float.parseFloat("15")};
		PersonRequest personRequest = new PersonRequest(arr, 2, true, 4, 0);
		assertEquals(personRequest.getTime()[0], floor.parseLine(string).getTime()[0]);
	}

	@Test
	void readFileTest(){
		Floor floor = new Floor(2, 5000);
		ArrayList<PersonRequest> personRequests = new ArrayList<>();
		personRequests.add(new PersonRequest(new float[]{(float) 14, (float) 5, (float) 15}, 2, true, 4, 0));
		assertEquals(personRequests.get(0).getTime()[0], floor.readFile().get(0).getTime()[0]);
	}

	@Test
	void parseConfigTest() throws FileNotFoundException {
		assertEquals(7, Floor.parseConfig());
	}

	@Test
	void generateByteArrayTest(){
		float[] arr = new float[]{Float.parseFloat("14"), Float.parseFloat("5"), Float.parseFloat("15")};
		PersonRequest personRequest = new PersonRequest(arr, 2, true, 4, 0);
		assertEquals(49, Floor.generateByteArray(personRequest)[0]);
	}

	@Test
	/**
	 * Test that floor sockets are working properly
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
