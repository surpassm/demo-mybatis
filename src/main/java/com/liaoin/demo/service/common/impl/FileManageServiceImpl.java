package com.liaoin.demo.service.common.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.common.jackson.ResultCode;
import com.github.surpassm.common.pojo.SurpassmFile;
import com.github.surpassm.common.tool.util.FileUtils;
import com.liaoin.demo.entity.common.FileManage;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.exception.StorageException;
import com.liaoin.demo.mapper.common.FileManageMapper;
import com.liaoin.demo.security.BeanConfig;
import com.liaoin.demo.service.common.FileManageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.surpassm.common.jackson.Result.fail;
import static com.github.surpassm.common.jackson.Result.ok;


/**
  * @author mc
  * Create date 2019-04-01 10:47:03
  * Version 1.0
  * Description 文件管理实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class FileManageServiceImpl implements FileManageService {
    @Resource
    private FileManageMapper fileManageMapper;
    @Resource
	private BeanConfig beanConfig;

	private final Path rootLocation= Paths.get("upload");

    @Override
    public Result insert(String accessToken,FileManage fileManage) {
        if (fileManage == null){
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        UserInfo loginUserInfo = beanConfig.getAccessToken(accessToken);
        fileManageMapper.insert(fileManage);
        return ok();
    }

    @Override
    public Result update(String accessToken,FileManage fileManage) {
        if (fileManage == null){
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        UserInfo loginUserInfo = beanConfig.getAccessToken(accessToken);
        fileManageMapper.updateByPrimaryKeySelective(fileManage);
        return ok();
    }

    @Override
    public Result deleteGetById(String accessToken,Integer id){
        if (id == null){
            return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
        FileManage fileManage = fileManageMapper.selectByPrimaryKey(id);
        if(fileManage == null){
            return fail(ResultCode.RESULE_DATA_NONE.getMsg());
        }
        UserInfo loginUserInfo = beanConfig.getAccessToken(accessToken);
        fileManageMapper.updateByPrimaryKeySelective(fileManage);
        return ok();
    }


    @Override
    public Result findById(String accessToken,Integer id) {
        if (id == null){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
        }
		FileManage fileManage = fileManageMapper.selectByPrimaryKey(id);
        if (fileManage == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
        return ok(fileManage);

    }

    @Override
    public Result pageQuery(String accessToken,Integer page, Integer size, String sort, FileManage fileManage) {
        page = null  == page ? 1 : page;
        size = null  == size ? 10 : size;
        PageHelper.startPage(page, size);
        Example.Builder builder = new Example.Builder(FileManage.class);
        if(fileManage != null){
        if (fileManage.getId() != null){
            builder.where(WeekendSqls.<FileManage>custom().andEqualTo(FileManage::getId,fileManage.getId()));
        }
        if (fileManage.getFileNewName() != null && !"".equals(fileManage.getFileNewName().trim())){
            builder.where(WeekendSqls.<FileManage>custom().andLike(FileManage::getFileNewName,"%"+fileManage.getFileNewName()+"%"));
        }
        if (fileManage.getFileSuffix() != null && !"".equals(fileManage.getFileSuffix().trim())){
            builder.where(WeekendSqls.<FileManage>custom().andLike(FileManage::getFileSuffix,"%"+fileManage.getFileSuffix()+"%"));
        }
        if (fileManage.getUrl() != null && !"".equals(fileManage.getUrl().trim())){
            builder.where(WeekendSqls.<FileManage>custom().andLike(FileManage::getUrl,"%"+fileManage.getUrl()+"%"));
        }
        }
        Page<FileManage> all = (Page<FileManage>) fileManageMapper.selectByExample(builder.build());
        Map<String, Object> map = new HashMap<>(16);
        map.put("total",all.getTotal());
        map.put("rows",all.getResult());
        return ok(map);
    }


	@Override
	public SurpassmFile store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			String coustem = FileUtils.nowDate() + "/" + file.getOriginalFilename();
			Path resolve = this.rootLocation.resolve(coustem);
			java.io.File dest = new java.io.File(resolve.toFile().getPath());
			//判断文件父目录是否存在
			if (!dest.getParentFile().exists()) {
				boolean mkdirs = dest.getParentFile().mkdirs();
			}
			Files.copy(file.getInputStream(), resolve);
			String path = resolve.toFile().getPath().replace("\\", "/");
			return SurpassmFile.builder().url(path).build();
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public List<Path> loadAll() {
		FilePaths filePaths = new FilePaths();
		filePaths.func(this.rootLocation.toFile());
		return filePaths.getPaths();
	}
	@Data
	public class FilePaths{
		List<Path> paths = new ArrayList<>();
		private void func(java.io.File fileNow){
			java.io.File[] fileOld = fileNow.listFiles();
			assert fileOld != null;
			for(java.io.File file:fileOld){
				//若是目录，则递归打印该目录下的文件
				if(file.isDirectory()) {
					func(file);
				}
				//若是文件，直接打印
				if(file.isFile()) {
					Path path = file.toPath();
					paths.add(path);
				}
			}
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public FileSystemResource loadAsResource(String getFileNameUrl) {
		FileSystemResource fileSystemResource = new FileSystemResource(getFileNameUrl);
		if(fileSystemResource.exists() || fileSystemResource.isReadable()) {
			return fileSystemResource;
		}
		else {
			throw new StorageException("Could not read file: " + getFileNameUrl);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	@Override
	public org.springframework.core.io.Resource serveFile(String fileUrl) {
		FileSystemResource resource = new FileSystemResource(fileUrl);
		if(resource.exists() || resource.isReadable()) {
			return resource;
		}
		else {
			throw new StorageException("Could not read file: " + fileUrl);
		}
	}

	@Override
	public Result insert(HttpServletRequest request,MultipartFile file) {
		try {
			SurpassmFile upload = FileUtils.upload(file, request, "upload");
			fileManageMapper.insert(FileManage.builder()
					.fileOldName(upload.getFileOldName())
					.fileNewName(upload.getFileNewName())
					.fileSuffix(upload.getFileSuffix())
					.url(upload.getUrl())
					.build());
			return ok(upload);
		} catch (Exception e) {
			log.info("文件上传失败", e);
			return fail();
		}

	}
}

