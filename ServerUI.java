package gruppuppgift;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** Utseendet för servern
 * 
 * @author Chippen Vlahija, Aziz Jashari
 *
 */

public class ServerUI extends JPanel {
	private JTextArea ta = new JTextArea();
	private final Integer[] YEARS = { 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028 };
	private final Integer[] MONTHS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private final Integer[] DAYS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28, 29, 30, 31 };
	private final Integer[] HOURS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24 };
	private final Integer[] MINUTES = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
			23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
			50, 51, 52, 53, 54, 55, 56, 57, 58, 59 };

	private ArrayList<Logging> logMessageList;
	private JComboBox fromYear = new JComboBox(YEARS);
	private JComboBox fromMonth = new JComboBox(MONTHS);
	private JComboBox fromDate = new JComboBox(DAYS);
	private JComboBox fromHour = new JComboBox(HOURS);
	private JComboBox fromMinute = new JComboBox(MINUTES);
	private JComboBox toYear = new JComboBox(YEARS);
	private JComboBox toMonth = new JComboBox(MONTHS);
	private JComboBox toDay = new JComboBox(DAYS);
	private JComboBox toHour = new JComboBox(HOURS);
	private JComboBox toMinute = new JComboBox(MINUTES);
	private JLabel lblFrom = new JLabel(" Från");
	private JLabel lblTo = new JLabel(" Till");
	private JLabel lblFromYY = new JLabel("ÅR");
	private JLabel lblFromMM = new JLabel("M");
	private JLabel lblFromDD = new JLabel("D");
	private JLabel lblFromHH = new JLabel("H");
	private JLabel lblFrommm = new JLabel("m");
	private JLabel lblToYY = new JLabel("ÅR");
	private JLabel lblToMM = new JLabel("M");
	private JLabel lblToDD = new JLabel("D");
	private JLabel lblToHH = new JLabel("H");
	private JLabel lblTomm = new JLabel("m");
	private JButton btnCheck = new JButton("Uppdatera loggen");
	private JTextArea taLog = new JTextArea();
	private JPanel pnlWest = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlFromSelection = new JPanel();
	private JPanel pnlToSelection = new JPanel();
	private JPanel pnlGridDateFrom = new JPanel();
	private JPanel pnlGridDateTo = new JPanel();
	private JPanel pnlGridTimeFrom = new JPanel();
	private JPanel pnlGridTimeTo = new JPanel();

	public ServerUI() {
		setPreferredSize(new Dimension(750, 400));
		setLayout(new BorderLayout());
		pnlWest.setPreferredSize(new Dimension(120, 150));
		pnlEast.setPreferredSize(new Dimension(120, 150));
		pnlWest.setLayout(new BorderLayout());
		
		pnlWest.add(lblFrom, BorderLayout.NORTH);
		pnlWest.add(pnlFromSelection, BorderLayout.CENTER);
		
		pnlFromSelection.setLayout(new BorderLayout());
		pnlFromSelection.add(pnlGridDateFrom, BorderLayout.NORTH);
		pnlFromSelection.add(pnlGridTimeFrom, BorderLayout.SOUTH);
		pnlGridDateFrom.setLayout(new GridLayout(2, 3));
		pnlGridDateFrom.add(lblFromYY);
		pnlGridDateFrom.add(lblFromMM);
		pnlGridDateFrom.add(lblFromDD);
		pnlGridDateFrom.add(fromYear);
		pnlGridDateFrom.add(fromMonth);
		pnlGridDateFrom.add(fromDate);
		pnlGridTimeFrom.setLayout(new GridLayout(2, 2));
		pnlGridTimeFrom.add(lblFromHH);
		pnlGridTimeFrom.add(lblFrommm);
		pnlGridTimeFrom.add(fromHour);
		pnlGridTimeFrom.add(fromMinute);
		pnlEast.setLayout(new BorderLayout());
		
		pnlEast.add(lblTo, BorderLayout.NORTH);
		pnlEast.add(pnlToSelection, BorderLayout.CENTER);
		
		pnlToSelection.setLayout(new BorderLayout());
		pnlToSelection.add(pnlGridDateTo, BorderLayout.NORTH);
		pnlToSelection.add(pnlGridTimeTo, BorderLayout.SOUTH);
		pnlGridDateTo.setLayout(new GridLayout(2, 3));
		pnlGridDateTo.add(lblToYY);
		pnlGridDateTo.add(lblToMM);
		pnlGridDateTo.add(lblToDD);
		pnlGridDateTo.add(toYear);
		pnlGridDateTo.add(toMonth);
		pnlGridDateTo.add(toDay);
		pnlGridTimeTo.setLayout(new GridLayout(2, 2));
		pnlGridTimeTo.add(lblToHH);
		pnlGridTimeTo.add(lblTomm);
		pnlGridTimeTo.add(toHour);
		pnlGridTimeTo.add(toMinute);
		taLog.setEditable(false);
		taLog.setLineWrap(true);
		btnCheck.addActionListener(new CheckListener());
		add(pnlWest, BorderLayout.WEST);
		add(pnlEast, BorderLayout.EAST);
		add(btnCheck, BorderLayout.SOUTH);
		JScrollPane scrollPanel = new JScrollPane(taLog);
		add(scrollPanel, BorderLayout.CENTER);
		
		JFrame frame = new JFrame("Server UI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(this);
		frame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() / 2 - frame.getWidth() / 2;
		double height = screenSize.getHeight() / 2 - frame.getHeight() / 2;
		frame.setLocation((int) width, (int) height);
		frame.setVisible(true);
	}

	private class CheckListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int yearFrom = (int) fromYear.getSelectedItem();
			int monthFrom = (int) fromMonth.getSelectedItem();
			int dayFrom = (int) fromDate.getSelectedItem();
			int hourFrom = (int) fromHour.getSelectedItem();
			int minuteFrom = (int) fromMinute.getSelectedItem();
			Calendar from = Calendar.getInstance();
			from.set(yearFrom, monthFrom - 1, dayFrom, hourFrom, minuteFrom);
			int yearTo = (int) toYear.getSelectedItem();
			int monthTo = (int) toMonth.getSelectedItem();
			int dayTo = (int) toDay.getSelectedItem();
			int hourTo = (int) toHour.getSelectedItem();
			int minuteTo = (int) toMinute.getSelectedItem();
			
			Calendar cale = Calendar.getInstance();
			cale.set(yearTo, monthTo - 1, dayTo, hourTo, minuteTo);
			taLog.setText("");
			for (Logging message : logMessageList) {
				if (message.getDate().compareTo(from.getTime()) >= 0 && message.getDate().compareTo(cale.getTime()) <= 0) {
					taLog.append(message.toString() + "\n");
				}
			}
		}
	}

	/**
	 * Uppdaterar
	 */

	public void updateTextArea(ArrayList<Logging> logMessageList) {
		taLog.setText("");
		this.logMessageList = logMessageList;
		for (Logging message : logMessageList) {
			taLog.append(message.toString() + "\n");
		}
	}

	/**
	 * @return TextAreaLog
	 * 
	 */
	public JTextArea getTextArea() {
		return taLog;
	}
}