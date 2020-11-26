package de.dvdrental.repositories.interfaces;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * This class is used, so that the @Deployment function does not have to be implemented in every Testclass
 */
public abstract class RepositoryTest implements RepositoryTestInterface {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "de.dvdrental")
                .addAsResource("META-INF/persistence_test.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
