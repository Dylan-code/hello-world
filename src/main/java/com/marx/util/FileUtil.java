package com.marx.util;

import com.marx.config.MarxConfig;
import com.marx.dao.TempFileMapper;
import com.marx.entity.TempFile;
import com.marx.service.TempFileService;
import com.marx.util.uuid.UUID;
import com.wobangkj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FileUtil {

    //项目所在路劲
    public final static String projectPath = System.getProperty("user.dir");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private TempFileService tempFileService;

    @Autowired
    private MarxConfig marxConfig;

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }

    /**
     * 获取文件大小
     *
     * @param b bytes
     * @return MB
     */
    public Long getFileSize(Long b) {
        return b / 1024 / 1024;
    }

    /**
     * 向数据库中添加临时文件路径信息
     *
     * @param tempFile 临时文件信息对象
     */
    public void addTempFile(TempFile tempFile) {
        tempFileService.insert(tempFile);
    }

    /**
     * 删除所有保存在数据库中的临时文件路径信息
     */
    public void deleteTempFile() {
        ((TempFileMapper) tempFileService.getDao()).deleteAllTempFile();
    }

    /**
     * 删除所有保存在磁盘中的临时文件
     */
    public void deleteDiskTempFile(List<TempFile> tempFiles) {
        for (TempFile tempFile : tempFiles) {
            FileUtils.delete(tempFile.getFileName());
        }
    }

    /**
     * 获取所有的临时文件路径信息
     */
    public List<TempFile> allTempFile() {
        TempFileMapper tempFileMapper = (TempFileMapper) tempFileService.getDao();
        return tempFileMapper.selectAll();
    }

    /**
     * 根据文件名删除对应保存在数据库中的临时文件信息
     *
     * @param FilePath 要删除的临时文件的相对路劲
     */
    public Integer deleteTempFileFromTable(String FilePath) {
        if (!FilePath.equals("") && FilePath != null) {
            TempFile tempFile = new TempFile(null, FilePath);
            return ((TempFileMapper) tempFileService).delete(tempFile);
        }
        return 0;
    }

    /**
     * 根据文件名字和路径信息删除磁盘上对应的文件
     *
     * @param FilePath 要删除的文件的相对路劲
     * @return 删除是否成功
     */
    public boolean deleteDiskFile(String FilePath) {
        if (FilePath == "" || FilePath == null) {
            return false;
        }
        //将磁盘中的文件删除
        return FileUtils.delete(FilePath);
    }

    /**
     * 将文件上传并保存在本地磁盘中
     *
     * @param file 对象
     * @return 保存成功返回文件所在的相对路劲
     */
    public String upload(MultipartFile file) {
        String upload = null;
        try {
            upload = FileUtils.upload(file, projectPath);
            //如果文件成功保存在磁盘中，则将该文件添加进临时文件库中
            TempFile tempFile = new TempFile(null, upload);
            tempFileService.insert(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return upload;
    }

    /**
     * 填充word文件里的内容    ${name} -->  沃邦
     */
    public String fillWord(MultipartFile file, Map<String, Object> map,HttpServletResponse response) throws IOException {
        boolean isChange = false;
        String oldValue = "";
        XWPFDocument document = new XWPFDocument(file.getInputStream());
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                oldValue = run.toString();
                String value = replaceKeyWord(oldValue, map);
                //如果value和原来的内容相比发生了变化，则说明有内容被替换了
                if (!value.equals(oldValue)) {
                    isChange = true;
                }
                run.setText(value, 0);
            }
        }
        //如果文本内容发生替换，则保存并将访问路径返回
        if (isChange) {
            return saveFillToResponse(document,response,file.getOriginalFilename()) ? "填充成功" : "填充失败";
        }
        return "填充失败";
    }

    /**
     * 将value中的关键字，以map中的内容为依据进行替换
     */
    public String replaceKeyWord(String value, Map<String, Object> map) {
        //获取该段文本中需要被填充的关键字  如：${name}
        List<String> keyWords = getKeyWords(value);
        //如果该段没有检测到关键字，则直接将内容返回
        if (keyWords.isEmpty()) {
            return value;
        }
        //遍历每一个关键字，然后将关键字替换为指定的内容
        for (String keyWord : keyWords) {
            String key = keyWord.replace("${", "").replace("}", "");
            String keyValue = (String) map.get(key);
            //如果数据库中没有指定该关键字的内容，则用""填充
            if (keyValue == null) {
                keyValue = "";
            }
            //进行替换
            value = value.replace(keyWord, keyValue);
        }
        return value;
    }

    /**
     * 提取文本中的关键字
     *
     * @param source 被提取的文本
     * @return 关键字集合
     */
    public List<String> getKeyWords(String source) {
        String regStr = "\\$\\{[a-zA-Z0-9]+\\}";
        List<String> matchStrs = new ArrayList<>();

        Pattern patten = Pattern.compile(regStr);
        Matcher matcher = patten.matcher(source);

        while (matcher.find()) {
            matchStrs.add(matcher.group());
        }

        return matchStrs;
    }

    /**
     * 将替换完成后的文档对象保存在磁盘中
     *
     * @param document 文档对象
     * @return 虚拟访问路径
     */
    public String saveFillFile(XWPFDocument document) {
        FileOutputStream out = null;
        ByteArrayOutputStream ostream = null;
        //用户访问用的虚拟路劲
        String path = "file" + File.separator + FORMATTER.format(LocalDate.now()) + File.separator + UUID.fastUUID() + ".docx";
        //物理保存路劲
        String filePath = projectPath + File.separator + path;
        System.out.println(filePath);
        File testFile = new File(filePath);
        File parentFile = testFile.getParentFile();
        try {
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!testFile.exists()) {
                testFile.createNewFile();
            }
            out = new FileOutputStream(testFile, true);
            ostream = new ByteArrayOutputStream();
            document.write(ostream);
            out.write(ostream.toByteArray());

            //如果文件成功保存在磁盘中，则将该文件添加进临时文件库中,后续自动删除
            tempFileService.insert(new TempFile(null, path));
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean saveFillToResponse(XWPFDocument document, HttpServletResponse response, String filename) {
        try {
            //response.setContentType("application/octet-stream");
            // 强制下载文件，不打开
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            document.write(ostream);
            response.getOutputStream().write(ostream.toByteArray());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
