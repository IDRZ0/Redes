package sockets;

//Nombre completo: Ignacio del Rio Zenteno
//Codigo: 46036

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	public static void main(String[] args) {
		ClientFrame frame = new ClientFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class ClientFrame extends JFrame {
	public ClientFrame() {
		setBounds(300, 300, 500, 500);
		setLayout(null);
		setTitle("Cliente");
		JLabel text = new JLabel(
				"Ingrese una URL (Ej.: http://www.google.com):");
		text.setBounds(25, 15, 400, 25);
		add(text);
		field = new JTextField();
		field.setBounds(25, 50, 200, 30);
		add(field);
		button = new JButton("Enviar");
		button.setBounds(350, 10, 100, 25);
		SendText event0 = new SendText();
		button.addActionListener(event0);
		add(button);
		textArea = new JTextArea();
		scroll = new JScrollPane(textArea);
		scroll.setBounds(25, 100, 400, 300);
		add(scroll);
		setVisible(true);
	}

	private class SendText implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				Socket socket = new Socket("127.0.0.1", 8888);
				DataOutputStream output = new DataOutputStream(
						socket.getOutputStream());
				output.writeUTF(field.getText());
				String message = field.getText();
				textArea.append("\n" + "DNS destino: " + message);
				field.setText("");
				output.close();
				socket.close();
			} catch (UnknownHostException e0) {
				e0.printStackTrace();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}

	private JTextField field;
	private JButton button;
	private JTextArea textArea;
	private JScrollPane scroll;
}