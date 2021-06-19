package socket;

//Nombre completo: Ignacio del Rio Zenteno
//Codigo: 46036

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		MarcoServidor mimarco = new MarcoServidor();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MarcoServidor extends JFrame {
	private JTextArea areatexto;
	private JScrollPane scroll;

	public MarcoServidor() {
		setBounds(800, 300, 300, 300);
		JPanel milamina = new JPanel();
		milamina.setLayout(new BorderLayout());
		areatexto = new JTextArea();
		scroll = new JScrollPane(areatexto);
		scroll.setBounds(0, 0, 300, 300);
		milamina.add(scroll);
		add(milamina);
		setVisible(true);
		Thread hilo = new Thread(new Runnable() {
			public void run() {
				try {
					ServerSocket elservidor = new ServerSocket(8888);
					while (true) {
						Socket elsocket = elservidor.accept();
						DataInputStream data_entrada = new DataInputStream(
								elsocket.getInputStream());
						String elmensaje = data_entrada.readUTF();
						areatexto.append("\n" + elmensaje);
						Socket socket1 = new Socket("192.168.0.2", 8890);
						DataOutputStream datos_salida = new DataOutputStream(
								socket1.getOutputStream());
						datos_salida.writeUTF(elmensaje);
						elsocket.close();
						socket1.close();
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		hilo.start();
		Thread hilo2 = new Thread(new Runnable() {
			public void run() {
				try {
					ServerSocket elservidor = new ServerSocket(8889);
					while (true) {
						Socket elsocket = elservidor.accept();
						DataInputStream data_entrada = new DataInputStream(
								elsocket.getInputStream());
						String elmensaje = data_entrada.readUTF();
						areatexto.append("\n" + elmensaje);
						Socket socket1 = new Socket("192.168.0.2", 8891);
						DataOutputStream datos_salida = new DataOutputStream(
								socket1.getOutputStream());
						datos_salida.writeUTF(elmensaje);
						elsocket.close();
						socket1.close();
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		hilo2.start();
	}
}