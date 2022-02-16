import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;

public class MateriasFrame extends JFrame {

	private JPanel contentPane;
	private Villanueva v;
	private JTextField txNombreMateria;
	private JTable table;
	private Object[][] datos;
	private String[] columnas = { "ID", "Nombre" };
	private DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
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
				if (!((JTextField) c).getText().trim().matches("^[A-zÀ-ú\s]*$")) {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese solo letras.");
					return false;
				}
			}
		}
		return true;
	}

	public MateriasFrame(Villanueva v) {
		this.v = v;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		if (!archivo.exists()) {
			if (System.getProperty("os.name").startsWith("Windows"))
				archivo = new File(path + "\\Datos\\Materias Precargadas.txt");

			else if (System.getProperty("os.name").startsWith("Linux"))
				archivo = new File(path + "/Datos/Materias Precargadas.txt");

			if (archivo.exists()) {
				control.CargarListaMaterias(archivo);
				datos = control.getDatosMaterias();
				modelo.setDataVector(datos, columnas);
			} else
				JOptionPane.showMessageDialog(null, "No se pudo cargar los archivos base de Materias.");
		} else {
			datos = control.getDatosMaterias();
			modelo.setDataVector(datos, columnas);

		}

		txNombreMateria = new JTextField();
		txNombreMateria.setBounds(124, 88, 171, 20);
		contentPane.add(txNombreMateria);
		txNombreMateria.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nro Materia:");
		lblNewLabel.setBounds(41, 45, 73, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(41, 91, 73, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lbID = new JLabel("Nro Materia Seleccionado");
		lbID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbID.setBounds(124, 45, 196, 14);
		contentPane.add(lbID);

		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (ComprobarCampos(contentPane)) {
					if (ComprobarLetras(contentPane)) {
						lbID.setText(control.AgregarMateria(txNombreMateria.getText()).toString());

						if (lbID.getText() != null) {
							JOptionPane.showMessageDialog(null, "Materia cargada con exito!");
							datos = control.getDatosMaterias();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();
							BorrarCampos(contentPane);
						} else
							JOptionPane.showMessageDialog(null, "La materia ya se encuentra cargada en el sistema.");
					}
				}
			}
		});
		btnCargar.setBounds(320, 36, 89, 23);
		contentPane.add(btnCargar);

		JButton btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarLetras(contentPane)) {
						if (control.QuitarMateria(Integer.parseInt(lbID.getText()))) {
							datos = control.getDatosMaterias();
							modelo.setDataVector(datos, columnas);
							modelo.fireTableDataChanged();
							JOptionPane.showMessageDialog(null, "Materia eliminada.");
							BorrarCampos(contentPane);
						}
					}
				}
			}
		});
		btnBaja.setBounds(320, 70, 89, 23);
		contentPane.add(btnBaja);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ComprobarCampos(contentPane)) {
					if (ComprobarLetras(contentPane)) {
						control.ModificarMateria(Integer.parseInt(lbID.getText()), txNombreMateria.getText());
						datos = control.getDatosMaterias();
						modelo.setDataVector(datos, columnas);
						modelo.fireTableDataChanged();
					}
				}
			}
		});
		btnModificar.setBounds(320, 104, 89, 23);
		contentPane.add(btnModificar);

		JButton btnCargaLista = new JButton("Cargar Lista");
		btnCargaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seleccionar.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
					archivo = seleccionar.getSelectedFile();

					if (control.CargarListaMaterias(archivo)) {
						datos = control.getDatosMaterias();
						modelo.setDataVector(datos, columnas);
						modelo.fireTableDataChanged();
						BorrarCampos(contentPane);
					}
				}
			}
		});
		btnCargaLista.setBounds(43, 517, 138, 23);
		contentPane.add(btnCargaLista);

		JButton btnGuardaLista = new JButton("Guardar Lista");
		btnGuardaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seleccionar.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
					archivo = seleccionar.getSelectedFile();

					if (!control.GuardarListaMaterias(archivo))
						JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo.");

				}
			}
		});
		btnGuardaLista.setBounds(43, 541, 138, 23);
		contentPane.add(btnGuardaLista);

		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAtras.setBounds(320, 541, 89, 23);
		contentPane.add(btnAtras);

		JLabel lblNewLabel_2 = new JLabel("Materias");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(189, 11, 106, 14);
		contentPane.add(lblNewLabel_2);

		JPanel panel = new JPanel();
		panel.setBounds(41, 158, 368, 352);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		table = new JTable(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String nombreSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 1);
				txNombreMateria.setText(nombreSeleccionado);
				String IDSeleccionado = (String) table.getValueAt(table.getSelectedRow(), 0);
				lbID.setText(IDSeleccionado);
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_4 = new JLabel("Seleccione de la lista para dar de baja o modificar:");
		lblNewLabel_4.setBounds(41, 133, 363, 14);
		contentPane.add(lblNewLabel_4);

		setUndecorated(true);
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.lightGray));

	}
}
