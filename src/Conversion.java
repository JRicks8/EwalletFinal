import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Conversion extends JFrame{
	private Calculations calc;
	private int width;
	private int height;
	private JTextField inputField;
	private JLabel inputLabel;
	private JLabel currencyLabel;
	private JLabel resultLabel;
	private JList<String> currencyList;
	private DefaultListModel<String> currencyListModel;
	private JScrollPane currencyListScroller;
	private JButton convertButton;
	
	
	public Conversion(Calculations _calc){
		this.calc = _calc;
		
		this.width = 400;
		this.height = 450;
		
		this.setTitle("Conversion");
		this.setSize(width, height);
		getContentPane().setLayout(null);
		
		this.currencyListModel = new DefaultListModel<String>();
		for(Currency cur : calc.userAtHand.getCurrencyRates()) {
			currencyListModel.addElement(cur.name);
		}
		
		inputLabel = new JLabel("amount to convert:");
		inputLabel.setBounds(10, 10, 200, 20);
		getContentPane().add(inputLabel);
		
		currencyLabel = new JLabel("choose currency:");
		currencyLabel.setBounds(10, 80, 200, 20);
		getContentPane().add(currencyLabel);
		
		resultLabel = new JLabel("Converted amount:");
		resultLabel.setBounds(10, 370, 200, 20);
		getContentPane().add(resultLabel);
		
		inputField = new JTextField("");
		inputField.setBounds(10, 40, 200, 30);
		getContentPane().add(inputField);
		
		currencyList = new JList<String>(currencyListModel);
		currencyListScroller = new JScrollPane(currencyList);
		currencyListScroller.setBounds(10, 100, 300, 200);
		getContentPane().add(currencyListScroller);
		
		convertButton = new JButton("Convert");
		convertButton.setBounds(10, 320, 160, 50);
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Currency cur = calc.userAtHand.getCurrencyByName(currencyList.getSelectedValue());
				if(cur == null) {
					resultLabel.setText("No currency selected.");
				}
				else {
					double result;
					try {
						result = Double.parseDouble(inputField.getText());
						
						result *= cur.rate;
						
						resultLabel.setText("Converted amount: " + result);
					}
					catch(Exception E) {
						resultLabel.setText("Invalid input: must be a number. ");
					}
				}
			}
		});
		getContentPane().add(convertButton);
		
	}
}
