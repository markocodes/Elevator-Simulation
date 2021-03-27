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
 * @version 2021-03-27
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
	/**
	 * Tests if file is read and parsed correctly.
	 */
	void readFileTest(){
		Floor floor = new Floor(2, 5000);
		ArrayList<PersonRequest> personRequests = new ArrayList<>();
		personRequests.add(new PersonRequest(new float[]{(float) 14, (float) 5, (float) 15}, 2, true, 4, 0));
		assertEquals(personRequests.get(0).getTime()[0], floor.readFile().get(0).getTime()[0]);
	}

	@Test
	/**
	 * Tests if config file is parsed and read correctly.
	 */
	void parseConfigTest() throws FileNotFoundException {
		assertEquals(7, Floor.parseConfig());
	}

	@Test
	/**
	 * Tests if the correct byte array is generated from PersonRequest.
	 */
	void generateByteArrayTest(){
		float[] arr = new float[]{Float.parseFloat("14"), Float.parseFloat("5"), Float.parseFloat("15")};
		PersonRequest personRequest = new PersonRequest(arr, 2, true, 4, 0);
		assertEquals(49, Floor.generateByteArray(personRequest)[0]);
	}

}
