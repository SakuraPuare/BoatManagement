package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.paginate.Page;
import com.sakurapuare.boatmanagement.pojo.dto.base.BaseFileUploadsDTO;
import com.sakurapuare.boatmanagement.pojo.entity.FileUploads;
import com.sakurapuare.boatmanagement.pojo.vo.base.BaseFileUploadsVO;
import com.sakurapuare.boatmanagement.service.base.BaseFileUploadsService;
import com.sakurapuare.boatmanagement.utils.POJOUtils;
import com.sakurapuare.boatmanagement.utils.ParamsUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.FILE_UPLOADS;

import com.mybatisflex.core.query.QueryColumn;

/**
 * 文件上传管理服务
 */
@Service
public class FileUploadsService extends BaseFileUploadsService {

    /**
     * 搜索字段
     */
    private static final QueryColumn[] SEARCH_FIELDS = {
            FILE_UPLOADS.ORIGINAL_NAME,
            FILE_UPLOADS.STORED_NAME,
            FILE_UPLOADS.FILE_TYPE,
            FILE_UPLOADS.BUSINESS_TYPE
    };

    /**
     * 管理员获取文件上传列表
     *
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 文件上传视图对象列表
     */
    public List<BaseFileUploadsVO> adminGetFileUploadsList(
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseFileUploadsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, FileUploads.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseFileUploadsVO.class);
    }

    /**
     * 管理员分页获取文件上传列表
     *
     * @param pageNum       页码
     * @param pageSize      每页大小
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 分页文件上传视图对象
     */
    public Page<BaseFileUploadsVO> adminGetFileUploadsPage(
            Integer pageNum,
            Integer pageSize,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseFileUploadsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, FileUploads.class);
        adminParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.pageAs(
                Page.of(pageNum, pageSize), queryWrapper, BaseFileUploadsVO.class);
    }

    /**
     * 管理员根据 ID 获取文件上传
     *
     * @param ids 文件上传 ID 字符串，多个 ID 用逗号分隔
     * @return 文件上传视图对象列表
     * @throws RuntimeException 当文件上传不存在时抛出
     */
    public List<BaseFileUploadsVO> adminGetFileUploadsByIds(String ids) {
        List<Long> idList = ParamsUtils.getListFromIds(ids);

        if (idList.isEmpty()) {
            throw new RuntimeException("文件上传记录不存在");
        }

        return POJOUtils.getListFromIds(idList, this::getFileUploadsInfo);
    }

    /**
     * 管理员创建文件上传记录
     *
     * @param fileUploadsDTO 文件上传数据传输对象
     * @return 文件上传视图对象
     * @throws RuntimeException 当创建失败时抛出
     */
    public BaseFileUploadsVO adminCreateFileUploads(BaseFileUploadsDTO fileUploadsDTO) {
        FileUploads fileUploads = POJOUtils.convertToEntity(fileUploadsDTO, FileUploads.class);

        if (!super.save(fileUploads)) {
            throw new RuntimeException("文件上传记录创建失败");
        }

        return getFileUploadsInfo(fileUploads.getId());
    }

    /**
     * 管理员更新文件上传记录
     *
     * @param id             文件上传 ID
     * @param fileUploadsDTO 文件上传数据传输对象
     * @return 文件上传视图对象
     * @throws RuntimeException 当文件上传不存在或更新失败时抛出
     */
    public BaseFileUploadsVO adminUpdateFileUploads(Long id, BaseFileUploadsDTO fileUploadsDTO) {
        FileUploads existingFileUploads = super.getById(id);
        if (existingFileUploads == null) {
            throw new RuntimeException("文件上传记录不存在");
        }

        FileUploads fileUploads = POJOUtils.convertToEntity(fileUploadsDTO, FileUploads.class);
        fileUploads.setId(id);

        if (!super.updateById(fileUploads)) {
            throw new RuntimeException("文件上传记录更新失败");
        }

        return getFileUploadsInfo(id);
    }

    /**
     * 管理员删除文件上传记录
     *
     * @param id 文件上传 ID
     * @throws RuntimeException 当文件上传不存在或删除失败时抛出
     */
    public void adminDeleteFileUploads(Long id) {
        FileUploads existingFileUploads = super.getById(id);
        if (existingFileUploads == null) {
            throw new RuntimeException("文件上传记录不存在");
        }

        if (!super.removeById(id)) {
            throw new RuntimeException("文件上传记录删除失败");
        }
    }

    /**
     * 用户获取自己的文件上传列表
     *
     * @param userId        用户ID
     * @param businessType  业务类型
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param filter        过滤条件
     * @return 文件上传视图对象列表
     */
    public List<BaseFileUploadsVO> userGetFileUploadsList(
            Long userId,
            String businessType,
            String search,
            String sort,
            String startDateTime,
            String endDateTime,
            BaseFileUploadsDTO filter) {
        QueryWrapper queryWrapper = POJOUtils.getQueryWrapper(filter, FileUploads.class);
        queryWrapper.and(FILE_UPLOADS.UPLOADER_ID.eq(userId));
        if (businessType != null && !businessType.isEmpty()) {
            queryWrapper.and(FILE_UPLOADS.BUSINESS_TYPE.eq(businessType));
        }
        userParseParams(queryWrapper, search, sort, startDateTime, endDateTime);
        return super.listAs(queryWrapper, BaseFileUploadsVO.class);
    }

    /**
     * 根据业务类型和业务ID获取文件列表
     *
     * @param businessType 业务类型
     * @param businessId   业务ID
     * @return 文件上传视图对象列表
     */
    public List<BaseFileUploadsVO> getFilesByBusiness(String businessType, Long businessId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(FILE_UPLOADS.BUSINESS_TYPE.eq(businessType))
                .and(FILE_UPLOADS.BUSINESS_ID.eq(businessId))
                .and(FILE_UPLOADS.IS_DELETED.eq(false));
        return super.listAs(queryWrapper, BaseFileUploadsVO.class);
    }

    /**
     * 获取文件上传信息
     *
     * @param id 文件上传 ID
     * @return 文件上传视图对象
     * @throws RuntimeException 当文件上传不存在时抛出
     */
    public BaseFileUploadsVO getFileUploadsInfo(Long id) {
        FileUploads fileUploads = super.getById(id);
        if (fileUploads == null) {
            throw new RuntimeException("文件上传记录不存在");
        }
        return POJOUtils.convertToVO(fileUploads, BaseFileUploadsVO.class);
    }

    /**
     * 管理员解析参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void adminParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime, String endDateTime) {
        POJOUtils.search(queryWrapper, SEARCH_FIELDS, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }

    /**
     * 用户解析参数
     *
     * @param queryWrapper  查询包装器
     * @param search        搜索关键词
     * @param sort          排序方式
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     */
    private void userParseParams(QueryWrapper queryWrapper, String search, String sort, String startDateTime, String endDateTime) {
        QueryColumn[] userSearchFields = {FILE_UPLOADS.ORIGINAL_NAME};
        POJOUtils.search(queryWrapper, userSearchFields, search);
        POJOUtils.dateRange(queryWrapper, startDateTime, endDateTime);
        POJOUtils.sort(queryWrapper, ParamsUtils.getSortFromParams(sort));
    }
} 