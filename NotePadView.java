package HW_03;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;

import HW_03.NotePadController.ReplaceListener;

//This Class is to display the view of the UI Components
public class NotePadView extends JFrame{
    private JTextPane textPane = new JTextPane();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu editMenu = new JMenu("Edit");
    private JMenuItem newFile_MenuItem = new JMenuItem("New File");
    private JMenuItem saveFile_MenuItem = new JMenuItem("Save File");
    private JMenuItem printFile_MenuItem = new JMenuItem("Print File");
    private JMenuItem openFile_MenuItem = new JMenuItem("Open File");
    private JMenuItem copy_MenuItem = new JMenuItem("Copy");
    private JMenuItem paste_MenuItem = new JMenuItem("Paste");
    private JMenuItem replace_MenuItem = new JMenuItem("Replace");
    
    private JMenu subMenu = new JMenu("Recent");
    private JMenuItem menuItem;
    
    //Code Smell3: GUI component can be placed into their corresponding methods
    private void build_FileMenu() {
    	fileMenu.add(newFile_MenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveFile_MenuItem);
        fileMenu.addSeparator();
        fileMenu.add(printFile_MenuItem);
        fileMenu.addSeparator();
        fileMenu.add(openFile_MenuItem);
        fileMenu.addSeparator();
        fileMenu.add(subMenu);
    }
    private void build_EditMenu() {
        editMenu.add(copy_MenuItem);
        editMenu.add(paste_MenuItem);
        editMenu.add(replace_MenuItem);
    }
 
    private void initialize_JMenuBar() {
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

//Constructor: Creates GUI View   
    public NotePadView() {
    	//Start building GUI
        setTitle("A Simple Notepad Tool");
        build_FileMenu();
        build_EditMenu(); 
        initialize_JMenuBar();
                
        add(new JScrollPane(textPane));
        setPreferredSize(new Dimension(600,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    
   void addNewListener(ActionListener newl) {
    	newFile_MenuItem.addActionListener(newl);
    } 
    void addSaveListener(ActionListener save) {
        saveFile_MenuItem.addActionListener(save);
    }
    void addPrintListener(ActionListener print) {
    	printFile_MenuItem.addActionListener(print);
    }
    void addOpenListener(ActionListener open) {
    	openFile_MenuItem.addActionListener(open);	
    }
    void addRecentListener(ActionListener recent) {
    	menuItem.addActionListener(recent);
    }
    void addCopyListener(ActionListener copy) {
    	copy_MenuItem.addActionListener(copy);
    }
    void addPasteListener(ActionListener paste) {
	    paste_MenuItem.addActionListener(paste);
    }
    void addReplaceListener(ActionListener replace) {
    	replace_MenuItem.addActionListener(replace);
    }

//Setters and Getters
    JTextPane getTextPane() {
    	return this.textPane; 
    }
    JMenu getSubMenu() {
    	return this.subMenu;
    }
    void updateSubMenu(String fileName) {
    	menuItem = new JMenuItem(fileName);
    	subMenu.add(menuItem);
    	subMenu.addSeparator();
    }
}