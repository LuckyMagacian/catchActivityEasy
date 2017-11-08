package net.imwork.yangyuanjian.catchactivity.impl.servlet;

import net.imwork.yangyuanjian.catchactivity.impl.assist.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by thunderobot on 2017/11/5.
 */
public class InitServlet extends HttpServlet{


    private static final long serialVersionUID = 1L;
    @Override
    public void init(ServletConfig config) throws ServletException {
        LogFactory.init();
    }

}
