package de.leuphana.cosa.componentservicebus.behaviour;

import org.junit.jupiter.api.*;
import org.osgi.framework.*;
import org.springframework.osgi.mock.MockBundleContext;
import org.springframework.osgi.mock.MockServiceReference;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComponentServiceBusTest {

    static ServiceReference reference;
    static BundleContext bundleContext;
    static Object service;

    static Bundle componentServiceBusBundle;

    @BeforeAll
    static void setUp() throws Exception {
        reference = new MockServiceReference();
        bundleContext = new MockBundleContext() {

            public ServiceReference getServiceReference(String clazz) {
                return reference;
            }

            public ServiceReference[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
                return new ServiceReference[]{reference};
            }

            public Object getService(ServiceReference ref) {
                if (reference == ref)
                    return service;
                return super.getService(ref);
            }
        };
    }

    @Test
    @Order(0)
    public void testOsgiEnvironment() throws Exception {
        Bundle[] bundles = bundleContext.getBundles();
        for (Bundle bundle : bundles) {
            System.out.print(bundle.getSymbolicName());
            System.out.print(", ");
        }
        System.out.println();
    }

//    @Test
//    @Order(1)
//    public void canBundlesBeInstalled() throws Exception {
//        componentServiceBusBundle = bundleContext.installBundle("mvn:de.leuphana.cosa/component-service-bus/1.0-SNAPSHOT");
//        Assertions.assertNotNull(componentServiceBusBundle);
//        Assertions.assertNotNull(bundleContext.installBundle("mvn:de.leuphana.cosa/route-system/1.0-SNAPSHOT"));
//        Assertions.assertNotNull(bundleContext.installBundle("mvn:de.leuphana.cosa/ui-system/1.0-SNAPSHOT"));
//        Assertions.assertNotNull(bundleContext.installBundle("mvn:de.leuphana.cosa/pricing-system/1.0-SNAPSHOT"));
//        Assertions.assertNotNull(bundleContext.installBundle("mvn:de.leuphana.cosa/document-system/1.0-SNAPSHOT"));
//        Assertions.assertNotNull(bundleContext.installBundle("mvn:de.leuphana.cosa/printing-system/1.0-SNAPSHOT"));
//        Assertions.assertNotNull(bundleContext.installBundle("mvn:de.leuphana.cosa/messaging-system/1.0-SNAPSHOT"));
//    }
}
