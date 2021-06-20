package MavenBdd.Generator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DaoAccess;
import model.Database;
import vue.Connection;
import vue.components.Btn;


/***
 * 
 * @author Brizeos
 * @version 0.01
 * {@link} https://www.linkedin.com/in/jonathan-pinho-44a9b914b/
 *
 */
@SuppressWarnings("serial")
public class App extends JFrame
{
	public static final Color MainColor = Color.black;
	private int width = 950, height = 640;
	private static DaoAccess dao = null;
	private static Database db = new Database();
	private static App frame; 
	private JPanel content;
	private static Connection conn = new Connection();



	public App(String title) throws HeadlessException {
		super(title);

		this.width = 950;
		this.height = 640;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setForeground(Color.WHITE);
		setResizable(false);
		setLayout(new BorderLayout());
		setBackground(new Color(25,25,25));
		setBounds(0,0,width,height);
		setUndecorated(true);
		setLocationRelativeTo(null);


		/**
		 * Mouvement de la fenetre
		 */
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});


		/**
		 * Header with exit button
		 */

//		ImageIcon original = new ImageIcon( getClass().getResource("exit.png") );
//		Image scale = scaleImage(original.getImage(), 25, 25);
//		ImageIcon img = new ImageIcon(scale);
		Btn exit = new Btn("Quiter");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});


		JPanel head = new JPanel();
		head.setLayout(new FlowLayout(FlowLayout.RIGHT));
		head.setBackground(new Color(25,25,25));
		head.add(exit);


		this.content = conn;

		JPanel motherPane = new JPanel();
		motherPane.setLayout(new BoxLayout(motherPane, BoxLayout.PAGE_AXIS));
		motherPane.setBackground(new Color(25,25,25));
		motherPane.add(head);
		motherPane.add(this.content);


		setContentPane(motherPane);
		setVisible(true);

	}



	public JPanel getContent() {
		return content;
	}


	public void setContent(JPanel content) {
		this.content = content;
	}



	private static Point point = new Point();

	public static void main( String[] args )
	{

		frame = new App("BddGenerator");    	
		frame.repaint();
		frame.revalidate();

	}


	/********************************
	 *        GETTERS/SETTERS		*
	 ********************************/


	/**
	 * @return Name of the Db actually charged.
	 */
	public static String getDBName() {
		return conn.getCs().getModel().getSelectedItem().toString();
	}

	public static Connection getConn() {
		return conn;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public static DaoAccess getDao() {
		return dao;
	}



	public static void setDao(DaoAccess dao) {
		App.dao = dao;
	}



	public static Database getDb() {
		return db;
	}



	public static void setDb(Database db) {
		App.db = db;
	}



	public static App getFrame() {
		return frame;
	}



	public static void setFrame(App frame) {
		App.frame = frame;
	}



	public static Point getPoint() {
		return point;
	}



	public static void setPoint(Point point) {
		App.point = point;
	}



	public static void setConn(Connection conn) {
		App.conn = conn;
	}



	private Image scaleImage(Image image, int w, int h) {

		Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

		return scaled;
	}





}
