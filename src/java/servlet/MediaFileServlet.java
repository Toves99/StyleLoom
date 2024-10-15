/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import javax.servlet.http.HttpServlet;
import service.serviceimpl.MediaFileServiceImpl;

/**
 *
 * @author cekesa
 */
public class MediaFileServlet extends HttpServlet{
    private final MediaFileService mediaFileService= new MediaFileServiceImpl();
    
}
