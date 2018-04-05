package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetCharacterEncoding implements Filter {
	
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig _filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("SetCharacterEncodingFilter创建了");
		//别他妈瞎贴代码，记得初始化啊
		this.filterConfig = _filterConfig;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("SetCharacterEncodingFilter销毁了");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
//		System.out.println("SetCharacterEncodingFilter执行了");
		HttpServletRequest  request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
        
        String encoding = filterConfig.getInitParameter("encoding");
        if(encoding==null){
        	encoding = "UTF-8";
        }
//        System.out.println(encoding);
        //POST:
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset="+encoding);
        chain.doFilter(request, response);
	}


}

