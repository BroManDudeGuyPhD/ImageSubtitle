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
static BufferedImage bi;
    
    public static void main(String[] args) {
        bi = null;
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        try {
            //Randomly choose image from directory
            final File dir = new File("C:/Users/aaf8553/Desktop/ww2");
            File[] files = dir.listFiles();
            Random rand = new Random();
            File f = files[rand.nextInt(files.length)];
            
            //Read image in
            bi = ImageIO.read(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
        //Size of Displayed Image
        

        //Message
        String message = "\"Test text HELLO NICHOLAS\" ";
        
        //Breaks message into words and keeps words together on new lines with wrapText class
        String ParsedMessage = wrapText(bi.getWidth()/11, message).replaceAll("  +"," ").trim();
        
        //Breaks lines int array
        String [] lines = ParsedMessage.split("\n");
        
        
        //Adds white space for longer messages
        final BufferedImage textRenderedImage = drawTextOnImage(ParsedMessage, bi, 30*lines.length, lines);
        
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
    
    public static String wrapText(int textviewWidth, String message) {

        String temp = "";
        String sentence = "";

        String[] array = message.split(" "); // split by space

        for (String word : array) {

            if ((temp.length() + word.length()) < textviewWidth) {  // create a temp variable and check if length with new word exceeds textview width.

                temp += " "+word;

            } else {
                sentence += temp+"\n"; // add new line character
                temp = word;
            }

        }

        return (sentence.replaceFirst(" ", "")+temp);

    }
    
    private static BufferedImage drawTextOnImage(String text, BufferedImage image, int space, String[] lines) {
        BufferedImage Image = new BufferedImage(image.getWidth(), image.getHeight() + space, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) Image.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, null);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.BOLD, 25));
        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = g2d.getFontMetrics().getHeight();

        for (int lineCount = 0; lineCount < lines.length; lineCount++) { //lines from above
            int xPos = 10;
            int yPos = Image.getHeight()-space+20 + lineCount * lineHeight;
            String line = lines[lineCount];
            g2d.drawString(line, xPos, yPos);
            
        }
        g2d.dispose();

        saveImageActionPerformed(Image);
        return Image;
        
    }
    
    private static void saveImageActionPerformed(BufferedImage image) {
     
      
         File saveFile = new File("C:/Users/aaf8553/Desktop/ww2/tempImage.png");
         try {
            ImageIO.write(image, "png", saveFile);
         } catch (IOException e) {
            e.printStackTrace();
         }
      
   }

    
    
}
