package cap.sim.gui;

import static org.junit.Assert.assertNotEquals;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.SystemColor;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import cap.model.entity.SoftwareArchitecture;
import cap.model.handler.HWML_XMLParser;
import cap.model.handler.SPML_XMLParser;
import cap.model.handler.XMLParser;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.generator.ConfigurationGenerator;
import cap.sim.generator.ProjectGenerator;
import cap.sim.generator.ScriptController;
import cap.sim.utility.FolderUtil;
import cap.sim.utility.GUIUtilities;

import javax.swing.JLabel;

public class CAPSIMMainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> filterls;
	private JTextField txtOutFile;
	private JTextField txtSAMLfile;
	
	public static String capsPath;
	public static String cubPath;
	private JTextField txtProjectName;
	private JTextField txtHWMLFile;
	private JTextField txtSPMLFile;
	
	private static String spml;
	private static String hwml;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GUIUtilities.GetSubstanceNebulaBrickWallLookAndFeel();
		//GetSubstanceBusinessLookAndFeel();
		//GetSubstanceMistAquaLookAndFeel();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CAPSIMMainUI frame = new CAPSIMMainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CAPSIMMainUI() {

		filterls = new ArrayList<String>();
		
		
		setTitle("CAPS Code Generator Framwork");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				setVisible(false);
				System.exit(0);
			}
		});
		setBackground(SystemColor.desktop);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 419);
		// if(user.getUserLevel()==2)
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);

			JMenuItem mntmChangePassword = new JMenuItem("Exit");
			mntmChangePassword.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					System.exit(0);
				}
			});
			mnFile.add(mntmChangePassword);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnConvertCapsTo = new JButton("Convert CAPS Models To CUPCARBON Project");
		btnConvertCapsTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CAPSIMMainUI.cubPath+="\\"+txtProjectName.getText();
				FolderUtil.checkPath(cubPath);
				
				XMLParser handler=new XMLParser(CAPSIMMainUI.capsPath);
				handler.loadDataFromXML();
				SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
				
				SPML_XMLParser spml=new SPML_XMLParser(CAPSIMMainUI.spml);
				spml.loadDataFromXML();
				
				HWML_XMLParser hwml=new HWML_XMLParser(CAPSIMMainUI.hwml);
				hwml.loadDataFromXML();
				
//				assertNotEquals("Elements are empty", 0, SoftwareArchitecture.getSoftwareArchitechure().getElements().size());
				
				ModelAnalyzer.ComponentId=0;
				ModelAnalyzer.SensorId=0;
				ModelAnalyzer.EventId=0;
//				// Analyzing Model >>> 
				ModelAnalyzer.analyzeModel(sa);	
//				// Create SenScript code files
				new ScriptController().createScript("");
//				// create Sensor Radios && Nodes(sensors) Files
				ConfigurationGenerator.creatConfigration();
				
				//add other folder
				FolderUtil.checkPath(cubPath+"\\gps");
				FolderUtil.checkPath(cubPath+"\\logs");
				FolderUtil.checkPath(cubPath+"\\network");
				FolderUtil.checkPath(cubPath+"\\results");
				FolderUtil.checkPath(cubPath+"\\tmp");
				FolderUtil.checkPath(cubPath+"\\xbee");
				
				ProjectGenerator.createDefaultSimulationParams(cubPath);
				// add simulationParams.cfg file under config folder
				// add .cup file under main folder
				ProjectGenerator.createProjectFile(cubPath, txtProjectName.getText());
				System.out.println("END");
				
			}
		});
		btnConvertCapsTo.setBounds(13, 287, 648, 42);
		panel.add(btnConvertCapsTo);
		
		JButton btnCupcarbon = new JButton("Save To");
		btnCupcarbon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				fc.setDialogTitle("Select Destination Folder");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				
				int returnVal = fc.showOpenDialog(CAPSIMMainUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//File file2=fc.getCurrentDirectory();
					// This is where a real application would open the file.
					txtOutFile.setText(file.getPath());
				} else {
					txtOutFile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				};
				CAPSIMMainUI.cubPath=txtOutFile.getText();
			}
		});
		//btnCupcarbon.setToolTipText("Load Element using filter");
		btnCupcarbon.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCupcarbon.setBounds(541, 178, 120, 42);
		panel.add(btnCupcarbon);
		
		txtOutFile = new JTextField();
		txtOutFile.setToolTipText("Project Name");
		txtOutFile.setEditable(false);
		txtOutFile.setColumns(10);
		txtOutFile.setBounds(12, 178, 517, 42);
		panel.add(txtOutFile);
		
		txtSAMLfile = new JTextField();
		txtSAMLfile.setToolTipText("File Path");
		txtSAMLfile.setEditable(false);
		txtSAMLfile.setColumns(10);
		txtSAMLfile.setBounds(12, 13, 517, 42);
		panel.add(txtSAMLfile);
		
		JButton btnCaps = new JButton("SAML");
		btnCaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				int returnVal = fc.showOpenDialog(CAPSIMMainUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					txtSAMLfile.setText(file.getPath());
				} else {
					txtSAMLfile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				}
				CAPSIMMainUI.capsPath=txtSAMLfile.getText();
			}
		});
		btnCaps.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCaps.setBounds(541, 13, 120, 42);
		panel.add(btnCaps);
		
		txtProjectName = new JTextField();
		txtProjectName.setToolTipText("");
		txtProjectName.setColumns(10);
		txtProjectName.setBounds(144, 233, 517, 42);
		panel.add(txtProjectName);
		
		JLabel lblProjectName = new JLabel("Project Name :");
		lblProjectName.setBounds(13, 233, 119, 41);
		panel.add(lblProjectName);
		
		txtHWMLFile = new JTextField();
		txtHWMLFile.setToolTipText("File Path");
		txtHWMLFile.setEditable(false);
		txtHWMLFile.setColumns(10);
		txtHWMLFile.setBounds(11, 68, 517, 42);
		panel.add(txtHWMLFile);
		
		JButton btnHm = new JButton("HWML");
		btnHm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				int returnVal = fc.showOpenDialog(CAPSIMMainUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					txtHWMLFile.setText(file.getPath());
				} else {
					txtHWMLFile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				}
				CAPSIMMainUI.hwml=txtHWMLFile.getText();
			}
		});
		btnHm.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHm.setBounds(540, 68, 120, 42);
		panel.add(btnHm);
		
		txtSPMLFile = new JTextField();
		txtSPMLFile.setToolTipText("File Path");
		txtSPMLFile.setEditable(false);
		txtSPMLFile.setColumns(10);
		txtSPMLFile.setBounds(11, 123, 517, 42);
		panel.add(txtSPMLFile);
		
		JButton btnSpml = new JButton("SPML");
		btnSpml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				int returnVal = fc.showOpenDialog(CAPSIMMainUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					txtSPMLFile.setText(file.getPath());
				} else {
					txtSPMLFile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				}
				CAPSIMMainUI.spml=txtSPMLFile.getText();
			}
		});
		btnSpml.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSpml.setBounds(540, 123, 120, 42);
		panel.add(btnSpml);

		
	}

	protected void checkSecondFilter(ArrayList<String> filter) {
	
		
	}

	protected ArrayList<String> getCheckedRows() {
		// TODO Auto-generated method stub
		ArrayList<String> ls=new ArrayList<String>();
//		for (int i = 0; i < table.getRowCount(); i++) {
//			try
//			{
//		     Boolean isChecked = Boolean.valueOf(table.getValueAt(i, 2).toString());
//
//		     if (isChecked) {
//		        //get the values of the columns you need.
//		    	 ls.add(table.getValueAt(i, 1).toString());
//		    } else {
//		        System.out.printf("Row %s is not checked \n", i);
//		    }
//		     }catch(Exception ex){
//		    	 System.out.println("Value is "+table.getValueAt(i, 2));
//		     }
//		}
//		System.out.println(ls.size());
		return ls;
	}



	public ArrayList<String> getFilterls() {
		return filterls;
	}

	public void setFilterls(ArrayList<String> filterls) {
		this.filterls = filterls;
	}
}
