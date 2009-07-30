/**
 * 
 */
package examples.calculator.client.widget;


/**
 * @author aliman
 *
 */
class Controller {
	
	private static final String ADDITION = "\\s*(\\d+)\\s*\\+\\s*(\\d+)\\s*";
	private static final String SUBTRACTION = "\\s*(\\d+)\\s*-\\s*(\\d+)\\s*";
	private static final String MULTIPLICATION = "\\s*(\\d+)\\s*\\*\\s*(\\d+)\\s*";
	private static final String DIVISION = "\\s*(\\d+)\\s*/\\s*(\\d+)\\s*";
	private static final String OPERATORS = "[+*/\\-]"; 
	private Model model;

	Controller(Model model) {
		this.model = model;
	}

	void calculate(String calculation) throws SyntaxException {

		Double result = null;

		String[] args = calculation.split(OPERATORS);
		
		if (calculation.matches(ADDITION)) {
			result = Double.parseDouble(args[0]) + Double.parseDouble(args[1]);
		} 
		else if (calculation.matches(SUBTRACTION)) {
			result = Double.parseDouble(args[0]) - Double.parseDouble(args[1]);
		}
		else if (calculation.matches(MULTIPLICATION)) {
			result = Double.parseDouble(args[0]) * Double.parseDouble(args[1]);
		}
		else if (calculation.matches(DIVISION)) {
			result = Double.parseDouble(args[0]) / Double.parseDouble(args[1]);
		}
		else {
			throw new SyntaxException("invalid syntax: "+calculation);
		}
		
		model.setResult(result);
	}

}
