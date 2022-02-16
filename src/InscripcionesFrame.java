
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;

public class InscripcionesFrame extends JFrame {

	private JPanel contentPane;
	private Villanueva v;
	private JTextField txParcial1;
	private JTextField txParcial2;
	private JTable table;
	private Object[][] datos;
	private String[] columnas = { "ID", "Alumno", "ID Alumno", "Materia", "1° Parcial", "2° Parcial", "Estado" };
	private DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
	private String path = System.getProperty("user.dir");
	private JFileChooser seleccionar = new JFileChooser(path);
	private static File archivo = new File("");
	private Controlador control = new Controlador();
	private JTextField txIdAlumno;
	private JComboBox<String> cbMaterias;

	void BorrarCampos(JPanel p) {
		for (Component c : p.getComponents()) {
			if (c instanceof JTextField)
				((JTextField) c).setText("");
		}
	}

	Boolean ComprobarCampos(JPanel p) {
		for (Component c : p.getComponents()) {
			if (c instanceof JTextField)
				if (((JTextField) c).getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
					return false;
				}
		}
		return true;
	}

	Boolean ComprobarNumeros(JPanel p) {
		for (Component c : p.getComponents()) {
			if (c instanceof JTextField) {
				if (!((JTextField) c).getText().trim().matches("([0-9]|\\.)+")) {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese solo numeros.");
					return false;
				}
			}
		}
		return true;
	}

	public InscripcionesFrame(Villanueva v) {
		this.v = v;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		if (!archivo.exists()) {

			if (System.getProperty("os.name").startsWith("Windows"))
				archivo = new File(path + "\\Datos\\Inscripciones Precargadas.txt");

			else if (System.getProperty("os.name").startsWith("Linux"))
				archivo = new File(path + "/Datos/Inscripciones Precargadas.txt");

			if (archivo.exists()) {
				control.CargarListaInscripciones(archivo);
				datos = control.getDatosInscripciones();
				modelo.setDataVector(datos, columnas);
			} else
				JOptionPane.showMessageDialog(null, "No se pudo cargar los archivos base de Inscripciones.");

		} else {
			datos = control.getDatosInscripciones();
			modelo.setDataVector(datos, columnas);
		}

		JLabel lblNroIncripcin = new JLabel("Nro Inscripci\u00F3n:");
		lblNroIncripcin.setBounds(44, 159, 116, 14);
		contentPane.add(lblNroIncripcin);

		JLabel lblNewLabel_2 = new JLabel("Inscripciones");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(182, 11, 116, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNroIncripcinGenerado = new JLabel("Nro Inscripci\u00F3n Seleccionado");
		lblNroIncripcinGenerado.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNroIncripcinGenerado.setBounds(145, 159, 171, 14);
		contentPane.add(lblNroIncripcinGenerado);

		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarNumeros(contentPane)) {
						lblNroIncripcinGenerado.setText(control.AgregarInscripcion(
								Integer.parseInt(txIdAlumno.getText()), cbMaterias.getSelectedItem().toString(),
								Double.parseDouble(txParcial1.getText()), Double.parseDouble(txParcial2.getText()))
								.toString());

						if (Integer.parseInt(lblNroIncripcinGenerado.getText()) != 0) {
							if (control.getDatosInscripciones() != null) {
								datos = control.getDatosInscripciones();
								modelo.setDataVector(datos, columnas);
								modelo.fireTableDataChanged();

								BorrarCampos(contentPane);
								JOptionPane.showMessageDialog(null, "Inscripcion cargada con exito!");
							}
						}
					}
				}
			}
		});
		btnCargar.setBounds(323, 39, 89, 23);
		contentPane.add(btnCargar);

		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarNumeros(contentPane)) {
						if (control.QuitarInscripcion(Integer.parseInt(lblNroIncripcinGenerado.getText()))) {
							datos = control.getDatosInscripciones();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();
							JOptionPane.showMessageDialog(null, "Inscripcion eliminada.");
							BorrarCampos(contentPane);
						} else
							JOptionPane.showMessageDialog(null, "Error.");
					}
				}
			}
		});
		btnBaja.setBounds(323, 73, 89, 23);
		contentPane.add(btnBaja);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarNumeros(contentPane)) {

						if (control.ModificarInscripcion(Integer.parseInt(lblNroIncripcinGenerado.getText()),
								cbMaterias.getSelectedItem().toString(), Double.parseDouble(txParcial1.getText()),
								Double.parseDouble(txParcial2.getText()))) {
							datos = control.getDatosInscripciones();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();
							BorrarCampos(contentPane);
						} else
							JOptionPane.showMessageDialog(null, "Error.");

					}
				}

			}
		});
		btnModificar.setBounds(323, 107, 89, 23);
		contentPane.add(btnModificar);

		JButton btnCargaLista = new JButton("Cargar Lista");
		btnCargaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seleccionar.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
					archivo = seleccionar.getSelectedFile();

					if (control.CargarListaInscripciones(archivo)) {
						if (control.getDatosInscripciones() != null) {
							datos = control.getDatosInscripciones();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();
						}
					}
				}
			}
		});
		btnCargaLista.setBounds(44, 507, 138, 23);
		contentPane.add(btnCargaLista);

		JButton btnGuardaLista = new JButton("Guardar Lista");
		btnGuardaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seleccionar.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
					archivo = seleccionar.getSelectedFile();
					control.GuardarListaInscripciones(archivo);
				}
			}
		});
		btnGuardaLista.setBounds(44, 541, 138, 23);
		contentPane.add(btnGuardaLista);

		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAtras.setBounds(323, 541, 89, 23);
		contentPane.add(btnAtras);

		txParcial1 = new JTextField();
		txParcial1.setBounds(127, 97, 171, 20);
		contentPane.add(txParcial1);
		txParcial1.setColumns(10);

		txParcial2 = new JTextField();
		txParcial2.setBounds(127, 128, 171, 20);
		contentPane.add(txParcial2);
		txParcial2.setColumns(10);

		JLabel lblNewLabel = new JLabel("Materia:");
		lblNewLabel.setBounds(44, 69, 73, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_3 = new JLabel("Parcial 1:");
		lblNewLabel_3.setBounds(44, 100, 73, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Parcial 2:");
		lblNewLabel_4.setBounds(44, 134, 60, 14);
		contentPane.add(lblNewLabel_4);

		JPanel panel = new JPanel();
		panel.setBounds(10, 212, 499, 290);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		JLabel lbEstado = new JLabel("Estado Actual");
		lbEstado.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lbEstado.setBounds(145, 184, 138, 14);
		contentPane.add(lbEstado);

		table = new JTable(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String IDSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 0);
				lblNroIncripcinGenerado.setText(IDSeleccionado);
				String IDAlumnoSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 2);
				txIdAlumno.setText(IDAlumnoSeleccionado);
				String materiaSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 3);
				cbMaterias.setSelectedItem(materiaSeleccionado);
				String p1Seleccionado = table.getValueAt(table.getSelectedRow(), 4).toString();
				txParcial1.setText(p1Seleccionado);
				String p2Seleccionado = (String) table.getValueAt(table.getSelectedRow(), 5);
				txParcial2.setText(p2Seleccionado);
				String estadoSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 6);
				lbEstado.setText(estadoSeleccionado);
			}
		});
		scrollPane.setViewportView(table);

		txIdAlumno = new JTextField();
		txIdAlumno.setBounds(127, 36, 171, 20);
		contentPane.add(txIdAlumno);
		txIdAlumno.setColumns(10);

		JLabel lbIdAlumno = new JLabel("ID Alumno:");
		lbIdAlumno.setBounds(44, 39, 73, 14);
		contentPane.add(lbIdAlumno);

		cbMaterias = new JComboBox();

		cbMaterias.setBounds(127, 65, 171, 22);
		contentPane.add(cbMaterias);

		JLabel lblNewLabel_1 = new JLabel("Estado: ");
		lblNewLabel_1.setBounds(44, 184, 73, 14);
		contentPane.add(lblNewLabel_1);

		setUndecorated(true);
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.lightGray));

		String[] lista = control.getListaMaterias();
		for (String s : lista)
			cbMaterias.addItem(s);
	}
}
