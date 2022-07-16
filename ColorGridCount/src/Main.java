import java.awt.*;
import java.sql.Array;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {

    static int COUNT;
    static Color[][] visited;
    static Color result[][];

    public static void main(String[] args){
        int rows = 3;
        int cols = 3;
        Color[][] colorGrid = createGame(rows,cols);

        visited = new Color[rows][cols];
        result = new Color[rows][cols];

        findLargestColorGrid(colorGrid);
    }

     public static Color[][] createGame(int row, int col) {

         Color[][] colorGrid = new Color[row][col];
         Random random = new Random();

         Arrays.setAll(colorGrid, x -> {
             Arrays.setAll(colorGrid[x], y -> new Color(random.nextInt(255),
                     random.nextInt(255), random.nextInt(255)));
             return colorGrid[x];
         });

//         colorGrid[0][0] = new Color(0);
//         colorGrid[0][1] = new Color(0);
//         colorGrid[0][2] = new Color(0);
//
//         colorGrid[1][0] = new Color(0);
//         colorGrid[1][1] = new Color(255);
//         colorGrid[1][2] = new Color(0);
//
//         colorGrid[2][0] = new Color(255);
//         colorGrid[2][1] = new Color(255);
//         colorGrid[2][2] = new Color(255);
//
//
//         for(int p=0; p<colorGrid.length; p++){
//             for(int q=0; q< colorGrid[p].length; q++){
//                 System.out.print(colorGrid[p][q] + " ");
//             }
//             System.out.println();
//         }

         return colorGrid;
    }


    public static void findLargestColorGrid(Color[][] colorGrid){
        int rows = colorGrid.length;
        int cols = colorGrid[0].length;
        int currentMax = Integer.MIN_VALUE;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                resetVisited(rows, cols);
                COUNT = 0;

                if (j + 1 < cols)
                    BFS(colorGrid[i][j], colorGrid[i][j + 1],
                            i, j, colorGrid);

                if (COUNT >= currentMax)
                {
                    currentMax = COUNT;
                    resetResult(colorGrid[i][j], colorGrid);
                }
                resetVisited(rows, cols);
                COUNT = 0;

                if (i + 1 < rows)
                    BFS(colorGrid[i][j],
                            colorGrid[i + 1][j], i, j, colorGrid);

                if (COUNT >= currentMax)
                {
                    currentMax = COUNT;
                    resetResult(colorGrid[i][j], colorGrid);
                }
            }
        }
        printResult(currentMax, rows, cols);
    }

    static boolean isValid(int x, int y, Color key, Color input[][])
    {
        if (x < input.length && y < input[0].length && x >= 0 && y >= 0)
        {
            if (visited[x][y] == null && input[x][y] == key)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    static void BFS(Color x, Color y, int i, int j, Color colorGrid[][])
    {
        if (!x.equals(y)) {
            return;
        }

        visited[i][j] = colorGrid[i][j];
        COUNT++;

        int x_moves[] = { 0, 0, 1, -1 };
        int y_moves[] = { 1, -1, 0, 0 };

        //Check adjacent nodes using recursion
        for (int u = 0; u < 4; u++)
            if ((isValid(i + y_moves[u], j + x_moves[u], x, colorGrid)) == true)
                BFS(x, y, i + y_moves[u],j + x_moves[u], colorGrid);
    }

    static void resetVisited(int rows, int cols)
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                visited[i][j] = null;
    }

    static void resetResult(Color key, Color colorGrid[][])
    {
        for (int i = 0; i < colorGrid.length; i++)
        {
            for (int j = 0; j < colorGrid[0].length; j++)
            {
                if (visited[i][j] != null && colorGrid[i][j] == key)
                    result[i][j] = visited[i][j];
                else
                    result[i][j] = null;
            }
        }
    }

    static void printResult(int res, int rows, int cols)
    {
        System.out.println ("The largest continues color grid size " + res );

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (result[i][j] != null)
                    System.out.print(result[i][j] + " ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
}
