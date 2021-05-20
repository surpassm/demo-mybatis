package com.liaoin.demo.controller.auth;

import com.github.pagehelper.PageInfo;
import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.FileManage;
import com.liaoin.demo.common.SurpassmFile;
import com.liaoin.demo.entity.UploadFileParam;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.service.FileManageService;
import com.liaoin.demo.util.ValidateUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.liaoin.demo.common.R.fail;
import static com.liaoin.demo.common.R.ok;
import static org.apache.commons.lang3.StringUtils.trim;


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


	@Login
    @PostMapping("v1/getById")
    @ApiOperation(value = "根据主键删除")
    public R deleteGetById(@ApiParam(hidden = true)@Login Long userId,
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
    public R findById(@ApiParam(hidden = true)@Login Long userId,
					  @ApiParam(value = "主键",required = true)@RequestParam(value = "id") Integer id) {
		FileManage byId = fileManageService.findById(id);
		return ok(byId);
    }

    @PostMapping("v1/pageQuery")
    @ApiOperation(value = "条件分页查询")
    public R pageQuery(@ApiParam(hidden = true)@Login Long userId,
					   @ApiParam(value = "第几页", required = true) @RequestParam(value = "page") Integer page,
					   @ApiParam(value = "多少条",required = true)@RequestParam(value = "size") Integer size,
					   @ApiParam(value = "排序字段")@RequestParam(value = "sort",required = false) String sort,
					   FileManage fileManage) {
		PageInfo<FileManage> fileManagePageInfo = fileManageService.pageQuery(page, size, sort, fileManage);
		return ok(fileManagePageInfo);
    }



	@PostMapping("v1/insert/upload")
	@ApiOperation("单文件上传（存入数据库）")
	public R insert(@ApiParam(hidden = true)@Login Long userId, HttpServletRequest request, @RequestParam MultipartFile file) {
		SurpassmFile surpassmFile = fileManageService.insert(request, file);
		return ok(surpassmFile);
	}
	@PostMapping("v1/insert/batchUpload")
	@ApiOperation(value = "批量文件上传（存入数据库,无法使用，存在消耗冲突）",hidden = true)
	public R insertBatch(@ApiParam(hidden = true) @Login Long userId, HttpServletRequest request, @RequestParam(required = false)@NotNull MultipartFile[] files) {
		try {
			fileManageService.insertBatch(request,files);
		} catch (Exception e) {
			throw new  CustomException(201,e.getMessage());
		}
		return ok();
	}

	@PostMapping("v1/upload")
	@ApiOperation(value = "单文件上传（不存入数据库）")
	public R store(@ApiParam(hidden = true) @Login Long userId,
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
	public R handleStorageFileNotFound(CustomException exc) {
		return fail("文件有重名,请重命名文件");
	}


	@GetMapping("v1/auth/listUploadedFiles")
	@ApiOperation(value = "返回所有文件列表")
	public R listUploadedFiles() {
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


	/**
	 * 断点续传方式上传文件：用于大文件上传
	 *
	 * @param param
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/breakpoint-upload", consumes = "multipart/*", headers = "content-type=multipart/form-data", produces = "application/json;charset=UTF-8")
	public R breakpointResumeUpload(UploadFileParam param, HttpServletRequest request) {
		return fileManageService.breakpointResumeUpload(param, request);
	}

	/**
	 * 检查文件MD5（文件MD5若已存在进行秒传）
	 *
	 * @param md5
	 * @param fileName
	 */
	@GetMapping(value = "/check-file")
	public R checkFileMd5(String md5, String fileName) {
		return fileManageService.checkFileMd5(md5, fileName);
	}


	/**
	 * 添加文件
	 * 断点续传完成后上传文件信息进行入库操作
	 *
	 * @param fileManage
	 */
	@PostMapping("/add")
	public R addFile(@RequestBody FileManage fileManage, BindingResult errors) {
		ValidateUtil.check(errors);
		return ok(fileManageService.insert(fileManage));
	}


	/**
	 * 文件下载
	 *
	 * @param id
	 * @param isSource
	 * @param request
	 * @param response
	 */
	@GetMapping(value = "/download/{id}")
	public void viewFilesImage(@PathVariable Integer id, @RequestParam(required = false) Boolean isSource, HttpServletRequest request, HttpServletResponse response) {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			FileManage fileManage = fileManageService.findById(id);
			if (fileManage == null) {
				throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
			}
			String filename = (!basicIsEmpty(isSource) && isSource) ? fileManage.getFileOldName() : fileManage.getUrl();
			inputStream = fileManageService.getFileInputStream(id);

			response.setHeader("Content-Disposition", "attachment;filename=" + convertToFileName(request, filename));
			// 获取输出流
			outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			log.error("文件下载出错", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean basicIsEmpty(Object object) {
		if (null == object) {
			return true;
		}
		return StringUtils.isEmpty(trim(object + ""));
	}

	/**
	 * 转换为文件名
	 *
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertToFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
		// 针对IE或者以IE为内核的浏览器：
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：  
			fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		}
		return fileName;
	}
}
