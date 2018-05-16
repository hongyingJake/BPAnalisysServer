package com.bp.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bp.models.ActionResultModel;
import com.bp.models.ResultValue;

/**
 * Web Service ������Ϣ��װ���ͷ���
 * @author aaa
 *
 */
public class ResultInfoCommon {
	/**
     * Web API�ɹ�������Ϣ
     * @param msg
     * @return
     */
    public static ResponseEntity<ActionResultModel> SuccessFulInfo(String msg)
    {
    	ActionResultModel model=new ActionResultModel();
    	model.Msg=msg;
    	model.Status=ResultValue.OK;
    	return new ResponseEntity<ActionResultModel>(model,HttpStatus.OK);
    }
    /**
     * Web APIʧ�ܷ�����Ϣ
     * @param failMsg ʧ�ܺ���ʾ����Ϣ
     * @return
     */
    public static ResponseEntity<ActionResultModel> FailInfo(String failMsg)
    {
    	ActionResultModel model=new ActionResultModel();
    	model.Msg=failMsg;
    	model.Status=ResultValue.Fail;
    	return new ResponseEntity<ActionResultModel>(model,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * ���سɹ�ʱJSON��ʽ����
     * @param msg
     * @return
     */
    public static ModelAndView SuccessJson(String msg)
    {
    	ActionResultModel model=new ActionResultModel();
    	model.Msg=msg;
    	model.Status=ResultValue.OK;
    	Map<String,ActionResultModel> map=new HashMap<String,ActionResultModel>();
    	map.put("BPInfo", model);
    	return new ModelAndView(new MappingJackson2JsonView(),map);
    }
    /**
     * ����ʧ��ʱJSON��ʽ����
     * @param failMsg
     * @return
     */
    public static ModelAndView FailJson(String failMsg)
    {
    	ActionResultModel model=new ActionResultModel();
    	model.Msg=failMsg;
    	model.Status=ResultValue.Fail;
    	Map<String,ActionResultModel> map=new HashMap<String,ActionResultModel>();
    	map.put("BPInfo", model);
    	return new ModelAndView(new MappingJackson2JsonView(),map);
    }
}
