package com.model2.mvc.web.purchase;

import java.util.Map;

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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="listPurchase")
	public String listPurchase(@ModelAttribute("search") Search search, Model model, HttpSession session) throws Exception{
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search, ((User)session.getAttribute("user")).getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);

		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping(value="getPurchase", method=RequestMethod.GET)
	public String getPurchase(@RequestParam("tranNo") int tranNo, Model model) throws Exception{
		model.addAttribute("purchase", purchaseService.getPurchase(tranNo));
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping(value="deletePurchase", method=RequestMethod.GET)
	public String deletePurchase(@RequestParam("tranNo") int tranNo, HttpSession session) throws Exception{
		purchaseService.deletePurchase(tranNo);
		
		return "redirect:/purchase/listPurchase";
	}
	
	@RequestMapping(value="updatePurchaseView", method=RequestMethod.GET)
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception{
		
		model.addAttribute("purchase", purchaseService.getPurchase(tranNo));
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping(value="addPurchaseView", method=RequestMethod.GET)
	public String addPurchaseView(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		model.addAttribute("product", productService.getProduct(prodNo));
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public String addPurchase(@ModelAttribute("purchase") Purchase purchase, @RequestParam("prodNo") int prodNo, 
														HttpSession session, Model model) throws Exception{
		System.out.println(purchase);
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setDivyDate(purchase.getDivyDate().replaceAll("-", ""));
		
		
		purchaseService.addPurchase(purchase);
		model.addAttribute("purchase", purchase);
		
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase, @RequestParam("tranNo") int tranNo) throws Exception{
		purchase.setTranNo(tranNo);
		purchase.setDivyDate(purchase.getDivyDate().replaceAll("-", ""));
		purchaseService.updatePurchase(purchase);
		
		return "redirect:/purchase/listPurchase";
	}
	
	@RequestMapping(value="updateTranCode", method=RequestMethod.GET)
	public String updateTranCode(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
		System.out.println("updateTranCode.do");
		
		purchaseService.updateTranCode(purchaseService.getPurchase(tranNo));
	
		return "redirect:/purchase/listPurchase";
	}
	
	@RequestMapping(value="updateTranCodeByProd", method=RequestMethod.GET)
	public String updateTranCodeByProd(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		System.out.println("updateTranCodeByProd.do");
		
		purchaseService.updateTranCode(purchaseService.getPurchase2(prodNo));

		return "redirect:/product/listProduct?menu=manage";
	}
	
	@RequestMapping(value="updateEvaluationView", method=RequestMethod.GET)
	public String updateEvaluationView(@RequestParam("tranNo") int tranNo, Model model) throws Exception{
		System.out.println("updateEvaluationView.do");
		Purchase purchase = purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase", purchase);
		model.addAttribute("product", productService.getProduct(purchase.getPurchaseProd().getProdNo()));
		return "forward:/purchase/reviewPurchase.jsp";
	}
	
	@RequestMapping(value="updateEvaluation", method=RequestMethod.POST)
	public String updateEvaluation(@ModelAttribute("purchase") Purchase purchase) throws Exception {
		System.out.println("updateEvaluation.do");
		purchaseService.updateEval(purchase);
		
		return "redirect:/purchase/listPurchase";
	}
}
