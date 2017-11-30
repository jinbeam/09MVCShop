package com.model2.mvc.web.product;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product, Model model, @RequestParam("uploadFile") MultipartFile uploadfile) throws Exception{
	
	    if (uploadfile != null) {
	      String fileName = uploadfile.getOriginalFilename();
	      product.setFileName(fileName);
	
	        File file = new File("C:/workspace/07.Model2MVCShop(URI,pattern)/WebContent/images/uploadFiles/" + fileName);

	        uploadfile.transferTo(file);
	    }
	      
		System.out.println("/addProduct.do");
		product.setManuDate((product.getManuDate()).replaceAll("-", ""));
		productService.addProduct(product);
		model.addAttribute("product", product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search,  @RequestParam("menu") String menu, Model model, HttpServletRequest request) throws Exception{
		System.out.println("/listProduct.do");
		String sorting = null;
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		if(request.getParameter("sorting") != null) {
			sorting = request.getParameter("sorting");
		}
		search.setSorting(sorting);
		
		Map<String, Object> map = productService.getProductList(search);
		// Business logic 수행
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("sorting", sorting);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, 
														Model model, HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		System.out.println("/getProduct.do");
		
		String str = "";
	
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			for(int i = 0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("history")){
					str += cookies[i].getValue();
					if(cookies.length != i) {
						str += ",";
					}
				}
			}
		}
		str += prodNo;
		
		Cookie cookie = new Cookie("history", str);
		response.addCookie(cookie);
		
		model.addAttribute("product", productService.getProduct(prodNo));
		request.setAttribute("menu", menu);
		
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping(value="updateProductView", method=RequestMethod.GET)
	public String updateProductView(HttpServletRequest request, HttpServletResponse response, 
																	HttpSession session, @RequestParam("prodNo") int prodNo) throws Exception{
		System.out.println("uprdateProductView.do");
		
		Product product = productService.getProduct(prodNo);
		
		//request.setAttribute("menu", request.getParameter("menu"));
		request.setAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, Model model) throws Exception{
		System.out.println("updateProduct.do");
		productService.updateProduct(product);
		
		model.addAttribute("product",productService.getProduct(product.getProdNo()));
		
		return "forward:/product/readProduct.jsp";
	}
	
}
