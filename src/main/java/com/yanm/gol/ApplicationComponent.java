package com.yanm.gol;

import com.yanm.ApplicationContext;

public interface ApplicationComponent {

    void initComponent(ApplicationContext context);

    void initState(ApplicationContext context);

}
