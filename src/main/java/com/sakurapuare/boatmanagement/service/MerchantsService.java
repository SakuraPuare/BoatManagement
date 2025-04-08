package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseMerchantsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Merchants;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseMerchantsVO;
import com.sakurapuare.boatmanagement.service.base.BaseMerchantsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.MERCHANTS;

@Service
@RequiredArgsConstructor
public class MerchantsService extends BaseMerchantsService {

    /**
     * 管理员搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            MERCHANTS.ID,
            MERCHANTS.STATUS,
            MERCHANTS.USER_ID,
            MERCHANTS.UNIT_ID
    };

    /**
     * 解析管理员商户查询参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void adminParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime,
                               String endDateTime) {
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 获取管理员商户列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 商户视图对象列表
     */
    public List<BaseMerchantsVO> adminGetMerchantList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseMerchantsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Merchants.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseMerchantsVO.class);
    }

    /**
     * 分页获取管理员商户列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页商户视图对象
     */
    public Page<BaseMerchantsVO> adminGetMerchantPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseMerchantsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, Merchants.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseMerchantsVO.class);
    }

    /**
     * 根据 ID 获取管理员商户
     *
     * @param ids 商户 ID 字符串，多个 ID 用逗号分隔
     * @return 商户视图对象列表
     * @throws RuntimeException 当商户不存在时抛出
     */
    public List<BaseMerchantsVO> adminGetMerchantByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("商户不存在");
        }

        List<Merchants> merchants = POJOUtils.getListFromIds(idList, super::getById);

        return POJOUtils.asOtherList(merchants, BaseMerchantsVO.class);
    }

    /**
     * 根据 ID 获取单个商户
     *
     * @param id 商户 ID
     * @return 商户视图对象
     * @throws RuntimeException 当商户不存在时抛出
     */
    public BaseMerchantsVO adminGetMerchantById(Long id) {
        Merchants merchant = super.getById(id);
        if (merchant == null) {
            throw new RuntimeException("商户不存在");
        }
        return POJOUtils.asOther(merchant, BaseMerchantsVO.class);
    }

    /**
     * 创建商户
     *
     * @param dto 商户数据传输对象
     * @return 创建的商户视图对象
     */
    public BaseMerchantsVO adminCreateMerchant(BaseMerchantsDTO dto) {
        Merchants merchant = POJOUtils.asOther(dto, Merchants.class);
        super.save(merchant);
        return POJOUtils.asOther(merchant, BaseMerchantsVO.class);
    }

    /**
     * 更新商户信息
     *
     * @param id  商户 ID
     * @param dto 商户数据传输对象
     * @return 更新后的商户视图对象
     * @throws RuntimeException 当商户不存在时抛出
     */
    public BaseMerchantsVO adminUpdateMerchant(Long id, BaseMerchantsDTO dto) {
        Merchants merchant = super.getById(id);
        if (merchant == null) {
            throw new RuntimeException("商户不存在");
        }
        
        Merchants updatedMerchant = POJOUtils.asOther(dto, Merchants.class);
        updatedMerchant.setId(id);
        super.updateById(updatedMerchant);
        return POJOUtils.asOther(updatedMerchant, BaseMerchantsVO.class);
    }

    /**
     * 删除商户
     *
     * @param id 商户 ID
     * @throws RuntimeException 当商户不存在时抛出
     */
    public void adminDeleteMerchant(Long id) {
        Merchants merchant = super.getById(id);
        if (merchant == null) {
            throw new RuntimeException("商户不存在");
        }
        super.removeById(id);
    }

    /*
     * 用户函数
     */

    private QueryWrapper getUserQueryWrapper(BaseMerchantsDTO queryDTO) {
        Merchants merchants = new Merchants();
        BeanUtils.copyProperties(queryDTO, merchants);
        return QueryWrapper.create(merchants);
    }

    public List<BaseMerchantsVO> getUserMerchantListQuery(BaseMerchantsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(queryDTO);
        return super.listAs(queryWrapper, BaseMerchantsVO.class);
    }

    public Page<BaseMerchantsVO> getUserMerchantPageQuery(Integer pageNum, Integer pageSize, BaseMerchantsDTO queryDTO) {
        QueryWrapper queryWrapper = getUserQueryWrapper(queryDTO);
        return super.pageAs(Page.of(pageNum, pageSize), queryWrapper, BaseMerchantsVO.class);
    }

    private void verifyMerchantId(Long id) {
        Merchants merchants = super.getById(id);
        if (merchants == null) {
            throw new IllegalArgumentException("商户不存在");
        }
    }

    public BaseMerchantsVO getUserMerchantById(Long id) {
        verifyMerchantId(id);
        Merchants merchants = super.getById(id);
        BaseMerchantsVO baseMerchantsVO = new BaseMerchantsVO();
        BeanUtils.copyProperties(merchants, baseMerchantsVO);
        return baseMerchantsVO;
    }
}
