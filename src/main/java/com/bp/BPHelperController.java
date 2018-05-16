package com.bp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
* @author zyk
* @version 创建时间：2017年8月28日 下午2:49:28
* 埋点系统帮助文档控制器   类说明
*/
@Controller
public class BPHelperController {
	/**
	 * 系统帮助主页面Action
	 * @return
	 */
	@RequestMapping("/BPHelper/Index")
	public ModelAndView BPHelperIndex() {
		ModelAndView mv = new ModelAndView("BPHelper/Index");// 指定视图
		return mv;
	}
}
