package ua.kn.laba5;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	public ImagePanel() {

		this.addComponentListener(new ComponentAdapter() {
			// �������������� �������� � ������ ��������� �������� ������...
			@Override
			public void componentResized(ComponentEvent ce) {
				repaint();
			}
		});

		this.addMouseWheelListener(new MouseWheelListener() {
			// ��������� ���������� ������� ������� ������� ������� �����, �� �
			// �������� ������ ��������
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() == 1) {
					zoom += 0.1;
				} else {
					zoom -= 0.1;
				}
				repaint();
			}
		});

		this.addMouseListener(new MouseAdapter() {
			// ���������� ������ ����� ��� ��������� ������ � ����������
			// Drag&Dpop
			@Override
			public void mousePressed(MouseEvent e) {
				press = e.getPoint();
				pressedBtn = true;
			}

			// ��� ������ ����� ������...
			// ����� ����� ��������� ���������� �������������� � ��������������
			// ����� ��������� ����� �������
			// �.�. �������� ����� ������ ���� �������(����� ������� ��������),
			// �� ��������� ���������� �� ������������
			// ����������� ��������. ������ �� ����� �� zoom
			// ����� if...else - ���� �� �� �� ����, ����� ����� �������������
			// ��� ������ ������������ ��������,
			// �� �������������� ��� ��������� ����� ��������
			@Override
			public void mouseReleased(MouseEvent e) {
				pressedBtn = false;
				if ((int) rect.getWidth() != 0 && (int) rect.getHeight() != 0) {
					xImg += rect.getX() / zoom;
					yImg += rect.getY() / zoom;
					if ((rect.getX() + rect.getWidth()) <= wImg) {
						wImg = (int) (rect.getWidth() / zoom);
					} else {
						wImg = (int) ((wImg - (int) rect.getX()) / zoom);
					}
					if ((rect.getY() + rect.getHeight()) <= hImg) {
						hImg = (int) (rect.getHeight() / zoom);
					} else {
						hImg = (int) ((hImg - (int) rect.getY()) / zoom);
					}
					zoom = 1;
					repaint();
				}
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			@Override

			// ������������ �������������, ������� ���������� ��� ����������.
			public void mouseDragged(MouseEvent e) {
				double x, y, w, h;

				if (e.getX() > (int) press.getX()) {
					x = press.getX();
					w = (double) e.getX() - press.getX();
				} else {
					x = (double) e.getX();
					w = press.getX() - (double) e.getX();
				}

				if (e.getY() > (int) press.getY()) {
					y = press.getY();
					h = (double) e.getY() - press.getY();
				} else {
					y = (double) e.getY();
					h = press.getY() - (double) e.getY();
				}
				setRect(new Rectangle2D.Double(x, y, w, h));
			}
		});
	}

	public void setRect(Rectangle2D rect) {
		this.rect = rect;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		if (img != null) {
			g2.drawImage(img, 0, 0, (int) (wImg * zoom), (int) (hImg * zoom), xImg, yImg, xImg + wImg, yImg + hImg,
					null);
			if (pressedBtn) {
				g2.draw(rect);
			}
		}
	}

	public void setImage(String path) throws IOException {

		img = ImageIO.read(new File(path));

		byte[] array = Files.readAllBytes(new File(path).toPath());
		byte[] array2 = new byte[600];
		ArrayList<Byte> list = new ArrayList<Byte>();

		System.out.println(array.length);

		// for (int j = 1; j <= 4; j++) {
		//
		// if (j == 1 || j == 3 || j == 5 || j == 7 || j == 9 || j == 11 || j ==
		// 13 || j == 15) {
		//
		// for (int i = 0; i < 3000; i++) {
		//
		// list.add(array[i]);
		// }
		// }
		// }

		for (int i = 0; i < 600; i++) {

			list.add(array[i]);
		}
		// byte [] array2 = new byte[3000];
		// for(int i=0; i<3000; i++){
		// array2[i]=array[i];
		// }

		byte[] array21 = new byte[list.size()];

		// for (int i = 0; i < list.size(); i++) {
		// array2[i] = list.get(i);
		// }

		BufferedImage imag_pic = ImageIO.read(new ByteArrayInputStream(array21));
		img = imag_pic;

		zoom = 1;
		xImg = 0;
		yImg = 0;
		wImg = img.getWidth(ImagePanel.this);
		hImg = img.getHeight(ImagePanel.this);
		// img.get();
		repaint();

		// byte[] imageInByte = img.toByteArray();
		// Image image = Toolkit.getDefaultToolkit().createImage(byte[]
		// imagedata);
	}

	private int xImg, yImg, wImg, hImg; // ���������� � ������� ��������
										// ���������� ����� �������� ������� ���
										// ����������.
										// ��� ��� wImg, hImg - ��� �� �����
										// ����� �������� ������� ��� ��������,
										// � ���������� ������� ����
										// ��������������.
	private double zoom = 1;
	private Rectangle2D rect = new Rectangle2D.Double();
	private static Image img = null;
	private Point2D press = new Point2D.Double(0, 0);
	private boolean pressedBtn = false;
}