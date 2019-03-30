import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame {
	
	JPanel panTop,panMemory,panBottom;
	JButton btnMemory[]=new JButton[5],btnOthers[]=new JButton[24];
	JLabel lblHistory,lblResult;
	String strMemory[]= {"MC","MR","M+","M-","MS"};
	String strOthers[]= {"%","Sqrt","Sqr"," 1/x","CE","C","\u232b","/","7","8","9","*","4","5","6","-","1","2","3","+","\u2213","0",".","="};
	int i;
	double memory,currentNumber,lastNumber;
	boolean flagConcat=true;
	char currentOperator;
	ImageIcon icon;
	
	Calculator(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panTop =new JPanel();
		panTop.setLayout(new GridLayout(3,1));
		lblHistory= new JLabel("",SwingConstants.RIGHT);
		lblHistory.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
		lblResult= new JLabel("0",SwingConstants.RIGHT);
		lblResult.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		
		panMemory = new JPanel();
		panMemory.setLayout(new GridLayout(1,5));
		for(i=0;i<strMemory.length;i++) {
			btnMemory[i]=new JButton(strMemory[i]);
			panMemory.add(btnMemory[i]);
			btnMemory[i].setBackground(new Color(227,231,237));
			btnMemory[i].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					JButton btn=( JButton)me.getSource();
					btn.setBackground(new Color(203,206,211));
				}
				public void mouseExited(MouseEvent me) {
					JButton btn=(JButton)me.getSource();
					btn.setBackground(new Color(227,231,237));
				}
			});
			btnMemory[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					JButton btn= (JButton)ae.getSource();
					String str=btn.getText();
					if(str.equalsIgnoreCase("MC")) {
						memory=0;
						flagConcat=false;
					}
					else if(str.equalsIgnoreCase("MR")) {
						lblResult.setText(formatCurrentValue(memory+""));
						flagConcat=false;
					}
					else if(str.equalsIgnoreCase("M+")) {
						memory+=Double.parseDouble(lblResult.getText());
						flagConcat=false;
					}
					else if(str.equalsIgnoreCase("M-")) {
						memory-=Double.parseDouble(lblResult.getText());
						flagConcat=false;
					}
					else if(str.equalsIgnoreCase("MS")) {
						memory=Double.parseDouble(lblResult.getText());
						flagConcat=false;
					}
				}
			});
			
		}
		
		panTop.add(lblHistory);
		panTop.add(lblResult);
		panTop.add(panMemory);
		
		panBottom = new JPanel();
		panBottom.setLayout(new GridLayout(6,4));
		for(i=0;i<btnOthers.length;i++) {
			btnOthers[i]=new JButton(strOthers[i]);
			panBottom.add(btnOthers[i]);
			if(Character.isDigit(strOthers[i].charAt(0))){
				btnOthers[i].setBackground(new Color(252, 252, 252));
				btnOthers[i].addMouseListener(new MouseAdapter(){
					public void mouseEntered(MouseEvent me) {
						JButton btn=(JButton)me.getSource();
						btn.setBackground(new Color(203,206,211));
					}
					public void mouseExited(MouseEvent me) {
						JButton btn=(JButton)me.getSource();
						btn.setBackground(new Color(252, 252, 252));
					}
				});
				btnOthers[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						JButton btn=(JButton)ae.getSource();
						char ch=btn.getText().charAt(0);
						String s=lblResult.getText();
						if(flagConcat==true) {
							s+=ch;
							lblResult.setText(formatCurrentValue(s));
						}
						else {
							s=ch+"";
							lblResult.setText(formatCurrentValue(s));
							flagConcat=true;
						}
					}
				});
			}
			else {
				btnOthers[i].setBackground(new Color(242,242,242));
				btnOthers[i].addMouseListener(new MouseAdapter(){
					public void mouseEntered(MouseEvent me) {
						JButton btn=(JButton)me.getSource();
						btn.setBackground(new Color(203,206,211));
					}
					public void mouseExited(MouseEvent me) {
						JButton btn=(JButton)me.getSource();
						btn.setBackground(new Color(242, 242, 242));
					}
				});
				btnOthers[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						JButton btn=(JButton)ae.getSource();
						String str=btn.getText();
						if(isOperator(str.charAt(0))) {
							flagConcat=false;
							currentNumber=Double.parseDouble(lblResult.getText());
							if(currentOperator!='\u0000') {
								switch(currentOperator) {
								case '+':
									lastNumber=lastNumber+currentNumber;
									break;
								case '-':
									lastNumber=lastNumber-currentNumber;
									break;
								case '*':
									lastNumber=lastNumber*currentNumber;
									break;
								case '/':
									lastNumber=lastNumber/currentNumber;
									break;
								default:
									break;
								}
								lblResult.setText(formatCurrentValue(lastNumber+""));
							}
							currentOperator=str.charAt(0);
							lastNumber=Double.parseDouble(lblResult.getText());
							lblHistory.setText(lblHistory.getText()+formatCurrentValue(currentNumber+"")+currentOperator);
						}
						else if(str.equalsIgnoreCase("%")){
							currentNumber=Double.parseDouble(lblResult.getText());
							currentNumber=(currentNumber/100)*lastNumber;
							lblResult.setText(formatCurrentValue(currentNumber+""));
						}
						else if(str.equalsIgnoreCase("sqrt")){
							currentNumber=Double.parseDouble(lblResult.getText());
							currentNumber=Math.sqrt(currentNumber);
							lblResult.setText(formatCurrentValue(currentNumber+""));
							flagConcat=false;
						}
						else if(str.equalsIgnoreCase("sqr")){
							currentNumber=Double.parseDouble(lblResult.getText());
							currentNumber=currentNumber*currentNumber;
							lblResult.setText(formatCurrentValue(currentNumber+""));
							flagConcat=false;
						}
						else if(str.equalsIgnoreCase(" 1/x")){
							currentNumber=Double.parseDouble(lblResult.getText());
							currentNumber=1/currentNumber;
							lblResult.setText(formatCurrentValue(currentNumber+""));
							flagConcat=false;
						}
						else if(str.equalsIgnoreCase("CE")){
							lblResult.setText("0");
							flagConcat=false;
						}
						else if(str.equalsIgnoreCase("C")){
							lblResult.setText("0");
							currentNumber=0;
							lastNumber=0;
							flagConcat=false;
							currentOperator='\u0000';
							lblHistory.setText("");
						}
						else if(str.equalsIgnoreCase("\u232b")){
							String s=lblResult.getText();
							lblResult.setText(formatCurrentValue(s.substring(0,s.length()-1)));
							flagConcat=false; 
						}
						else if(str.equalsIgnoreCase("\u2213")){
							currentNumber=Double.parseDouble(lblResult.getText());
							currentNumber=-currentNumber;
							lblResult.setText(formatCurrentValue(currentNumber+""));
							flagConcat=false;
						}
						else if(str.equalsIgnoreCase(".")){
							String s=lblResult.getText();
							int pos = s.indexOf(".");
							if(pos==-1) 
								lblResult.setText(s+".");
						}
						else if(str.equalsIgnoreCase("=")){
							currentNumber=Double.parseDouble(lblResult.getText());
							switch(currentOperator) {
							case '+':
								lastNumber=lastNumber+currentNumber;
								break;
							case '-':
								lastNumber=lastNumber-currentNumber;
								break;
							case '*':
								lastNumber=lastNumber*currentNumber;
								break;
							case '/':
								lastNumber=lastNumber/currentNumber;
								break;
							default:
								break;
							}
							lblResult.setText(formatCurrentValue(lastNumber+""));
							lblHistory.setText("");
							currentOperator='\u0000';
							lastNumber=0.0;
							flagConcat=false;
						}
					}
				});
			}
			
		}
		
		icon= new ImageIcon("src/images/calculator.png");
		
		setIconImage(icon.getImage());
		add(panTop,"North");
		add(panBottom);
		setBounds(100,100,400,600);
		setTitle("Calculator");
		setResizable(true);
		setVisible(true);	
		
	}

	String formatCurrentValue(String s) {
		if(s.isEmpty())
			return "0";
		double d=Double.parseDouble(s);
		if(d==(int)d)
			return (int)d+"";
		else
			return d+"";		
	}
	
	boolean isOperator(char ch) {
		if(ch=='+' ||ch=='-'||ch=='*'||ch=='/')
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		new Calculator();
	}

}
