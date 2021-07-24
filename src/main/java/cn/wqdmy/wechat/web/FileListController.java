package cn.wqdmy.wechat.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.wqdmy.wechat.util.ReadFileUtils;

/**
 * 文件列表
 * 
 * @author dmy
 *
 */
@Controller
public class FileListController {

	private final String defaultDocPath = "/opt/web-apps/doc/";

	/**
	 * 文档列表
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/api/file/list")
	public ModelAndView fileList(@RequestParam(value = "path", required = false) String path) {
		ModelAndView view = new ModelAndView("doc-list");
		String docPath = defaultDocPath;
		if (Objects.nonNull(path)) {
			docPath = path;
		}
		List<String> fileList = listFile("/", new File(docPath));

		return view.addObject("fileList", fileList);

	}

	/**
	 * 文件内容
	 * 
	 * @param pathName
	 * @return
	 */
	@RequestMapping("/api/file/detail")
	public ModelAndView fileDetail(@RequestParam("pathName") String pathName) {
		ModelAndView view = new ModelAndView("doc-detail");
		File file = new File(defaultDocPath + pathName);
		String fileDetail = "空";
		if (file.exists()) {
			if (file.getName().matches(".*(?i)pdf")) {
				fileDetail = ReadFileUtils.readPdf(file);
			} else {
				fileDetail = ReadFileUtils.readWord(file);
			}
		}
		view.addObject("fileName", file.getName());
		return view.addObject("fileDetail", fileDetail);

	}

	/**
	 * 遍历递归读取目录
	 * 
	 * @param parent
	 * @param file
	 * @return
	 */
	private List<String> listFile(String parent, File file) {
		List<String> fileList = new ArrayList<>();
		if (Objects.isNull(file) || !file.exists()) {
			return fileList;
		}
		if (file.isDirectory()) {
			File[] fList = file.listFiles();
			for (File f : fList) {
				fileList.addAll(listFile(file.getName(), f));
			}
		} else {
			fileList.add(parent + "/" + file.getName());
		}
		return fileList;
	}
}
