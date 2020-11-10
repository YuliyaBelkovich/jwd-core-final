package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        PropertyReaderUtil.loadProperties();
        PropertyReaderUtil.readProperties();

        final Supplier<ApplicationContext> applicationContextSupplier = NassaContext::getInstance; // todo
        final NassaContext nassaContext = NassaContext.getInstance();

        nassaContext.init();
        return applicationContextSupplier::get;
    }
}
