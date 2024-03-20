
package minesweeper;
import java.io.*;
import java.util.*;
import java.math.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
/**
 *
 * @author Franek
 */
public class Minesweeper implements ActionListener {
   Scanner keyboard = new Scanner(System.in); 
    
  int[][] arr = new int[10][10];
  JFrame f;
  final int gridSize = 800;
  int count = 0;
  JLabel l = new JLabel();
  JButton b[][] = new JButton[arr.length][arr[0].length];
  Minesweeper()
  {
    
  }
  
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
        // TODO code application logic here
        Minesweeper app = new Minesweeper();
        app.run();
        app.setup();
        
    }
    
     public void actionPerformed(ActionEvent e)
    {
      count++;
      
      String[] tokens=e.getActionCommand().split(" ");  
      l.setText(String.valueOf(count));      
      int xClick = Integer.parseInt(tokens[0]);
      int yClick = Integer.parseInt(tokens[1]);      
      //b[yClick][xClick].setText(String.valueOf(arr[yClick][xClick]));
      //b[yClick][xClick] = new JButton(String.valueOf(arr[yClick][xClick]));
      //b[yClick][xClick].setActionCommand(String.valueOf(arr[yClick][xClick]));
      System.out.println(arr[yClick][xClick]);
    }

  void setup()
  {
      f = new JFrame();  
    
    for (int i = 0; i < arr.length; i++) 
    {
      for (int j = 0; j < arr[0].length; j++) 
      {
        b[i][j] = new JButton(i + " " + j);
        b[i][j].setBounds((i*55)+10,(j*55)+10,50,50);
        b[i][j].addActionListener(this);
        b[i][j].setActionCommand(i + " " + j);
        f.add(b[i][j]);
      }
    }
    f.add(l);
    l.setBounds(50,300,100,15);
    f.setLayout(null);    
    f.setSize(gridSize, gridSize);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  void run()throws IOException
  {   

    System.out.println("arr.length = y = " + arr.length);
    System.out.println("arr[0].length = x = " + arr[0].length);

    genMines2(arr, getNoMines());
    
    //boardClear(arr);
    genNumbers(arr);
    /*
     * for(int k = 0; k <= 2; k ++)
     * {
     * for(int l = 0; l <= 2; l ++)
     * {
     * arr[k][l] = 5;
     * }
     * }
     */
    // System.out.println(test(arr));
    showSimple(arr);
    mineCheck(arr);
    writeOut(arr);
    System.out.println();
    System.out.println();
    System.out.println();

    for (int x = 0; x < arr[0].length; x++) 
    {
      System.out.print(arr[0][x] + " ");

    }
    for (int y = 0; y < arr.length; y++) 
    {
      System.out.println(arr[y][0]);
    }
  }
  
  
  void genNumbers(int[][] arr) 
  {
    int s1 = 0;
    int e1 = 0;
    int s2 = 0;
    int e2 = 0;
    for (int y = 0; y < arr.length; y++) 
    {
      for (int x = 0; x < arr[0].length; x++) 
      {     
        if (arr[y][x] == 9)
        {//Dont delete because it doesnt work without it?
          //x++;
        } 
        else 
        {
          
          if (arr[y][x] == 0) 
          {
            System.out.println("y " + y + " x " + x);
            s1 = y - 1;
            e1 = y + 1;
            s2 = x - 1;
            e2 = x + 1;
            if (y == 0) 
            {
              s1++;
            }
            if (y == (arr.length - 1)) 
            {
              e1--;
            }
            if (x == 0) {
              s2++;
            }
            if (x == (arr[0].length - 1)) 
            {
              e2--;
            }
          }
          arr[y][x] = numSearch(arr, s1, e1, s2, e2);
        }
        
      }
    }
  }

  int numSearch(int[][] arr, int s1, int e1, int s2, int e2) 
  {
    int count = 0;
    for (int u = s1; u <= e1; u++) 
    {
      for (int c = s2; c <= e2; c++) 
      {
        if (arr[u][c] == 9) 
        {
          count++;
        }
      }
    }
    return count;
  }

  int getNoMinesTest() 
  {
    int noMines = (int) (Math.random() * 200);
    return noMines;
  }

  int getNoMines() 
  {
    System.out.println("insert number of mines ");
    int noMines = keyboard.nextInt();
    keyboard.nextLine();
    return noMines;
  }

  void boardClear(int[][] arr) 
  {
    for (int i = 0; i < arr.length; i++) 
    {
      for (int j = 0; j < arr[0].length; j++) 
      {
        arr[i][j] = 0;
      }
    }
  }

  boolean test(int[][] arr) 
  {
    System.out.println("test ");
    boolean test = true;
    int noMines = 0;
    for (int i = 0; i < 100; i++) 
    {
      noMines = getNoMinesTest();
      genMines2(arr, noMines);
      if (mineCheck(arr) != noMines) 
      {
        System.out.println("false ");
        test = false;
      }
      boardClear(arr);
    }
    return test;
  }

  int mineCheck(int[][] arr) 
  {
    String space = System.lineSeparator();
    int count = 0;
    for (int i = 0; i < arr.length; i++) 
    {
      for (int j = 0; j < arr[0].length; j++) 
      {
        if (arr[i][j] == 9) 
        {
          count++;
        }
      }
    }
    System.out.println(space + "Number of Mines is " + count);
    return count;

  }

  void genMines2(int[][] arr, int noMines) 
  {

    int x = 0;
    int y = 0;
    for (int i = 0; i < noMines; i++) 
    {
      x = (int) (Math.random() * arr[0].length);
      y = (int) (Math.random() * arr.length);

      while (arr[y][x] == 9) 
      {
        x = (int) (Math.random() * arr[0].length);
        y = (int) (Math.random() * arr.length);

        if (arr[y][x] == 0) 
        {
          arr[y][x] = 9;
          break;
        }
        // System.out.println("Bob 2");
      }
      arr[y][x] = 9;
    }
  }

  void showSimple(int[][] arr) 
  {
    int b = 1;
    System.out.print("\n\n\n");
    for (int i = 0; i < arr[0].length; i++) 
    {
      if (i >= 10) {
        System.out.print((i - 10) + " ");
      } else {
        System.out.print(i + " ");
      }
    }
    System.out.println();
    System.out.println();
    for (int y = 0; y < arr.length; y++) 
    {
      for (int x = 0; x < arr[0].length; x++) 
      {

        System.out.print(arr[y][x] + " ");
        if (b % arr[0].length == 0) 
        {
          System.out.println();
        }
        b++;
      }
    }
  }
  void writeOut(int[][] arr)throws IOException
  {
    PrintWriter fileWriter = new PrintWriter("arr.txt");
    int x = 0;
    for(int i = 0; i < arr.length; i++) 
    {
      for(int j = 0; j < arr[0].length; j++)
      {
        if(i != x)
        {
          fileWriter.println();
          x++;
        }
        fileWriter.print(arr[i][j] + ",");
      }
    }
    fileWriter.close();
  }
}
