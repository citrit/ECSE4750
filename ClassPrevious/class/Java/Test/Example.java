package howto;

import java.awt.*;

class ColorGenerator
{
   private int i = 0;

   private Color [] rgc =
   {
      Color.yellow,
      Color.red,
      Color.pink,
      Color.orange,
      Color.magenta,
      Color.green,
      Color.cyan,
      Color.blue
   };

   public Color generateColor()
   {
      i = i < rgc.length ? i : 0;

      return rgc[i++];
   }
}

class NewPanel extends Panel
{
   private String str;
   private TextArea ta;
   private ColorGenerator cg;

   public NewPanel(String str, TextArea ta)
   {
      super();
      this.str = str;
      this.ta = ta;
      cg = new ColorGenerator();
   }

   public Insets insets()
   {
      return new Insets(5, 5, 5, 5);
   }

   public void paint(Graphics g)
   {
      ta.appendText("Panel \"" + str + "\" saw paint...\n");

      g.setColor(cg.generateColor());
      g.fillRect(0, 0, size().width - 1, size().height - 1);

      g.setColor(Color.black);
      g.drawRect(0, 0, size().width - 1, size().height - 1);
   }
}

public class Example extends java.applet.Applet
{
   private TextArea ta;
   private ColorGenerator cg;

   public void init()
   {
      ta = new TextArea();
      cg = new ColorGenerator();

      setLayout(new BorderLayout());

      add("North", new Button("Redraw Applet Panel Only"));

      add("Center", ta);

      NewPanel p1 = new NewPanel("1", ta);
      NewPanel p2 = new NewPanel("2", ta);

      p2.add(new NewPanel("2A", ta));
      p2.add(new NewPanel("2B", ta));

      p1.add(p2);

      add("South", p1);
   }

   public Dimension preferredSize()
   {
      return new Dimension(400, 230);
   }

   public Insets insets()
   {
      return new Insets(5, 5, 5, 5);
   }

   public boolean action(Event e, Object o)
   {
      repaint();

      return true;
   }

   public void paint(Graphics g)
   {
      ta.appendText("Applet saw paint...\n");

      g.setColor(cg.generateColor());
      g.fillRect(0, 0, size().width - 1, size().height - 1);

      g.setColor(Color.black);
      g.drawRect(0, 0, size().width - 1, size().height - 1);
   }

   public static void main(String [] args)
   {
      Frame f = new Frame("Example 1");

      Example ex = new Example();

      ex.init();

      f.add("Center", ex);

      f.pack();
      f.show();
   }
}
