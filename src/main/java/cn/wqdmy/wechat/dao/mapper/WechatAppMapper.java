package cn.wqdmy.wechat.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.wqdmy.wechat.dao.domain.WechatApp;


@Mapper
public interface WechatAppMapper {

	public List<WechatApp> findAll();
}
