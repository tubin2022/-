package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.dto.OrderDTO;
import com.ecjtu.ferry.pojo.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    /**
     * 添加订单(购买船票)
     * @param order 订单实体
     */
    public void addOrder(Order order);

    /**
     * 删除订单（退票）
     * @param oid   订单主键id
     */
    public void deleteOrder(Integer oid);

    /**
     * 根据订单id（主键）查询
     * @param oid   订单主键id
     * @return
     */
    public OrderDTO findById(Integer oid);

    /**
     * 根据条件分页查询订单信息
     * @param orderVO   封装的条件实体
     * @return
     */
    public List<OrderDTO> pageQuery(OrderVO orderVO);

    /**
     * 查询总记录数
     * @param orderVO   封装的条件实体
     * @return
     */
    public int findAllCount(OrderVO orderVO);

}
