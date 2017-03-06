package tmall.web.servlet;

import tmall.bean.Category;
import tmall.service.CategoryService;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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

    private CategoryService categoryService = new CategoryService();

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream in = super.parseUpload(request, params);
        String name = params.get("name");
        Category category = new Category(name);
        categoryService.add(category);

        // 获取根目录下的文件夹
        File imageFolder = new File(request.getServletContext().getRealPath("/imgs/category"));
        File imageFile = new File(imageFolder, category.getId() + ".jpg");
        try {
            if ((in != null && in.available() != 0)) {
                try (FileOutputStream out = new FileOutputStream(imageFile)){
                    byte[] buff = new byte[1024];
                    while (in.read(buff) != -1) {
                        out.write(buff);
                    }
                    out.flush();
                    BufferedImage img = ImageUtil.change2jpg(imageFile);
                    ImageIO.write(img, "jpg", imageFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
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
        List<Category> categoryList = categoryService.list(page.getStart(), page.getCount());
        int total = categoryService.getTotal();
        page.setTotal(total);
        request.setAttribute("clist", categoryList);
        request.setAttribute("page", page);
        return "/WEB-INF/jsp/admin/listCategory.jsp";
    }

}
