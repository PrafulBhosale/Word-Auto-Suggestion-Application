package com.nlp.bigram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordSuggestionApp extends JFrame implements KeyListener{

	WordProcessor w;
	JPanel contentPane;
	JTextArea textArea;
	JTextPane textPane;
	JList list;
	static String corpusText = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordSuggestionApp frame = new WordSuggestionApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordSuggestionApp getAppInstance(){
		return this;
	}
	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	public WordSuggestionApp() throws FileNotFoundException {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(39, 95, 572, 317);
		contentPane.add(textArea);
		textArea.addKeyListener(this);
		textArea.setFocusable(true);
		Font areaFont = textArea.getFont();
		textArea.setFont(new Font(areaFont.getName(), Font.PLAIN,25));
		
		
		//textArea.addMouseListener(this);
        //this.getDocument().addDocumentListener(this);
		
		list = new JList();
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    
	    list.setFont(new Font(areaFont.getName(), Font.PLAIN, 20));
	   //.addListSelectionListener((ListSelectionListener) this);
	    //list.setVisibleRowCount(5);
	    list.setBounds(650, 100, 130, 400);
	    //list.addMouseListener(this);
	    createMouseEvents(textArea);
	    JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setSize(150, 300);
	    scrollPane.setLocation(650, 100);
	    contentPane.add(scrollPane);
	    
	    textPane = new JTextPane();
	    textPane.setEditable(false);
	    textPane.setEnabled(false);
	    textPane.setBounds(39, 562, 600, 47);
	    textPane.setForeground(Color.red);
	    contentPane.add(textPane);
	    textPane.setFont(new Font(areaFont.getName(), Font.PLAIN, 20));
	    textPane.setText("Processing Corpus ....");
	    w = new WordProcessor();
	    textPane.setText("Corpus has been processed...");
	    
	    JButton btnNewButton = new JButton("Update Corpus");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		w.updateCorpus(WordSuggestionApp.corpusText);
	    		try {
					w.processCorpus();
					textPane.setText("Corpus is updated successfully!! ");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    });
	    btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    btnNewButton.setBounds(208, 443, 229, 47);
	    contentPane.add(btnNewButton);
	    
	    JLabel lblListOfSuggestions = new JLabel("    Suggestions");
	    lblListOfSuggestions.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    lblListOfSuggestions.setBounds(650, 46, 150, 32);
	    contentPane.add(lblListOfSuggestions);
	    
	    JLabel lblWritingPad = new JLabel("         Writing Pad");
	    lblWritingPad.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    lblWritingPad.setBounds(215, 32, 192, 47);
	    contentPane.add(lblWritingPad);
	    
	    JLabel lblLog = new JLabel("   Log");
	    lblLog.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    lblLog.setBounds(39, 517, 69, 32);
	    contentPane.add(lblLog);
	}
	
	 private void createMouseEvents(JTextArea textArea) {
		 
		// TODO Auto-generated method stub
		MouseListener m1 = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				 JList theList = (JList) mouseEvent.getSource();
			        if (mouseEvent.getClickCount() == 1) {
			          int index = theList.locationToIndex(mouseEvent.getPoint());
			          if (index >= 0) {
			            Object o = theList.getModel().getElementAt(index);
			            System.out.println("Clicked on: " + o.toString());
			           textArea.setText(textArea.getText().concat(o.toString()));
			          }
			        }
			      }


			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
		};
		list.addMouseListener(m1);
		
		MouseListener m2 = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				 
			      }


			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (textArea.getSelectedText() != null) { // See if they selected something 
			        String s = textArea.getSelectedText();
			        System.out.println("selected text =>" + s);
			        WordSuggestionApp.corpusText = s;
			        // Do work with String s
			    }
			}
		};
		textArea.addMouseListener(m2);
		
	}

	public void keyReleased(KeyEvent e) {
	        if(e.getKeyCode()== KeyEvent.VK_SPACE){
	        	String[] words = this.textArea.getText().split(" ");
	        	int l = words.length;
	        	//System.out.println(words[l-1]);
	        	
				DefaultListModel listModel = new DefaultListModel();
				List suggestions= w.processWord(words[l-1]);
				for (Iterator it = suggestions.iterator(); it.hasNext();) {
				       Map.Entry entry = (Map.Entry) it.next();
				       listModel.addElement((String)entry.getKey());
				       textPane.setText("Select desired word from suggestions");
				      // System.out.println((String)entry.getKey());
				} 
				
				
				list.setModel(listModel);
	        	
	        	if(list.getModel().getSize() == 0){
	        		textPane.setText("No Suggestion found in corpus.");
	        	}
	        }
	       // System.out.println(this.textArea.getText());
	    }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
