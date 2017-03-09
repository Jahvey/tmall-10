package tmall.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.impl.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: wangxh
 * 创建日期: 17-3-8
 * 简介:
 */
@WebServlet(name = "productImageServlet", urlPatterns = "/productImageServlet")
public class ProductImageServlet extends BaseBackServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageServlet.class);

    // 这几行不能在构造器中初始化, 因为类初始化时init方法还没有初始化
    // 导致 request.getServletContext 为 null
    private File image_folder_single;
    private File image_folder_single_middle;
    private File getImage_folder_single_small;
    private File image_folder_detail;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        image_folder_single = new File(this.getServletContext().getRealPath("/imgs/productSingle"));
        image_folder_single_middle = new File(this.getServletContext().getRealPath(
            "/imgs/productSingle_middle"));
        getImage_folder_single_small = new File(this.getServletContext().getRealPath(
            "/imgs/productSingle_small"));
        image_folder_detail = new File(this.getServletContext().getRealPath("/imgs/productDetail"));
    }

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream in = super.parseUpload(request, params);
        int pid = Integer.parseInt(params.get("pid"));
        String type = params.get("type");
        File imageFolder;
        try {
            if (in != null && in.available() != 0) {
                // 如果有输入流则上传图片, 并计入数据库
                ProductImage productImage = new ProductImage(super.productService.get(pid),
                        type);
                super.productImageService.add(productImage);
                if (ProductImageDAO.TYPE_SINGLE.equals(type)) {
                    imageFolder = this.image_folder_single;
                } else {
                    imageFolder = this.image_folder_detail;
                }
                // 保存文件
                String fileName = pid + ".jpg";
                File f = new File(imageFolder, fileName);
                super.saveFile(f, in);
                if (ProductImageDAO.TYPE_SINGLE.equals(type)) {
                    File f_middle_folder = this.image_folder_single_middle;
                    File f_small_folder = this.getImage_folder_single_small;
                    File f_middle = new File(f_middle_folder, fileName);
                    File f_small = new File(f_small_folder, fileName);
                    // 复制中型和小型的图片个一份到指定目录
                    ImageUtil.resizeImage(f, 56, 56, f_small);
                    ImageUtil.resizeImage(f, 217, 190, f_middle);
                }
            } else logger.warn("没有获取到图片");
        } catch (IOException e) {
            logger.error("文件读取出错", e);
        }
        return "@admin_productImage_list?pid=" + pid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage productImage = super.productImageService.get(id);
        super.productImageService.delete(id);
        String fileName = productImage.getProduct().getId() + ".jpg";
        String type = productImage.getType();
        File image = null;
        if (ProductImageDAO.TYPE_SINGLE.equals(type)) {
            image = new File(this.image_folder_single, fileName);
            File imageMiddle = new File(this.image_folder_single_middle, fileName);
            File imageSmall = new File(this.getImage_folder_single_small, fileName);
            if (imageMiddle.exists() && image.delete()) logger.info("文件 {} 删除成功", imageMiddle);
            if (imageSmall.exists() && imageSmall.delete()) logger.info("文件 {} 删除成功", imageSmall);
        } else if (ProductImageDAO.TYPE_DETAIL.equals(type)){
            image = new File(this.image_folder_detail, fileName);
        }
        if (image != null && image.exists() && image.delete()) logger.info("文件 {} 删除成功", image);
        return "@admin_productImage_list?pid=" + productImage.getProduct().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = super.productService.get(pid);
        List<ProductImage> pisSingle = super.productImageService.list(product, ProductImageDAO.TYPE_SINGLE);
        List<ProductImage> pisDetail = super.productImageService.list(product, ProductImageDAO.TYPE_DETAIL);
        request.setAttribute("p", product);
        request.setAttribute("pisSingle", pisSingle);
        request.setAttribute("pisDetail", pisDetail);
        return "/WEB-INF/jsp/admin/listProductImage.jsp";
    }

}
