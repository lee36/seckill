package com.lee.seckillshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.commons.model.Store;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.mapper.*;
import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.service.GoodsService;
import com.lee.seckillshop.commons.vo.GoodSolrDocument;
import com.lee.seckillshop.commons.vo.SeckillGoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author admin
 * @date 2018-09-30
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static String uploadPath="F:\\IDEA_project\\seckill-shop\\src\\main\\resources\\upload\\goods";
    private static String showPath="http://localhost:8080/goods/";

    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private JedisComponet jedisTemplate;
    @Autowired
    private GoodSolrDocumentRepository goodSolrDocumentRepository;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Map<String, Object> showIndex(Pageable pageable) throws Exception {
        Map map = jedisTemplate.get("index:info:page=" + pageable.getPageNumber(), Map.class);
        HashMap<String, Object> info = new HashMap<>();
        if (map == null || map.size() == 0) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
            List<Goods> popularGoods = goodsMapper.findTop4ByWeight();
            PageInfo<Goods> pageInfo = new PageInfo<>(popularGoods);
            info.put("goods", pageInfo);
            List<SeckillGoodVo> seckillGoods = seckillGoodsMapper.seckillGoodListTop4();
            info.put("seckillGoods", addStartTimeAndFinishedTime(seckillGoods));
            info.put("catalogs", catalogMapper.findAllCatalog());
            jedisTemplate.set("index:info:page=" + pageable.getPageNumber(), info, 5L);
            return info;
        } else {
            return map;
        }
    }

    @Override
    public Page<GoodSolrDocument> findAllSolrGoods(Pageable pageable) {
        return goodSolrDocumentRepository.findAll(pageable);
    }

    @Override
    public Page<GoodSolrDocument> findByInfo(String info, String info1, Pageable pageable) {
        return goodSolrDocumentRepository.findByGoodInfoOrGoodNameLike(info, info1, pageable);
    }

    @Override
    public Goods findById(Integer id) {
        Goods goods = goodsMapper.findById(id);
        return goods;
    }

    @Override
    public List<Goods> getMySelfGoodsList(Integer id) {
        return goodsMapper.getMySelfGoodsList(id);
    }

    @Override
    public Boolean addGood(MultipartFile file, Goods goods) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return false;
        }
        Integer id = user.getId();
        Store store = storeMapper.findByUserId(id);
        //生成新的文件名称
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."), filename.length());
        String newFileName=UUID.randomUUID().toString();
        String newFullFileName=newFileName+suffix;
        String dbFileName=showPath+newFullFileName;
        //先存入数据库
        goods.setStore(store);
        goods.setImg(dbFileName);
        int i = goodsMapper.addGoods(goods);
        if(i>0){
            //上传文件
            try {
                file.transferTo(new File(uploadPath, newFullFileName));
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public Boolean updateGoods(MultipartFile file, Goods goods) {
        String newFullFileName=null;
        if(file!=null){
            //先存入数据库
            //生成新的文件名称
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf("."), filename.length());
            String newFileName=UUID.randomUUID().toString();
            newFullFileName=newFileName+suffix;
            String dbFileName=showPath+newFullFileName;
            goods.setImg(dbFileName);
        }
        int i = goodsMapper.updateGoods(goods);
        if(i>0){
            //上传文件
            if(file!=null){
                //先存入数据库
                try {
                    file.transferTo(new File(uploadPath, newFullFileName));
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteSelected(List<Integer> ids) {
        try {
            ids.stream().forEach((id) -> {
                goodsMapper.deleteById(id);
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Goods> getIndexGoods() {
        return goodsMapper.findTop8ByWeight();
    }

    @Override
    public List<Goods> getCatalogGoods(Integer id) {
        return goodsMapper.getCatalogGoods(id);
    }

    @Override
    public List<Goods> getStoreGoods(Integer id) {
        return goodsMapper.getStoreGoods(id);
    }

    /**
     * 添加秒杀开始时间和时间
     *
     * @param seckillGoods
     * @return
     */
    public List<SeckillGoodVo> addStartTimeAndFinishedTime(List<SeckillGoodVo> seckillGoods) {
        for (SeckillGoodVo seckillGood : seckillGoods) {
            seckillGood.setFinishedTime(seckillGood.getEndTime().getTime());
            seckillGood.setStartTime(seckillGood.getCreateTime().getTime());
        }
        return seckillGoods;
    }
}
