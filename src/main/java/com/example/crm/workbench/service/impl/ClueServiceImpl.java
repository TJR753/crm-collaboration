package com.example.crm.workbench.service.impl;

import com.example.crm.settings.domain.User;
import com.example.crm.utils.DateTimeUtil;
import com.example.crm.utils.UUIDUtil;
import com.example.crm.workbench.dao.*;
import com.example.crm.workbench.domain.*;
import com.example.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public List<User> getUserList() {
        List<User> userList=clueDao.getUserList();
        return userList;
    }

    @Override
    public boolean saveClue(Clue clue) {
        int i=clueDao.saveClue(clue);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Clue getDetail(String id) {
        Clue clue=clueDao.getDetail(id);
        return clue;
    }

    @Override
    public boolean disassociate(String id) {
        int i=clueActivityRelationDao.disassociate(id);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean getlinkedActivity(ClueActivityRelation clueActivityRelation) {
        int i=clueActivityRelationDao.linkedActivity(clueActivityRelation);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean convertClue(Tran tran, String flag, String clueId,User user) {
        //任何一步失败即为false
        boolean flag1=true;
//        1. 通过线索id获得线索
        Clue clue = clueDao.getDetail(clueId);
//        2. 通过线索获取客户信息，当客户不存在时，新建客户(根据公司名精确匹配)
        Customer customer=customerDao.getCustomerByCompany(clue.getCompany());
        String createTime = DateTimeUtil.getSysTime();
        if(customer==null){
            customer = new Customer();
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(user.getName());
            customer.setCreateTime(createTime);
            customer.setId(UUIDUtil.getUUID());
            customer.setDescription(clue.getDescription());
            customer.setName(clue.getCompany());
            customer.setOwner(user.getId());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());
        }
        int i=customerDao.save(customer);
        if(i!=1){
            flag1=false;
        }
//        3. 通过线索提取联系人信息,保存联系人
        Contacts contacts = new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getName());
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setAppellation(clue.getAppellation());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setMphone(clue.getMphone());
        contacts.setSource(clue.getSource());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setJob(clue.getJob());
        contacts.setOwner(user.getId());
        i=contactsDao.save(contacts);
        if(i!=1){
            flag1=false;
        }
//        4. 将线索备注转换到联系人备注和客户备注
        List<ClueRemark> clueRemarkList=clueRemarkDao.getClueReamrk(clueId);
        //线索备注数量
        int count1=clueRemarkList.size();

        for(ClueRemark cr:clueRemarkList){
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setCreateBy(user.getName());
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(cr.getNoteContent());
            i=contactsRemarkDao.save(contactsRemark);
            if(i!=1){
                flag1=false;
            }
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateBy(user.getName());
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(cr.getNoteContent());
            i=customerRemarkDao.save(customerRemark);
            if(i!=1){
                flag1=false;
            }
        }
//        5. 线索和市场活动的关系转换为联系人和市场活动的关系
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getLinkedActivity(clueId);
        //线索和市场活动的关系的数量
        int count2 = clueActivityRelationList.size();

        for(ClueActivityRelation car:clueActivityRelationList){
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(car.getActivityId());
            i=contactsActivityRelationDao.save(contactsActivityRelation);
            if(i!=1){
                flag1=false;
            }
        }
//        6. 如果由创建交易需求,创建一条交易
        if("true".equals(flag)){
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(user.getId());
//            tran.setExpectedDate();
            tran.setCustomerId(customer.getId());
//            tran.setType("");
            tran.setSource(clue.getSource());
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getName());
            tran.setCreateTime(createTime);
            tran.setDescription(clue.getDescription());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());
            i=tranDao.save(tran);
            if(i!=1){
                flag1=false;
            }
//            7. 如果创建了交易,则创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setTranId(tran.getId());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setCreateBy(user.getName());
            tranHistory.setCreateTime(createTime);
            tranHistory.setStage(tran.getStage());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            i=tranHistoryDao.save(tranHistory);
            if(i!=1){
                flag1=false;
            }
        }
//        8. 删除线索备注
        i=clueRemarkDao.deleteByClueId(clueId);
        if(i!=count1){
            flag1=false;
        }
//        9. 删除线索和市场活动关系
        i=clueActivityRelationDao.deleteByClueId(clueId);
        if(i!=count2){
            flag1=false;
        }
//        10. 删除线索
        i=clueDao.deleteById(clueId);
        if(i!=1){
            flag1=false;
        }
        return flag1;
    }

    @Override
    public void save() {
        Clue clue = new Clue();
        clue.setId(UUIDUtil.getUUID());
        clueDao.saveClue(clue);
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setNoteContent("daf");
//        clueRemark.setId(UUIDUtil.getUUID());
        clueRemarkDao.save(clueRemark);
    }
}


