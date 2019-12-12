package com.liaoin.demo.controller.common;

import com.github.pagehelper.PageInfo;
import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.common.FileManage;
import com.liaoin.demo.common.SurpassmFile;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.service.common.FileManageService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2019-04-01 10:47:03
  * Version 1.0
  * Description 文件管理控制层
  */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/fileManage/")
@Api(tags  =  "文件管理Api")
public class FileManageController {

    @Resource
    private FileManageService fileManageService;


    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public Result deleteGetById(@ApiParam(hidden = true)@Login Long userId,
								@ApiParam(value = "主键",required = true)@RequestParam(value = "id") @NotNull Integer id) {
		FileManage byId = fileManageService.findById(id);
		if (byId == null){
			return fail();
		}
		fileManageService.deleteGetById(byId);
		return ok();
    }

    @PostMapping("v1/findById")
    @ApiOperation(value = "根据主键查询")
    public Result findById(@ApiParam(hidden = true)@Login Long userId,
                           @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Integer id) {
		FileManage byId = fileManageService.findById(id);
		return ok(byId);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public Result pageQuery(@ApiParam(hidden = true)@Login Long userId,
                            @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
                            @ApiParam(value = "多少条",required = true)@RequestParam(value = "size") Integer size,
                            @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
                            FileManage fileManage) {
		PageInfo<FileManage> fileManagePageInfo = fileManageService.pageQuery(page, size, sort, fileManage);
		return ok(fileManagePageInfo);
    }



	@PostMapping("v1/insert/upload")
	@ApiOperation("单文件上传（存入数据库）")
	public Result insert(@ApiParam(hidden = true)@Login Long userId, HttpServletRequest request, @RequestParam MultipartFile file) {
		SurpassmFile surpassmFile = fileManageService.insert(request, file);
		return ok(surpassmFile);
	}
	@PostMapping("v1/insert/batchUpload")
	@ApiOperation(value = "批量文件上传（存入数据库,无法使用，存在消耗冲突）",hidden = true)
	public Result insertBatch(@ApiParam(hidden = true) @Login Long userId, HttpServletRequest request, @RequestParam(required = false)@NotNull MultipartFile[] files) {
		try {
			fileManageService.insertBatch(request,files);
		} catch (Exception e) {
			throw new  CustomException(201,e.getMessage());
		}
		return ok();
	}

	@PostMapping("v1/upload")
	@ApiOperation(value = "单文件上传（不存入数据库）")
	public Result store(@ApiParam(hidden = true) @Login Long userId,
						@RequestParam("file") MultipartFile file) {
		SurpassmFile store = fileManageService.store(file);
		return ok(store);
	}

	@GetMapping("v1/auth/getFileNameUrl")
	@ApiOperation(value = "文件下载")
	public ResponseEntity<org.springframework.core.io.Resource> getFileNameUrl(@RequestParam String getFileNameUrl){
		FileSystemResource file = fileManageService.loadAsResource(getFileNameUrl);
		return ResponseEntity
				.ok()
				.contentLength(file.getFile().length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFilename())
				.body(file);
	}

	@ExceptionHandler(CustomException.class)
	public Result handleStorageFileNotFound(CustomException exc) {
		return fail("文件有重名,请重命名文件");
	}


	@GetMapping("v1/auth/listUploadedFiles")
	@ApiOperation(value = "返回所有文件列表")
	public Result listUploadedFiles() {
		List<String> serveFile = fileManageService
				.loadAll()
				.stream()
				.map(path ->
						MvcUriComponentsBuilder
								.fromMethodName(FileManageController.class, "serveFile", path.toFile().getPath().replace("\\","/"))
								.build().toString())
				.collect(Collectors.toList());
		return ok(serveFile);
	}

	@GetMapping("v1/auth/getPath")
	@ApiOperation(value = "后端专用",hidden = true)
	public ResponseEntity<org.springframework.core.io.Resource> serveFile(@RequestParam String path) throws IOException {
		org.springframework.core.io.Resource file = fileManageService.serveFile(path);
		return ResponseEntity
				.ok()
				.contentLength(file.getFile().length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFilename())
				.body(file);
	}
}
