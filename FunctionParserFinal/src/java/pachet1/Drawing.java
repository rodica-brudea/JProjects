/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pachet1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rodica
 */
@WebServlet(name = "Drawing", urlPatterns = {"/Drawing"})
public class Drawing extends HttpServlet 
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     */
    String st=null;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        boolean[] corect={true};
        PrintWriter out = response.getWriter();
        try 
        {
            String html="";
            html+="<!DOCTYPE html> <html> <head> <title> Function Parser</title> </head> <body> ";
            String str=request.getParameter("fcn");
            if (str==null || str.equals("") || str.equals(" "))
            {
                html+="<form method='GET' id='form2' action='Function'>";
                html+="<input type='hidden' name='corect' id='corect' value='red' />";
                html+="<input type='hidden' name='fcn' id='fcn' value='no_function_input' />";
                html+="</form> ";
                html+="<script>"
                    + "document.getElementById('form2').submit(); "
                    + "</script>";
                html+="</body> </html>"; 
                out.println(html);
                out.close();        
            }
            else
            {
                String var=request.getParameter("valoare");
                String lim=request.getParameter("limita");
                String res="";
                if (lim.equals(""))
                {
                    res=verify(str, var, corect);  
                }
                else
                {   String prim=verify(str, var, corect);
                    double d1=0, d2=0;
                    try
                    {
                        d1=Double.parseDouble(prim);
                    }
                    catch(NumberFormatException e)
                    {
                        corect[0]=false;
                        res=prim;
                    }
                    try
                    {
                        d2=Double.parseDouble(lim);
                    }
                    catch(NumberFormatException e)
                    {
                        corect[0]=false;
                        res="incorrect_value_for_upper_bound";
                    }
                    if (res.equals(""))
                    {
                        res=null;
                    }
                    if (res==null)
                    {
                        d1=Double.parseDouble(var);
                        if (d2<d1)
                        {
                            double aux=d1;
                            d1=d2;
                            d2=aux;
                            
                            String aux2=var;
                            var=lim;
                            lim=aux2;
                        }
                        
                        html+="<canvas id='desen' width='100' height='100'> </canvas>";
                        
                        html+=draw(str, d1, d2);
                    }
                }
                html+="<form method='get' id='form2' action='Function'>";
                if (corect[0]==true)
                {
                    html+="<input type='hidden' name='corect' id='corect' value='green' />";
                }
                else
                {
                    html+="<input type='hidden' name='corect' id='corect' value='red' />";
                }

                String a[]=new String[str.length()];
                Scanner sc=new Scanner(str);
                int j=0;
                while (sc.hasNext())
                {
                    a[j]=sc.next();
                    ++j;
                }
                sc.close();
                String fn="";
                for (int i=0; i<j-1; i++)
                {
                    fn+=a[i]+"_";
                }
                fn+=a[j-1];

                html+="<input type='hidden' name='fcn' id='fcn' value="+res+" />";

                if (lim.equals("") || res!=null)
                {          
                    html+="<input type='hidden' name='fcn2' id='fcn2' value="+fn+" />";
                    html+="<input type='hidden' name='v2' id='v2' value="+var+" />";
                    html+="</form> ";
                    html+="<script>"
                        + "document.getElementById('form2').submit(); "
                        + "</script>";
                }
                else
                {
                    html+="<br />&nbsp;&nbsp;&nbsp;&nbsp;<input type='submit' value='back' "
                            + "style='position:absolute; left:20px; top:20px; ' />";
                    html+="&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<label style='position:absolute; left:80px; top:20px; '"
                            + ">x = ["+var+", "+lim+"]</label>";
                    html+="&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<label style='position:absolute; left:80px; top:60px; '"
                            + ">f(x) = "+str+"</label>";
                    html+="</form> ";
                }
                html+="</body> </html>"; 
                out.println(html);
            } 
        }
        finally 
        {
            out.close();
        }
    }
        
    String verify(String str, String var, boolean[] corect)
    {
        double x;
        corect[0]=false;
        
        if (var.equals("NaN"))
        {
            return "incorrect_value_for_x";
        }
        try
        {
            x=Double.parseDouble(var);
        }
        catch(NumberFormatException e)
        {
            return "incorrect_value_for_x";
        }
        String a[]=new String[str.length()];
        Scanner sc=new Scanner(str);
        int j=0;
        while (sc.hasNext())
        {
            a[j]=sc.next();
            if (a[j].equals("") || a[j].equals(" "))
            {
                --j;
            }
            ++j;
        }
        String c="", res="";
        int po=0, pc=0;
        for (int i=0; i<j; i++)
        {
            if (a[i].equals("e"))
            {
                a[i]=""+Math.E;
            }
            if (a[i].equals("pi"))
            {
                a[i]=""+Math.PI;
            }
            if (c.equals(""))
            {
                if (a[i].equals("pow") || a[i].equals("log") || a[i].equals("+") ||
                        a[i].equals("*") || a[i].equals("/") || a[i].equals("."))
                {
                    return "incorrect_function";
                }
                if (a[i].equals("-"))
                {
                    a[i]="--";
                }
            }
            else
            {
               if (Character.isDigit(c.charAt(c.length()-1)) || 
                       c.equals("x") || c.equals("pi") || c.equals("e"))
               {
                   if (Character.isDigit(c.charAt(c.length()-1)))
                   {
                       try
                       {
                           double d=Double.parseDouble(c);
                       }
                       catch(NumberFormatException e)
                       {
                           return "incorrect_number";
                       }
                   } 
                   if (!a[i].equals("+") && !a[i].equals("-") && !a[i].equals("*") && 
                           !a[i].equals("/") && !a[i].equals("pow") && !a[i].equals("log") &&
                           !a[i].equals(")"))
                   {
                       return "incorrect_function_after_number";
                   }
               }
               if (a[i].substring(a[i].length()-1).equals("."))
               {
                   return "single_dot";
               }
               
               if (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/"))
               {
                   if (a[i].equals("+") || a[i].equals("-") || a[i].equals("*") || 
                           a[i].equals("/") || a[i].equals("pow") || a[i].equals("log"))
                   {
                       return "incorrect_parameter_after_operator";
                   }
               }    
               if (c.equals("pow") || c.equals("log") || 
                       c.equals("sin") || c.equals("cos") || c.equals("tg") || 
                        c.equals("ctg") || c.equals("arcsin") || c.equals("arccos") || 
                       c.equals("arctg") || c.equals("arcctg"))
               {
                   if (!a[i].equals("x") && !a[i].equals("e") && !a[i].equals("pi") &&
                           !Character.isDigit(a[i].charAt(0)) && !a[i].equals("("))
                   {
                       return "incorrect_parameter_after_function";
                   }
               }
               if (c.equals("("))
               {
                   if (a[i].equals("+") || a[i].equals("*") || a[i].equals("/") || 
                           a[i].equals("pow") || a[i].equals("log") || a[i].equals(")"))
                   {
                       return "incorrect_function_after_open_parenthesis";
                   }
                   if (a[i].equals("-"))
                   {
                       a[i]="--";
                   }
               }
                if (a[i].equals(")"))
               {
                   if (c.equals("+") || c.equals("-") || c.equals("*") || 
                           c.equals("/") || c.equals("pow") || c.equals("log") || 
                           c.equals("sin") || c.equals("cos") || c.equals("tg") || 
                           c.equals("ctg") || c.equals("arcsin") || c.equals("arccos") || 
                           c.equals("arctg") || c.equals("arcctg"))
                   {
                       return "incorrect_function_before_closing_parenthesis";
                   }
               }
            }
            if (a[i].equals("("))
            {
                ++po;
            }
            if (a[i].equals(")"))
            {
                ++pc;
            }
            if (pc>po)
            {
                return "closing_parenthesis_greater_then_opem";
            }
            c=a[i];
        }
        if (po!=pc)
        {
            return "parenthesis_do_not_match";
        }
        if (c.equals("+") || c.equals("-") || c.equals("*") || 
            c.equals("/") || c.equals("pow") || c.equals("log") || 
            c.equals("sin") || c.equals("cos") || c.equals("tg") || 
            c.equals("ctg") || c.equals("arcsin") || c.equals("arccos") || 
            c.equals("arctg") || c.equals("arcctg"))
        {
            return "last_symbol_is_incorrect";
        }
        st="";
        for (int i=0; i<j; i++)
        {
            st+=a[i]+" ";
        }
        Parser p=new Parser();
        sc=new Scanner(st);
        p.root=p.create(p.root, st, sc);
        double d=p.calculate(p.root, x);
        res+=d;
        sc.close();
        corect[0]=true;
        return res;
    }
    
    String draw(String str, double d1, double d2)
    {
        String html="";
        Parser p=new Parser();
        Scanner sc=new Scanner(st);
        p.root=p.create(p.root, st, sc);
        int n1=(int)(d1*40);
        int n2=(int)(d2*40);
        
        html+="<script> "
                + "     w = window.innerWidth; " 
                + "     h = window.innerHeight; "
                + "     desen=document.getElementById('desen'); "
                + "     desen.width=w; "
                + "     desen.height=h; "
                + "     ctx = desen.getContext('2d'); "
                
                + "     ctx.translate(50,50); "
                
                + "     ctx.beginPath(); "
                + "     ctx.moveTo(w/2-50,0); "
                + "     ctx.lineTo(w/2-50,h-100); "
                + "     ctx.stroke(); "
                
                + "     ctx.beginPath(); "
                + "     ctx.moveTo(0,h/2-50); "
                + "     ctx.lineTo(w-100,h/2-50); "
                + "     ctx.stroke(); "
                
                + "     ctx.beginPath(); "
                + "     ctx.font='12px sans-serif'; "
                + "     for (var i=w/2-90, j=w/2-10, k=1; i>0, j<w-100; i-=40, j+=40, k++) "
                + "     { "
                + "         ctx.moveTo(i, h/2-48); "
                + "         ctx.lineTo(i, h/2-52); "
                + "         ctx.fillText('-'+k,i-8,h/2-35);"
                
                + "         ctx.moveTo(j, h/2-48); "
                + "         ctx.lineTo(j, h/2-52); "
                + "         ctx.fillText(''+k,j-8,h/2-35);"                
                + "     } "   
                
                + "     for (var i=h/2-90, j=h/2-10, k=1; i>0, j<h-100; i-=40, j+=40, k++) "
                + "     { "
                + "         ctx.moveTo(w/2-48, i); "
                + "         ctx.lineTo(w/2-52, i); "
                + "         ctx.fillText(''+k,w/2-70,i);"
                
                + "         ctx.moveTo(w/2-48, j); "
                + "         ctx.lineTo(w/2-52, j); "
                + "         ctx.fillText('-'+k,w/2-70,j);"              
                + "     } " 
                + "     ctx.stroke(); "				
                
                + "     var l=document.createElement('label'); "
                + "     l.innerHTML='X'; "
                + "     l.style.position='absolute'; "
                + "     l.style.left=(w-50)+'px'; "
                + "     l.style.top=(h/2+15)+'px'; "
                + "     l.style.color='blue'; "
                + "     document.body.appendChild(l); "               
                
                + "     var l=document.createElement('label'); "
                + "     l.innerHTML='f(X)'; "
                + "     l.style.position='absolute'; "
                + "     l.style.left=(w/2-30)+'px'; "
                + "     l.style.top=50+'px'; "
                + "     l.style.color='blue'; "
                + "     document.body.appendChild(l); "
                
                + "     elem=document.createElement('pct'); "
                + "     pt=document.createTextNode('0'); "
                + "     elem.style.position='absolute'; "
                + "     elem.style.color='rgb(255, 0, 0)'; "
                + "     elem.style.left=(w/2 - 15)+'px'; "
                + "     elem.style.top=(h/2 + 15)+'px'; "
                + "     elem.appendChild(pt); "
                + "     document.body.appendChild(elem); "
                + "     f2(ctx); ";
        
        html+=" function f2(ctx) "
                + "{ var elem; "
                + " var pt; "
                + " var m1; "
                + " var m2; "
                + " var m3; "
                + " var m4; "
                + "var w=window.innerWidth; "
                + "var h=window.innerHeight; ";
        double d;
        int m1, m2, m3, m4;
        d=p.calculate(p.root, (double)(n1/40.0));
        m1=n1;
        m2=(int)(d*40);
        
        html+= "    m1="+m1+"; "
                    + " m2="+m2+"; "
                    + " elem=document.createElement('pct'); "
                    + " pt=document.createTextNode('.'); "
                    + " elem.style.position='absolute'; "
                    + " elem.style.color='blue'; "
                    + " elem.style.left=(w/2 + parseInt(m1)+6)+'px'; "
                    + " elem.style.top=(h/2 - parseInt(m2)-6)+'px'; "
                    + " elem.appendChild(pt); "
                    + " document.body.appendChild(elem); ";
        
        for (int i=n1+1; i<=n2; i++)
        {
            d=p.calculate(p.root, (double)(i/40.0));
            m3=m1;
            m4=m2;
            m1=i;
            m2=(int)(d*40);
            html+=  " m1="+m1+"; "
                    + " m2="+m2+"; "
                    + " m3="+m3+"; "
                    + " m4="+m4+"; "
                    + " ctx.beginPath(); "
                    + " ctx.strokeStyle='blue'; "
                    + " if (Math.abs(m2-m4)<1000) "
                    + " { "
                    + "     ctx.moveTo(w/2-50+parseInt(m1),h/2-50-parseInt(m2)); "
                    + "     ctx.lineTo(w/2-50+parseInt(m3),h/2-50-parseInt(m4)); "
                    + "     ctx.stroke(); "
                    + " } ";
        }
        html+=" } </script> ";
        sc.close();
        return html;
    }
}

/*synchronized String draw2(String str, double d1, double d2)
    {
        String html="";
        Parser p=new Parser();
        Scanner sc=new Scanner(st);
        p.root=p.create(p.root, st, sc);
        int n1=(int)(d1*20);
        int n2=(int)(d2*20);
        html+="<script> "
                + "window.onload = f1; "
                + "function f1() "
                + "{  "
                + "     var w = window.innerWidth; " 
                + "     var h = window.innerHeight; "
                + "     var l=document.createElement('label'); "
                + "     l.innerHTML='x'; "
                + "     l.style.position='absolute'; "
                + "     l.style.left=(w-50)+'px'; "
                + "     l.style.top=(h/2+15)+'px'; "
                + "     document.body.appendChild(l); "               
                + "     for (i=50; i<=h-50; i++) "
                + "     { "
                + "         var l=document.createElement('label'); "
                + "         l.innerHTML='.'; "
                + "         l.style.position='absolute'; "
                + "         l.style.left=w/2+'px'; "
                + "         l.style.top=i+'px'; "
                + "         document.body.appendChild(l); "
                + "     } "
                + "     var l=document.createElement('label'); "
                + "     l.innerHTML='f(x)'; "
                + "     l.style.position='absolute'; "
                + "     l.style.left=(w/2-30)+'px'; "
                + "     l.style.top=50+'px'; "
                + "     document.body.appendChild(l); "
                + "     for (i=50; i<=w-50; i++) "
                + "     { "
                + "         var l=document.createElement('label'); "
                + "         l.innerHTML='.'; "
                + "         l.style.position='absolute'; "
                + "         l.style.left=i+'px'; "
                + "         l.style.top=h/2+'px'; "
                + "         document.body.appendChild(l); "
                + "     } "
                + "     elem=document.createElement('pct'); "
                + "     pt=document.createTextNode('0'); "
                + "     elem.style.position='absolute'; "
                + "     elem.style.color='rgb(255, 0, 0)'; "
                + "     elem.style.left=(w/2 - 15)+'px'; "
                + "     elem.style.top=(h/2 + 15)+'px'; "
                + "     elem.appendChild(pt); "
                + "     document.body.appendChild(elem); "
                + "     f2(); "
                + "} ";
        html+=" function f2() "
                + "{ var elem; "
                + " var pt; "
                + " var m1; "
                + " var m2; "
                + "var w=window.innerWidth; "
                + "var h=window.innerHeight; ";
        double d;
        int m1, m2;
        for (int i=n1; i<=n2; i++)
        {
            d=p.calculate(p.root, (double)(i/20.0));
            m1=(int)(i/2);
            m2=(int)(d*10);
            html+=" m1='"+m1+"'; "
                    + " m2='"+m2+"'; "
                    + " elem=document.createElement('pct'); "
                    + " pt=document.createTextNode('.'); "
                    + " elem.style.position='absolute'; "
                    + " elem.style.color='rgb(0, 0, 255)'; "
                    + " elem.style.left=(w/2 + parseInt(m1))+'px'; "
                    + " elem.style.top=(h/2 - parseInt(m2))+'px'; "
                    + " elem.appendChild(pt); "
                    + " document.body.appendChild(elem); ";
        }
        html+=" } </script> ";
        sc.close();
        return html;
    }*/