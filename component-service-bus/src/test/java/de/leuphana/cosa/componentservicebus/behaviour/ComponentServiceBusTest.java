package de.leuphana.cosa.componentservicebus.behaviour;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.mock.MockBundleContext;
import org.springframework.osgi.mock.MockServiceReference;

public class ComponentServiceBusTest {

//    static ServiceReference reference;
//    static BundleContext bundleContext;
//    static Object service;
//
//    @BeforeAll
//    static void setUp() throws Exception {
//        reference = new MockServiceReference();
//        bundleContext = new MockBundleContext() {
//
//            public ServiceReference getServiceReference(String clazz) {
//                return reference;
//            }
//
//            public ServiceReference[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
//                return new ServiceReference[] { reference };
//            }
//
//            public Object getService(ServiceReference ref) {
//                if (reference == ref)
//                    return service;
//                return super.getService(ref);
//            }
//        };
//    }
//
//    @Test
//    public void testOsgiEnvironment() throws Exception {
//        Bundle[] bundles = bundleContext.getBundles();
//        for (Bundle bundle : bundles) {
//            System.out.print(bundle.getSymbolicName());
//            System.out.print(", ");
//        }
//        System.out.println();
//    }

}
