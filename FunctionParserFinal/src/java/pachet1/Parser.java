/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pachet1;

import java.util.*;
/**
 *
 * @author Rodica
 */
class Tree
{
    @SuppressWarnings("FieldMayBeFinal")
    private String node;
    private Tree left, right, father;
    
    Tree()
    {
        node="";
        left=right=father=null;
    }
    
    Tree(String node)
    {
        this.node=node;
        left=right=father=null;
    }
    
    void setFather(Tree t)
    {
        father=t;
    }
    
    void insertLeft(Tree t)
    {
        left=t;
    }
    
    void insertRight(Tree t)
    {
        right=t;
    }
    
    Tree getFather()
    {
        return father;
    }
    
    Tree getLeft()
    {
        return left;
    }
    
    Tree getRight()
    {
        return right;
    }
    
    String getNode()
    {
        return node;
    }
}

public class Parser 
{
    Tree root;
    
    Parser()
    {
        root=null;
    }
    
    Tree create(Tree t, String str, Scanner sc) // the tree to be constructed from the string str
    {
        Tree t1=t; // t1 is the root of the current subtree (including the main tree itself)
        while (sc.hasNext())
        {
            String s1=sc.next();// one operator or operand
            if (s1.equals("("))
            {
                Tree bt=create(null, str, sc); // subtree
                if (t==null)
                {
                    t1=t=bt;  // root
                }
                else
                {
                    if (t.getLeft()==null)
                    {
                        t.insertLeft(bt);
                    }
                    else
                    {
                        t.insertRight(bt);
                    }
                    bt.setFather(t); // root of the subtree becomes child of the current node
                    if (t.getNode().equals("sin") || t.getNode().equals("cos") || 
                           t.getNode().equals("tg") || t.getNode().equals("ctg") || 
                           t.getNode().equals("arcsin") || t.getNode().equals("arccos") || 
                           t.getNode().equals("arctg") || t.getNode().equals("arcctg") ||
                           t.getNode().equals("pow") || t.getNode().equals("log"))
                    { }
                    else
                    {
                        t=bt;
                    }
                }
            }
            else if (s1.equals(")"))
            {
                break;  //end of subtree, return to parent tree
            }
            else
            {
                Tree bt=new Tree(s1); // new node
                if (t1==null)  // first element, set as the current root
                {
                    t=t1=bt;
                }
                else
                {
                    if (s1.equals("+") || s1.equals("-")) // operator takes 2 arguments, lowest precedence, therefore highest in the tree
                    {
                        bt.insertLeft(t1); // rotates the root left
                        if (t1.getFather()!=null) 
                        {
                            bt.setFather(t1.getFather()); // t1 has a father
                        }
                        t1.setFather(bt);
                        t1=t=bt; // current node and root of the subtree become the new node
                    }
                    else if (s1.equals("*") || s1.equals("/") || s1.equals("pow") || 
                            s1.equals("log")) // 2 arguments, high precedence 
                    {
                        bt.setFather(t.getFather()); // new node replaces current node
                        if (t.getFather()!=null) // links the new node
                        {
                            if (t==t.getFather().getLeft())
                            {
                                t.getFather().insertLeft(bt);
                            }
                            if (t==t.getFather().getRight())
                            {
                                t.getFather().insertRight(bt);
                            }
                        }
                        else
                        {
                            t1=bt;
                        }
                        bt.insertLeft(t); // current node is inserted left to the new node
                        t.setFather(bt);
                        t=bt; // current node becomes the new node
                    }
                    else if (s1.equals("sin") || s1.equals("cos") || s1.equals("tg") || 
                            s1.equals("ctg") || s1.equals("arcsin") || s1.equals("arccos") 
                            || s1.equals("arctg") || s1.equals("arcctg") || 
                            s1.equals("--")) // one argument, highest precedence
                    {
                        if (t.getLeft()==null) // inserts new node in the available place
                        {
                            t.insertLeft(bt);                            
                        }
                        else
                        {
                            t.insertRight(bt); 
                        }
                        bt.setFather(t);
                        t=bt;
                    }
                    else
                    {
                        if (t.getLeft()==null) // inserts a (temporary) leaf
                        {
                            t.insertLeft(bt);                            
                        }
                        else
                        {
                            t.insertRight(bt); 
                        }
                        bt.setFather(t);
                        if (t.getNode().equals("sin") || t.getNode().equals("cos") || 
                           t.getNode().equals("tg") || t.getNode().equals("ctg") || 
                           t.getNode().equals("arcsin") || t.getNode().equals("arccos") || 
                           t.getNode().equals("arctg") || t.getNode().equals("arcctg") ||
                           t.getNode().equals("pow") || t.getNode().equals("log"))
                        { }
                        else
                        {
                            t=bt;
                        }
                    }
                }
            }
        }
        return t1; // root of the (sub)tree
    }
    
    double calculate(Tree t, double x)
    {
        if (t!=null)
        {
            if (t.getNode().equals("x")) // variable substitution
            {
                return x;
            }
            else if (t.getNode().equals("--"))
            {
                return -calculate(t.getLeft(), x);
            }
            else if (t.getNode().equals("+"))
            {
                return calculate(t.getLeft(), x)+calculate(t.getRight(), x);
            }
            else if (t.getNode().equals("-"))
            {
                return calculate(t.getLeft(), x)-calculate(t.getRight(), x);
            }
            else if (t.getNode().equals("*"))
            {
                return calculate(t.getLeft(), x)*calculate(t.getRight(), x);
            }
            else if (t.getNode().equals("/"))
            {
                return calculate(t.getLeft(), x)/calculate(t.getRight(), x);
            }
            else if (t.getNode().equals("log"))
            {
                return Math.log(calculate(t.getRight(), x)) / Math.log(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("pow"))
            {
                return Math.pow(calculate(t.getLeft(), x), calculate(t.getRight(), x));
            }
            else if (t.getNode().equals("sin"))
            {
                return Math.sin(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("cos"))
            {
                return Math.cos(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("tg"))
            {
                return Math.tan(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("ctg"))
            {
                return 1/Math.tan(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("arcsin"))
            {
                return Math.asin(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("arccos"))
            {
                return Math.acos(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("arctg"))
            {
                return Math.atan(calculate(t.getLeft(), x));
            }
            else if (t.getNode().equals("arcctg"))
            {
                return Math.PI/2 - Math.atan(calculate(t.getLeft(), x));
            }
            else // number
            {
                return Double.parseDouble(t.getNode());
            }
        }
        else 
        {
            return 0;
        }
    }
    
    void print(Tree t)
    {
        if (t!=null)
        {
            System.out.print("{");
            print(t.getLeft());
            System.out.print(t.getNode());
            print(t.getRight());
            System.out.print("}");
        }
    }
    
    public static void main(String args[])
    {
        Parser p=new Parser();
        String str="x * cos 3 * x pow 2";///*"2 + ( -- x )";//3 + x - ( -- x + 2 ) * 3"; //"2 pow x";//"cos x"; ///*"-- 2 * 4 + 5 - 7""( cos ( 2 pow 3 ) + 1 ) * 3 - 5";*/"x * sin x + cos ( 3 + x pow 2 ) * 2";
        Scanner sc=new Scanner(str);
        p.root=p.create(p.root, str, sc);
        //p.print(p.root);
        double d=p.calculate(p.root, 3);
        System.out.println(d);
    }
}