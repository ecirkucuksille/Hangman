/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.sdu.lecture.hangman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author ecirk
 */
public class HangFrame extends javax.swing.JFrame {

    /*
        A String array containing the letters of the alphabet 
        is defined to place the alphabet on the buttons 
    */
    String[] alphabet = {"A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", 
                         "H", "I", "İ", "J", "K", "L", "M", "N", "O",
                         "Ö", "P", "R", "S", "Ş", "T", "U", "U", "V",
                         "Y", "Z"};
    
    /*
        Creating a 29-element JButton array for the buttons where 
        the letters of the Turkish alphabet will be placed
    */
    JButton[] buttons = new JButton[29];
    
    /*
        Since the game works with words with a maximum of 7 letters, 
        a JLabel array with 7 elements is created
    */
    JLabel[] labels = new JLabel[7];
    
    /*
        Defining a String variable named word to store the randomly 
        selected word
    */
    String word = ""; 
    
    /*
        A List object named remainWord is defined to store the randomly 
        selected word as letters and to check whether all the letters 
        in the word are found.
    */
    List<Character> remainWord= new ArrayList<>();
    
    /*
        A variable named counter is defined to store the number of 
        wrong guesses of the user.
    */
    int counter = 0;
    
    /*
        A variable of type JButton named selectedButton is defined to 
        store the button selected from buttons array.
    */
    JButton selectedButton = null;
    
    
    /*
        A variable is defined that determines how many mistakes the user 
        makes before the game ends.
    */
    int wrongRight = 4;
    
    
    /**
     * Creates new form HangFrame
     */
    public HangFrame() {
        initComponents();
        
        //initializeButtons method is called.
        initializeButtons();
        
        //initializeLabel method is called.
        initializeLabel();
                
        /*
            When the user guesses wrong, the path of the gif to be shown is determined
        */
        String path = System.getProperty("user.dir") + "/resource/dog.gif";
        Icon imgIcon = new ImageIcon(path);
  
        //The image is binded to the object named lbl_dog and made invisible.
        lbl_dog.setIcon(imgIcon);
        lbl_dog.setVisible(false);
    }

    // The process of drawing a hanging man graph
    @Override    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Graphics2D g2 = (Graphics2D) g;
        if(counter == 1)
            g2.draw(new Line2D.Float(700, 450, 860, 450));
        else if(counter == 2){
            g2.draw(new Line2D.Float(700, 450, 860, 450));
            g2.draw(new Line2D.Float(780, 300, 780, 450));
        }
        else if(counter == 3){
            g2.draw(new Line2D.Float(700, 450, 860, 450));
            g2.draw(new Line2D.Float(780, 300, 780, 450));
            g2.draw(new Line2D.Float(720, 300, 780, 300));
            g2.draw(new Line2D.Float(720, 300, 720, 330));
        }
        else if(counter == 4){
            g2.draw(new Line2D.Float(700, 450, 860, 450));
            g2.draw(new Line2D.Float(780, 300, 780, 450));
            g2.draw(new Line2D.Float(720, 300, 780, 300));
            g2.draw(new Line2D.Float(720, 300, 720, 330));
            g2.drawOval(710, 330, 20, 20);
            g2.draw(new Line2D.Float(720, 350, 720, 400));
            g2.draw(new Line2D.Float(720, 370, 700, 385));
            g2.draw(new Line2D.Float(720, 370, 740, 385));
        
            g2.draw(new Line2D.Float(720, 400, 700, 420));
            g2.draw(new Line2D.Float(720, 400, 740, 420));
        }
    }

    /*
    1. Words are read from the words.txt file containing the words to be 
       used in the game and transferred to a List object named word.
    2. One of these words is randomly selected and transferred to the 
       word variable.
    3. Each letter in the word variable is passed to an object named remainWord.
    */
    public void readFileWord(){
        try {
            Random rnd = new Random();
            String path = System.getProperty("user.dir") + "/resource/words.txt";
            File file = new File(path);
            List<String> words= FileUtils.readLines(file, "UTF-8");
            word = words.get(rnd.nextInt(words.size()));
            remainWord = word.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
        } catch (IOException ex) {
            Logger.getLogger(HangFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    
    
    public void buttonPressed(ActionEvent e){
        selectedButton = ((JButton)e.getSource());
    }
    
    /*
        1. The Layout of the JPanel object named pnl_buttons is 
        set as a GridLayout with 4 rows and 9 columns.
        2. for every single button: 
            a. The letters of the alphabet are written as text in order.
            b. Made passive
            c. Adding actionPerformed event.
            d. The created button is added to the pnl_buttons panel.
    */
    private void initializeButtons() {
        pnl_buttons.setLayout(new GridLayout(4,9));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(alphabet[i]);
            buttons[i].setEnabled(false);
            buttons[i].addActionListener(e -> buttonPressed(e));
            pnl_buttons.add(buttons[i]);
        }
        
    }
    
    // The method defined for the tasks to be performed at the end of the game.
    public void endGame(){
        counter = 0;
        lbl_dog.setVisible(false);
        remainWord.clear();
        for (int i = 0; i < word.length(); i++)
            labels[i].setText(String.valueOf(word.charAt(i)));
        
        for(JButton btn : buttons)
            btn.setEnabled(false);
        
    }
    
    // Method to check whether the game has been won.
    private boolean winControl(){
        for(char ch : remainWord)
            if(ch != 'X')
                return false;
        return true;
    }
    
    
    /*
        1. The Layout of the JPanel object named pnl_labels is 
        set as a GridLayout with 1 row and 7 columns.  In this panel, 
        the space between columns is set to 20 and the space 
        between rows is set to 1.
        2. for every single label:
           a. JLabel object are created and ____ is written in the text.
           b. At the same time, the sequence number is set to the name 
              property to determine the order of each label.
           c. made invisible.
           d. Adding actionPerformed event.
        
    */
    private void initializeLabel() {
          pnl_labels.setLayout(new GridLayout(1, 7, 20, 1));
          for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("____");
            labels[i].setName(String.valueOf(i));
            labels[i].setVisible(false);
            labels[i].addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                  // The selected label is being determined.
                  JLabel lbl = (JLabel)e.getSource();
                  // The name property of the selected label is retrieved, 
                  // which determines the order of the label.
                  int index = Integer.parseInt(lbl.getName());
                  
                  // It is checked whether the character of the word in 
                  // the selected order and the selected letter are the same.
                  if(String.valueOf(word.charAt(index)).equals(selectedButton.getText())){
                        // The selected character is set to the related label.
                        lbl.setText(selectedButton.getText()); 
                        // The label containing the animated gif is being made visible.
                        lbl_dog.setVisible(false);
                        // The corresponding index in the remainWord variable, 
                        // which is used to check whether all letters are found 
                        // correctly, is replaced with the character 'X'.
                        remainWord.set(index, 'X');
                        // If there is no relevant character left in the 
                        // searched word, the button for that letter is made passive.
                        if(!remainWord.contains(selectedButton.getText().charAt(0)))
                            selectedButton.setEnabled(false);
                        // Checking whether the game is over.
                        if(winControl()){
                            endGame();
                            counter = 0;
                            repaint();
                            JOptionPane.showMessageDialog(null, "Tebrikler Kazandınız");
                        }
                  }
                  // If the selected character is not in the searched word, 
                  // necessary actions are taken.
                  else{
                      lbl_dog.setVisible(true);
                      selectedButton.setEnabled(false);
                      counter++;
                      repaint();
                      if(counter == wrongRight)
                          endGame();
                  }
                      
                           
                }

            });
            pnl_labels.add(labels[i]);
        }
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_buttons = new javax.swing.JPanel();
        btn_start = new javax.swing.JButton();
        pnl_labels = new javax.swing.JPanel();
        lbl_dog = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnl_buttons.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnl_buttonsLayout = new javax.swing.GroupLayout(pnl_buttons);
        pnl_buttons.setLayout(pnl_buttonsLayout);
        pnl_buttonsLayout.setHorizontalGroup(
            pnl_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );
        pnl_buttonsLayout.setVerticalGroup(
            pnl_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        btn_start.setText("Başla");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });

        pnl_labels.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pnl_labelsLayout = new javax.swing.GroupLayout(pnl_labels);
        pnl_labels.setLayout(pnl_labelsLayout);
        pnl_labelsLayout.setHorizontalGroup(
            pnl_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        pnl_labelsLayout.setVerticalGroup(
            pnl_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnl_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl_labels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(btn_start))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(lbl_dog)))
                .addContainerGap(511, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lbl_dog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                .addComponent(pnl_labels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(pnl_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /*
        1. Calling the readFileWord method.
        2. All labels are made invisible.
        3. The labels are made visible as long as the selected word length and 
           their texts are displayed as "____" is being made.
        4. All buttons containing characters are made active.

    */
    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        readFileWord();
        for(JLabel lbl : labels)
            lbl.setVisible(false);
        
        for (int i = 0; i < word.length(); i++){
            labels[i].setVisible(true);
            labels[i].setText("____");
        }
        
        for(JButton btn : buttons)
            btn.setEnabled(true);
        
    }//GEN-LAST:event_btn_startActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HangFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_start;
    private javax.swing.JLabel lbl_dog;
    private javax.swing.JPanel pnl_buttons;
    private javax.swing.JPanel pnl_labels;
    // End of variables declaration//GEN-END:variables



  
}
