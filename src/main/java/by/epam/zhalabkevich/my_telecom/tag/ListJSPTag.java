package by.epam.zhalabkevich.my_telecom.tag;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ListJSPTag extends BodyTagSupport {
    private int num;
    private JSPListBean list;

    public JSPListBean getList() {
        return list;
    }

    public void setList(JSPListBean list) {
        this.list = list;
    }

    public void setNum(String num) {
        this.num = Integer.valueOf(num);
    }


    public int doStartTag() throws JspTagException {
        int i = 1;
        try {
            JspWriter out = pageContext.getOut();
           /* out.write(" <div class=\"table-responsive\">");
            out.write("<table class=\"table table-striped\">");
            out.write("<tr class=\"active\">");
            out.write("<th>№</th>");
            out.write("<th> ${name} </th>");
            out.write("<th> ${descr} </th>");
            out.write("<th> ${speed} </th>");
            out.write("<th> ${price}, $</th>");
            out.write("<th> ${discount} </th>");
            out.write("<th></th>");
            out.write("</tr>");
*/


            while (num >= 1) {
                Tariff tariff = list.getElement();
                num = num - 1;
                out.write("<tr><td>");
                out.write(Integer.toString(i));
                out.write("</td><td>");
                out.write(tariff.getName());
                out.write("</td><td>");
                out.write(tariff.getDescription());
                out.write("</td><td>");
                out.write(Integer.toString(tariff.getSpeed()));
                out.write("</td><td>");
                out.write(tariff.getPrice().toString());
//                out.write("</td><td>");
//                out.write(Double.toString(tariff.getDiscount()));
                out.write("</td></tr>");
                i++;
            }

        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }


    public int doEndTag() throws JspException {
        try {
            // pageContext.getOut().write("</table></div>");
            pageContext.getOut().write("");
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }
}
