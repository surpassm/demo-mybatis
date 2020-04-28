package com.liaoin.demo.service;

import com.github.pagehelper.PageInfo;
import com.liaoin.demo.entity.FileManage;
import com.liaoin.demo.common.SurpassmFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.List;

/**
 * @author mc
 * Create date 2019-04-01 10:47:03
 * Version 1.0
 * Description 文件管理接口
 */
public interface FileManageService {
    /**
     * 新增
     *
     * @param fileManage 对象
     * @return 前端返回格式
     */
    FileManage insert(FileManage fileManage);

    /**
     * 修改
     *
     * @param fileManage 对象
     */
    void update(FileManage fileManage);

    /**
     * 根据主键删除
     *
     * @param fileManage fileManage
     */
    void deleteGetById(FileManage fileManage);

    /**
     * 根据主键查询
     *
     * @param id 标识
     * @return 前端返回格式
     */
    FileManage findById(Integer id);

    /**
     * 条件分页查询
     *
     * @param page       当前页
     * @param size       显示多少条
     * @param sort       排序字段
     * @param fileManage 查询条件
     * @return 前端返回格式
     */
    PageInfo<FileManage> pageQuery(Integer page, Integer size, String sort, FileManage fileManage);

    /**
     * 单文件上传（不存入数据库）
     *
     * @param file file
     * @return SurpassmFile
     */
    SurpassmFile store(MultipartFile file);

    /**
     * 返回所有文件列表
     *
     * @return Path
     */
    List<Path> loadAll();

    /**
     * 根据文件名称获取path路径
     *
     * @param filename 文件名称
     * @return path
     */
    Path load(String filename);

    /**
     * 文件下载
     *
     * @param getFileNameUrl 文件url地址
     * @return FileSystemResource
     */
    FileSystemResource loadAsResource(String getFileNameUrl);

    /**
     * 删除当前路径所有文件
     */
    void deleteAll();

    /**
     * 后端专用 根据文件url获取io源
     *
     * @param fileUrl url
     * @return org.springframework.core.io.Resource
     */
    Resource serveFile(String fileUrl);

    /**
     * 单文件上传（存入数据库）
     *
     * @param request request
     * @param file    MultipartFile
     * @return SurpassmFile
     */
    SurpassmFile insert(HttpServletRequest request, MultipartFile file);

    /**
     * 批量文件上传
     *
     * @param request request
     * @param file    file
     */
    void insertBatch(HttpServletRequest request, MultipartFile[] file) throws Exception;
}
