package com.liaoin.demo.service.common;

import com.github.pagehelper.PageInfo;
import com.liaoin.demo.entity.common.FileManage;
import com.liaoin.demo.entity.common.SurpassmFile;
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
	 * @param fileManage 对象
	 * @return 前端返回格式
	 */
	FileManage insert(FileManage fileManage);
    /**
	 * 修改
	 * @param fileManage 对象
	 * @return 前端返回格式
	 */
	void update(FileManage fileManage);
    /**
	 * 根据主键删除
	 * @return 前端返回格式
	 */
	void deleteGetById(FileManage fileManage);
    /**
	 * 根据主键查询
	 * @param id 标识
	 * @return 前端返回格式
	 */
	FileManage findById(Integer id);
    /**
	 * 条件分页查询
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param fileManage 查询条件
	 * @return 前端返回格式
	 */
	PageInfo<FileManage> pageQuery(Integer page, Integer size, String sort, FileManage fileManage);


	SurpassmFile store(MultipartFile file);

	List<Path> loadAll();

	Path load(String filename);

	FileSystemResource loadAsResource(String getFileNameUrl);

	void deleteAll();

	Resource serveFile(String fileUrl);

	SurpassmFile insert(HttpServletRequest request,MultipartFile file);

	/**
	 * 批量文件上传
	 * @param request request
	 * @param file file
	 * @return return
	 */
	void insertBatch(HttpServletRequest request, MultipartFile[] file) throws Exception;
}
