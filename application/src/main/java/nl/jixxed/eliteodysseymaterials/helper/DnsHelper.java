package nl.jixxed.eliteodysseymaterials.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.io.UncheckedIOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DnsHelper {

    public static String resolveCname(final String fqdn) throws NamingException {
        return resolve(fqdn, "CNAME");
    }

    private static String resolve(final String fqdn, final String type) throws NamingException {
        final Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        final InitialDirContext idc = new InitialDirContext(p);
        final Attributes attrs = getAttributes(idc, fqdn, type);
        final Attribute attr = attrs.get(type);
        if (attr != null) {
            final String targetCName = attr.get(0).toString();
            return (targetCName.endsWith(".")) ? targetCName.substring(0, targetCName.length() - 1) : targetCName;
        } else {
            throw new IllegalArgumentException("No CNAME record found.");
        }
    }

    private static Attributes getAttributes(final InitialDirContext idc, final String fqdn, final String type) throws NamingException {
        Attributes attrs;
        try {
            attrs = idc.getAttributes(fqdn, new String[]{type});
        } catch (final UncheckedIOException ex) { //fallback to fixed dns
            attrs = idc.getAttributes("dns://1.1.1.1/" + fqdn, new String[]{type});
        }
        return attrs;
    }
}
