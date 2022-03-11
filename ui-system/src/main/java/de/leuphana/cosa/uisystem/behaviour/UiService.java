package de.leuphana.cosa.uisystem.behaviour;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class UiService implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Starting UiService.");
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Unregistering UiService.");
    }
}
