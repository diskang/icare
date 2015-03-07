package com.sjtu.icare.modules.sys.utils.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 无状态Subject实现类
 * @author jty
 * @version 2015-03-06
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {  
    public Subject createSubject(SubjectContext context) {  
        //不创建session  
//        context.setSessionCreationEnabled(false);  
        return super.createSubject(context);  
    }  
} 