package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.PropertyDAO;
import tmall.util.Page;

public class PropertyServlet extends BaseBackServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		
		String name = request.getParameter("name");
		Property p = new Property();
		p.setCategory(c);
		p.setName(name);
		
		propertyDAO.add(p);
		return "@admin_property_list?cid="+cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));//为什么不需要获取cid呢？
		//因为id是主键，cid是外键
		Property p = propertyDAO.get(id);
		propertyDAO.delete(id);
		return "@admin_property_list?cid="+p.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Property p = propertyDAO.get(id);
		request.setAttribute("p", p);
		return "admin/editProperty.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
	    Category c = categoryDAO.get(cid);
	     
	    int id = Integer.parseInt(request.getParameter("id"));
	    String name= request.getParameter("name");
	    Property p = new Property();
	    p.setCategory(c);
	    p.setId(id);
	    p.setName(name);
	    propertyDAO.update(p);
	    return "@admin_property_list?cid="+p.getCategory().getId();
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = new Category();
		List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getTotal(cid);
		page.setParam("&cid="+c.getId());
		page.setTotal(total);
		
		request.setAttribute("c", c);
		request.setAttribute("ps", ps);
		request.setAttribute("page", page);
		
		return "admin/listProperty.jsp";
	}

}
