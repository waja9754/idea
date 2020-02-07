package com.example.test.crupmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @auohor ai
 * @data 2019/12/28
 */
public class CrupService<T> {
    private Class tClass;

    /**
     * 通用mapper
     */
    @Autowired
    CrupMapper<T> crupMapper;


    public CrupService() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        tClass = (Class) params[0];
    }

    public CrupMapper<T> getCurpMapper() {
        return  crupMapper;
    }

    /**
     * 插入或更新前的数据检查
     *
     * @param t
     * @return
     */
    protected void dataValidator(T t) {

    }

    ///////////////////////////////////////////////////////////////////////////
    // 通用方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取一个例子
     *
     * @return
     */
    public Example getExample() {
        return new Example(tClass);
    }

    /**
     * 插入为空的数据
     *
     * @return
     */
    public int insertAll(T t) {
        dataValidator(t);
        return crupMapper.insert(t);
    }

    /**
     * 插入不为空的数据
     *
     * @param t
     * @return
     */
    public int insertNotNull(T t) {
        dataValidator(t);
        return crupMapper.insertSelective(t);
    }

    /**
     * 批量添加
     * @param list
     * @return
     */
    public int insertList(List<T> list){
        return crupMapper.insertList(list);
    }

    /**
     * 更新为空的数据
     *
     * @param t
     * @return
     */
    public int updateAll(T t) {
        dataValidator(t);
        return crupMapper.updateByPrimaryKey(t);
    }

    /**
     * 更新不为空的数据
     *
     * @param t
     * @return
     */
    public int updateNotNull(T t) {
        dataValidator(t);
        return crupMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据Key删除数据
     *
     * @param key
     * @param value
     * @return
     */
    public int deleteByProperty(String key, String value) {
        Example example = new Example(tClass);
        int count = 0;
        if (!StringUtils.isEmpty(value)) {
            example.createCriteria()
                    .andEqualTo(key, value);
            count = crupMapper.deleteByExample(example);
        }
        return count;
    }

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    public int deleteById(Integer id){
        return crupMapper.deleteByPrimaryKey(id);
    }
    /**
     * 根据对象删除
     * 注意，可能是多个
     *
     * @param e
     * @return
     */
    public int deleteByExample(Example e) {
        return crupMapper.deleteByExample(e);
    }

    /**
     * 查询所有
     *
     * @param t
     * @return
     */
    public List<T> selectAll(T t) {
        return crupMapper.select(t);
    }

    /**
     * 查询单条数据
     *
     * @param t
     * @return
     */
    public T selectOne(T t) {
        List<T> list = selectAll(t);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据主键为id的字段查询
     * 注意：主键必须是id
     *
     * @param id
     * @return
     */
    public T selectById(Integer id) {
        return crupMapper.selectByPrimaryKey(id);
    }

    /**
     * 单属性查询单条
     *
     * @param key
     * @param value
     * @return
     */
    public T selectByProperty(String key, String value) {
        Example example = new Example(tClass);
        example.createCriteria()
                .andEqualTo(key, value);
        List<T> list = crupMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询多条记录
     *
     * @param e
     * @return
     */
    public T selectOneByExample(Example e) {
        List<T> list = selectByExample(e);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询多条记录
     *
     * @param e
     * @return
     */
    public List<T> selectByExample(Example e) {
        return crupMapper.selectByExample(e);
    }


    /**
     * 查询条数
     *
     * @param example
     * @return
     */
    public int selectCountByExample(Example example) {
        return crupMapper.selectCountByExample(example);
    }

    /**
     * 查询一批单一属性相同的
     *
     * @param key
     * @param value
     * @return
     */
    public List<T> selectAllByProperty(String key, String value) {
        Example example = null;
        if (!StringUtils.isEmpty(value)) {
            example = new Example(tClass);
            example.createCriteria()
                    .andEqualTo(key, value);
        }
        return crupMapper.selectByExample(example);
    }

    /**
     * in查询
     *
     * @param key
     * @param values
     * @return
     */
    public List<T> selectInByProperty(String key, List values) {
        if (values.size() == 0) {
            return new ArrayList<>();
        }
        Example example = new Example(tClass);
        example.createCriteria().andIn(key, values);
        return crupMapper.selectByExample(example);
    }



}
