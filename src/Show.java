import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Show extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5611934508328608593L;
	private JPanel contentPane;
	private File[] files;
	private JLabel lblImg;
	private ImageChanger ic;
	private int interval;
	/**
	 * Create the frame.
	 * @param i 
	 */
	public Show(File[] files, int interval) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				stop();
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				lblImg.setBounds(0, 0, lblImg.getParent().getWidth(), lblImg.getParent().getHeight());
			}
		});
		
		setBackground(Color.BLACK);
		this.files = files;
		this.interval = interval;
		shuffle();
		setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblImg = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setBackground(Color.BLACK);
		lblImg.setBounds(0, 0, this.getWidth(), this.getHeight());
		lblImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (isUndecorated() == true) {
					undecorate(false);
				} else {
					undecorate(true);
				}
				
			}
		});
		contentPane.add(lblImg);
		setUndecorated(true);
		setVisible(true);
		ic = new ImageChanger();
		ic.start();
	}
	
	private void shuffle() {
		ArrayList<File> files2 = new ArrayList<File>();
		int rand = 0;
		for (File f : files) {
			files2.add(rand, f);
			rand = (int) (Math.random() * (files2.size() + 1));
		}
		files2.toArray(files);
	}
	
	@SuppressWarnings("deprecation")
	private void stop() {
		ic.stop();
		setVisible(false);
		dispose();
	}
	
	private void undecorate(boolean undec) {
		dispose();
		this.setUndecorated(undec);
		setVisible(true);
	}

	private class ImageChanger extends Thread {		
		public void run() {
			while(true) {
				for (File f : files) {
					try {
						lblImg.setIcon(new ImageIcon(resizeMe(ImageIO.read(f))));
						Thread.sleep(interval * 1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				shuffle();
			}
		}
	}

	private Image resizeMe(BufferedImage img) {
		float fh = (float) this.getHeight() / (float) img.getHeight();
		float fw = (float) this.getWidth() / (float) img.getWidth();
		float f = 0f;
		if (fh > fw) {
			f = fw;
		} else {
			f = fh;
		}
		return img.getScaledInstance((int) (img.getWidth() * f), (int) (img.getHeight() * f), Image.SCALE_FAST);	
	}
}
