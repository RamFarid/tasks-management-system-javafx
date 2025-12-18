/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Mogaze
 */
public class FileManagerException extends RuntimeException {
    final public static String deplicateErrCode = "DEPLICATE_INDEX";
    private String code;
    public FileManagerException(String message, String code) {
        super(message);
        this.code = code;
    }
    public FileManagerException(String message) {
        super(message);
    }
}
