/**
 * 
 */
package examples.calculator.client.widget;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class TestRenderer extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "examples.calculator.Calculator";
	}
	
	public void testOnResultChanged() {
		
		// setup testee
		Model model = new Model();
		Renderer testee = new Renderer();
		model.addListener(testee);
		Label resultLabel = new Label();
		testee.setResultLabel(resultLabel);
		
		// do stuff under test
		model.setResult(new Double(42.0));
		
		// check outcome
		assertEquals("42.0", resultLabel.getText());

	}

}
