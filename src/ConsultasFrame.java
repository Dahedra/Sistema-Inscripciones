import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ConsultasFrame extends JFrame {

	private JPanel contentPane;
	private Villanueva v;
	private Object[][] datos;
	private String[] columnas = { "Apellido", "Nombre", "Parcial 1", "Parcial 2" };
	private DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
	private JTable table;
	private Controlador control = new Controlador();
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public ConsultasFrame(Villanueva v) {
		this.v = v;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Listados");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(184, 11, 131, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Seleccione una materia:");
		lblNewLabel_1.setBounds(40, 48, 124, 14);
		contentPane.add(lblNewLabel_1);

		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAtras.setBounds(314, 541, 89, 23);
		contentPane.add(btnAtras);

		JPanel panel = new JPanel();
		panel.setBounds(22, 111, 403, 419);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		table = new JTable(modelo);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel("Promocionados:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(22, 77, 293, 23);
		contentPane.add(lblNewLabel_2);
		setUndecorated(true);
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.lightGray));

		comboBox = new JComboBox();
		comboBox.setEnabled(true);
		comboBox.setBounds(174, 44, 141, 22);
		contentPane.add(comboBox);
		
		comboBox.addItem("");
		
		String[] lista = control.getListaMaterias();
		
		for (String s : lista)
			comboBox.addItem(s);
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					
					datos = control.getListaPromocionados(comboBox.getSelectedItem().toString());
					
					if (!control.getCheckPromo())
						JOptionPane.showMessageDialog(null, "No hay promocionados en la catedra.");
					else
					{
						lblNewLabel_2.setText("Promocionados de "+comboBox.getSelectedItem().toString()+":");
						modelo.setDataVector(datos, columnas);
						modelo.fireTableDataChanged();	
					}
					
				}
			}
		});
	}
}
