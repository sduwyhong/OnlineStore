package servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import service.BusinessService;
import service.impl.BusinessServiceImpl;
import util.BeanUtil;
import util.idGenerator;
import domain.Book;
import domain.Category;

public class ManageServlet extends HttpServlet {
	private BusinessService bs = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if(op.equals("addCategory")){
			addCategory(request,response);
		}else if(op.equals("showCategories")){
			showCategories(request,response);
		}else if(op.equals("addBook")){
			addBook(request,response);
		}else if(op.equals("addBookUI")){
			addBookUI(request,response);
		}else if(op.equals("showBooks")){
			showBooks(request,response);
		}else if(op.equals("deleteBook")){
			deleteBook(request,response);
		}else if(op.equals("editBookUI")){
			editBookUI(request,response);
		}else if(op.equals("editBook")){
			editBook(request,response);
		}
	}

	private void editBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException  {
		String bookId = request.getParameter("id");
		Book originalBook = bs.findBookById(bookId);
		//上传，检查encrypt是否为multipart
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new RuntimeException("enctype must be multipart/form-data");
		}

		Book newBook = new Book();
		newBook.setId(bookId);
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//ServletFileUpload对象用来解析字段与文件请求
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> fileItems;
		try {
			fileItems = sfu.parseRequest(request);
			for(FileItem item:fileItems){
				if(item.isFormField()){//处理字段
					processFormField(item,newBook);
				}else{//处理文件
					System.out.println("item.getName():"+item.getName());
					if(!item.getName().equals("")){//若上传文件不为空，则新建图片，同时删除旧图
						processUploadField(item,newBook);
						String realPath = getServletContext().getRealPath("/images");
						String fullPath = realPath+File.separator+originalBook.getPath()+File.separator+originalBook.getFileName();
						System.out.println("fullPath:"+fullPath);
						File originalFile = new File(fullPath);
						originalFile.deleteOnExit();
						
					}else{//没上传文件，文件路径不变
						newBook.setFileName(originalBook.getFileName());
						newBook.setPath(originalBook.getPath());
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		System.out.println(newBook);
		bs.editBook(newBook);
		showBooks(request, response);
	
	}

	private void showBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Book> books = bs.findAllBooks();
		request.setAttribute("books", books);
		request.getRequestDispatcher("/manage/showBooks.jsp").forward(request, response);
	}

	private void addBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> categories = bs.findAllCategories();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}

	private void addBook(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException{
		//上传，检查encrypt是否为multipart
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new RuntimeException("enctype must be multipart/form-data");
		}

		Book book = new Book();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		//ServletFileUpload对象用来解析请求
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> fileItems;
		try {
			fileItems = sfu.parseRequest(request);
			for(FileItem item:fileItems){
				if(item.isFormField()){
					processFormField(item,book);
				}else{
					processUploadField(item,book);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		bs.addBook(book);
		response.sendRedirect(request.getContextPath()+"/common/success.jsp");
	}

	private void processUploadField(FileItem item,Book book)  throws ServletException, IOException{
		String uploadFieldName = item.getName();
		if(uploadFieldName!=null&&!uploadFieldName.equals("")){
			//根目录为/images
			String realPath = getServletContext().getRealPath("/images");

			String childDirectoryPath = genChildDirectory(realPath);
			book.setPath(childDirectoryPath);

			String fullPath = realPath+File.separator+childDirectoryPath;
			//只需要原来文件名后缀
			String fileName = idGenerator.genGUID()+"."+FilenameUtils.getExtension(uploadFieldName);
			book.setFileName(fileName);

			File childDirectory = new File(fullPath);
			if(!childDirectory.exists()){
				childDirectory.mkdirs();
			}

			try {
				item.write(new File(childDirectory,fileName));
			} catch (Exception e) {
				e.printStackTrace();

			}


		}
	}

	private String genChildDirectory(String realPath) {
		//generate childDirectory by current date
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String childDirectory = df.format(date);
		File file = new File(realPath, childDirectory);
		if(!file.exists()){
			file.mkdirs();
			System.out.println(file+"made dirs");
		}
		return childDirectory;
	}

	private void processFormField(FileItem item,Book book) throws UnsupportedEncodingException {
		String fieldName = item.getFieldName();
		String fieldValue = item.getString("UTF-8");
		try {
			//通过字段名和值设置属性
			BeanUtils.setProperty(book, fieldName, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("categoryId".equals(fieldName)){
			Category c = bs.findCategoryById(fieldValue);
			book.setCategory(c);
		}

	}

	private void showCategories(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		List<Category> categories = bs.findAllCategories();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/manage/showCategories.jsp").forward(request, response);
	}

	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		Category c = BeanUtil.fillBean(request, Category.class);
		System.out.println(c.getName());
		bs.addCategory(c);
		//处理完后，要页面转向。Redirect After Post
		response.sendRedirect(request.getContextPath()+"/common/success.jsp");
	}
	
	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		System.out.println(id);
		bs.deleteBook(id);
		showBooks(request,response);
	}
	
	private void editBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId = request.getParameter("id");
		Book book = bs.findBookById(bookId);
		request.setAttribute("b", book);
		List<Category> categories = bs.findAllCategories();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/manage/editBook.jsp").forward(request, response);
	}
	
	
}
