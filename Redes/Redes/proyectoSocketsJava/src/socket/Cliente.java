package socket;

//Nombre completo: Ignacio del Rio Zenteno
//Codigo: 46036

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Cliente {
	public static void main(String[] args) {
		MarcoCliente1 mimarco1 = new MarcoCliente1();
		mimarco1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MarcoCliente1 extends JFrame implements Runnable {
	public MarcoCliente1() {
		setBounds(1000, 500, 500, 500);
		setLayout(null);
		setTitle("Cliente");
		JLabel texto = new JLabel("Mensaje:");
		texto.setBounds(25, 15, 75, 25);
		add(texto);
		campo1 = new JTextField();
		campo1.setBounds(125, 10, 200, 30);
		add(campo1);
		miboton = new JButton("Enviar");
		miboton.setBounds(350, 10, 100, 25);
		SendText event0 = new SendText();
		miboton.addActionListener(event0);
		add(miboton);
		areatexto = new JTextArea();
		scroll = new JScrollPane(areatexto);
		scroll.setBounds(50, 50, 400, 300);
		add(scroll);
		setVisible(true);
		Thread elhilo = new Thread(this);
		elhilo.start();
	}

	private class SendText implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				Socket elsocket = new Socket("192.168.0.2", 8888);
				DataOutputStream datos_salida = new DataOutputStream(
						elsocket.getOutputStream());
				datos_salida.writeUTF(campo1.getText());
				String mensajito = campo1.getText();
				areatexto.append("\n" + "Cliente: " + mensajito);
				campo1.setText("");
				datos_salida.close();
			} catch (UnknownHostException e0) {
				e0.printStackTrace();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}

	private JTextField campo1;
	private JButton miboton;
	private JTextArea areatexto;
	private JScrollPane scroll;

	public void run() {
		try {
			ServerSocket server = new ServerSocket(8891);
			while (true) {
				Socket socket = server.accept();
				DataInputStream data_entrada = new DataInputStream(
						socket.getInputStream());
				String elmensaje = data_entrada.readUTF();
				areatexto.append("\n" + "Consumidor: " + elmensaje);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}