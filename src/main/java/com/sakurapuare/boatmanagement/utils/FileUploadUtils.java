package com.sakurapuare.boatmanagement.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 */
public class FileUploadUtils {

    /**
     * 允许的图片文件类型
     */
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    /**
     * 允许的文档文件类型
     */
    private static final List<String> ALLOWED_DOCUMENT_TYPES = Arrays.asList(
            "application/pdf", "application/msword", 
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );

    /**
     * 最大文件大小（10MB）
     */
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 基础上传路径
     */
    private static final String BASE_UPLOAD_PATH = "uploads";

    /**
     * 验证文件是否为允许的图片类型
     *
     * @param file 上传的文件
     * @return 是否为允许的图片类型
     */
    public static boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        return ALLOWED_IMAGE_TYPES.contains(file.getContentType());
    }

    /**
     * 验证文件是否为允许的文档类型
     *
     * @param file 上传的文件
     * @return 是否为允许的文档类型
     */
    public static boolean isValidDocumentFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        return ALLOWED_DOCUMENT_TYPES.contains(file.getContentType());
    }

    /**
     * 验证文件大小
     *
     * @param file 上传的文件
     * @return 文件大小是否在允许范围内
     */
    public static boolean isValidFileSize(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        return file.getSize() <= MAX_FILE_SIZE;
    }

    /**
     * 生成唯一的文件名
     *
     * @param originalFilename 原始文件名
     * @return 唯一的文件名
     */
    public static String generateUniqueFileName(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            return UUID.randomUUID().toString();
        }

        String extension = getFileExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        
        return timestamp + "_" + uuid + (extension.isEmpty() ? "" : "." + extension);
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名（不包含点）
     */
    public static String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    /**
     * 根据业务类型生成文件存储路径
     *
     * @param businessType 业务类型
     * @param filename     文件名
     * @return 文件存储路径
     */
    public static String generateFilePath(String businessType, String filename) {
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return BASE_UPLOAD_PATH + "/" + businessType.toLowerCase() + "/" + datePath + "/" + filename;
    }

    /**
     * 保存文件到指定路径
     *
     * @param file     上传的文件
     * @param filePath 文件保存路径
     * @throws IOException 文件保存失败时抛出
     */
    public static void saveFile(MultipartFile file, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        
        // 创建目录（如果不存在）
        Files.createDirectories(path.getParent());
        
        // 保存文件
        file.transferTo(path.toFile());
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return 文件是否存在
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * 获取文件大小的可读格式
     *
     * @param size 文件大小（字节）
     * @return 可读的文件大小格式
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0 B";
        
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }

    /**
     * 验证文件类型是否匹配业务类型
     *
     * @param file         上传的文件
     * @param businessType 业务类型
     * @return 是否匹配
     */
    public static boolean isValidFileForBusinessType(MultipartFile file, String businessType) {
        if (file == null || file.isEmpty() || businessType == null) {
            return false;
        }

        switch (businessType.toUpperCase()) {
            case "AVATAR":
            case "GOODS_IMAGE":
            case "BOAT_IMAGE":
                return isValidImageFile(file);
            case "CERT_FILE":
                return isValidDocumentFile(file) || isValidImageFile(file);
            case "OTHER":
                return isValidImageFile(file) || isValidDocumentFile(file);
            default:
                return false;
        }
    }

    /**
     * 获取完整的文件访问URL
     *
     * @param filePath 文件路径
     * @param baseUrl  基础URL
     * @return 完整的文件访问URL
     */
    public static String getFileAccessUrl(String filePath, String baseUrl) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        
        if (baseUrl == null || baseUrl.isEmpty()) {
            return filePath;
        }
        
        // 确保baseUrl以/结尾，filePath不以/开头
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        String normalizedFilePath = filePath.startsWith("/") ? filePath.substring(1) : filePath;
        
        return normalizedBaseUrl + normalizedFilePath;
    }
} 