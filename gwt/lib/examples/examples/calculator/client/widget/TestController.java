package examples.calculator.client.widget;


import static org.junit.Assert.*;

import org.junit.Test;

public class TestController {

	@Test
	public void testCalculate() throws Exception {
		
		Model model = new Model();
		Controller controller = new Controller(model);
		
		// check initial state of model
		assertNull(model.getResult());
		
		String calculation;
		
		// try integer addition
		calculation = "2+4";
		controller.calculate(calculation);
		assertEquals(new Integer(6), model.getResult());
		
		// try integer subtraction
		calculation = "5-3";
		controller.calculate(calculation);
		assertEquals(new Integer(2), model.getResult());
		
		// try integer multiplication
		calculation = "6*7";
		controller.calculate(calculation);
		assertEquals(new Integer(42), model.getResult());
		
		// try integer division
		calculation = "12/4";
		controller.calculate(calculation);
		assertEquals(new Integer(3), model.getResult());
		
		// try bad calculation
		calculation = "25%7";
		try {
			controller.calculate(calculation);
			fail("expected exception");
		} catch (SyntaxException e) {
			// expected
		}
	}

}
