package org.fugerit.java.demo.unittestdemoapp;

import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocHelper {

    /*
     * FreemarkerDocProcessConfig is thread-safe and should be initialized once for each config file.
     *
     * Consider using a @ApplicationScoped or Singleton approach.
     */
    private FreemarkerDocProcessConfig docProcessConfig = FreemarkerDocProcessConfigFacade
            .loadConfigSafe("cl://unit-test-demo-app/fm-doc-process-config.xml");

    /**
     * Accessor for FreemarkerDocProcessConfig configuration.
     *
     * @return the FreemarkerDocProcessConfig instance associated with this helper.
     */
    public FreemarkerDocProcessConfig getDocProcessConfig() {
        return this.docProcessConfig;
    }

}
