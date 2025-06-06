package com.sakurapuare.boatmanagement.service.base;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.pojo.entity.Notifications;
import com.sakurapuare.boatmanagement.mapper.NotificationsMapper;
import org.springframework.stereotype.Service;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 通知表 服务层实现。
 *
 */
@Service
@CacheConfig(cacheNames = "notifications")
public class BaseNotificationsService extends ServiceImpl<NotificationsMapper, Notifications> {


    @CacheEvict(allEntries = true)
    public boolean remove(QueryWrapper query) {
        return super.remove(query);
    }

    @CacheEvict(key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @CacheEvict(allEntries = true)
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return super.removeByIds(ids);
    }

    @CacheEvict(allEntries = true)
    public boolean update(Notifications entity, QueryWrapper query) {
        return super.update(entity, query);
    }

    @CacheEvict(key = "#entity.id")
    public boolean updateById(Notifications entity, boolean ignoreNulls) {
        return super.updateById(entity, ignoreNulls);
    }

    @CacheEvict(allEntries = true)
    public boolean updateBatch(Collection<Notifications> entities, int batchSize) {
        return super.updateBatch(entities, batchSize);
    }

    @Cacheable(key = "#id")
    public Notifications getById(Serializable id) {
        return super.getById(id);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public Notifications getOne(QueryWrapper query) {
        return super.getOne(query);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> R getOneAs(QueryWrapper query, Class<R> asType) {
        return super.getOneAs(query, asType);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public Object getObj(QueryWrapper query) {
        return super.getObj(query);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> R getObjAs(QueryWrapper query, Class<R> asType) {
        return super.getObjAs(query, asType);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public List<Object> objList(QueryWrapper query) {
        return super.objList(query);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> List<R> objListAs(QueryWrapper query, Class<R> asType) {
        return super.objListAs(query, asType);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public List<Notifications> list(QueryWrapper query) {
        return super.list(query);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public <R> List<R> listAs(QueryWrapper query, Class<R> asType) {
        return super.listAs(query, asType);
    }

    /**
     * @deprecated 无法通过注解进行缓存操作。
     */
    @Deprecated
    public List<Notifications> listByIds(Collection<? extends Serializable> ids) {
        return super.listByIds(ids);
    }

    @Cacheable(key = "#root.methodName + ':' + #query.toSQL()")
    public long count(QueryWrapper query) {
        return super.count(query);
    }

    @Cacheable(key = "#root.methodName + ':' + #page.getPageSize() + ':' + #page.getPageNumber() + ':' + #query.toSQL()")
    public <R> Page<R> pageAs(Page<R> page, QueryWrapper query, Class<R> asType) {
        return super.pageAs(page, query, asType);
    }

} 