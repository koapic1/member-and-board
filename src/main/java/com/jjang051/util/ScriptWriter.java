package com.jjang051.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ScriptWriter {
	public static void init(HttpServletResponse response) {
		//response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	}
	public static void alert(HttpServletResponse response, String alertMgs) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('"+alertMgs+"');");
		out.println("</script>");
	}

	public static void alertAndBack(HttpServletResponse response, String alertMgs) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('"+alertMgs+"');");
		out.println("history.back();");
		out.println("</script>");
	}

	public static void alertAndNext(HttpServletResponse response, String alertMgs, String nextURL) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('"+alertMgs+"');");
		out.println("location.href='"+nextURL+"';");
		out.println("</script>");
	}
}
