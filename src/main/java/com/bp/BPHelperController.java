package com.bp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
* @author zyk
* @version ����ʱ�䣺2017��8��28�� ����2:49:28
* ���ϵͳ�����ĵ�������   ��˵��
*/
@Controller
public class BPHelperController {
	/**
	 * ϵͳ������ҳ��Action
	 * @return
	 */
	@RequestMapping("/BPHelper/Index")
	public ModelAndView BPHelperIndex() {
		ModelAndView mv = new ModelAndView("BPHelper/Index");// ָ����ͼ
		return mv;
	}
}
