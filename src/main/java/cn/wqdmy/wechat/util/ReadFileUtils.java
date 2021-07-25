package cn.wqdmy.wechat.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件读取工具
 */
public final class ReadFileUtils {

	private static Logger log = LoggerFactory.getLogger(ReadFileUtils.class);

	/**
	 * 读取PDF文件内容
	 *
	 * @param pathName
	 * @return
	 */
	public static String readPdf(String pathName) {
		return readPdf(new File(pathName));
	}

	/**
	 * 读取PDF文件内容
	 *
	 * @param pdfFile
	 * @return
	 */
	public static String readPdf(File pdfFile) {
		if (!pdfFile.getName().matches(".+[.](?i)(pdf)$")) {
			return "文件类型错误.pdf";
		}
		try (PDDocument document = PDDocument.load(pdfFile)) {
			if (document.isEncrypted()) {
				log.error("文件内容加密：{}", pdfFile.getName());
			} else {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
				String pdfFileInText = tStripper.getText(document);
				return pdfFileInText;
			}
		} catch (InvalidPasswordException e) {
			log.error("文件读取密匙错误：{}", pdfFile.getName(), e);
		} catch (IOException e) {
			log.error("PDF文件读取失败：{}", pdfFile.getName(), e);
		}
		return "PDF文件读取失败";
	}

	/**
	 * 读取Word文件内容
	 *
	 * @param pathName
	 * @return
	 */
	public static String readWord(String pathName) {
		return readWord(new File(pathName));
	}

	/**
	 * 读取Word文件内容
	 *
	 * @param wordFile
	 * @return
	 */
	public static String readWord(File wordFile) {
		if (wordFile.getName().matches(".+[.](?i)doc$")) {
			try (FileInputStream fileStream = new FileInputStream(wordFile);
					WordExtractor wordExtractor = new WordExtractor(fileStream);) {
				return wordExtractor.getText();
			} catch (Exception e) {
				log.error("Word文件.doc读取失败：{}", wordFile.getName(), e);
			}

		} else if (wordFile.getName().matches(".+[.](?i)docx$")) {
			try (FileInputStream fileStream = new FileInputStream(wordFile);
					XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(fileStream));) {
				return extractor.getText();
			} catch (Exception e) {
				log.error("Word文件.docx读取失败：{}", wordFile.getName(), e);
			}

		} else {
			return "文件类型错误.doc|.docx";
		}

		return "Word文件读取失败";
	}
}
