import java.io.*;
import java.util.*;
/**
 * Ken Johnson
 * Friday April 21st, 2017
 * 
 * Confirmed: The program is creating the correct number of nodes.
 */
public class TreeNode {
    private TreeNode _parent;
    private TreeNode[] _children;
    private char[] _value;
    private int _positionOfLastMove;
    private char _pieceOfLastMove;
    private int _level;
    public static int _numOfChildrenOfRoot = 9; // This is a class instance variable
    private String _outcome = "z";
    private static int _wins;
    private int _siblingNumber;
    private int _winningAncestors;
    
    /**
     * The constructor for objects of class Node.
     */
    public TreeNode()
    {
        _parent = null;
        _children = new TreeNode[_numOfChildrenOfRoot];
        _value = new char[9];
        _positionOfLastMove = -1;
        _pieceOfLastMove = 'z';
        _level = -1;
        _siblingNumber = 9;
        _winningAncestors = 0;

        // Initialise _children
        for (int i = 0; i < _children.length; i++)
        {
            _children[i] = null;
        }

        // Initialise _value
        for (int i = 0; i < _value.length; i++)
        {
            _value[i] = 'z';
        }
    }
    
    /**
     * Uses recursion to create tree.
     */
    public static TreeNode tree(TreeNode root, int numOfChildren)
    {
        for (int a = 0; a < numOfChildren; a++){
            TreeNode child = root.addChild();
            tree(child, numOfChildren - 1);
        }
        return root;
    }

    public TreeNode addChild() {
        TreeNode childNode = new TreeNode();
        childNode._parent = this;
        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] == null)
            {
                _children[i] = childNode;
                break;
            }
        }
        childNode.nextMove(childNode);
        return childNode;
    }
    
    public static void traverse(TreeNode root)
    {
        for (int i = 0; i < root._children.length; i++){
            if (root._children[i] != null){
                if (root.getLevel() > 4){
                    findWins(root, root.getLevel());
                }
                traverse(root._children[i]);
            }
        }
    }
    
    public char[] nextMove(TreeNode child)
    {
        char nextMove = 'z';
        char[] parentValue;
        TreeNode parent;
        TreeNode previousSibling;
        parentValue = new char[9];
        previousSibling = child.getPreviousSibling();                
        parent = child._parent;
        parentValue = _parent.getValue();
        _level = parent.getLevel() + 1;        

        // This code is working. JP
        if (getLevel() % 2 == 1){
            nextMove = 'X';
        } else {
            nextMove = 'O';
        }
        
        // This code is working. JP
        if (getPreviousSibling() == null || isRoot() == true){
            // This TreeNode is first sibling.
            _positionOfLastMove = 0;
        } else {
            // This TreeNode is not first sibling.
            _positionOfLastMove = previousSibling.getPositionOfLastMove() + 1;
        }
        
        // Copy parent node onto child node
        for (int j = 0; j < 9; j++){
            _value[j] = parentValue[j];
        }
        
        for (int i = _positionOfLastMove; i < _value.length; i++){
            if (_value[i] == 'z') {
                child._positionOfLastMove = i;
                makeMove(nextMove, i);
                break;
            }
        }
        return _value;
    }
    
    /**
     * Using recursion to get level of tree.
     */
    public int getLevel() {
        if (this.isRoot()){
            return 0;
        } else {
            return _parent.getLevel() + 1;
        }
    }
    
    /**
     * Used to find level.
     */
    public boolean isRoot() {
        if (_parent == null){
            return true;
        } else {
            return false;
        }
    }

    public TreeNode getPreviousSibling()
    {
        TreeNode[] siblings = _parent.getChildren();

        if (siblings[0] == this)
        {
            return null; // There's no previous sibling so return null.
        }

        for (int i = 0; i < siblings.length; i++)
        {
            TreeNode current = siblings[i];
            if (current == this)
            {
                return siblings[i - 1];
            }
        }

        return null; // This line should never execute.
    }

    // Returns true if the move was successful.
    public boolean makeMove(char piece, int position)
    {
        if (position > -1 && position < 9)
        {
            if (_value[position] == 'z') // Check the position is available.
            {
                _value[position] = piece;
                _positionOfLastMove = position;
                return true;
            }
        }
        return false;
    }
    
    public TreeNode getParent()
    {
        return _parent;
    }

    public void setParent(TreeNode parent)
    {
        _parent = parent;
    }

    public TreeNode[] getChildren()
    {
        return _children;
    }

    public void setChildren(TreeNode[] children)
    {
        _children = children;
    }

    public char[] getValue()
    {
        return _value;
    }

    public void setValue(char[] value)
    {
        _value = value;
    }

    public int getPositionOfLastMove()
    {
        return _positionOfLastMove;
    }

    public void setPositionOfLastMove(int positionOfLastMove)
    {
        _positionOfLastMove = positionOfLastMove;
    }

    public char getPieceOfLastMove()
    {
        return _pieceOfLastMove;
    }

    public void setPieceOfLastMove(char pieceOfLastMove)
    {
        _pieceOfLastMove = pieceOfLastMove;
    }

    public char[] getValueOfParent()
    {
        return _parent.getValue();
    }
    
    public static void setWins(int wins)
    {
        _wins = wins;
    }
    
    public static int getWins()
    {
        return _wins;
    }
    
    public int siblingNumber(TreeNode current)
    {
        _siblingNumber--;
        if (current.getPreviousSibling().equals(null))
        {
            return _siblingNumber;
        }
        else
        {
            siblingNumber(current.getPreviousSibling());
        }
        return 0; //This line should never run
    }
    
    public boolean firstLevel(TreeNode current)
    {
        if (current.getParent().getParent().equals(null))
        {
            return true;
        } else {
            return false;
        }
    }

    public int getWinningAncestors()
    {
        return _winningAncestors;
    }

    /*
     * 'level' must be set to 5, 6, 7, 8 or 9.
     * 
     * ALGORITHM
     * (1) Check if the current node is on the correct level.
     * (2) If it is, check if the current node is a winning node.
     */
    public static void findWins(TreeNode treeNode, int level)
    {
        TreeNode[] children = treeNode.getChildren();
        int i = 0;
        TreeNode current = children[i];

        while (current != null && i < children.length)
        {
            if (current.getLevel() <= level) // Check if the current node is on the correct level.
            {
                current.winning(); // Check if the current node is a winning node.
                findWins(current, level);
            }
            else
            {
                findWins(current, level);
            }

            current = children[i++];
        }
        return;
    }
    
    public boolean winning()
    {
        if (_value[0] == 'X' && _value[1] == 'X' && _value[2] == 'X') {_outcome = "W";} // First row
        if (_value[3] == 'X' && _value[4] == 'X' && _value[5] == 'X') {_outcome = "W";} // Second row
        if (_value[6] == 'X' && _value[7] == 'X' && _value[8] == 'X') {_outcome = "W";} // Third row
        if (_value[0] == 'X' && _value[3] == 'X' && _value[6] == 'X') {_outcome = "W";} // First column
        if (_value[1] == 'X' && _value[4] == 'X' && _value[7] == 'X') {_outcome = "W";} // Second column
        if (_value[2] == 'X' && _value[5] == 'X' && _value[8] == 'X') {_outcome = "W";} // Third column
        if (_value[0] == 'X' && _value[4] == 'X' && _value[8] == 'X') {_outcome = "W";} // Left/right diagonal
        if (_value[2] == 'X' && _value[4] == 'X' && _value[6] == 'X') {_outcome = "W";} // Right/left diagonal
    
        if (_value[0] == 'O' && _value[1] == 'O' && _value[2] == 'O') {_outcome = "L";} // First row
        if (_value[3] == 'O' && _value[4] == 'O' && _value[5] == 'O') {_outcome = "L";} // Second row
        if (_value[6] == 'O' && _value[7] == 'O' && _value[8] == 'O') {_outcome = "L";} // Third row
        if (_value[0] == 'O' && _value[3] == 'O' && _value[6] == 'O') {_outcome = "L";} // First column
        if (_value[1] == 'O' && _value[4] == 'O' && _value[7] == 'O') {_outcome = "L";} // Second column
        if (_value[2] == 'O' && _value[5] == 'O' && _value[8] == 'O') {_outcome = "L";} // Third column
        if (_value[0] == 'O' && _value[4] == 'O' && _value[8] == 'O') {_outcome = "L";} // Left/right diagonal
        if (_value[2] == 'O' && _value[4] == 'O' && _value[6] == 'O') {_outcome = "L";} // Right/left diagonal
            
        if (_outcome.equals("W")){
            _wins++;
            //System.out.println(_wins);
            printValuesOfAncestors(this); // JP
        }
        
        if (_outcome.equals("L") || _outcome.equals("W")){
            return true;
        }
        
        return false;
    }
    
    public void printValue()
    {
        System.out.print(_value[0]);
        System.out.print(_value[1]);
        System.out.println(_value[2]);
        System.out.print(_value[3]);
        System.out.print(_value[4]);
        System.out.println(_value[5]);
        System.out.print(_value[6]);
        System.out.print(_value[7]);
        System.out.println(_value[8]);
        System.out.println("");
    }
    
    public static void printValuesOfAncestors(TreeNode treeNode)
    {
        if (treeNode == null) // Base case
        {
            return;
        }

        //treeNode.printValue();

        printValuesOfAncestors(treeNode.getParent());

        if (treeNode.getLevel() == 1)
            treeNode._winningAncestors++;

        return;
    }
}