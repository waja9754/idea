package com.example.test.action;

import com.example.test.service.InfoService;
import com.example.test.table.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(value = "info")
    public Map<String,Object> Info() {
        HashMap result = new HashMap();
        result.put("data",infoService.selectByExample(null));
        return result;
    }

    @GetMapping(value = "insert")
    public List<Info> insert() {
        Info infoList = new Info();
        infoList.setId(812112);
        infoList.setName("蒋嫒");
        infoService.insertAll(infoList);
        return infoService.selectAll(null);
    }

    @GetMapping(value = "get")
    public List<Info> getList() {
        return infoService.selectByExample(null);
    }

    @GetMapping(value = "update")
    public List<Info> update() {
        Example example = new Example(Info.class);
        example.createCriteria().andEqualTo("id",1);
        Info info = infoService.selectOneByExample(example);
        System.out.println(info);
        info.setName("lisi");
        int i = infoService.updateNotNull(info);
        System.out.println(i);
        return infoService.selectByExample(null);
    }

    @GetMapping(value = "delete")
    public List<Info> delete(){
        infoService.deleteByProperty("name","蒋嫒");
        return infoService.selectByExample(null);
    }



}