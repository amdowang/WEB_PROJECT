package com.jst.common.json;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class WriterUtil {
	public static void writerJson(HttpServletResponse response,
			String jsonString) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(jsonString);
		out.close();
	}
}
