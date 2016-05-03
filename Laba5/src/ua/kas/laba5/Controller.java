package ua.kas.laba5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class Controller implements Initializable{

	@FXML ImageView iv;
	String s1;
	File file = new File("Bla-bla-bla");
	String line = "";
	
	public void select(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
		fileChooser.addChoosableFileFilter(filter);
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			System.out.println(path);
			String ss = path.substring(path.indexOf("&"));
			s1 = path;
			iv.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(ss)));

			try{
				FileWriter fw = new FileWriter(file);
				
				fw.append(ss);
				fw.flush();
				fw.close();
			}catch(Exception ex){}
			
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("No Data");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null){
				System.out.println(line);
				iv.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(line)));

			}
			
//			fr.close();
			br.close();
			
		}catch(Exception ex){}
	}
}
