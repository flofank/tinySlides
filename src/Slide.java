import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Slide extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5264707060924800468L;
	private String[] types = {"jpg","gif","bmp"};
	private JButton btnChoosePath;
	private JButton btnStart;
	private String path;
	private JLabel lblNewLabel, lblOrdner;
	private JTextField txtInterval;
	private File[] files;

	/**
	 * Create the application.
	 */
	public Slide() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 10));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("tinySlides");
		setBounds(100, 100, 449, 148);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		lblOrdner = new JLabel("");
		lblOrdner.setOpaque(true);
		lblOrdner.setBackground(Color.LIGHT_GRAY);
		lblOrdner.setBounds(10, 14, 277, 18);
		lblOrdner.setText("Bitte einen Ordner wählen");
		getContentPane().add(lblOrdner);
		
		btnChoosePath = new JButton("Ordner w\u00E4hlen");
		btnChoosePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPath();
			}
		});
		btnChoosePath.setBounds(297, 13, 127, 21);
		getContentPane().add(btnChoosePath);
		
		btnStart = new JButton("Slideshow starten");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startSlideshow();
			}
		});
		btnStart.setBounds(261, 62, 162, 39);
		getContentPane().add(btnStart);
		
		lblNewLabel = new JLabel("Anzeigezeit [s] pro Bild:");
		lblNewLabel.setBounds(10, 43, 188, 20);
		getContentPane().add(lblNewLabel);
		
		txtInterval = new JTextField();
		txtInterval.setBounds(165, 43, 86, 20);
		getContentPane().add(txtInterval);
		txtInterval.setColumns(10);
	}
	
	private void selectPath() {
		JFileChooser fc = new JFileChooser("C:\\Users\\fankhauserfl\\Pictures");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int val = fc.showOpenDialog(this);
		
		if (val == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			path = f.getAbsolutePath();
		}
		
		File folder = new File(path);
		if (folder.isDirectory()) {
			files = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					for (String t : types) {
						try {
							if (t.equals(name.split("\\.")[name.split("\\.").length -1 ])) {
								return true;
							}
						} catch (Exception e) {}
					}
					return false;
				}
			});
			lblOrdner.setText("Es wurden " + files.length + " Bilder gefunden.");
		} else {
			lblOrdner.setText("Bitte einen Ordner wählen");
		}
	}
	
	private void startSlideshow() {
		int interval = 3;
		try {
			interval = Integer.parseInt(txtInterval.getText());
		} catch (Exception e) {
		}
		
		new Show(files, interval);
	}
}
