package sockets;

//Nombre completo: Ignacio del Rio Zenteno
//Codigo: 46036

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) {
		ServerFrame frame = new ServerFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class ServerFrame extends JFrame {
	private JTextArea textArea;
	private JScrollPane scroll;

	public ServerFrame() {
		setBounds(800, 300, 300, 300);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		textArea = new JTextArea();
		scroll = new JScrollPane(textArea);
		scroll.setBounds(0, 0, 300, 300);
		panel.add(scroll);
		add(panel);
		setTitle("Servidor");
		setVisible(true);
		Thread operURL = new Thread(new Runnable() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(8888);
					while (true) {
						Socket socket = server.accept();
						DataInputStream input = new DataInputStream(
								socket.getInputStream());
						String message = input.readUTF();
						textArea.append("\n" + message);
						String url = message;
						try {
							Runtime.getRuntime().exec(
									"rundll32 url.dll,FileProtocolHandler "
											+ url);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		operURL.start();
	}
}