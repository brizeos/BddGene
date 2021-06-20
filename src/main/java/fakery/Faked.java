package fakery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.github.javafaker.Faker;

import MavenBdd.Generator.App;
import model.Column;
import vue.components.ComboSelecter;
import vue.components.Lab;

@SuppressWarnings("serial")
public class Faked extends JPanel{
	
	private Column parentCol;
	private ComboSelecter ftType;
	private ComboSelecter ftSec;
	private static Faker f= new Faker();
	private String data;
	private JPanel fakeOption;
	
	/**
	 * 
	 * @param column Reference to Column parent.
	 * @throws IOException 
	 */
	public Faked(Column column) throws IOException {
		this.parentCol = column;
		this.data = "";
		setBackground(App.MainColor);
		if( this.parentCol.getType().matches("(?i)TINYINT|SMALLINT|MEDIUMINT|INT|BIGINT") ) {
			setContent("lstNumber.json");
		}else if(this.parentCol.getType().matches("(?i)FLOAT|DOUBLE|DECIMAL|REAL") ){
			setContent("lstDouble.json");
		}else if(this.parentCol.getType().matches("(?i)TINYTEXT|MEDIUMTEXT|TEXT|LONGTEXT|TINYBLOB|MEDIUMBLOB|LONGBLOB|VARCHAR|BLOB") ){
			setContent("lstText.json");
		}else if(this.parentCol.getType().matches("(?i)YEAR") ){
			setContent("lstDateTime.json");
		}else if(this.parentCol.getType().matches("(?i)DATE") ){
			setContent("lstDateTime.json");
		}else if(this.parentCol.getType().matches("(?i)DATETIME") ){
			setContent("lstDateTime.json");
		}else if(this.parentCol.getType().matches("(?i)TIME") ){
			setContent("lstDateTime.json");
		}else if(this.parentCol.getType().matches("(?i)TIMESTAMP") ){
			setContent("lstDateTime.json");
		}else {
			setContent("error.json");
		}
		
	}

	@SuppressWarnings("unchecked")
	private void setContent(String string) throws FileNotFoundException {
//		URL url = getClass().getResource("/ressources/"+string);
//		InputStream is = new FileInputStream(url.getPath());
		InputStream is = getClass().getResourceAsStream("/ressources/"+string);
		JsonReader jr = Json.createReader(is);
		JsonObject empObj = jr.readObject();
        jr.close();
        
        
        /**
         * First comboBox
         */
        JPanel type = new JPanel();
        type.setBackground(App.MainColor);
		type.setLayout(new BoxLayout(type, BoxLayout.LINE_AXIS));
		Lab labType = new Lab("Content : ", false);
		labType.setBounds(10, 10, 100, 40);
		ArrayList<String> lsStr1 = new ArrayList<String>();
		lsStr1.addAll( (Collection<? extends String>) empObj.get("lst") );
		this.ftType = new ComboSelecter(lsStr1);
		this.ftType.setSelectedIndex(0);
        
		type.add(labType);
		type.add(this.ftType);
		add(type);
        
		
		/**
		 * Second ComboBox
		 */
		JPanel sec = new JPanel();
		sec.setBackground(App.MainColor);
		HashMap<String, ArrayList<String>> lsStr2 = new HashMap<String, ArrayList<String>>();
		lsStr2.putAll((Map<? extends String, ? extends ArrayList<String>>) empObj.get("data"));
		Lab labSec = new Lab("Sec : ", false);
		this.ftSec = new ComboSelecter(lsStr2, this.ftType.getSelectedItem().toString());
		this.ftSec.setSelectedIndex(0);
		
		sec.add(labSec);
		sec.add(this.ftSec);
		add(sec);
		
		
		/**
		 * Options
		 */
		this.fakeOption = newOption();    
		this.fakeOption.setBackground(App.MainColor);
		add(this.fakeOption);	
		
		
		
		this.ftType.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getFtSec().ReloadComboSec(getFtType().getSelectedItem().toString());
				getFtSec().setSelectedIndex(0);
			}
		});
		
		this.ftSec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				getParentCol().setData( getFtSec().getSelectedItem().toString() );
				resetFakeOption( );
			}
		});
		
		
	}


	/***
	 * Launch creation of a panel containing options if needed for current faked selection.
	 * @return Jpanel with settings for this.parentColumn
	 */
	private JPanel newOption() {
		JPanel pane = null;
		
		
		if( this.parentCol.getType().matches("(?i)TINYINT|SMALLINT|MEDIUMINT|INT|BIGINT") ) {
			pane = new FakedNumberOption(this);
			
		}else if( this.parentCol.getType().matches("(?i)FLOAT|DOUBLE|DECIMAL|REAL") ){
			pane = new FakedDoubleOption(this);
			
		}else if(this.parentCol.getType().matches("(?i)TINYTEXT|MEDIUMTEXT|TEXT|LONGTEXT|TINYBLOB|MEDIUMBLOB|LONGBLOB|VARCHAR|BLOB") ){
			pane = new FakedTextOption(this);
			
		}else if(this.parentCol.getType().matches("(?i)YEAR") ){
			pane = new FakedDateTimeOption(this);
			
		}else if(this.parentCol.getType().matches("(?i)DATE") ){
			pane = new FakedDateTimeOption(this);
			
		}else if(this.parentCol.getType().matches("(?i)DATETIME") ){
			pane = new FakedDateTimeOption(this);
			
		}else if(this.parentCol.getType().matches("(?i)TIME") ){
			pane = new FakedDateTimeOption(this);
			
		}else if(this.parentCol.getType().matches("(?i)TIMESTAMP") ){
			pane = new FakedDateTimeOption(this);
			
		}else {
			pane = new FakedTextOption(this);
			
		}
		pane.setBackground(App.MainColor);
		return pane;
	}
	
	
	private void resetFakeOption() {
		((FakeModel) getFakeOption()).resetAll();
		
	}
	
	
	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/
	public ComboSelecter getFtType() {
		return ftType;
	}

	public void setFtType(ComboSelecter ftType) {
		this.ftType = ftType;
	}

	public ComboSelecter getFtSec() {
		return ftSec;
	}

	public void setFtSec(ComboSelecter ftSec) {
		this.ftSec = ftSec;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		this.parentCol.setData(data);
	}

	public JPanel getFakeOption() {
		return fakeOption;
	}

	public void setFakeOption(JPanel fakeOption) {
		this.fakeOption = fakeOption;
	}

	public static Faker getF() {
		return f;
	}

	public static void setF(Faker f) {
		Faked.f = f;
	}
	
	public void setparentCol(Column p) {
		this.parentCol = p;
	}
	
	public Column getParentCol() {
		return parentCol;
	}

	
}
