package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.PRODUCT;
import com.biru.common.entity.DatatableSet;
import com.biru.service.ProductService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = PRODUCT.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> param) {
		return productService.inquiry(param);
	}
	
	@RequestMapping(value = PRODUCT.SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody Map<String, Object> param) throws Exception {
		return productService.saveProcess(param);
	}
	
	@RequestMapping(value = PRODUCT.DROPDOWN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getDropDown() {
		return productService.getDropDown();
	}
	
	@RequestMapping(value = PRODUCT.SAVE_INS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object saveIns(@RequestBody Map<String, Object> param) {
		return productService.saveIns(param);
	}
	
	@RequestMapping(value = PRODUCT.INQUIRY_INS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object inquiryIns(@RequestBody Map<String, Object> param) {
		return productService.inquiryInsurance(param);
	}
	
	@RequestMapping(value = PRODUCT.REMOVE_INS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object removeIns(@RequestBody Map<String, Object> param) {
		return productService.removeIns(param);
	}
	
	@RequestMapping(value = PRODUCT.DROPDOWN_PA_CHILD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getDropDownPaChild() {
		return productService.getDropDownPaChild();
	}
}
