/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pachet1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Rodica
 * 
 * instructiuni:
 * 
 *  stop pentru oprire inserare / stegere la pozitie, tastatura pentru introducere valoare x,
 *  butoane pentru scris functia
 */
@WebServlet(name = "Function", urlPatterns = {"/Function"})


public class Function extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
       
        String val=request.getQueryString();
        
        
        String [] val2=null;
        String [] val3;
        String fin="";
        String corect="";
        if (val!=null && !val.equals(""))
        {
            val2=val.split("&");
            
            
            corect=val2[0].split("=")[1];
            if (val2.length>2)
            {
                
                val3=val2[2].substring(5).split("_");
                for (int i=0; i<val3.length; i++)
                {
                    if (val3[i].equals("%2B"))
                    {
                        val3[i]="+";
                    }
                    if (val3[i].equals("%2F"))
                    {
                        val3[i]="/";
                    }
                    if (val3[i].equals("%28"))
                    {
                        val3[i]="(";
                    }
                    if (val3[i].equals("%29"))
                    {
                        val3[i]=")";
                    }
                    fin+=val3[i]+" ";
                }
            }
        }
        System.out.println("v = "+val);
        String x="NaN";
        if (val2!=null && val2.length>2 && !val2[1].substring(4).equals("incorrect_value_for_x"))
        {
            x=val2[3].substring(3);
        }
        String html="";
        html+="<!DOCTYPE html> <html> <head> <title>Function Parser</title>";
        html+="<script> "
                + "total=''; "
                + "partial=''; "
                + "rest=''; "
                + "str=''; "
                + "fol=''; "
                + "inp=''; "
                + "b=false; "
                + "inser=false; "
                + " window.onload = fun;"
                + " function fun() "
                + " { ";
        if (val!=null && !val.equals("") && !val2[1].substring(4).equals("null"))
        {
            String rezultat=val2[1].substring(4);
            try
            {
                double doub=Double.parseDouble(rezultat);
                if (doub*10 == (int)doub*10)
                {
                    rezultat=""+(int)doub;
                }
                int intreg=Integer.parseInt(rezultat);
                rezultat=""+intreg;
            }
            catch(NumberFormatException exep)
            {
                rezultat=val2[1].substring(4);
            }
            
            html+="var x=document.getElementById('result'); "
                    + "x.innerHTML='"+rezultat+"&nbsp;&nbsp;&nbsp;&nbsp;'; ";
            html+="var x=document.getElementById('valoare'); "
                    + "x.value='"+x+"'; ";
            html+="var x=document.getElementById('functie'); "
                    + "x.value='"+fin+"'; ";
        }
        html+= " } ";
        html+="  function f1(e) "
                + "{ "
                + "     inp=''; "
                + "     var val=e.target.value; "
                + "     if (!isNaN(val)) "
                + "     { "
                + "         str+=val; "
                + "         b=true; "
                + "         document.getElementById('functie').value=total+str; "
                + "         document.getElementById('dot').disabled=false; "
                + "     } "
                + "     else if (val.localeCompare('.')==0) "
                + "     { "
                + "         str+=val; "
                + "         b=true; "
                + "         document.getElementById('functie').value=total+str; "
                + "         e.target.disabled=true; "
                + "     } "
                + "     else if (val.localeCompare('-/+')==0) "
                + "     { "
                + "         var negat=''; "
                + "         if (b==true) "
                + "         { "
                + "             negat = document.getElementById('functie').value; "
                + "         } "
                + "         else "
                + "         { "
                + "             document.getElementById('functie').value=''; "
                + "         } "
                + "         var sir=new Array(0); "
                + "         if (negat.localeCompare('')!=0) "
                + "         { "
                + "             sir=negat.split(' '); "
                + "             if (sir[sir.length-1].localeCompare('')==0) "
                + "             { "
                + "                 sir.splice(sir.length-1, 1); "
                + "             } "
                + "         } "
                + "         element=anterior=ante_anterior=''; "
                + "         if (sir.length>0) "
                + "         { "
                + "             element=sir[sir.length-1]; "
                + "         } "
                + "         if (sir.length>1) "
                + "         { "
                + "             anterior=sir[sir.length-2]; "
                + "         } "
                + "         if (sir.length>2) "
                + "         { "
                + "             ante_anterior=sir[sir.length-3]; "
                + "         } "
                + "         if (element.localeCompare('+')==0 || element.localeCompare('')==0) "
                + "         { "
                + "             element='-'; "
                + "         } "
                + "         else if (element.localeCompare('-')==0) "
                + "         { "
                + "             element='+'; "
                + "         } "
                + "         else if (element.localeCompare(')')==0) "
                + "         { "
                + "             element=') -'; "
                + "         } "
                + "         else "
                + "         { "
                + "             if (anterior.localeCompare('+')==0 || "
                + "                 anterior.localeCompare('')==0 || "
                + "                 anterior.localeCompare('(')==0)"
                + "             { "
                + "                 if (anterior.localeCompare('(')==0)"
                + "                 { "
                + "                     anterior='( -'; "
                + "                 } "
                + "                 else "
                + "                 { "
                + "                     anterior='-'; "
                + "                 } "
                + "             } "
                + "             else if (anterior.localeCompare('-')==0) "
                + "             { "
                + "                 if (ante_anterior.localeCompare('')==0 || "
                + "                     ante_anterior.localeCompare('(')==0) "
                + "                 { "
                + "                     anterior=''; "
                + "                 } "
                + "                 else "
                + "                 { "
                + "                     anterior='+'; "
                + "                 } "
                + "             } "
                + "             else "
                + "             { "
                + "                 anterior=anterior+' ( -'; "
                + "             } "
                + "         } "
                + "         total=''; "
                + "         for (var i=0; i<sir.length-2; i++) "
                + "         { "
                + "              total+=sir[i]+' '; "
                + "         } "
                + "         if (anterior.localeCompare('')!=0) "
                + "         { "
                + "             total+=anterior+' '+element+' '; "
                + "         } "
                + "         else "
                + "         { "
                + "             total+=element+' ';"
                + "         } "
                + "         inp=total.substring(0, total.length-1); "
                + "         str=''; "
                + "         fol=element+' '; "                
                + "         document.getElementById('functie').value=total; "
                + "         document.getElementById('dot').disabled=true; "
                + "     } "
                + "     else if (str.localeCompare('')!=0) "
                + "     { "
                + "         total+=str+' '+val+' '; "
                + "         fol=val+' '; "
                + "         b=true; "
                + "         str=''; " 
                + "         document.getElementById('functie').value=total; "
                + "         document.getElementById('dot').disabled=true; "
                + "     } "
                + "     else if (val.localeCompare('<-')!=0 && val.localeCompare('CE')!=0 && "
                + "             val.localeCompare('C')!=0)"
                + "     { "
                + "         total+=val+' '; "
                + "         fol=val+' '; "
                + "         b=true; "
                + "         document.getElementById('functie').value=total; "
                + "         document.getElementById('dot').disabled=true; "
                + "     } "
                + "} "
                + "function f2(e) "
                + "{ "
                + "     var val=e.target.value; "
                + "     if (val.localeCompare('<-')==0 && (str.localeCompare('')!=0 || "
                + "                                         inp.localeCompare('')!=0)) "
                + "     { "
                + "         if (inp.localeCompare('')!=0) "
                + "         { "
                + "             if (inp.charAt(inp.length-1).localeCompare(' ')!=0)"
                + "             { "
                + "                 inp=inp.substring(0, inp.length-1); "
                + "                 document.getElementById('functie').value = inp; "
                + "                 str=''; "
                + "                 total=inp; "
                + "             } "
                + "         } "
                + "         else "
                + "         { "
                + "             str=str.substring(0, str.length-1); "
                + "             document.getElementById('functie').value=total+str; "
                + "         } "
                + "     } "
                + "     else if (val.localeCompare('<-')==0)"
                + "     { "
                + "         total=total.substring(0, total.length-fol.length); "
                + "         document.getElementById('functie').value=total; "
                + "         fol=''; "
                + "     } "
                + "     if (val.localeCompare('CE')==0) "
                + "     { "
                + "         if (str.localeCompare('')==0)"
                + "         {"
                + "             total=total.substring(0, total.length-fol.length); "
                + "         }"
                + "         str=''; "
                + "         fol=''; "
                + "         document.getElementById('functie').value=total; "
                + "     } "
                + "     if (val.localeCompare('C')==0) "
                + "     {"
                + "         total=''; "
                + "         str=''; "
                + "         fol=''; "
                + "         document.getElementById('functie').value=total; "
                + "     }"
                + "} "
                + "function f3()"
                + "{ "
                + "     elem=document.getElementById('fcn'); "
                + "     elem.value=document.getElementById('functie').value; "
                + "     b=false; "
                + "     total=''; "
                + "     str=''; "
                + "} "
                + "function f4(e)"
                + "{"
                + "     var el=e.target.value; "
                + "     var l=document.createElement('label'); "
                + "     l.style.backgroundColor='white'; "
                + "     l.style.position='absolute'; "
                + "     l.style.left=e.clientX+10+'px'; "
                + "     l.style.top=e.clientY+'px'; "
                + "     l.id='lb'; "
                + "     if (el.localeCompare('<-')==0) "
                + "     {"
                + "         l.innerHTML='&nbsp;deletes one symbol&nbsp;'; "
                + "     }"
                + "     if (el.localeCompare('CE')==0) "
                + "     {"
                + "         l.innerHTML='&nbsp;deletes current input&nbsp;'; "
                + "     }"
                + "     if (el.localeCompare('C')==0) "
                + "     {"
                + "         l.innerHTML='&nbsp;deletes the entire input&nbsp;'; "
                + "     }"
                + "     if (el.localeCompare('pow')==0) "
                + "     {"
                + "         l.innerHTML='&nbsp;use: x pow y&nbsp;'; "
                + "     }"
                + "     if (el.localeCompare('log')==0) "
                + "     {"
                + "         l.innerHTML='&nbsp;use: a log x ; a=base&nbsp;'; "
                + "     }"
                + "     document.body.appendChild(l); "
                + "} "
                + "function f5() "
                + "{ "
                + "     var l=document.body.lastChild; "
                + "     if (l.id=='lb' || l.id=='interval')"
                + "     {"
                + "         document.body.removeChild(l); "
                + "     }"
                + "} "
                + " function f6() "
                + "{"
                + "     var f=document.getElementById('functie'); "
                + "     f.value=''; "
                + "     var x=document.getElementById('valoare'); "
                + "     x.value=''; "
                + "     x=document.getElementById('limita'); "
                + "     x.value=''; "
                + "     var r=document.getElementById('result'); "
                + "     r.innerHTML=''; "
                + "     total=''; "
                + "     partial=''; "
                + "     rest=''; "
                + "     inp=''; "
                + "     str=''; "
                + "     fol=''; "
                + " } "
                + " function f7() "
                + "{ "
                + "     total=document.getElementById('functie').value; "
                + "} "
                + " function f8() "
                + "{ "
                + "     total=document.getElementById('functie').value; "
                + "     erase=document.getElementById('sterge').value-1;"
                + "     if (!isNaN(erase)) "
                + "     {"
                + "         var values = total.split(' ');"
                + "         if (values[values.length-1].localeCompare('')==0)"
                + "         { "
                + "             values.splice(values.length-1, 1); "
                + "         } "
                + "         total=''; "
                + "         for (var i=0; i<values.length; i++) "
                + "         { "
                + "             if (i!=erase) "
                + "             {"
                + "                 total+=values[i]+' ';"
                + "             }"
                + "         } "
                + "         document.getElementById('functie').value=total; "
                + "     }"
                + "} "
                + "function f9(e)"
                + "{ "
                + "     total=document.getElementById('functie').value; "
                + "     var values = total.split(' ');"
                + "     if (values[values.length-1].localeCompare('')==0)"
                + "     { "
                + "         values.splice(values.length-1, 1); "
                + "     } "
                + "     if (values.length>0)"
                + "     {"
                + "         var l=document.createElement('label'); "
                + "         l.style.backgroundColor='white'; "
                + "         l.style.position='absolute'; "
                + "         l.style.left=e.clientX+10+'px'; "
                + "         l.style.top=e.clientY+'px'; "
                + "         l.id='interval'; "
                + "         l.innerHTML='&nbsp;1 to '+values.length+'&nbsp;'; "
                + "         document.body.appendChild(l); "
                + "     }"
                + "} "
                + " function f10() "
                + "{ "
                + "     partial=document.getElementById('functie').value; "
                + "     var ins=document.getElementById('ins').value; "
                + "     if (!isNaN(ins)) "
                + "     {"
                + "         var values = partial.split(' ');"
                + "         if (values[values.length-1].localeCompare('')==0)"
                + "         { "
                + "             values.splice(values.length-1, 1); "
                + "         } "
                + "         partial=''; "
                + "         rest=''; "
                + "         for (var i=0; i<values.length; i++) "
                + "         { "
                + "             if (i<ins) "
                + "             { "
                + "                 partial+=values[i]+' '; "
                + "             } "
                + "             else"
                + "             { "
                + "                 rest+=values[i]+' '; "
                + "             } "
                + "         } "
                + "         total=document.getElementById('functie').value=partial+'_ '+rest; "
                + "         str=''; "
                + "         inser=true; "
                + "     }"
                + "} "
                + "function f11()"
                + "{ "
                + "     if (inser===true)"
                + "     { "
                + "         var local=document.getElementById('functie').value; "
                + "         var v=''; "
                + "         var nou=''; "
                + "         var i=0; "
                + "         while (local.charAt(i)!='_') "
                + "         { "
                + "             ++i; "
                + "         } "
                + "         v=local.substring(i+2, i+2+rest.length); "
                + "         nou=local.substring(i+2+rest.length); "
                + "         local=partial+nou+'_ '+v; "
                + "         document.getElementById('functie').value=local; "
                + "     } "
                + "}"
                + "function f11_2() "
                + "{ "
                + "     if (inser==true) "
                + "     { "
                + "         inser=false; "
                + "         var local=document.getElementById('functie').value; "
                + "         var i=0; "
                + "         while (i<local.length && local.charAt(i)!='_') "
                + "         { "
                + "             ++i; "
                + "         } "
                + "         var v1=local.substring(0,i); "
                + "         var v2=''; "
                + "         if (i+1 < local.length) "
                + "         { "
                + "             v2=local.substring(i+1);"
                + "         } "
                + "         local=v1+v2; "
                + "         document.getElementById('functie').value=local; "
                + "         total=local; "
                + "         partial=''; "
                + "         rest=''; "
                + "         str=''; "
                + "     } "
                + "} "
                + "function f12(e)"
                + "{ "
                + "     total=document.getElementById('functie').value; "
                + "     var values = total.split(' ');"
                + "     if (values[values.length-1].localeCompare('')==0)"
                + "     { "
                + "         values.splice(values.length-1, 1); "
                + "     } "
                + "     if (values.length>0)"
                + "     {"
                + "         var l=document.createElement('label'); "
                + "         l.style.backgroundColor='white'; "
                + "         l.style.position='absolute'; "
                + "         l.style.left=e.clientX+10+'px'; "
                + "         l.style.top=e.clientY+'px'; "
                + "         l.id='interval'; "
                + "         l.innerHTML='&nbsp;0 to '+values.length+'&nbsp;'; "
                + "         document.body.appendChild(l); "
                + "     }"
                + "} "
                + ""
                + "function ch2() "
                + "{"
                + " var x = document.getElementById('valoare');"
                + " var y = x.value;"
                + " if (y == null || y.localeCompare('')==0 || isNaN(y))"
                + "{"
                + "  x.value = Math.round(Math.E*1000000)/1000000;"
                + "}"
                + "else "
                + "{"
                + " var z = document.getElementById('limita');"
                + " z.value = Math.round(Math.E*1000000)/1000000;"
                + "} "
                + "}"
                + "function ch3() "
                + "{"
                + " var x = document.getElementById('valoare');"
                + " var y = x.value;"
                + " if (y == null || y.localeCompare('')==0 || isNaN(y))"
                + "{"
                + "  x.value = Math.round(Math.PI*1000000)/1000000;"
                + "}"
                + "else "
                + "{"
                + " var z = document.getElementById('limita');"
                + " z.value = Math.round(Math.PI*1000000)/1000000;"
                + "} "
                + "}"
                
                + "";
                html+="</script> </head> <body> ";
        
        html+="<table width='1000' id='t_complet'>"
                + "<tr>"
                    + "<td style='width:80px;'></td>"
                    + "<td>";
        
        html+="<br /> <div style='width:100px; font-size:20px; font-weight:bolder; '>"
                + "f(x) : *<input id='functie' type='text' size='100' style='height:20px;' "
                + "disabled='true' value='' />"
                + "</div><br /><br />";
        
        html+="<table width='600' id='t_functii' onclick='f11();'>";
        
        html+="<tr align='center'>";
        html+="<td><input type='button' id='x' value='x' style='width:100px; font-size:20px; "
                + " font-weight:bolder;' onclick='f1(event)'></td>";
        html+="<td><input type='button' id='stop' value='stop' style='width:100px; font-size:20px;' "
                + "font-weight:bolder;' onclick='f11_2()'></td>";
        html+="<td><input type='button' id='bsp' value='<-' style='width:100px; font-size:20px;' "
                + "onclick='f2(event)' onmouseover='f4(event)' onmouseout='f5()'></td>";
        html+="<td><input type='button' id='ce' value='CE' style='width:100px; font-size:20px;' "
                + "onclick='f2(event)' onmouseover='f4(event)' onmouseout='f5()'></td>";
        html+="<td><input type='button' id='c' value='C' style='width:100px; font-size:20px;' "
                + "onclick='f11_2(); f2(event)' onmouseover='f4(event)' onmouseout='f5()'></td>";
        html+="<td><input type='button' id='e' value='e' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='pi' value='pi' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="</tr>";
        
        html+="<tr align='center'>";
        html+="<td><input type='button' id='one' value='1' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='two' value='2' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='three' value='3' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='plus' value='+' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='pow' value='pow' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)' onmouseover='f4(event)' onmouseout='f5()'></td>";
        html+="<td><input type='button' id='sin' value='sin' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='arcsin' value='arcsin' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="</tr>";
        
        html+="<tr align='center'>";
        html+="<td><input type='button' id='four' value='4' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='five' value='5' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='six' value='6' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='minus' value='-' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='log' value='log' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)' onmouseover='f4(event)' onmouseout='f5()'></td>";
        html+="<td><input type='button' id='cos' value='cos' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='arccos' value='arccos' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="</tr>";
        
        html+="<tr align='center'>";
        html+="<td><input type='button' id='seven' value='7' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='eight' value='8' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='nine' value='9' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='mult' value='*' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='paro' value='(' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='tg' value='tg' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='artg' value='arctg' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="</tr>";
        
        html+="<tr align='center'>";
        html+="<td><input type='button' id='sign' value='-/+' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='zero' value='0' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='dot' value='.' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='divis' value='/' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='parc' value=')' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='ctg' value='ctg' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="<td><input type='button' id='arcctg' value='arcctg' style='width:100px; font-size:20px;' "
                + "onclick='f1(event)'></td>";
        html+="</tr>";
        
        html+="</table>";
        //html+= "<script> function g(e) { alert(e);} </script>";
        
        html+="<form method='GET' action='Drawing' autocomplete='off'>";
        
        
        html+="<br /><div style='width:100px; font-size:20px; font-weight:bolder;' >X is : *"
                + "<table>"
                + "<tr>"
                + "<td>"
                + "<input id='valoare' name='valoare' type='text' size='20' "
                + "style='height:20px;' /> "
                + "</td>"
                
                + "<td><input type='button' id='e2' value='e' style='width:100px; font-size:20px;' "
                + "onclick='ch2()'></td>"
                
                +"<td><input type='button' id='pi2' value='pi' style='width:100px; font-size:20px;' "
                + "onclick='ch3()'"
                + "</td>"
                + "</tr>"
                + "</table>"
                
                + "";
        
        
                
        /*html+=" <input type='button' id='pe1' value='pi' style='width:100px; font-size:20px;"
                + "position: absolute; left: 300px; z-index: -5;'"
                + "onclick='che(event)'> ";*/
               
                
        html+= " between "
                + "&nbsp;<input id='limita' name='limita' type='text' size='20'  "
                + "style='height:20px;' "
                + "value='' /></div>";
        
        html+="<br /><input id='btn' type='submit' value='=' onclick = 'f11_2(); f3();' "
                + "style='width:100px; font-size:20px; font-weight:bolder;' /> "
                + "&nbsp;&nbsp;&nbsp;&nbsp;";
        
        if (corect.equals("red"))
        {
            html+="<label id='result' style='width:100px; font-size:20px; font-weight:bolder; "
                + "color:rgb(255, 0, 0);'></label> ";
        }
        else
        {
            html+="<label id='result' style='width:100px; font-size:20px; font-weight:bolder; "
                + "color:rgb(0, 255, 0);'></label> ";
        }
                html+="<br /><br /><input type='button' value='new' onclick='f11_2(); f6();' "
                + "style='width:100px; font-size:20px; font-weight:bolder;' />" 
                + "&nbsp;&nbsp;&nbsp;&nbsp;" ;        
        
        html+="<input type='button' value='same' onclick='f11_2(); f7(); f0();' "
                + "style='width:100px; font-size:20px; font-weight:bolder;' /><br /><br />";  
        
        html+="<input type='button' value='delete at place' style='width:180px; font-size:20px; "
                + "font-weight:bolder;' onclick='f11_2(); f8();' onmouseover='f9(event);' onmouseout='f5();' />"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<input type='text' id='sterge' size='2' style='height:20px;' /><br /><br />";
        
        html+="<input type='button' value='insert at place' style='width:180px; font-size:20px; "
                + "font-weight:bolder;' onclick='f11_2(); f10();' onmouseover='f12(event);' onmouseout='f5();' />"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<input type='text' id='ins' size='2' style='height:20px;' />"; 
        
        html+="<input type='hidden' name='fcn' id='fcn' />"
                + "<br /><label style='font-size:1em;'><br />"
                + "note: functions have higher precedence than operators</label>";
        
        html+="</td></tr></table>";
        
        html+="</form> </body> </html>"; 
        out.println(html);
        out.close();
    }
}

/*session

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

        HttpSession session = request.getSession();
        ServletContext sc = session.getServletContext();
        request.getSession().invalidate();
        sc.getSessionCookieConfig().setHttpOnly(false); // context has been initialized
        
         
create session then add it to request; change JSESSIONID
        HttpSession session = request.getSession();
        
        
        String name = session.getId();
        HttpSession session = request.getSession();
        System.out.println(name);
        sc.getSessionCookieConfig().setHttpOnly(false);
        
        Cookie cc = new Cookie("JSESSIONID", "setHttpOnly=false");
        
        
        HttpSession session = request.getSession();
        ServletContext sc = session.getServletContext();
        sc.getSessionCookieConfig().setHttpOnly(false);
        
        Cookie ck [] = request.getCookies();
        for (Cookie ck1 : ck) {
            //System.out.println(ck1.getName() + " - " + ck1.getValue());
        }

        Cookie cc = new Cookie("setHttpOnly","false");        
        //response.addCookie(cc);        
        
            + "function dh2(e)"
                + "{"
                + "  var x = e;"
                + "  var y = x.name;"
                + "  alert(y); "
                + "}"
                    e = this
*/


