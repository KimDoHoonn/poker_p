package noticeboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberAddServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String id = request.getParameter("ID");
		String password = request.getParameter("PASSWORD");
		String name = request.getParameter("NAME");
		String tel = request.getParameter("TEL");
		response.setContentType("tetx/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>회원가입</TITLE></HEAD>");
		out.println("<BODY>");
		out.printf("아이디 : %s <br>", id);
		out.printf("비밀번호 : %s <br>", password);
		out.printf("아이디 : %s <br>", name);
		out.printf("전화번호: %d <br>", tel);
		out.println("</BODY>");
		out.println("</HTML>");
	}
}
