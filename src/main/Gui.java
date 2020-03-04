package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//Simple GUI using Swing.
public class Gui {
    static JFileChooser fileChooser;
    public static void main(String args[]){


        JFrame frame = new JFrame("Contact Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BoxLayout boxLayout= new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BusinessCardReader reader = new BusinessCardReader(selectedFile.getAbsolutePath());
                ContactInfo contactInfo= reader.readFromCard();
                JLabel name= new JLabel("Name: "+ contactInfo.getName());
                JLabel email= new JLabel("Email: "+ contactInfo.getEmail());
                JLabel phone= new JLabel("Phone: "+contactInfo.getPhone());

                frame.add(name);
                frame.add(email);
                frame.add(phone);
            }catch(IOException e){
                JLabel errorLabel = new JLabel("Unable to open file");
                errorLabel.setVerticalAlignment(JLabel.BOTTOM);
                frame.add(errorLabel);
            }
        }else{
            JLabel errorLabel = new JLabel("Unable to open file");
            errorLabel.setVerticalAlignment(JLabel.BOTTOM);
        }

    }

}