/**
 * 
 */
package examples.calculator.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Label resultLabel = null;
	private Controller controller = null;
	private RootPanel rootPanel = null;
	private TextBox calculationTextBox;
	private Button calculateButton;

	Renderer() {}

	void setResultLabel(Label resultLabel) {
		this.resultLabel = resultLabel;
	}

	void setController(Controller controller) {
		this.controller = controller;
	}

	void setRootPanel(RootPanel rootPanel) {
		this.rootPanel = rootPanel;
	}

	void refresh() {
		if (rootPanel != null && controller != null) {
			
			// render main page elements
			VerticalPanel p0 = new VerticalPanel();
			rootPanel.add(p0);
			p0.add(new HTML("<h1>Calculator</h1>"));
			
			HorizontalPanel p1 = new HorizontalPanel();
			p0.add(p1);
			p1.add(new Label("enter calculation (e.g., 31+11, 61-19, 6*7, 84/2):"));
			calculationTextBox = new TextBox();
			p1.add(calculationTextBox);
			calculateButton = new Button("calculate!");
			p1.add(calculateButton);
			p1.setSpacing(5);

			HorizontalPanel p2 = new HorizontalPanel();
			p0.add(p2);
			p2.add(new Label("result: "));
			resultLabel = new Label();
			p2.add(resultLabel);
			p2.setSpacing(5);
			
			// set up event handlers
			calculateButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String calculation = calculationTextBox.getValue();
					try {
						controller.calculate(calculation);
					} catch (SyntaxException e) {
						Window.alert(e.getLocalizedMessage());
					}
				}
			});
		}
	}

	public void onResultChanged(Double from, Double to) {
		resultLabel.setText(to.toString());
	}

}
