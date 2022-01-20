package com.example.crm.workbench.service.impl;

import com.example.crm.settings.dao.UserDao;
import com.example.crm.settings.domain.User;
import com.example.crm.utils.DateTimeUtil;
import com.example.crm.utils.UUIDUtil;
import com.example.crm.workbench.dao.CustomerDao;
import com.example.crm.workbench.dao.TranDao;
import com.example.crm.workbench.dao.TranHistoryDao;
import com.example.crm.workbench.domain.Customer;
import com.example.crm.workbench.domain.Tran;
import com.example.crm.workbench.domain.TranHistory;
import com.example.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Override
    public List<User> getUseList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }

    @Override
    public List<String> getCustomerName(String name) {
        List<String> customerList=customerDao.getCustomer(name);
        return customerList;
    }


    @Override
    public boolean save(Tran tran) {
        String customerId=customerDao.getCustomerId(tran.getCustomerId());
        //如果客户不存在，则创建客户
        boolean flag=true;
        if(customerId==null){
            Customer customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setName(tran.getName());
            customer.setWebsite("");
            customer.setPhone("");
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setDescription(tran.getDescription());
            customer.setAddress("");
            int i=customerDao.save(customer);
            if(i!=1){
                flag=false;
            }
            customerId=customer.getId();
        }
        //保存交易
        tran.setCustomerId(customerId);
        int i=tranDao.save(tran);
        if(i!=1){
            flag=false;
        }
        //保存交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setTranId(tran.getId());
        i=tranHistoryDao.save(tranHistory);
        if(i!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Tran getTranById(String id) {
        Tran tran=tranDao.getTranById(id);
        return tran;
    }

    @Override
    public List<TranHistory> getTranHistory(String tranId) {
        List<TranHistory> tranHistoryList=tranHistoryDao.getTranHistory(tranId);
        return tranHistoryList;
    }

    @Override
    public HashMap<String, Object> changeStage(Tran tran) {
        //改变交易并保存
        Tran t = tranDao.getTranById(tran.getId());
        t.setStage(tran.getStage());
        t.setEditBy(tran.getEditBy());
        t.setEditTime(DateTimeUtil.getSysTime());
        t.setPossibility(tran.getPossibility());
        int i=tranDao.update(t);
        boolean success=true;
        if(i!=1){
            success=false;
        }
        //保存交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(t.getStage());
        tranHistory.setPossibility(t.getPossibility());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setTranId(t.getId());
        i=tranHistoryDao.save(tranHistory);
        if(i!=1){
            success=false;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",success);
        map.put("tran",t);
        return map;
    }

    @Override
    public Map<String, Object> getChart() {
        //获得total
        int total=tranDao.getTotal();
        //获得List<Map<String,Object>>
        List<Map<String,Object>> mapList=tranDao.getStageGroup();
        //放入map
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("tranList",mapList);
        return map;
    }

}
