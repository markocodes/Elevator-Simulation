import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Floor class
 *
 * @author Group 5
 * @version 2021-02-06
 */
class FloorTest{


	public FloorTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	/**
	 * Test readFile() method
	 */
	void testReadFile() {
		Controller testController = new Controller();
		Floor testFloor = new Floor(testController);
		float[] testTime = {14,5,(float) 15.2};
		
		PersonRequest testCase = new PersonRequest(testTime,2,true,4);
		
		ArrayList<PersonRequest> testRead = testFloor.readFile();
		
		for (int i = 0; i<testRead.size(); i++){
			assertEquals(testRead.get(i),testCase);
		}
	}

	@Test
	/**
	 * Test parseLine() method
	 */
	void testParseLine() {
		Controller testController = new Controller();
		Floor testFloor = new Floor(testController);
		float[] testTime = {12,19,(float) 30.0};
		
		PersonRequest testResult = testFloor.parseLine("12:19:30.0 1 Up 6");
		
		assertArrayEquals(testResult.getTime(),testTime);
		assertEquals(testResult.getFloor(),1);
		assertTrue(testResult.isU_d());
		assertEquals(testResult.getCarButton(),6);
		
		
	}

}
