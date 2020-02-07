package com.example.test.action;

import com.example.test.service.InfoService;
import com.example.test.table.Info;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auohor ai
 * @data 2019/12/27
 */

@RestController
@RequestMapping(value = "test")
public class InfoAction {

    @Autowired
    InfoService infoService;

    @PostMapping(value = "info")
    public Map<String,Object> Info(String value, Integer currentPage , Integer pagesize) {
        PageHelper.startPage(currentPage,pagesize);
        HashMap result = new HashMap();
        if (value.isEmpty()) {
            result.put("data", infoService.selectByExample(null));
        }else{
            Example example = new Example(Info.class);
            Example example1 =new Example(Info.class);
            example.createCriteria().andLike("jobNumber","%" +value+"%");
            List<Info> info = infoService.selectByExample(example);
            example1.createCriteria().andLike("name","%" +value+"%");
            List<Info> info1 = infoService.selectByExample(example1);
            if(!info.isEmpty()){
                result.put("data",info);
            }else if(!info1.isEmpty()){
                result.put("data",info1);
            }
        }
        result.put("currentPage", currentPage);
        return result;
    }

    @PostMapping(value = "insert")
    public Map<String,Object> insert(String jobNumber,String name,String password) {
        Info infoList = new Info();
        infoList.setJobNumber(jobNumber);
        infoList.setName(name);
        infoList.setPassword(password);
        infoService.insertAll(infoList);
        HashMap result = new HashMap();
        result.put("data",infoService.selectByExample(null));
        return result;
    }

    @PostMapping(value = "update")
    public Map<String,Object> update(String jobNumber,String name,String password) {
        Example example = new Example(Info.class);
        example.createCriteria().andEqualTo("jobNumber",jobNumber);
        Info info = infoService.selectOneByExample(example);
        info.setName(name);
        info.setPassword(password);
        int i = infoService.updateNotNull(info);
        HashMap result = new HashMap();
        result.put("data",infoService.selectByExample(null));
        return result;
    }

    @PostMapping(value = "delete")
    public Map<String,Object> delete(Integer id){
        infoService.deleteById(id);
        HashMap result = new HashMap();
        result.put("data",infoService.selectByExample(null));
        return result;
    }



}