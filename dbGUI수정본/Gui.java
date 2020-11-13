import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.*;
import java.util.*;
public class Gui extends JFrame {
	String [] tables;
	String val;
	String name;
	int va=0;
	ArrayList<String> s;
	String insert;
	ArrayList<String> ta;
	String [] login;
	public Gui(ArrayList<String> s,ArrayList<String> ta,String name,String[] tables,String[] login) {
		LinkedHashSet<String> p = new LinkedHashSet();
		ArrayList<String> inx = new ArrayList();
		ArrayList<String> inx2 = new ArrayList();
		ArrayList<String> ssn = new ArrayList();
	
		String[] dname = {"부서명","Research","Administration","Headquarters"};
		this.name = name;
		this.s=s;
		this.ta=ta;
		this.login=login;
		int size=0;
		String[] header =new String[s.size()];
		
		for(String temp: s) {
			header[size++]=temp;
		}
		String [][] cc = new String[ta.size()][s.size()];
		for(int i=0; i<ta.size();i++) {
			String array[] = ta.get(i).split("&");
			for(int j=0;j<s.size();j++) {
				cc[i][j]=array[j];
			}
		}
		
		JButton[] button = new JButton[s.size()];
		JPanel checkPanel = new JPanel();
		JPanel atrPanel = new JPanel();
		JCheckBox[] atr = new JCheckBox[s.size()];
		JCheckBox[] check = new JCheckBox[ta.size()];
		JTextField text = new JTextField();
		JButton updateButton = new JButton("선택수정");
		JButton insertButton = new JButton("삽입");
		JButton deleteTypedButton = new JButton("입력삭제");
		JButton deleteSelectedButton = new JButton("선택삭제");
		
		Container d= getContentPane();
		setTitle("database");
		d.revalidate();
		JPanel c = new JPanel();
		// c.setLayout(new FlowLayout());
			
		
		JComboBox combo = new JComboBox(tables);
		c.add(combo, BorderLayout.NORTH);
		JComboBox combo2 = new JComboBox(dname);
		c.add(combo2, BorderLayout.NORTH);
		Font font = new Font("돋움",4,25);
		Font font2 = new Font("돋움",4,20);
		text.setFont(font);
		JTable table = new JTable(cc,header);
		table.setRowHeight(40);
		JScrollPane scrollpane = new JScrollPane(table);
		table.setPreferredSize(new Dimension(700,500));
		scrollpane.setPreferredSize(new Dimension(960,500));
		c.add(updateButton);
		updateButton.setPreferredSize(new Dimension(100,40));
		c.add(insertButton);
		insertButton.setPreferredSize(new Dimension(100,40));
		deleteSelectedButton.setPreferredSize(new Dimension(100,40));
		c.add(deleteTypedButton);
		c.add(deleteSelectedButton);
		c.add(text);
		text.setPreferredSize(new Dimension(900,40));
		deleteTypedButton.setPreferredSize(new Dimension(100,40));
		ActionListener listener = new ActionListener()
		  {
		     @Override
		     public void actionPerformed(ActionEvent e)
		     {	
		    	 
		    	 p.add(e.getActionCommand());
		     }   
		  };

		  for(int m=0;m<ta.size();m++) {
				check[m] = new JCheckBox("tuple"+(m+1)+"");
				check[m].setPreferredSize(new Dimension(90,50));
				checkPanel.add(check[m], BorderLayout.CENTER);
		}
		c.add(checkPanel);
			
		
		for(int j=0 ; j<s.size();j++) {
			atr[j] = new JCheckBox();
			atr[j].setText(s.get(j));
			atr[j].setFont(font2);
			atr[j].setSelected(true);
			atr[j].setPreferredSize(new Dimension(100,100));
			atr[j].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e){
					int j=0;
					for(int i=0;i<s.size();i++) {				
						if(atr[i].isSelected()) {
						j++;
						}					
					
					}
					for(int i=0;i<s.size();i++) {
						
						if(atr[i].isSelected()) {
							System.out.println(i);
							table.getColumn(atr[i].getText()).setWidth(960/j);
							table.getColumn(atr[i].getText()).setMinWidth(960/j);
							table.getColumn(atr[i].getText()).setMaxWidth(960/j);
							
							
						}
						else if(!atr[i].isSelected()) {
							table.getColumn(atr[i].getText()).setWidth(0);
							table.getColumn(atr[i].getText()).setMinWidth(0);
							table.getColumn(atr[i].getText()).setMaxWidth(0);
							
						}
						
					}
					c.repaint();
					d.add(c);				
			}
			});

			atrPanel.add(atr[j],  BorderLayout.SOUTH);
		}
		c.add(atrPanel);
		c.add(scrollpane,  BorderLayout.SOUTH);

		d.add(c);

		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						insert=text.getText();
						Dbinsert dbi = new Dbinsert(insert,name,login);
						Db2 db=new Db2(name,login);
						dispose();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteTypedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						insert=text.getText();
						Dbdelete dbi = new Dbdelete(insert,name,login);
						Db2 db=new Db2(name,login);
						dispose();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteSelectedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						for(int i=0; i<ta.size();i++) {
							String array[] = ta.get(i).split("&");
							if(check[i].isSelected()) {
								ssn.add(array[1]);
							}
						}
						Dbcheckd dbs = new Dbcheckd(ssn,name,login);
						c.repaint();
						d.add(c);
						Db2 db=new Db2(name,login);
						dispose();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					for(int i=0; i<ta.size();i++) {
							String array[] = ta.get(i).split("&");
							if(check[i].isSelected()) {
								ssn.add(array[1]);
							}
						}
						insert = text.getText();
						Dbupdate dbu = new Dbupdate(ssn,insert,name,login);

						Db2 db=new Db2(name,login);
						dispose();
						
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
		
	
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource(); 
				int index = cb.getSelectedIndex(); 
				try {			
					
					Db2 db = new Db2(tables[index],login);

					dispose();
				} catch (SQLException | IOException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		combo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name =="employee") {
				JComboBox cb = (JComboBox)e.getSource(); 
				int index = cb.getSelectedIndex();
				if(index==0) {
					try {
						Db2 db2= new Db2("employee",login);
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if(index==1) {
					try {
						Dbdata db1 =new Dbdata(name,dname[index],login);
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if(index==2) {
					try {
						Dbdata db1 =new Dbdata(name,dname[index],login);
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				else if(index==3) {
					try {
						Dbdata db1 =new Dbdata(name,dname[index],login);
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}

				dispose();
			}
			}
		});
		
		
		
		
		
		
		// 肄ㅻ낫諛뺤뒪�뿉 Action 由ъ뒪�꼫 �벑濡�. �꽑�깮�맂 �븘�씠�뀥�쓽 �씠誘몄� 異쒕젰
		
		setSize(1000,800);
		setVisible(true);
	}
	
	

	public static void main(String [] args) {
		
		
	}
}