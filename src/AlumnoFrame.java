import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

public class AlumnoFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textApellido;
	private Object[][] datos;
	private String[] columnas = { "Nro Libreta", "Apellido", "Nombre" };
	private DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
	private Villanueva v;
	private JTable table;
	private String path = System.getProperty("user.dir");
	private JFileChooser seleccionar = new JFileChooser(path);
	private static File archivo = new File("");
	private Controlador control = new Controlador();

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

	Boolean ComprobarLetras(JPanel p) {
		for (Component c : p.getComponents()) {
			if (c instanceof JTextField) {
				if (!((JTextField) c).getText().trim().matches("^[A-zÀ-ú]*$")) {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese solo letras.");
					return false;
				}
			}
		}
		return true;
	}

	public AlumnoFrame(Villanueva v) {
		setResizable(false);
		this.v = v;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.lightGray));

		if (!archivo.exists()) {
			if (System.getProperty("os.name").startsWith("Windows"))
				archivo = new File(path + "\\Datos\\Alumnos Precargados.txt");

			else if (System.getProperty("os.name").startsWith("Linux"))
				archivo = new File(path + "/Datos/Alumnos Precargados.txt");
			if (archivo.exists()) {
				control.CargarListaAlumnos(archivo);
				datos = control.getDatosAlumnos();
				modelo.setDataVector(datos, columnas);
			} else
				JOptionPane.showMessageDialog(null, "No se pudo cargar los archivos base de Alumnos.");
		} else {
			datos = control.getDatosAlumnos();
			modelo.setDataVector(datos, columnas);
		}

		JLabel lbNroLibreta = new JLabel("Nro Libreta Seleccionado");
		lbNroLibreta.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbNroLibreta.setBounds(111, 57, 196, 14);
		contentPane.add(lbNroLibreta);

		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarLetras(contentPane)) {
						lbNroLibreta.setText(
								control.AgregarAlumno(textApellido.getText(), textNombre.getText()).toString());

						if (lbNroLibreta.getText() != null) {
							datos = control.getDatosAlumnos();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();

							BorrarCampos(contentPane);
							JOptionPane.showMessageDialog(null, "Alumno ingresado con exito!");
						} else
							JOptionPane.showMessageDialog(null, "Error.");
					}
				}
			}
		});
		btnCargar.setBounds(317, 46, 89, 23);
		contentPane.add(btnCargar);

		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarLetras(contentPane)) {
						if (control.QuitarAlumno(Integer.parseInt(lbNroLibreta.getText()))) {
							datos = control.getDatosAlumnos();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();
							BorrarCampos(contentPane);
							JOptionPane.showMessageDialog(null, "Alumno dado de baja.");
						}
					}

				}
			}
		});
		btnBaja.setBounds(317, 79, 89, 23);
		contentPane.add(btnBaja);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarLetras(contentPane)) {
						control.ModificarAlumno(Integer.parseInt(lbNroLibreta.getText()), textNombre.getText(),
								textApellido.getText());
						datos = control.getDatosAlumnos();
						modelo.setDataVector(datos, columnas);
						modelo.fireTableDataChanged();
						BorrarCampos(contentPane);
					}
				}
			}
		});
		btnModificar.setBounds(317, 113, 89, 23);
		contentPane.add(btnModificar);

		JButton btnCargatx = new JButton("Cargar Lista");
		btnCargatx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seleccionar.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
					archivo = seleccionar.getSelectedFile();

					if (control.CargarListaAlumnos(archivo)) {
						datos = control.getDatosAlumnos();
						modelo.setDataVector(datos, columnas);
						modelo.fireTableDataChanged();
					}
				}
			}
		});
		btnCargatx.setBounds(42, 507, 145, 23);
		contentPane.add(btnCargatx);

		JButton btnGuardartx = new JButton("Guardar Lista");
		btnGuardartx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seleccionar.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
					archivo = seleccionar.getSelectedFile();
					if (!control.GuardarListaAlumnos(archivo))
						JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo.");
				}
			}
		});
		btnGuardartx.setBounds(42, 541, 145, 23);
		contentPane.add(btnGuardartx);

		textNombre = new JTextField();
		textNombre.setBounds(111, 81, 196, 20);
		contentPane.add(textNombre);
		textNombre.setColumns(10);

		textApellido = new JTextField();
		textApellido.setBounds(111, 115, 196, 20);
		contentPane.add(textApellido);
		textApellido.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nro Libreta:");
		lblNewLabel.setBounds(42, 57, 98, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(42, 90, 98, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Apellido:");
		lblNewLabel_2.setBounds(42, 124, 98, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Alumnos");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(184, 11, 123, 24);
		contentPane.add(lblNewLabel_3);

		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAtras.setBounds(317, 541, 89, 23);
		contentPane.add(btnAtras);

		modelo = new DefaultTableModel(datos, columnas);

		JPanel panel = new JPanel();
		panel.setBounds(42, 178, 363, 329);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		table = new JTable(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String nombreSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 2);
				textNombre.setText(nombreSeleccionado);
				String apellidoSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 1);
				textApellido.setText(apellidoSeleccionado);
				String idSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 0);
				lbNroLibreta.setText(idSeleccionado);
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_4 = new JLabel("Seleccione de la lista para dar de baja o modificar:");
		lblNewLabel_4.setBounds(42, 153, 363, 14);
		contentPane.add(lblNewLabel_4);

	}
}
