/**
 * 
 */
package examples.calculator.client.widget;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class CalculatorWidget {

	public CalculatorWidget() {
		
		Model model = new Model();
		Controller controller = new Controller(model);
		
		Renderer renderer = new Renderer();
		renderer.setController(controller);
		model.addListener(renderer);
		
		renderer.setRootPanel(RootPanel.get());
		renderer.refresh();
		
	}
}
