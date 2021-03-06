package tmall.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;
import tmall.util.Page;

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
 * 创建日期: 17-3-4
 * 简介:
 */
@WebServlet(name = "categoryServlet", urlPatterns = "/categoryServlet")
public class CategoryServlet extends BaseBackServlet {

    private final String imagePath = "/imgs/category";
    private static final Logger logger = LoggerFactory.getLogger(CategoryServlet.class);


    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream in = super.parseUpload(request, params);
        String name = params.get("name");
        Category category = new Category(name);
        super.categoryService.add(category);
        // 获取根目录下的文件夹
        File imageFolder = new File(request.getServletContext().getRealPath(imagePath));
        File imageFile = new File(imageFolder, category.getId() + ".jpg");
        try {
            if (in != null && in.available() != 0) saveFile(imageFile, in);
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        super.categoryService.delete(id);
        File image = new File(new File(request.getServletContext().getRealPath(imagePath)), id + ".jpg");
        boolean result = image.delete();
        if (result) logger.info("删除分类 {} 及相关图片成功", id);
        else logger.warn("分类 {} 相关图片删除失败", id);
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("id"));
        Category category = super.categoryService.get(cid);
        request.setAttribute("c", category);
        return "/WEB-INF/jsp/admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream in = super.parseUpload(request, params);
        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");
        Category category = new Category(id, name);
        super.categoryService.update(category);
        // 获取根目录下的文件夹
        File imageFolder = new File(request.getServletContext().getRealPath("/imgs/category"));
        File imageFile = new File(imageFolder, category.getId() + ".jpg");
        try {
            if (in != null && in.available() != 0) super.saveFile(imageFile, in);
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> categoryList = super.categoryService.list(page.getStart(), page.getCount());
        int total = super.categoryService.getTotal();
        page.setTotal(total);
        request.setAttribute("clist", categoryList);
        request.setAttribute("page", page);
        return "/WEB-INF/jsp/admin/listCategory.jsp";
    }



}
