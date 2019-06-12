package test;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;

//Initializing File Menu Items, Edit Menu Items, and Replace Menu Items
public class NotePadController{
	private NotePadView view;
	private NotePadModel model;
	
	public NotePadController(NotePadView _view, NotePadModel _model) {
		this.view = _view;
		this.model = _model;
				
		//add action events to the view
	    view.addNewListener(new NewListener());
	    view.addSaveListener(new SaveListener());
	    view.addPrintListener(new PrintListener());
	    view.addOpenListener(new OpenListener());
	    view.addRecentListener(new RecentListener());
	    view.addCopyListener(new CopyListener());
	    view.addPasteListener(new PasteListener());
	    view.addReplaceListener(new ReplaceListener());
	}
	
    void updateSubMenu(HashMap<String, String> recentFiles) {
    	JMenu subMenu = view.getSubMenu();
    	subMenu.removeAll();
    	
    	//add JMenuItems to recen file JMenu
		if(recentFiles != null) {
			for(String recentFileName : recentFiles.keySet()) {
				JMenuItem recentFileMenuItem = new JMenuItem(recentFileName);
				recentFileMenuItem.addActionListener(new OpenWithoutDialogListener(recentFileName));
				subMenu.add(recentFileMenuItem);
			}
		}

    }
	
	
	class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.getTextPane().setText("");
		}
	}
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			File fileToWrite = null;
	        JFileChooser fc = new JFileChooser();
	        int returnVal = fc.showSaveDialog(null);
	        if (returnVal == JFileChooser.APPROVE_OPTION)
	            fileToWrite = fc.getSelectedFile();
	        try {
	            PrintWriter out = new PrintWriter(new FileWriter(fileToWrite));
	            out.println(view.getTextPane().getText());
	            JOptionPane.showMessageDialog(null, "File is saved successfully...");	                   
	            out.close();
	            
	            
	        } catch (IOException ex) {}
		}
	}
	class PrintListener implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
	    	 try{
	             PrinterJob pjob = PrinterJob.getPrinterJob();
	             pjob.setJobName("Sample Command Pattern");
	             pjob.setCopies(1);
	             pjob.setPrintable(new Printable() {
	                 public int print(Graphics pg, PageFormat pf, int pageNum) {
	                     if (pageNum>0)
	                         return Printable.NO_SUCH_PAGE;
	                     pg.drawString(view.getTextPane().getText(), 500, 500);
	                     return Printable.PAGE_EXISTS;
	                 }
	             });

	             if (pjob.printDialog() == false)
	                 return;
	             pjob.print();
	         } catch (PrinterException pe) {
	             JOptionPane.showMessageDialog(null,
	                     "Printer error" + pe, "Printing error",
	                     JOptionPane.ERROR_MESSAGE);
	         }
		}
	}
	class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			File fileToOpen = null;
			JFileChooser fc = new JFileChooser();
	        int returnVal = fc.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	           fileToOpen = fc.getSelectedFile();
	           String filename = fileToOpen.getAbsolutePath();
	           try {
	        	   FileReader reader = new FileReader(filename);
	        	   BufferedReader bread = new BufferedReader(reader);
	        	   view.getTextPane().read(bread, null);
		           //update model
		           model.updateRecentDB(fileToOpen.getName(),fileToOpen.getAbsolutePath());
		            
		           HashMap<String, String> recentFiles = model.getRecentFiles();
		            
		           //update view
		           updateSubMenu(recentFiles);
	           } catch (IOException ex) {
	           }
	        }
		}
	}
	
	class OpenWithoutDialogListener implements ActionListener {
		
		private String fileName;
		
		public OpenWithoutDialogListener(String fileName) {
			this.fileName = fileName;
		}
		
		public void actionPerformed(ActionEvent e) {
			File fileToOpen = null;
	        JFileChooser fileChooser = new JFileChooser();
	        String filePath = fileChooser.getCurrentDirectory() + "\\" + fileName;
			fileToOpen = new File(filePath);

           String filename = fileToOpen.getAbsolutePath();
           try {
        	   FileReader reader = new FileReader(filename);
        	   BufferedReader bread = new BufferedReader(reader);
        	   view.getTextPane().read(bread, null);
	            
        	   //update model
	           model.updateRecentDB(fileToOpen.getName(),fileToOpen.getAbsolutePath());
	            
	           HashMap<String, String> recentFiles = model.getRecentFiles();
	            
	           //update view
	           updateSubMenu(recentFiles);
	           
           } catch (IOException ex) {
           }
		}
	}
	
	class RecentListener implements ActionListener {
		HashMap<String,String> hs = model.getRecentFiles(); //contains filepaths
		JMenu subMenu = view.getSubMenu(); 
		private JMenuItem menuitem;
		private String filePath;
		
		public void actionPerformed(ActionEvent e) {
			
			//traverse through each MenuItem
			for(int i=0 ; i<subMenu.getItemCount(); i++) {
				menuitem = subMenu.getItem(i);
				
				for(String key: hs.keySet()) {
					if(menuitem.getName().equals(key)) {
						filePath = hs.get(key);
			           try { //this opens the file
			        	   FileReader reader = new FileReader(filePath);
			        	   BufferedReader bread = new BufferedReader(reader);
			        	   view.getTextPane().read(bread, null);
			           } catch (IOException ex) {}
					}
				}
			}
			
		}
	}
	class CopyListener implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
	    	view.getTextPane().copy();
		}
	}
	class PasteListener implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
			JTextPane textPane = view.getTextPane();
	    	StyledDocument doc = textPane.getStyledDocument();
	        Position position = doc.getEndPosition();
	        System.out.println("offset"+position.getOffset());
	        textPane.paste();
		}
	}
	
	class ReplaceListener implements ActionListener{
		JTextPane textPane = view.getTextPane();
		
		public void actionPerformed(ActionEvent e) {
			String find = view.getTextPane().getSelectedText();
			String replace = "";
			
				if(!find.isEmpty()) {
					replace = JOptionPane.showInputDialog("Replace or insert with");
					int n = JOptionPane.showConfirmDialog(textPane,"Are you sure you want to replace?",
							"View carefully",
                            JOptionPane.YES_NO_OPTION);					
					
					if (n == JOptionPane.YES_OPTION) {
						textPane.replaceSelection(replace);
					} else if (n == JOptionPane.NO_OPTION) {
						return;
					} 
				}	
		}
	}
}