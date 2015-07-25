package adeady.benchmarks

import org.springframework.stereotype.Component

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
public class SimpleCorsFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request
        HttpServletResponse httpResponse = (HttpServletResponse) response

        httpResponse.addHeader("Access-Control-Allow-Origin", "*")

        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
        httpResponse.addHeader("Access-Control-Allow-Headers", "Content-Type")
        httpResponse.addHeader("Access-Control-Allow-Headers", "Accept")
        httpResponse.addHeader("Access-Control-Max-Age", "3000") // a long time


        chain.doFilter(request, response)
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}