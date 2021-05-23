package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 5000813763080802861L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String a = req.getParameter("a");
			String b = req.getParameter("b");
			String n = req.getParameter("n");
			if (a == null || b == null || n == null) {
				req.setAttribute("errorMessage", "One or more required parameters are missing.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
			int startNum = Integer.parseInt(a);
			int endNum = Integer.parseInt(b);
			int numOfPages = Integer.parseInt(n);
			if (startNum > endNum) {
				int temp = startNum;
				startNum = endNum;
				endNum = temp;
			}
			if (startNum < -100 || startNum > 100) {
				req.setAttribute("errorMessage", "Parameter a is invalid.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
			if (endNum < -100 || endNum > 100) {
				req.setAttribute("errorMessage", "Parameter b is invalid.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
			if (numOfPages < 1 || numOfPages > 5) {
				req.setAttribute("errorMessage", "Parameter n is invalid.");
				req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
			}
			HSSFWorkbook excelWorkbook = createExcelWorkbook(startNum, endNum, numOfPages);
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
			excelWorkbook.write(resp.getOutputStream());
		} catch (NumberFormatException ex) {
			req.setAttribute("errorMessage", "One or more parameters are not parsable to integer.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private HSSFWorkbook createExcelWorkbook(int startNum, int endNum, int numOfPages) {
		HSSFWorkbook excelWorkbook = new HSSFWorkbook();
		for (int i = 1; i <= numOfPages; i++) {
			HSSFSheet sheet = excelWorkbook.createSheet(Integer.toString(i) + "-th powers");
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue(Integer.toString(i) + "-th power");
			for (int j = startNum, currentRow = 1; j <= endNum; j++, currentRow++) {
				HSSFRow row = sheet.createRow(currentRow);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		return excelWorkbook;
	}
	
}
