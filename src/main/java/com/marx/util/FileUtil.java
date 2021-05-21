package com.marx.util;

import com.marx.config.MarxConfig;
import com.marx.dao.TempFileMapper;
import com.marx.entity.TempFile;
import com.marx.entity.User;
import com.marx.service.TempFileService;
import com.marx.util.uuid.UUID;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

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
     * @return
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
     * */
    public String fillWord(MultipartFile file,Map<String,Object> map) throws IOException {
        FileOutputStream out = null;
        ByteArrayOutputStream ostream = null;
        //用户访问用的虚拟路劲
        String path = "";
        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String value = run.toString();
                    //获取该段文本中需要被填充的关键字  如：${name}
                    List<String> keyWords = getKeyWords(value);
                    //遍历每一个关键字，然后将关键字替换为指定的内容
                    for (String keyWord : keyWords) {
                        String key = keyWord.replace("${", "").replace("}", "");
                        String keyValue = (String) map.get(key);
                        //如果数据库中没有指定该关键字的内容，则用""填充
                        if(keyValue == null){
                            keyValue = "";
                        }
                        //进行替换
                        value = value.replace(keyWord,keyValue);
                    }
                    System.out.println("value----------->"+value);
                    run.setText(value,0);
                }
            }

            //用户访问用的虚拟路劲
            path = "file"  +  File.separator + FORMATTER.format(LocalDate.now()) + File.separator + UUID.fastUUID() + ".docx";
            //物理保存路劲
            String filePath = projectPath + File.separator + path;
            System.out.println(filePath);
            File testFile = new File(filePath);
            File parentFile = testFile.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            if(!testFile.exists()){
                testFile.createNewFile();
            }
            out = new FileOutputStream(testFile,true);
            ostream = new ByteArrayOutputStream();
            document.write(ostream);
            out.write(ostream.toByteArray());

            //如果文件成功保存在磁盘中，则将该文件添加进临时文件库中,后续自动删除
            tempFileService.insert(new TempFile(null, path));

        } catch (IOException e) {
            log.warn("word填充异常："+e.getMessage());
        }
        if (out != null) {
            out.close();
        }
        if(ostream != null) {
            ostream.close();
        }
        return path;
    }

    /**
     * 获取一段字符串中需要填充的地方
     * */
    public List<String> getKeyWords(String source){
        String regStr = "\\$\\{[a-zA-Z0-9]+\\}";
        List<String> matchStrs = new ArrayList<>();

        Pattern patten = Pattern.compile(regStr);
        Matcher matcher = patten.matcher(source);

        while (matcher.find()) {
            matchStrs.add(matcher.group());
        }

        return matchStrs;
    }



    public static void main(String[] args) {
        User user = new User("admin", "123456");
        try {
            Map<String, Object> convert = BeanUtils.convert(user);
            String username = (String) convert.get("username");
            System.out.println(username);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
