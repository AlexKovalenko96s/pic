package ua.kn.laba5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ImageViewer {
	
	public static void main(String[] args) {
		JFrame frame = new ImageViewerFrame();
		((ImageViewerFrame) frame).try_pic();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
}

class ImageViewerFrame extends JFrame{
	
	static public String string_file;
	static File file = new File("Bla-bla-bla");
	static String line = "";
	
	public void try_pic(){
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null){
				System.out.println(line);
				panel.setImage(line);
			}
//			fr.close();
			br.close();
			
		}catch(Exception ex){}
	}
	
	
	public ImageViewerFrame() {
		
		// Выделение области мышкой
		

		setTitle("ImageViewer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		label = new JLabel();
		add(label);
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
	            chooser.setCurrentDirectory(new File("."));
	            int result = chooser.showOpenDialog(null);
	 
	            if (result == JFileChooser.APPROVE_OPTION) {
	                panel.setImage(chooser.getSelectedFile().getPath());
	                String s = chooser.getSelectedFile().getPath();
	                string_file = s.substring(s.indexOf("&"));
	                try{
	    				FileWriter fw = new FileWriter(file);
	    				
	    				fw.append(string_file);
	    				fw.flush();
	    				fw.close();
	    			}catch(Exception ex){}
	            }
	 
	        }
		});

		panel = new ImagePanel();
		this.add(panel);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
	}

	private JLabel label;
	// добавление экземпляра ImagePanel
	private ImagePanel panel;
	private JFileChooser chooser;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;

}