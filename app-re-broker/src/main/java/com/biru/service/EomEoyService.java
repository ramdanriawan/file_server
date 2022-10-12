package com.biru.service;

import java.util.List;
import java.util.Map;

import com.biru.entity.ProgressEntity;
import com.biru.helper.TrHelper;

public interface EomEoyService {

	public List<TrHelper> checkDataEom(Map<String, Object> p_Params);
	public void doProcess(Map<String, Object> p_Params);
	public void doCancelEom(Map<String, Object> p_Params);
	public void initProcess(Map<String, Object> p_Params);
	public ProgressEntity getProgress (Map<String, Object> p_Param);
	public void resetProgress (Map<String, Object> p_Param);
	public Object isProcessStillRunning (Map<String, Object> p_Param);
	
}
