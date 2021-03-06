package com.own.promotion.controller;

import java.util.ArrayList;
import java.util.List;

import com.own.face.util.Resp;
import com.own.face.util.base.BaseController;
import com.own.promotion.dao.PromotionTypeDao;
import com.own.promotion.dao.domain.PromotionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value="/sale/promotionType")
public class PromotionTypeController extends BaseController {
	@Autowired
	private PromotionTypeDao promotionTypeDao;

	@ApiOperation(value = "查询单条活动信息")
	@GetMapping("/{id}")
	public @ResponseBody
	Resp findPromotionTypeById(@PathVariable Integer id){
		log.info("查询id为："+id+"的活动类型");
		return new Resp(promotionTypeDao.getFromId(id));
	}

	@ApiOperation(value = "查询活动列表，支持搜索")
	@GetMapping("/query/all")
	public @ResponseBody Resp findAll(){
		return new Resp(promotionTypeDao.findAllPromotionType());
	}

	@ApiOperation(value = "保存活动信息")
	@PostMapping("/save")
	public @ResponseBody Resp save(@RequestBody PromotionType p){
		promotionTypeDao.save(p);//创建节点
		promotionTypeDao.createRelationship(p.getId().intValue(), 24, "DATA");//建立关系，24为模板节点的id,DATA为关系名
		return new Resp(p);
	}

	@ApiOperation(value = "删除数据以及关系")
	@DeleteMapping("/{id}")
	public @ResponseBody Resp deletePromotion(@PathVariable Integer id){
		log.info("删除节点id为："+id+"的数据");
		//删除该数据，并且删除关系
		return new Resp(promotionTypeDao.deleteRelationships(id));
	}

	@ApiOperation(value = "修改信息")
	@PutMapping("/update")
	public @ResponseBody Resp upPromotion(PromotionType p){
		promotionTypeDao.save(p);
		return new Resp(p);
	}
}
