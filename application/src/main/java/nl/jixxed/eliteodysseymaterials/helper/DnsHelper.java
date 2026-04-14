/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
