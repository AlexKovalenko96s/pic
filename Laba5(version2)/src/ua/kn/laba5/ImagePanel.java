package ua.kn.laba5;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
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

	public void setImage(String path) {
		try {
			img = ImageIO.read(new File(path));
			zoom = 1;
			xImg = 0;
			yImg = 0;
			wImg = img.getWidth(ImagePanel.this);
			hImg = img.getHeight(ImagePanel.this);
			repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
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