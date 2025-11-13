package test.org.fugerit.java.demo.unittestdemoapp;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPInterface;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldif.LDIFReader;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestLdap {

    private static final InetAddress inetAddress = SafeFunction.get(InetAddress::getLocalHost);
    private static final String DOMAIN_DSN = "ou=users,dc=test,dc=it";

    private InMemoryDirectoryServer ldapServer;

    @BeforeEach
    void setUp() throws Exception {
        // Configurazione LDAP server in memoria
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(DOMAIN_DSN);

        // Bind su indirizzo specifico
        config.setListenerConfigs(
                InMemoryListenerConfig.createLDAPConfig(
                        "default",
                        InetAddress.getByName(inetAddress.getHostAddress()),
                        0, // porta casuale
                        null));

        ldapServer = new InMemoryDirectoryServer(config);

        // Importa dati dal file LDIF
        try (InputStream ldifStream = getClass().getClassLoader()
                .getResourceAsStream("ldap/ldap_user_db.ldif")) {

            if (ldifStream == null) {
                throw new IllegalStateException("File LDIF non trovato: ldap/ldap_user_db.ldif");
            }

            LDIFReader ldifReader = new LDIFReader(ldifStream);
            ldapServer.importFromLDIF(true, ldifReader);
            ldifReader.close();
        }

        ldapServer.startListening();
    }

    @AfterEach
    void tearDown() {
        if (ldapServer != null) {
            ldapServer.shutDown(true);
        }
    }

    @Test
    void testLdapInterface() throws LDAPException {
        final LDAPInterface ldapConnection = ldapServer.getConnection();
        final SearchResult searchResult = ldapConnection.search(
                DOMAIN_DSN,
                SearchScope.SUB,
                "(objectClass=person)");

        assertEquals(2, searchResult.getEntryCount());
    }

    // Helper method per ottenere porta e host se servono
    public int getLdapPort() {
        return ldapServer.getListenPort();
    }

    public String getLdapHost() {
        return inetAddress.getHostAddress();
    }
}