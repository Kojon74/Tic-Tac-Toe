import java.util.Scanner;
import java.util.*;
/**
 * Write a description of class UserInterface here.
 * 
 * @author (Ken Johnson)
 */
public class UserInterface
{
    Scanner scanner = new Scanner(System.in);
    private double _numberOfWinning;
    private double _wins;
    private double _chance;
    
    public UserInterface()
    {
        intro();
    }
    
    public void intro()
    {
        for (int x = 0; x < 20; x++){
            System.out.println("");
        }
        System.out.println("Welcome to Ken's Tic-Tac-Toe cheat sheet!");
        grid();
        System.out.println("Enter number corrisponding to grid ");
        System.out.println("to find the chance of winning compared ");
        System.out.println("to other first moves.");
        String input1 = scanner.nextLine();
        int input2 = Integer.parseInt(input1) - 1;
        _numberOfWinning = 0.0;
        _wins = 0.0;
        _chance = 0.0;
        if (input2 <= 9) {
            TreeNode root = new TreeNode();
            TreeNode.tree(root, TreeNode._numOfChildrenOfRoot);
            TreeNode.findWins(root, 9);
            TreeNode[] childrenOfRoot = root.getChildren(); // JP
            _numberOfWinning = childrenOfRoot[input2].getWinningAncestors();
            _wins = TreeNode.getWins();
            _chance = (_numberOfWinning/2255268)*100;
            System.out.println("");
            int iNumberOfWinning = (int) _numberOfWinning;
            int iWins = (int) _wins;
            System.out.println("The chance of winning by starting with move " + (input2 + 1) + " is: " + iNumberOfWinning + "/2255268");
            System.out.println("");
            round2();
        } else {
            System.out.println("Please enter a number within the specified range ");
        }
    }
    
    public void grid()
    {
        System.out.print("The numbers in the grid below will be referred to later on in the program, ");
        System.out.println("scroll back up if you need to see it.");
        System.out.println("");
        System.out.println("1 | 2 | 3");
        System.out.println("– – – – –");
        System.out.println("4 | 5 | 6");
        System.out.println("– – – – –");
        System.out.println("7 | 8 | 9");
        System.out.println("");
    }
    
    public void round2()
    {
        System.out.println("If you would like to view this as a percentage, press 1.");
        System.out.println("To find the chance of winning from a different starting position press 2.");
        System.out.println("To end the program press 3");
        String input3 = scanner.nextLine();
        int input4 = Integer.parseInt(input3);
        if (input4 == 1) {
            System.out.println("");
            System.out.print("The percentage is: ");
            System.out.printf("%.2f", _chance);
            System.out.println("%");
            System.out.println("");
            System.out.println("To find the chance of winning from a different starting position press 2.");
            System.out.println("To end the program press 3");
            String input5 = scanner.nextLine();
            int input6 = Integer.parseInt(input5);
            if (input6 == 2){
                intro();
            } else if (input6 == 3) {
                return;
            }
        } else if (input4 == 2) {
            intro();
        } else if (input4 == 3) {
            return;
        } else {
            System.out.println("");
            System.out.println("Error, enter one of the specified numbers");
            System.out.println("");
            round2();
        }
    }
}
