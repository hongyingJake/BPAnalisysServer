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
 * Web Service 返回信息封装类型方法
 * @author aaa
 *
 */
public class ResultInfoCommon {
	/**
     * Web API成功返回消息
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
     * Web API失败返回消息
     * @param failMsg 失败后提示的信息
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
     * 返回成功时JSON格式对象
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
     * 返回失败时JSON格式对象
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
