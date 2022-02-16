import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Villanueva extends JFrame{

	private JPanel contentPane;
	private AlumnoFrame alumnoF;
	private MateriasFrame materiaF;
	private InscripcionesFrame inscripcionF;
	private ConsultasFrame consultaF;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Villanueva villanueva = new Villanueva();
					villanueva.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	 public Villanueva() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 190, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.lightGray));
		
		alumnoF = new AlumnoFrame(this);
		materiaF = new MateriasFrame(this);
		
		JButton btnAlumno = new JButton("Alumno");
		btnAlumno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alumnoF.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAlumno.setBounds(26, 26, 138, 23);
		contentPane.add(btnAlumno);
		
		JButton btnMaterias = new JButton("Materias");
		btnMaterias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				materiaF.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnMaterias.setBounds(26, 60, 138, 23);
		contentPane.add(btnMaterias);
		
		JButton btnConsultas = new JButton("Consultas");
		btnConsultas.setEnabled(false);
		btnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaF = new ConsultasFrame(Villanueva.this);
				
				consultaF.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		JButton btnInscripciones = new JButton("Inscripciones");
		btnInscripciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConsultas.setEnabled(true);
				inscripcionF = new InscripcionesFrame(Villanueva.this);
				inscripcionF.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnConsultas.setBounds(26, 128, 138, 23);
		contentPane.add(btnConsultas);
		btnInscripciones.setBounds(26, 94, 138, 23);
		contentPane.add(btnInscripciones);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(26, 201, 138, 23);
		contentPane.add(btnSalir);
	}
}
