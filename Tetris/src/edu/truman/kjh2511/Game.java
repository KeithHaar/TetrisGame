package edu.truman.kjh2511;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game {
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel shapeStart = new JLabel();
		GridPrinter gp = new GridPrinter();
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == e.VK_UP){
					System.out.println("ROTATE!");
					gp.rotate();
				}
				if (e.getKeyCode() == e.VK_DOWN){
					System.out.println("FALL!");
					gp.fall();
				}
				if (e.getKeyCode() == e.VK_RIGHT){
					System.out.println("Right!");
					gp.moveRight();
				}
				if (e.getKeyCode() == e.VK_LEFT){
					System.out.println("Left!");
					gp.moveLeft();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		});
		/*frame.add(new Timer(750, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				gp.fall();
			}
		}));*/
		panel.setLayout(new BorderLayout());;
		panel.add(gp, BorderLayout.CENTER);
		panel.add(shapeStart, BorderLayout.NORTH);
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setSize(500, 1000);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
}
