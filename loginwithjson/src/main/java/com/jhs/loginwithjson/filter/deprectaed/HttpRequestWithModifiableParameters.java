package com.jhs.loginwithjson.filter.deprectaed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestWithModifiableParameters extends HttpServletRequestWrapper {

    private Map<String, String[]> params;

    public HttpRequestWithModifiableParameters(HttpServletRequest request) {
        super(request);
        this.params = new HashMap<>(request.getParameterMap());
    }

    @Override
    public String getParameter(String name) {
        String returnValue = null;
        String[] paramArray = getParameterValues(name);
        if (paramArray != null && paramArray.length > 0) {
            returnValue = paramArray[0];
        }
        return returnValue;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;
        String[] temp = params.get(name);
        if (temp != null) {
            result = new String[temp.length];
            System.arraycopy(temp, 0, result, 0, temp.length);
        }
        return result;
    }

    public void setParameter(String name, String value) {
        params.put(name, new String[]{value});
    }
}
