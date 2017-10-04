package imagesubtitle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A simple program that accepts an image and resizes image to accept text
 * @author BroManDudeGuyPhD
 */
public class ImageSubtitle {

    
    public static void main(String[] args) {
        BufferedImage bi = null;
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        try {
            //Randomly choose image from directory
            final File dir = new File("<Path To Images>");
            File[] files = dir.listFiles();
            Random rand = new Random();
            File f = files[rand.nextInt(files.length)];
            
            //Read image in
            bi = ImageIO.read(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
        //Size of Displayed Image
        BufferedImage resizedImage = resize(bi, 300, 300);

        //Message
        String message = "Hello I hope you can make it to the party man I am tired its awful how many people are      i am sorry                                 ";
        
        //Breaks message into lines
        String ParsedMessage = message.replaceAll("(.{25})", "$1\n").replaceAll("  +"," ").trim();
        
        //Breaks lines int array
        String [] lines = ParsedMessage.split("\n");
        
        
        //Adds white space for longer messages
        final BufferedImage textRenderedImage = drawTextOnImage(ParsedMessage, resizedImage, 30*lines.length, lines);
        
        JPanel p = new JPanel() {
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
                g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
                g2d.drawImage(textRenderedImage, 0, 0, null);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(textRenderedImage.getWidth(), textRenderedImage.getHeight());
            }
        };

        frame.add(p);
        frame.pack();
        frame.setVisible(true);

    }
    
    private static BufferedImage drawTextOnImage(String text, BufferedImage image, int space, String[] lines) {
        BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight() + space, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

        g2d.drawImage(image, 0, 0, null);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.BOLD, 25));
        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = g2d.getFontMetrics().getHeight();

        for (int lineCount = 0; lineCount < lines.length; lineCount++) { //lines from above
            int xPos = 15;
            int yPos = 320 + lineCount * lineHeight;
            String line = lines[lineCount];
            g2d.drawString(line, xPos, yPos);
        }
        g2d.dispose();

        return bi;
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    
}
